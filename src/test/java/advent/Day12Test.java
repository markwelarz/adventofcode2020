package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						F10
						N3
						F7
						R90
						F11
						""".replace("\n", "\r\n");
		var day12 = new Day12();
		var answer = day12.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(25);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day12input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day12().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(882L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						F10
						N3
						F7
						R90
						F11
						""".replace("\n", "\r\n");
		var day12 = new Day12();
		var answer = day12.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(286);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day12input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day12().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(28885L);
	}
}
