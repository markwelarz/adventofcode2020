package advent;

import advent.support.EyeColour;
import advent.support.EyeColourConverter;
import advent.support.PassportEntry;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class Day4
{
	public long part1(CharSource input) throws IOException
	{
		return Splitter.on("\r\n\r\n")
						.splitToStream(input.read())
						.filter(this::isPassportValidPart1)
						.count();
	}

	private boolean isPassportValidPart1(String passportEntry)
	{
		final Set<String> requiredFields = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

		return Splitter.on(CharMatcher.whitespace())
						.omitEmptyStrings()
						.trimResults()
						.withKeyValueSeparator(":")
						.split(passportEntry)
						.keySet()
						.containsAll(requiredFields);
	}

	public long part2(CharSource input) throws IOException
	{
		return Splitter.on("\r\n\r\n")
						.splitToStream(input.read())
						.filter(this::isPassportValidPart2)
						.count();
	}

	private boolean isPassportValidPart2(String passportEntry)
	{
		Map<String, String> passportFields = Splitter.on(CharMatcher.whitespace())
						.omitEmptyStrings()
						.trimResults()
						.withKeyValueSeparator(":")
						.split(passportEntry);

		return doValidation(passportFields);
	}

	boolean doValidation(Map<String, String> passportFields)
	{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		PassportEntry passportEntry = new PassportEntry();
		try
		{
			ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
			convertUtilsBean.register(new EyeColourConverter(), EyeColour.class);
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean);

			beanUtilsBean.populate(passportEntry, passportFields);
			Set<ConstraintViolation<PassportEntry>> violations = validator.validate(passportEntry);

			if (violations.isEmpty())
			{
				System.out.println("========= valid ========");
				System.out.println("valid: " + passportFields);
				System.out.println("========= valid ========");
			}
			else
			{
				System.out.println(violations);
			}

			return violations.isEmpty();
		}
		catch (IllegalAccessException | InvocationTargetException | ConversionException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}

