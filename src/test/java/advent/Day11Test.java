package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test
{
	@Test
	public void testAdjacentMethodsPart1_1()
	{
		var input = """
						L.LL.LL.LL
						LLLLLLL.LL
						L.L.L.L.LL     
						""";

		SeatingArea seatingArea = new SeatingAreaPart1(input);
		assertThat(seatingArea.belowRightOccupied(0)).isFalse();
		assertThat(seatingArea.belowRightOccupied(5)).isFalse();
		assertThat(seatingArea.belowRightOccupied(9)).isFalse();
		assertThat(seatingArea.belowRightOccupied(10)).isFalse();
		assertThat(seatingArea.belowRightOccupied(19)).isFalse();
		assertThat(seatingArea.belowRightOccupied(20)).isFalse();
		assertThat(seatingArea.belowRightOccupied(23)).isFalse();
		assertThat(seatingArea.belowRightOccupied(29)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(0)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(1)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(7)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(19)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(23)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(29)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(0)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(1)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(9)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(10)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(11)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(19)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(0)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(8)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(9)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(10)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(18)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(20)).isFalse();
		assertThat(seatingArea.leftOccupied(0)).isFalse();
		assertThat(seatingArea.leftOccupied(1)).isFalse();
		assertThat(seatingArea.leftOccupied(9)).isFalse();
		assertThat(seatingArea.leftOccupied(10)).isFalse();
		assertThat(seatingArea.leftOccupied(20)).isFalse();
		assertThat(seatingArea.aboveOccupied(3)).isFalse();
		assertThat(seatingArea.aboveOccupied(9)).isFalse();
		assertThat(seatingArea.aboveOccupied(10)).isFalse();
		assertThat(seatingArea.aboveOccupied(11)).isFalse();
		assertThat(seatingArea.belowOccupied(1)).isFalse();
		assertThat(seatingArea.belowOccupied(10)).isFalse();
		assertThat(seatingArea.belowOccupied(20)).isFalse();
		assertThat(seatingArea.belowOccupied(25)).isFalse();
		assertThat(seatingArea.belowOccupied(29)).isFalse();
		assertThat(seatingArea.rightOccupied(0)).isFalse();
		assertThat(seatingArea.rightOccupied(9)).isFalse();
		assertThat(seatingArea.rightOccupied(10)).isFalse();
		assertThat(seatingArea.rightOccupied(20)).isFalse();
		assertThat(seatingArea.rightOccupied(29)).isFalse();
	}

	@Test
	public void testAdjacentMethodsRight()
	{
		var input = """
						L.LL.LL.##
						#LLLLLL.##
						#.L.L.L.LL     
						""";

		SeatingArea seatingArea = new SeatingAreaPart1(input);
		assertThat(seatingArea.leftOccupied(9)).isTrue();
		assertThat(seatingArea.rightOccupied(9)).isFalse();
		assertThat(seatingArea.aboveOccupied(9)).isFalse();
		assertThat(seatingArea.belowOccupied(9)).isTrue();
		assertThat(seatingArea.aboveLeftOccupied(9)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(9)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(9)).isTrue();
		assertThat(seatingArea.belowRightOccupied(9)).isFalse();

		assertThat(seatingArea.leftOccupied(19)).isTrue();
		assertThat(seatingArea.rightOccupied(19)).isFalse();
		assertThat(seatingArea.aboveOccupied(19)).isTrue();
		assertThat(seatingArea.belowOccupied(19)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(19)).isTrue();
		assertThat(seatingArea.aboveRightOccupied(19)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(19)).isFalse();
		assertThat(seatingArea.belowRightOccupied(19)).isFalse();
	}

	@Test
	public void testAdjacentMethodsLeft()
	{
		var input = """
						L.LL.LL.##
						#LLLLLL.##
						#.L.L.L.LL     
						""";

		SeatingArea seatingArea = new SeatingAreaPart1(input);
		assertThat(seatingArea.leftOccupied(0)).isFalse();
		assertThat(seatingArea.rightOccupied(0)).isFalse();
		assertThat(seatingArea.aboveOccupied(0)).isFalse();
		assertThat(seatingArea.belowOccupied(0)).isTrue();
		assertThat(seatingArea.aboveLeftOccupied(0)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(0)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(0)).isFalse();
		assertThat(seatingArea.belowRightOccupied(0)).isFalse();

		assertThat(seatingArea.leftOccupied(10)).isFalse();
		assertThat(seatingArea.rightOccupied(10)).isFalse();
		assertThat(seatingArea.aboveOccupied(10)).isFalse();
		assertThat(seatingArea.belowOccupied(10)).isTrue();
		assertThat(seatingArea.aboveLeftOccupied(10)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(10)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(10)).isFalse();
		assertThat(seatingArea.belowRightOccupied(10)).isFalse();

		assertThat(seatingArea.leftOccupied(20)).isFalse();
		assertThat(seatingArea.rightOccupied(20)).isFalse();
		assertThat(seatingArea.aboveOccupied(20)).isTrue();
		assertThat(seatingArea.belowOccupied(20)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(20)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(20)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(20)).isFalse();
		assertThat(seatingArea.belowRightOccupied(20)).isFalse();
	}

	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						L.LL.LL.LL
						LLLLLLL.LL
						L.L.L..L..
						LLLL.LL.LL
						L.LL.LL.LL
						L.LLLLL.LL
						..L.L.....
						LLLLLLLLLL
						L.LLLLLL.L
						L.LLLLL.LL      
						""";

		var answer = new Day11().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(37L);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day11input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day11().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(2472L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						.......#.
						...#.....
						.#.......
						.........
						..#L....#
						....#....
						.........
						#........
						...#.....
						""";

		var seatingArea = new SeatingAreaPart2(input);
		assertThat(seatingArea.leftOccupied(0)).isFalse();
		assertThat(seatingArea.rightOccupied(0)).isTrue();
		assertThat(seatingArea.aboveOccupied(0)).isFalse();
		assertThat(seatingArea.belowOccupied(0)).isTrue();
		assertThat(seatingArea.aboveLeftOccupied(0)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(0)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(0)).isFalse();
		assertThat(seatingArea.belowRightOccupied(0)).isFalse();
	}

	@Test
	public void part2Test2Left()
	{
		var input = """
						.............
						.L.L.#.#.#.#.
						.............
						""";

		var seatingArea = new SeatingAreaPart2(input);
		assertThat(seatingArea.leftOccupied(13)).isFalse();
		assertThat(seatingArea.leftOccupied(14)).isFalse();
		assertThat(seatingArea.leftOccupied(15)).isFalse();
		assertThat(seatingArea.leftOccupied(16)).isFalse();
		assertThat(seatingArea.leftOccupied(17)).isFalse();
		assertThat(seatingArea.leftOccupied(18)).isFalse();
		assertThat(seatingArea.leftOccupied(19)).isTrue();
	}

	@Test
	public void part2Test2Right()
	{
		var input = """
						.............
						.L.L.#.#.#.L.
						.............
						""";

		var seatingArea = new SeatingAreaPart2(input);
		assertThat(seatingArea.rightOccupied(14)).isFalse();
		assertThat(seatingArea.rightOccupied(15)).isFalse();
		assertThat(seatingArea.rightOccupied(16)).isTrue();
		assertThat(seatingArea.rightOccupied(17)).isTrue();
		assertThat(seatingArea.rightOccupied(18)).isTrue();
		assertThat(seatingArea.rightOccupied(19)).isTrue();
		assertThat(seatingArea.rightOccupied(20)).isTrue();
		assertThat(seatingArea.rightOccupied(21)).isTrue();
		assertThat(seatingArea.rightOccupied(22)).isFalse();
	}

	@Test
	public void part2Test3a()
	{
		var input = """
						.##.##.
						#.#.#.#
						##...##
						...L...
						##...##
						#.#.#.#
						.##.##.
						""";

		var seatingArea = new SeatingAreaPart2(input);
		assertThat(seatingArea.leftOccupied(24)).isFalse();
		assertThat(seatingArea.rightOccupied(24)).isFalse();
		assertThat(seatingArea.aboveOccupied(24)).isFalse();
		assertThat(seatingArea.belowOccupied(24)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(24)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(24)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(24)).isFalse();
		assertThat(seatingArea.belowRightOccupied(24)).isFalse();
	}

	@Test
	public void part2Test3b()
	{
		var input = """
						#.##.##.##
						#######.##
						#.#.#..#..
						####.##.##						
						""";

		var seatingArea = new SeatingAreaPart2(input);
		assertThat(seatingArea.leftOccupied(19)).isTrue();
		assertThat(seatingArea.rightOccupied(19)).isFalse();
		assertThat(seatingArea.aboveOccupied(19)).isTrue();
		assertThat(seatingArea.belowOccupied(19)).isTrue();
		assertThat(seatingArea.aboveLeftOccupied(19)).isTrue();
		assertThat(seatingArea.aboveRightOccupied(19)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(19)).isFalse();
		assertThat(seatingArea.belowRightOccupied(19)).isFalse();
	}

	@Test
	public void part2Test3c()
	{
		var input = """
						#.LL.LL.L#
						#LLLLLL.LL
						L.L.L..L..
						LLLL.LL.LL
						L.LL.LL.LL
						L.LLLLL.LL
						..L.L.....
						LLLLLLLLL#
						#.LLLLLL.L
						#.LLLLL.L#
						""";

		var seatingArea = new SeatingAreaPart2(input);
		assertThat(seatingArea.leftOccupied(3)).isFalse();
		assertThat(seatingArea.rightOccupied(3)).isFalse();
		assertThat(seatingArea.aboveOccupied(3)).isFalse();
		assertThat(seatingArea.belowOccupied(3)).isFalse();
		assertThat(seatingArea.aboveLeftOccupied(3)).isFalse();
		assertThat(seatingArea.aboveRightOccupied(3)).isFalse();
		assertThat(seatingArea.belowLeftOccupied(3)).isFalse();
		assertThat(seatingArea.belowRightOccupied(3)).isFalse();
	}

	@Test
	public void part2Test4() throws Exception
	{
		var input = """
						L.LL.LL.LL
						LLLLLLL.LL
						L.L.L..L..
						LLLL.LL.LL
						L.LL.LL.LL
						L.LLLLL.LL
						..L.L.....
						LLLLLLLLLL
						L.LLLLLL.L
						L.LLLLL.LL  
						""";

		var answer = new Day11().part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(26L);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day11input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day11().part2(input);
		System.out.println(answer);
		assertThat(answer).isGreaterThan(101L);
		assertThat(answer).isEqualTo(2197L);
	}
}
