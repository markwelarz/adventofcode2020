package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day21Test
{
	@Test
	public void part1Test1() throws Exception
	{
		var input = """
						mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
						trh fvjkl sbzzf mxmxvkd (contains dairy)
						sqjhc fvjkl (contains soy)
						sqjhc mxmxvkd sbzzf (contains fish)
						""".replace("\n", "\r\n");
		var answer = new Day21().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(5);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day21input.txt").getFile(), Charsets.UTF_8);
		long answer = new Day21().part1(input);
		System.out.println(answer);
		assertThat(answer).isLessThan(3825L);
	}

	@Test
	public void part2Test1() throws Exception
	{
		var input = """
						mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
						trh fvjkl sbzzf mxmxvkd (contains dairy)
						sqjhc fvjkl (contains soy)
						sqjhc mxmxvkd sbzzf (contains fish)
						""".replace("\n", "\r\n");
		var answer = new Day21().part2(CharSource.wrap(input));
		assertThat(answer).isEqualTo("mxmxvkd,sqjhc,fvjkl");
	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day21input.txt").getFile(), Charsets.UTF_8);
		var answer = new Day21().part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo("gpgrb,tjlz,gtjmd,spbxz,pfdkkzp,xcfpc,txzv,znqbr");
	}
}
