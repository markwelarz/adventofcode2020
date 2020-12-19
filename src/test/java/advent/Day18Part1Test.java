package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day18Part1Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						1 + 2 * 3 + 4 * 5 + 6
						""".replace("\n", "\r\n");
		var answer = new Day18Part1().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(71);
	}

	@Test
	public void part1Test2() throws Exception
	{
		var input = """
						2 * 3 + (4 * 5)
						""".replace("\n", "\r\n");
		var answer = new Day18Part1().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(26);
	}

	@Test
	public void part1Test3() throws Exception
	{
		var input = """
						5 + (8 * 3 + 9 + 3 * 4 * 3)
						""".replace("\n", "\r\n");
		var answer = new Day18Part1().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(437);
	}

	@Test
	public void part1Test4() throws Exception
	{
		var input = """
						5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
						""".replace("\n", "\r\n");
		var answer = new Day18Part1().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(12240);
	}

	@Test
	public void part1Test5() throws Exception
	{
		var input = """
						((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
						""".replace("\n", "\r\n");
		var answer = new Day18Part1().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(13632);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day18input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day18Part1().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(12918250417632L);
	}
}
