package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day7Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						light red bags contain 1 bright white bag, 2 muted yellow bags.
						dark orange bags contain 3 bright white bags, 4 muted yellow bags.
						bright white bags contain 1 shiny gold bag.
						muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
						shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
						dark olive bags contain 3 faded blue bags, 4 dotted black bags.
						vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
						faded blue bags contain no other bags.
						dotted black bags contain no other bags.      
						""";
		var answer = new Day7Part1().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(4);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day7input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day7Part1().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(335);
	}

	@Test
	public void part2Test() throws Exception
	{
		var input = """
						shiny gold bags contain 2 dark red bags.
						dark red bags contain 2 dark orange bags.
						dark orange bags contain 2 dark yellow bags.
						dark yellow bags contain 2 dark green bags.
						dark green bags contain 2 dark blue bags.
						dark blue bags contain 2 dark violet bags.
						dark violet bags contain no other bags.
						""";
		var answer = new Day7Part2().part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(126);
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day7input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day7Part2().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(2431);
	}
}
