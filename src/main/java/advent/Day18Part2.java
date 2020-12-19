package advent;

import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18Part2
{
	public long part2(CharSource input) throws IOException
	{
		return input.lines()
						.mapToLong(this::evaluate).sum();
	}

	private long evaluate(String expressionLine)
	{
		StringBuilder expression = new StringBuilder(StringUtils.replaceChars(expressionLine, " ", ""));

		boolean madeChanges;
		do
		{
			madeChanges = evaluateBracketed(expression);
		}
		while (madeChanges);

		evaluateSimple(expression);

		return Long.parseLong(expression.toString());
	}

	private boolean evaluateBracketed(StringBuilder expression)
	{
		System.out.println("expression: " + expression.toString());

		// do the innermost brackets
		int left = expression.lastIndexOf("(");
		if (left == -1)
			return false;

		int right = expression.indexOf(")", left + 1);
		long answer = evaluateSimple(new StringBuilder(expression.substring(left, right + 1)));

		expression.replace(left, right + 1, String.valueOf(answer));

		System.out.println("expression: " + expression.toString());

		return true;
	}

	private long evaluateSimple(StringBuilder expression)
	{
		computeSimpleAdditions(expression);
		removePointlessBrackets(expression);
		computeSimpleMultiplications(expression);
		removePointlessBrackets(expression);

		return Long.parseLong(expression.toString());
	}

	private void removePointlessBrackets(StringBuilder expression)
	{
		if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')')
		{
			expression.deleteCharAt(expression.length() - 1);
			expression.deleteCharAt(0);
		}
	}

	private void computeSimpleAdditions(StringBuilder expression)
	{
		Pattern simpleAddition = Pattern.compile("\\d+\\+\\d+"); // 1+2
		boolean found;
		do
		{
			Matcher matcher = simpleAddition.matcher(expression);
			found = matcher.find();
			if (found)
			{
				String simpleAddText = matcher.group();
				long left = Long.parseLong(StringUtils.substringBefore(simpleAddText, "+"));
				long right = Long.parseLong(StringUtils.substringAfter(simpleAddText, "+"));

				expression.replace(matcher.start(), matcher.end(), String.valueOf(left + right));
			}
		}
		while (found);
	}

	private void computeSimpleMultiplications(StringBuilder expression)
	{
		Pattern simpleMultiplication = Pattern.compile("\\d+\\*\\d+"); // 1*2
		boolean found;
		do
		{
			Matcher matcher = simpleMultiplication.matcher(expression);
			found = matcher.find();
			if (found)
			{
				String simpleAddText = matcher.group();
				long left = Long.parseLong(StringUtils.substringBefore(simpleAddText, "*"));
				long right = Long.parseLong(StringUtils.substringAfter(simpleAddText, "*"));

				expression.replace(matcher.start(), matcher.end(), String.valueOf(left * right));
			}
		}
		while (found);
	}

}
