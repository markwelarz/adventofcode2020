package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day19Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						0: 1 2
						1: "a"
						2: 1 3 | 3 1
						3: "b"
						""".replace("\n", "\r\n");
		var regex = new Day19().buildMegaRegexPart1(input);
		assertThat(regex.toString()).isEqualTo("(a)((a)(b)|(b)(a))");
	}

	@Test
	public void part1Test2() throws Exception
	{
		var input = """
						0: 4 1 5
						1: 2 3 | 3 2
						2: 4 4 | 5 5
						3: 4 5 | 5 4
						4: "a"
						5: "b"
						""".replace("\n", "\r\n");
		var day19 = new Day19();
		var megaRegex = day19.buildMegaRegexPart1(input);
		assertThat(megaRegex.matcher("aaaabb").matches()).isTrue();
		assertThat(megaRegex.matcher("aaabab").matches()).isTrue();
		assertThat(megaRegex.matcher("abbabb").matches()).isTrue();
		assertThat(megaRegex.matcher("abbbab").matches()).isTrue();
		assertThat(megaRegex.matcher("aabaab").matches()).isTrue();
		assertThat(megaRegex.matcher("abaaab").matches()).isTrue();
		assertThat(megaRegex.matcher("aabbbb").matches()).isTrue();
		assertThat(megaRegex.matcher("ababbb").matches()).isTrue();
	}

	@Test
	public void part1Test3() throws Exception
	{
		var input = """
						0: 4 1 5
						1: 2 3 | 3 2
						2: 4 4 | 5 5
						3: 4 5 | 5 4
						4: "a"
						5: "b"

						ababbb
						bababa
						abbbab
						aaabbb
						aaaabbb
						""".replace("\n", "\r\n");
		var day19 = new Day19();
		var answer = day19.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(2);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day19input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day19().part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(198L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						42: 9 14 | 10 1
						9: 14 27 | 1 26
						10: 23 14 | 28 1
						1: "a"
						11: 42 31
						5: 1 14 | 15 1
						19: 14 1 | 14 14
						12: 24 14 | 19 1
						16: 15 1 | 14 14
						31: 14 17 | 1 13
						6: 14 14 | 1 14
						2: 1 24 | 14 4
						0: 8 11
						13: 14 3 | 1 12
						15: 1 | 14
						17: 14 2 | 1 7
						23: 25 1 | 22 14
						28: 16 1
						4: 1 1
						20: 14 14 | 1 15
						3: 5 14 | 16 1
						27: 1 6 | 14 18
						14: "b"
						21: 14 1 | 1 14
						25: 1 1 | 1 14
						22: 14 14
						8: 42
						26: 14 22 | 1 20
						18: 15 15
						7: 14 5 | 1 21
						24: 14 1

						abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
						bbabbbbaabaabba
						babbbbaabbbbbabbbbbbaabaaabaaa
						aaabbbbbbaaaabaababaabababbabaaabbababababaaa
						bbbbbbbaaaabbbbaaabbabaaa
						bbbababbbbaaaaaaaabbababaaababaabab
						ababaaaaaabaaab
						ababaaaaabbbaba
						baabbaaaabbaaaababbaababb
						abbbbabbbbaaaababbbbbbaaaababb
						aaaaabbaabaaaaababaa
						aaaabbaaaabbaaa
						aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
						babaaabbbaaabaababbaabababaaab
						aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba      
						""".replace("\n", "\r\n");
		var day19 = new Day19();
		var answer = day19.part2(CharSource.wrap(input), 10);
		assertThat(answer).isEqualTo(12);
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 })
	public void part2Solution(int rule11Repeats) throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day19input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day19().part2(input, rule11Repeats);
		System.out.println(answer);
	}
}
