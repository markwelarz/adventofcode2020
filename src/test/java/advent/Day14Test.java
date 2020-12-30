package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
						mem[8] = 11
						mem[7] = 101
						mem[8] = 0
						""".replace("\n", "\r\n");
		var day14 = new Day14();
		var answer = day14.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(165);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day14input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day14().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(6513443633260L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						mask = 000000000000000000000000000000X1001X
						mem[42] = 100
						mask = 00000000000000000000000000000000X0XX
						mem[26] = 1
						""".replace("\n", "\r\n");
		var day14 = new Day14();
		var answer = day14.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(208);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day14input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day14().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(3442819875191L);
	}
}
