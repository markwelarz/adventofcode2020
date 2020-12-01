package advent;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test
{
	@Test
	public void part1Test1() throws Exception
	{
		BitSet g;
		var input = """
						1721
						979
						366
						299
						675
						1456""";

		var day1 = new Day1();
		int answer = day1.part1(new StringReader(input), 2);
		assertThat(answer).isEqualTo(514579);
	}

	@Test
	public void part1Solution() throws Exception
	{
		var day1 = new Day1();
		var input = new InputStreamReader(new ClassPathResource("advent/day1input.txt").getInputStream(), StandardCharsets.UTF_8);
		int answer = day1.part1(input, 2);

		System.out.println(answer);
		assertThat(answer).isEqualTo(357504);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						1721
						979
						366
						299
						675
						1456""";

		var day1 = new Day1();
		int answer = day1.part1(new StringReader(input), 3);
		assertThat(answer).isEqualTo(241861950);
	}

	@Test
	public void part2Solution() throws Exception
	{
		var day1 = new Day1();
		var input = new InputStreamReader(new ClassPathResource("advent/day1input.txt").getInputStream(), StandardCharsets.UTF_8);
		int answer = day1.part1(input, 3);

		System.out.println(answer);
		assertThat(answer).isEqualTo(12747392);
	}
}
