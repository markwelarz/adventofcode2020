package advent;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;
import org.apache.commons.lang3.StringUtils;
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14
{
	public long part1(CharSource input) throws IOException
	{
		return input.readLines(new FerryPart1());
	}

	public long part2(CharSource input) throws IOException
	{
		return input.readLines(new FerryPart2());
	}

	class FerryPart1 implements LineProcessor<Long>
	{
		private Map<Integer, Long> memory = new HashMap<>();
		private long clearMask = 0;
		private long setMask = 0;

		@Override
		public boolean processLine(String line) throws IOException
		{
			List<String> split = Splitter.on(CharMatcher.anyOf("="))
							.trimResults()
							.omitEmptyStrings()
							.splitToList(line);
			if (split.get(0).equals("mask"))
			{
				clearMask = Long.parseLong(StringUtils.replaceChars(split.get(1), "X", "1"), 2);
				setMask = Long.parseLong(StringUtils.replaceChars(split.get(1), "X", "0"), 2);
			}
			else if (split.get(0).startsWith("mem"))
			{
				int location = Integer.parseInt(StringUtils.substringBetween(split.get(0), "[", "]"));
				long newValue = Long.parseLong(split.get(1));

				newValue &= clearMask;
				newValue |= setMask;

				memory.put(location, newValue);
			}
			else
			{
				assert false : split;
			}

			return true;
		}

		@Override
		public Long getResult()
		{
			memory.forEach((k, v) -> System.out.println(k + " " + v));
			return memory.values().stream().mapToLong(v -> v).sum();
		}
	}

	class FerryPart2 implements LineProcessor<Long>
	{
		private Map<Long, Long> memory = new HashMap<>();
		private long clearFloatingBitsMask;
		private long applyMask;
		private List<Long> floatingMasks = new ArrayList<>();

		@Override
		public boolean processLine(String line) throws IOException
		{
			List<String> split = Splitter.on(CharMatcher.anyOf("="))
							.trimResults()
							.omitEmptyStrings()
							.splitToList(line);
			if (split.get(0).equals("mask"))
			{
				System.out.println("new mask: " + split.get(1));
				clearFloatingBitsMask = Long.parseLong(StringUtils.replaceChars(CharMatcher.anyOf("0").replaceFrom(split.get(1), '1'), "X", "0"), 2);
				applyMask = Long.parseLong(StringUtils.replaceChars(split.get(1), "X", "0"), 2);
				int numBitsToReplace = StringUtils.countMatches(split.get(1), 'X');
				floatingMasks = Generator.permutation(0, 1).withRepetitions(numBitsToReplace).stream()
								.map(v -> v.stream().map(v1 -> Character.forDigit(v1, 10)).collect(Collectors.toList()))
								.map(v -> overlayX(split.get(1), v))
								.map(v -> Long.parseLong(v, 2))
								.collect(Collectors.toList());

				System.out.println("these masks will now be applied:");
				floatingMasks.forEach(v -> System.out.println(StringUtils.leftPad(Long.toString(v, 2), 7, "0") + " " + v));
			}
			else if (split.get(0).startsWith("mem"))
			{
				long location = Long.parseLong(StringUtils.substringBetween(split.get(0), "[", "]"));
				long newValue = Long.parseLong(split.get(1));

				for (long floatingMask : floatingMasks)
				{
					long maskedLocation = location & clearFloatingBitsMask;
					maskedLocation |= floatingMask;
					maskedLocation |= applyMask;
					System.out.println("setting : " + newValue + " at location" + StringUtils.leftPad(Long.toString(maskedLocation, 2), 7, "0") + " " + maskedLocation);

					memory.put(maskedLocation, newValue);
				}
			}
			else
			{
				assert false : split;
			}

			return true;
		}

		@Override
		public Long getResult()
		{
			memory.forEach((k, v) -> System.out.println(k + " " + v));
			return memory.values().stream().mapToLong(v -> v).sum();
		}

		private String overlayX(String maskTemplate, List<Character> bits)
		{
			Iterator<Character> nextBit = bits.iterator();
			StringBuilder stringBuilder = new StringBuilder(maskTemplate);
			for (int i = 0; i < stringBuilder.length(); i++)
			{
				if (stringBuilder.charAt(i) == 'X')
				{
					stringBuilder.setCharAt(i, nextBit.next());
				}
				else
				{
					stringBuilder.setCharAt(i, '0');
				}
			}

			return stringBuilder.toString();
		}
	}
}
