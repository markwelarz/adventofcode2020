package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						16
						10
						15
						5
						1
						11
						7
						19
						6
						12
						4
						""";
		var answer = new Day10().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(35);
	}

	@Test
	public void part1Test2() throws Exception
	{
		var input = """
						28
						33
						18
						42
						31
						14
						46
						20
						48
						47
						24
						23
						49
						45
						19
						38
						39
						11
						1
						32
						25
						35
						8
						17
						7
						9
						4
						2
						34
						10
						3
						""";
		var answer = new Day10().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(220);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day10input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day10().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(2470L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						16
						10
						15
						5
						1
						11
						7
						19
						6
						12
						4
						""";
		long answer = new Day10().part2(CharSource.wrap(input));
		System.out.println(answer);
		assertThat(answer).isEqualTo(8L);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day10input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day10().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(1973822685184L);
	}
}
