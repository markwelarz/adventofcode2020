package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						nop +0
						acc +1
						jmp +4
						acc +3
						jmp -3
						acc -99
						acc +1
						jmp -4
						acc +6 
						""";
		var answer = new Day8().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(5);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day8input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day8().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(1501);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						nop +0
						acc +1
						jmp +4
						acc +3
						jmp -3
						acc -99
						acc +1
						jmp -4
						acc +6
						""";
		var answer = new Day8().part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(8);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day8input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day8().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(509);
	}
}
