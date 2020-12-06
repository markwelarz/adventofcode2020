package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Day6Test
{
	@Test
	public void part1Test1() throws IOException
	{
		var input = """
						abcx
						abcy
						abcz
						""".replace("\n", "\r\n");
		var answer = new Day6().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(6);
	}

	@Test
	public void part1Test2() throws IOException
	{
		var input = """
						abc

						a
						b
						c

						ab
						ac

						a
						a
						a
						a

						b
						""".replace("\n", "\r\n");
		var answer = new Day6().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(11);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day6input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day6().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(6506L);
	}

	@Test
	public void part2Test1() throws IOException
	{
		var input = """
						abc

						a
						b
						c

						ab
						ac

						a
						a
						a
						a

						b      
						   """.replace("\n", "\r\n");
		var answer = new Day6().part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(6L);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day6input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day6().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(3243L);
	}
}
