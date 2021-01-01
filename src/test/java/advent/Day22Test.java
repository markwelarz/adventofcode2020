package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day22Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						Player 1:
						9
						2
						6
						3
						1

						Player 2:
						5
						8
						4
						7
						10      
						""".replace("\n", "\r\n");
		var answer = new Day22().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(306);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day22input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day22().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(32472L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						Player 1:
						9
						2
						6
						3
						1

						Player 2:
						5
						8
						4
						7
						10      
						""".replace("\n", "\r\n");
		var answer = new Day22().part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(291);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day22input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day22().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(36463L);
	}
}
