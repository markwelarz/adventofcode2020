package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5Test
{
	@Test
	public void part1Test1() throws IOException
	{
		var answer = new Day5().part1(CharSource.wrap("FBFBBFFRLR"));
		assertThat(answer).isEqualTo(357);
	}

	@Test
	public void part1Test2() throws IOException
	{
		var answer = new Day5().part1(CharSource.wrap("BFFFBBFRRR"));
		assertThat(answer).isEqualTo(567);
	}

	@Test
	public void part1Test3() throws IOException
	{
		var answer = new Day5().part1(CharSource.wrap("FFFBBBFRRR"));
		assertThat(answer).isEqualTo(119);
	}

	@Test
	public void part1Test4() throws IOException
	{
		var answer = new Day5().part1(CharSource.wrap("BBFFBBFRLL"));
		assertThat(answer).isEqualTo(820);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day5input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day5().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(850L);
	}

	@Test
	public void part2Solution() throws IOException
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day5input.txt").getFile(), Charsets.UTF_8);
		int answer = new Day5().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(599);
	}
}
