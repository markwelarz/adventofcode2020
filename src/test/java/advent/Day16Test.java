package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day16Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						class: 1-3 or 5-7
						row: 6-11 or 33-44
						seat: 13-40 or 45-50

						your ticket:
						7,1,14

						nearby tickets:
						7,3,47
						40,4,50
						55,2,20
						38,6,12
						""".replace("\n", "\r\n");
		var answer = new Day16().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(71);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day16input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day16().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(23054L);
	}
	
	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day16input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day16().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(51240700105297L);
	}
}
