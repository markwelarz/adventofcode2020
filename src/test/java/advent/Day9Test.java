package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						35
						20
						15
						25
						47
						40
						62
						55
						65
						95
						102
						117
						150
						182
						127
						219
						299
						277
						309
						576
						""";

		var answer = new Day9().part1(CharSource.wrap(input), 5);
		assertThat(answer).isEqualTo(127);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day9input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day9().part1(input, 25);
		System.out.println(answer);
		assertThat(answer).isEqualTo(105950735L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						35
						20
						15
						25
						47
						40
						62
						55
						65
						95
						102
						117
						150
						182
						127
						219
						299
						277
						309
						576
						""";
		var answer = new Day9().part2(CharSource.wrap(input), 5);
		assertThat(answer).isEqualTo(62);
	}

	@RepeatedTest(value = 100)
	public void part2SlowSolution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day9input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day9().part2(input, 25);
		System.out.println(answer);
		assertThat(answer).isEqualTo(13826915L);
	}

	@Test
	public void part2FastTest1() throws Exception
	{
		var input = """
						35
						20
						15
						25
						47
						40
						62
						55
						65
						95
						102
						117
						150
						182
						127
						219
						299
						277
						309
						576
						""";
		var answer = new Day9().part2PrefixSum(CharSource.wrap(input), 5);
		assertThat(answer).isEqualTo(62);
	}

	@RepeatedTest(value = 100)
	public void part2FastSolution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day9input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day9().part2PrefixSum(input, 25);
		System.out.println(answer);
		assertThat(answer).isEqualTo(13826915L);
	}
}
