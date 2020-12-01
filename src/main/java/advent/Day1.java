package advent;

import org.apache.commons.io.IOUtils;
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class Day1
{
	public int part1(Reader input, int size) throws IOException
	{
		return Generator.combination(parse(input))
						.simple(size)
						.stream()
						.filter(v -> v.stream().mapToInt(Integer::intValue).sum() == 2020)
						.mapToInt(v -> v.stream().reduce((n1, n2) -> n1 * n2).get())
						.findFirst().getAsInt();
	}

	private List<Integer> parse(Reader input) throws IOException
	{
		return IOUtils.readLines(input)
						.stream()
						.map(Integer::parseInt)
						.collect(Collectors.toList());
	}
}
