package advent.support;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeightValidator implements ConstraintValidator<Height, String>
{
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		String[] heightUnitSplit = StringUtils.splitByCharacterType(value);

		if (heightUnitSplit == null || heightUnitSplit.length != 2)
		{
			context.buildConstraintViolationWithTemplate("wrong").addConstraintViolation();
			return false;
		}

		if (StringUtils.equals(heightUnitSplit[1], "cm"))
		{
			int height = Integer.parseInt(heightUnitSplit[0]);
			if (height < 150 || height > 193)
			{
				context.buildConstraintViolationWithTemplate("wrong").addConstraintViolation();
				return false;
			}
		}
		else if (StringUtils.equals(heightUnitSplit[1], "in"))
		{
			int height = Integer.parseInt(heightUnitSplit[0]);
			if (height < 59 || height > 76)
			{
				context.buildConstraintViolationWithTemplate("wrong").addConstraintViolation();
				return false;
			}
		}

		return true;
	}
}
