package advent;

import com.google.common.io.CharSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day15Test
{
	@Test
	public void part1Test0() throws Exception
	{
		var input = "0,3,6";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
	}

	@Test
	public void part1Test1() throws Exception
	{
		var input = "1,3,2";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(1);
	}

	@Test
	public void part1Test2() throws Exception
	{
		var input = "2,1,3";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(10);
	}

	@Test
	public void part1Test3() throws Exception
	{
		var input = "1,2,3";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(27);
	}

	@Test
	public void part1Test4() throws Exception
	{
		var input = "2,3,1";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(78);
	}

	@Test
	public void part1Test5() throws Exception
	{
		var input = "3,2,1";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(438);
	}

	@Test
	public void part1Test6() throws Exception
	{
		var input = "3,1,2";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(1836);
	}

	@Test
	public void part1Solution() throws Exception
	{
		var input = "11,18,0,20,1,7,16";
		var day15 = new Day15();
		var answer = day15.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(639L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = "0,3,6";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(175594);
	}

	@Test
	public void part2Test2() throws Exception
	{
		var input = "1,3,2";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(2578);
	}

	@Test
	public void part2Test3() throws Exception
	{
		var input = "2,1,3";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(3544142);
	}

	@Test
	public void part2Test4() throws Exception
	{
		var input = "1,2,3";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(261214);
	}

	@Test
	public void part2Test5() throws Exception
	{
		var input = "2,3,1";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(6895259);
	}

	@Test
	public void part2Test6() throws Exception
	{
		var input = "3,2,1";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(18);
	}

	@Test
	public void part2Test7() throws Exception
	{
		var input = "3,1,2";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(362);
	}

	@Test
	public void part2Solution() throws Exception
	{
		var input = "11,18,0,20,1,7,16";
		var day15 = new Day15();
		var answer = day15.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(266L);
	}
}
