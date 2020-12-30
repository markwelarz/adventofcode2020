package advent;

import com.google.common.base.Splitter;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day15
{
	public long part1(CharSource input) throws IOException
	{
		LinkedList<Integer> spokenNumbers = new LinkedList<>();
		Splitter.on(",").splitToStream(input.read())
						.mapToInt(Integer::parseInt)
						.forEach(spokenNumbers::add);

		int turn = spokenNumbers.size() + 1;
		while (turn <= 2020)
		{
			int last = spokenNumbers.getLast();
			int lastIndex = spokenNumbers.size() - 1;
			int secondLastIndex = spokenNumbers.subList(0, spokenNumbers.size() - 1).lastIndexOf(last);

			if (secondLastIndex == -1)
				spokenNumbers.add(0);
			else
				spokenNumbers.add(lastIndex - secondLastIndex);

			turn++;
		}

		return spokenNumbers.getLast();
	}

	public long part2(CharSource input) throws IOException
	{
		Map<Integer, Integer> postionOfSpokenNumbers = new HashMap<>();
		int[] initialNumbers = Splitter.on(",").splitToStream(input.read())
						.mapToInt(Integer::parseInt).toArray();

		for (int i = 0; i < initialNumbers.length - 1; i++)
		{
			postionOfSpokenNumbers.put(initialNumbers[i], i + 1);
		}

		int lastSpoken = initialNumbers[initialNumbers.length - 1];

		for (int turn = initialNumbers.length; turn < 30_000_000; turn++)
		{
			Integer lastSpokenIndex = postionOfSpokenNumbers.getOrDefault(lastSpoken, -1);

			postionOfSpokenNumbers.put(lastSpoken, turn);

			if (lastSpokenIndex == -1)
				lastSpoken = 0;
			else
				lastSpoken = turn - lastSpokenIndex;
		}

		return lastSpoken;
	}
}
