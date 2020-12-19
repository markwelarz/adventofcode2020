package advent;

import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day18Part1
{
	public long part1(CharSource input) throws IOException
	{
		return input.lines().mapToLong(this::evaluate).sum();
	}

	private long evaluate(String expression)
	{
		List<String> expressionParts = expression.chars()
						.mapToObj(v -> (char) v)
						.map(String::valueOf)
						.filter(StringUtils::isNotBlank)
						.collect(Collectors.toList());

		return doEvaluate(expressionParts.iterator());
	}

	private long doEvaluate(Iterator<String> it)
	{
		long subtotal = 0;

		while (it.hasNext())
		{
			String expressionPart = it.next();
			if (NumberUtils.isDigits(expressionPart))
			{
				subtotal = Integer.parseInt(expressionPart);
			}
			else if (expressionPart.equals("("))
			{
				subtotal = doEvaluate(it);
			}
			else if (expressionPart.equals("+"))
			{
				subtotal += doTerm(it);
			}
			else if (expressionPart.equals("*"))
			{
				subtotal *= doTerm(it);
			}
			else if (expressionPart.equals(")"))
			{
				return subtotal;
			}
			else
				throw new IllegalStateException("unexpected " + expressionPart);
		}

		return subtotal;
	}

	private long doTerm(Iterator<String> it)
	{
		long subtotal;

		String expressionPart = it.next();
		if (NumberUtils.isDigits(expressionPart))
		{
			subtotal = Integer.parseInt(expressionPart);
		}
		else if (expressionPart.equals("("))
		{
			subtotal = doEvaluate(it);
		}
		else
			throw new IllegalStateException("unexpected " + expressionPart);

		return subtotal;
	}

}
