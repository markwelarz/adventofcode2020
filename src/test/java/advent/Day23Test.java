package advent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day23Test
{
	@Test
	public void part1Test1()
	{
		var answer = new Day23().part1("389125467", 10);
		assertThat(answer).isEqualTo("92658374");
	}

	@Test
	public void part1Test2()
	{
		var answer = new Day23().part1("389125467", 100);
		assertThat(answer).isEqualTo("67384529");
	}

	@Test
	public void part1Solution()
	{
		var answer = new Day23().part1("186524973", 100);
		System.out.println("answer " + answer);
		assertThat(answer).isEqualTo("45983627");
	}

	@Test
	public void part2Test1()
	{
		var answer = new Day23().part2("389125467", 10_000_000);
		assertThat(answer).isEqualTo(149245887792L);
	}

	@Test
	public void part2Solution()
	{
		var answer = new Day23().part2("186524973", 10_000_000);
		System.out.println("answer " + answer);
		assertThat(answer).isEqualTo(111080192688L);
	}
}
