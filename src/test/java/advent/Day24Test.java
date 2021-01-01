package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day24Test
{
	final String example1 = """
					sesenwnenenewseeswwswswwnenewsewsw
					neeenesenwnwwswnenewnwwsewnenwseswesw
					seswneswswsenwwnwse
					nwnwneseeswswnenewneswwnewseswneseene
					swweswneswnenwsewnwneneseenw
					eesenwseswswnenwswnwnwsewwnwsene
					sewnenenenesenwsewnenwwwse
					wenwwweseeeweswwwnwwe
					wsweesenenewnwwnwsenewsenwwsesesenwne
					neeswseenwwswnwswswnw
					nenwswwsewswnenenewsenwsenwnesesenew
					enewnwewneswsewnwswenweswnenwsenwsw
					sweneswneswneneenwnewenewwneswswnese
					swwesenesewenwneswnwwneseswwne
					enesenwswwswneneswsenwnewswseenwsese
					wnwnesenesenenwwnenwsewesewsesesew
					nenewswnwewswnenesenwnesewesw
					eneswnwswnwsenenwnwnwwseeswneewsenese
					neswnwewnwnwseenwseesewsenwsweewe
					wseweeenwnesenwwwswnew  
					""".replace("\n", "\r\n");

	@Test
	public void part1Test1() throws Exception
	{
		var answer = new Day24().part1(CharSource.wrap(example1));
		assertThat(answer).isEqualTo(10);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day24input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day24().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(360L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var answer = new Day24().part2(CharSource.wrap(example1), 1);
		assertThat(answer).isEqualTo(15);
	}

	@Test
	public void part2Test2() throws Exception
	{
		var answer = new Day24().part2(CharSource.wrap(example1), 2);
		assertThat(answer).isEqualTo(12);
	}

	@Test
	public void part2Test3() throws Exception
	{
		var answer = new Day24().part2(CharSource.wrap(example1), 3);
		assertThat(answer).isEqualTo(25);
	}

	@Test
	public void part2Test4() throws Exception
	{
		var answer = new Day24().part2(CharSource.wrap(example1), 4);
		assertThat(answer).isEqualTo(14);
	}

	@Test
	public void part2Test5() throws Exception
	{
		var answer = new Day24().part2(CharSource.wrap(example1), 5);
		assertThat(answer).isEqualTo(23);
	}

	@Test
	public void part2Test6() throws Exception
	{
		var answer = new Day24().part2(CharSource.wrap(example1), 100);
		assertThat(answer).isEqualTo(2208);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day24input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day24().part2(input, 100);
		System.out.println(answer);
		assertThat(answer).isEqualTo(3924L);
	}
}
