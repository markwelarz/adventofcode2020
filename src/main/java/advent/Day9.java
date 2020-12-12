package advent;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.io.CharSource;
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

public class Day9
{
	public long part1(CharSource input, int windowSize) throws IOException
	{
		List<Long> numberSequence = input.lines()
						.map(Long::parseLong)
						.collect(Collectors.toList());

		return StreamUtils.zip(StreamUtils.windowed(numberSequence.stream(), windowSize),
						numberSequence.stream().skip(windowSize), this::isSameAsASumOfPrevious)
						.filter(v -> v > 0)
						.findFirst().get();
	}

	private Long isSameAsASumOfPrevious(List<Long> list, long newNumber)
	{
		return Generator.combination(list).simple(2).stream()
						.anyMatch(v -> v.get(0) + v.get(1) == newNumber) ? 0 : newNumber;
	}

	public long part2(CharSource input, int windowSize) throws IOException
	{
		List<Long> numberSequence = input.lines()
						.map(Long::parseLong)
						.collect(Collectors.toList());

		long invalidNumber = part1(input, windowSize);

		for (int searchWindowSize = numberSequence.size(); searchWindowSize > 1; searchWindowSize--)
		{
			LongSummaryStatistics sumMinMax = StreamUtils.windowed(numberSequence.stream(), searchWindowSize)
							.filter(v -> isSum(v, invalidNumber))
							.flatMap(Collection::stream)
							.collect(Collectors.summarizingLong(v -> v));
			if (sumMinMax.getCount() > 0)
			{
				return sumMinMax.getMax() + sumMinMax.getMin();
			}
		}

		throw new IllegalArgumentException("could not find " + invalidNumber);
	}

	public long part2PrefixSum(CharSource input, int windowSize) throws IOException
	{
		List<Long> numberSequence = input.lines()
						.map(Long::parseLong)
						.collect(Collectors.toList());

		List<Long> prefixSum = prefixSum(numberSequence);
		long invalidNumber = part1(input, windowSize);

		for (int searchWindowSize = numberSequence.size(); searchWindowSize > 2; searchWindowSize--)
		{
			for (int start = 0; start + searchWindowSize < numberSequence.size(); start++)
			{
				if (isSumFaster(prefixSum, numberSequence, start, searchWindowSize, invalidNumber))
				{
					LongSummaryStatistics sumMinMax = numberSequence.subList(start, start + searchWindowSize).stream()
									.collect(Collectors.summarizingLong(v -> v));
					return sumMinMax.getMax() + sumMinMax.getMin();
				}
			}
		}

		throw new IllegalArgumentException("could not find " + invalidNumber);
	}

	private boolean isSumFaster(List<Long> prefixSum, List<Long> numberSequence, int from, int length, long wanted)
	{
		long sum = prefixSum.get(from + length - 1) - prefixSum.get(from);
		return sum == wanted;
	}

	private List<Long> prefixSum(List<Long> numberSequence)
	{
		List<Long> prefixSum = new ArrayList<>();
		prefixSum.add(numberSequence.get(0));
		for (int i = 1; i < numberSequence.size(); i++)
			prefixSum.add(prefixSum.get(i - 1) + numberSequence.get(i));
		return prefixSum;
	}

	private boolean isSum(List<Long> sumThis, long wanted)
	{
		long s = sumThis.stream().mapToLong(v -> v).sum();
		return s == wanted;
	}
}
