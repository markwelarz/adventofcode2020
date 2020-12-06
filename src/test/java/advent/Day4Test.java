package advent;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Day4Test
{
	@Test
	public void part1Test1() throws IOException
	{
		var input = """
						ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
						byr:1937 iyr:2017 cid:147 hgt:183cm

						iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
						hcl:#cfa07d byr:1929

						hcl:#ae17e1 iyr:2013
						eyr:2024
						ecl:brn pid:760753108 byr:1931
						hgt:179cm

						hcl:#cfa07d eyr:2025 pid:166559648
						iyr:2011 ecl:brn hgt:59in""".replace("\n", "\r\n");

		var answer = new Day4().part1(CharSource.wrap(input));
		assertThat(answer).isEqualTo(2);
	}

	@Test
	public void part1Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day4input.txt").getFile(), Charsets.UTF_8);
		var day4 = new Day4();
		long answer = day4.part1(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(247L);
	}

	@Test
	public void validateAllValidFields()
	{
		boolean valid = new Day4().doValidation(validPassport());
		assertThat(valid).isTrue();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "1", "12", "123", "1234", "1919", "2003" })
	public void validateBirthYearInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("byr", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "1", "12", "123", "1234", "2009", "2021" })
	public void validateIssueYearInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("iyr", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "1", "12", "123", "1234", "2019", "2031" })
	public void validateExpirationYearInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("eyr", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "170", "12", "149cm", "194cm", "190in", "190", "58in", "77in" })
	public void validateHeightInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("hgt", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "123456", "#12345", "#1234567", "#aaa", "123abz", "123abc" })
	public void validateHairColourInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("hcl", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "blue", "wat" })
	public void validateEyeColourInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("ecl", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "1234567890", "12345678" })
	public void validatePassportIdInvalid(String invalidValue)
	{
		var input = validPassport();
		input.put("pid", invalidValue);
		boolean valid = new Day4().doValidation(input);
		assertThat(valid).isFalse();
	}

	@Test
	public void part2AllInvalid() throws Exception
	{
		var input = """
						eyr:1972 cid:100
						hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

						iyr:2019
						hcl:#602927 eyr:1967 hgt:170cm
						ecl:grn pid:012533040 byr:1946

						hcl:dab227 iyr:2012
						ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

						hgt:59cm ecl:zzz
						eyr:2038 hcl:74454a iyr:2023
						pid:3556412378 byr:2007""".replace("\n", "\r\n");

		var day4 = new Day4();
		long answer = day4.part2(CharSource.wrap(input));
		System.out.println(answer);
		assertThat(answer).isEqualTo(0L);
	}

	@Test
	public void partAllValid() throws Exception
	{
		var input = """
						pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
						hcl:#623a2f

						eyr:2029 ecl:blu cid:129 byr:1989
						iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

						hcl:#888785
						hgt:164cm byr:2001 iyr:2015 cid:88
						pid:545766238 ecl:hzl
						eyr:2022

						iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719      
												""".replace("\n", "\r\n");
		var day4 = new Day4();
		long answer = day4.part2(CharSource.wrap(input));
		System.out.println(answer);
		assertThat(answer).isEqualTo(4L);
	}

	@Test
	public void missingEyr() throws IOException
	{
		var input = """
						cid:341 pid:842156559
						hgt:167cm hcl:#602927 byr:1939 ecl:amb iyr:2016
																""".replace("\n", "\r\n");
		var day4 = new Day4();
		long answer = day4.part2(CharSource.wrap(input));
		System.out.println(answer);
		assertThat(answer).isEqualTo(0L);

	}

	@Test
	public void part2Solution() throws Exception
	{
		CharSource input = Files.asCharSource(new ClassPathResource("advent/day4input.txt").getFile(), Charsets.UTF_8);
		var day4 = new Day4();
		long answer = day4.part2(input);
		System.out.println(answer);
		assertThat(answer).isEqualTo(156L);
	}

	private Map<String, String> validPassport()
	{
		Map<String, String> allValid = new HashMap<>();
		allValid.put("byr", "1979");
		allValid.put("iyr", "2010");
		allValid.put("eyr", "2025");
		allValid.put("hgt", "180cm");
		allValid.put("hcl", "#acacac");
		allValid.put("ecl", "amb");
		allValid.put("pid", "123456789");

		return allValid;
	}
}
