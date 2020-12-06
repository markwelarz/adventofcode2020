package advent.support;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class EyeColourConverter implements Converter
{
	@Override
	public <T> T convert(Class<T> type, Object value)
	{
		try
		{
			return (T) EyeColour.valueOf(((String) value).toUpperCase());
		}
		catch (NullPointerException | IllegalArgumentException e)
		{
			throw new ConversionException(e);
		}
	}
}
