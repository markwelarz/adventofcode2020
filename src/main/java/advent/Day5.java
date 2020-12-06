package advent;

import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5
{
	public long part1(CharSource input) throws IOException
	{
		return input.readLines()
						.stream()
						.map(this::convertToBinaryString)
						.map(v -> Splitter.fixedLength(7).splitToList(v))
						.map(v -> List.of(Integer.parseInt(v.get(0), 2), Integer.parseInt(v.get(1), 2)))
						.mapToInt(v -> v.get(0) * 8 + v.get(1))
						.max()
						.getAsInt();
	}

	private String convertToBinaryString(String code)
	{
		return StringUtils.replaceChars(code, "BFRL", "1010");
	}

	public int part2(CharSource input) throws IOException
	{
		Deque<Integer> seenSeatIds = input.readLines()
						.stream()
						.map(this::convertToBinaryString)
						.map(v -> Splitter.fixedLength(7).splitToList(v))
						.map(v -> List.of(Integer.parseInt(v.get(0), 2), Integer.parseInt(v.get(1), 2)))
						.map(v -> v.get(0) * 8 + v.get(1))
						.sorted()
						.collect(Collectors.toCollection(LinkedList::new));

		int firstSeat = seenSeatIds.getFirst();
		int lastSeat = seenSeatIds.getLast();

		int expectedSum = IntStream.rangeClosed(firstSeat, lastSeat).sum();
		int actualSum = seenSeatIds.stream().mapToInt(v -> v).sum();

		return expectedSum - actualSum;
	}
}
