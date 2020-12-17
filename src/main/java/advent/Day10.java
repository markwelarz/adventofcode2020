package advent;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10
{
	public long part1(CharSource input) throws IOException
	{
		List<Long> sorted = input.lines()
						.map(Long::parseLong)
						.sorted()
						.collect(Collectors.toList());

		long jolt1Diff = 0;
		long jolt3Diff = 0;

		if (sorted.get(0) == 1)
			jolt1Diff++;
		else if (sorted.get(0) == 3)
			jolt3Diff++;

		for (int i = 1; i < sorted.size(); i++)
		{
			long diff = sorted.get(i) - sorted.get(i - 1);
			if (diff == 1)
				jolt1Diff++;
			else if (diff == 3)
				jolt3Diff++;
		}

		jolt3Diff++;

		return jolt1Diff * jolt3Diff;
	}

	private long LARGEST;

	public long part2(CharSource input) throws IOException
	{
		Adapter joltAdapterArray = new Day10().buildTree(input);
		long answer = joltAdapterArray.countArrangements("", 0, 0);
		return answer;
	}

	private Adapter buildTree(CharSource input) throws IOException
	{
		List<Adapter> sequence = input.lines()
						.map(Long::parseLong)
						.map(v -> new JoltAdapter(v, new ArrayList<>()))
						.collect(Collectors.toList());

		LARGEST = input.lines().mapToLong(Long::parseLong).max().getAsLong();

		sequence.add(new JoltAdapter(0, new ArrayList<>()));
		sequence.add(new MyDevice(LARGEST + 3));

		Map<Long, Adapter> mapping = Maps.uniqueIndex(sequence, Adapter::rating);
		for (Map.Entry<Long, Adapter> e : Maps.filterValues(mapping, Predicates.instanceOf(JoltAdapter.class)).entrySet())
		{
			long rating = e.getKey();
			JoltAdapter joltAdapter = (JoltAdapter) e.getValue();
			if (mapping.containsKey(rating + 1))
				joltAdapter.next().add(mapping.get(rating + 1));
			if (mapping.containsKey(rating + 2))
				joltAdapter.next().add(mapping.get(rating + 2));
			if (mapping.containsKey(rating + 3))
				joltAdapter.next().add(mapping.get(rating + 3));
		}

		return mapping.get(0L);
	}

	interface Adapter
	{
		long countArrangements(String arrangementsDebug, long totalSoFar, long previousAdapterRating);

		long rating();
	}

	class JoltAdapter implements Adapter
	{
		private long rating;
		private List<Adapter> nextOptions;
		private Map<Long, Long> alreadyAnswered = new HashMap<>();

		JoltAdapter(long rating, List<Adapter> nextOptions)
		{
			this.rating = rating;
			this.nextOptions = nextOptions;
		}

		@Override
		public long countArrangements(String arrangementsDebug, long totalSoFar, long previousAdapterRating)
		{
			if (alreadyAnswered.containsKey(previousAdapterRating))
				return alreadyAnswered.get(previousAdapterRating);

			long answer = 0;

			arrangementsDebug = arrangementsDebug + "," + rating;
			totalSoFar += (this.rating - previousAdapterRating);

			for (Adapter nis : nextOptions)
			{
				long downstreamArrangementCount = nis.countArrangements(arrangementsDebug, totalSoFar, this.rating);
				if (downstreamArrangementCount > 0)
					answer += downstreamArrangementCount;
			}

			alreadyAnswered.put(previousAdapterRating, answer);

			return answer;
		}

		public List<Adapter> next()
		{
			return nextOptions;
		}

		@Override
		public long rating()
		{
			return rating;
		}
	}

	class MyDevice implements Adapter
	{
		private long rating;

		public MyDevice(long rating)
		{
			this.rating = rating;
		}

		@Override
		public long countArrangements(String arrangementsDebug, long totalSoFar, long previousAdapterRating)
		{
			return 1;
		}

		@Override
		public long rating()
		{
			return rating;
		}
	}
}
