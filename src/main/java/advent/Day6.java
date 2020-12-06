package advent;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6
{
	public long part1(CharSource input) throws IOException
	{
		return Splitter.on("\r\n\r\n")
						.splitToStream(input.read())
						.mapToLong(this::countDistinctQuestionsInGroup)
						.sum();
	}

	public long part2(CharSource input) throws IOException
	{
		return Splitter.on("\r\n\r\n")
						.splitToStream(input.read())
						.mapToLong(this::countOverlappingQuestionsInGroup)
						.sum();
	}

	private long countDistinctQuestionsInGroup(String input)
	{
		return CharMatcher.whitespace()
						.removeFrom(input)
						.chars()
						.distinct()
						.count();
	}

	private long countOverlappingQuestionsInGroup(String input)
	{
		return input.lines()
						.map(v -> v.chars().boxed().collect(Collectors.toSet()))
						.reduce(Sets::intersection)
						.map(Set::size)
						.get();
	}

}
