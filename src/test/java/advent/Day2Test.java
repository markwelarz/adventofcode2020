package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class Day2Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						1-3 a: abcde
						1-3 b: cdefg
						2-9 c: ccccccccc      
						""".replace("\n", "\r\n");
		Day2 day2 = new Day2();
		long answer = day2.part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(2);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						1-3 a: abcde
						1-3 b: cdefg
						2-9 c: ccccccccc      
						""".replace("\n", "\r\n");
		Day2 day2 = new Day2();
		long answer = day2.part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo(1);
	}

	@Test
	public void part1() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day2input.txt").getFile(), Charsets.UTF_8);
		Day2 day2 = new Day2();
		long answer = day2.part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(660);
	}

	@Test
	public void part2() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day2input.txt").getFile(), Charsets.UTF_8);
		Day2 day2 = new Day2();
		long answer = day2.part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(530);
	}

	@Test
	public void parsePasswordPolicyList()
	{
		var input = """
						1-3 a: abcde
						21-55 b: cdefg
						2-9 c: ccccccccc      
						""".replace("\n", "\r\n");
		PasswordListParser parser = Parboiled.createParser(PasswordListParser.class);
		ParsingResult<?> result = new ReportingParseRunner<>(parser.PasswordPolicyList()).run(input);
		List<PasswordEntry> passwordEntries = (List) result.resultValue;
		assertThat(passwordEntries).extracting("low", "high", "ch", "password")
						.containsExactly(tuple(1, 3, 'a', "abcde"),
										tuple(21, 55, 'b', "cdefg"),
										tuple(2, 9, 'c', "ccccccccc"));
	}
}
