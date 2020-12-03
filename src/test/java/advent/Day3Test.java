package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						..##.......
						#...#...#..
						.#....#..#.
						..#.#...#.#
						.#...##..#.
						..#.##.....
						.#.#.#....#
						.#........#
						#.##...#...
						#...##....#
						.#..#...#.#""";

		var day3 = new Day3();
		long answer = day3.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(7);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day3input.txt").getFile(), Charsets.UTF_8);
		var day3 = new Day3();
		long answer = day3.part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(265L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						..##.......
						#...#...#..
						.#....#..#.
						..#.#...#.#
						.#...##..#.
						..#.##.....
						.#.#.#....#
						.#........#
						#.##...#...
						#...##....#
						.#..#...#.#""";

		var day3 = new Day3();
		long answer = day3.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(336);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day3input.txt").getFile(), Charsets.UTF_8);
		var day3 = new Day3();
		long answer = day3.part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(3154761400L);
	}
}
