package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day17Test
{
	@Test
	public void part1CubeNeighbours()
	{
		assertThat(new Day17.CubePart1(100, 200, 300).neighbouring()).hasSize(26);
	}

	@Test
	public void part2CubeNeighbours()
	{
		assertThat(new Day17.HyperCube(100, 200, 300, 400).neighbouring()).hasSize(80);
	}

	@Test
	public void part1TestReadInitialState() throws Exception
	{
		var input = """
						.#.
						..#
						###
						""";
		var cubes = new Day17<Day17.CubePart1>().parseInitialState(CharSource.wrap(input), (x, y, z, notUsed) -> new Day17.CubePart1(x, y, z));
		assertThat(cubes).containsExactlyInAnyOrder(
						new Day17.CubePart1(1, 0, 0),
						new Day17.CubePart1(2, 1, 0),
						new Day17.CubePart1(0, 2, 0),
						new Day17.CubePart1(1, 2, 0),
						new Day17.CubePart1(2, 2, 0));
	}

	@Test
	public void part2TestReadInitialState() throws Exception
	{
		var input = """
						.#.
						..#
						###
						""";
		var cubes = new Day17<Day17.HyperCube>().parseInitialState(CharSource.wrap(input), Day17.HyperCube::new);
		assertThat(cubes).containsExactlyInAnyOrder(
						new Day17.HyperCube(1, 0, 0, 0),
						new Day17.HyperCube(2, 1, 0, 0),
						new Day17.HyperCube(0, 2, 0, 0),
						new Day17.HyperCube(1, 2, 0, 0),
						new Day17.HyperCube(2, 2, 0, 0));
	}

	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						.#.
						..#
						###
						""";
		long answer = Day17.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(112L);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day17input.txt").getFile(), Charsets.UTF_8);
		long answer = Day17.part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(237L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						.#.
						..#
						###
						""";
		long answer = Day17.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(848L);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day17input.txt").getFile(), Charsets.UTF_8);
		long answer = Day17.part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(2448L);
	}
}
