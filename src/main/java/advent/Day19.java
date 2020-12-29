package advent;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day19
{
	private Pattern matchingRules;

	public long part1(CharSource input) throws IOException
	{
		var rulesAndMessages = Splitter.on("\r\n\r\n")
						.trimResults()
						.omitEmptyStrings()
						.splitToList(input.read());

		buildMegaRegexPart1(rulesAndMessages.get(0));

		long matches = Splitter.on("\r\n")
						.splitToStream(rulesAndMessages.get(1))
						.filter(v -> matchingRules.matcher(v).matches())
						.count();

		return matches;
	}

	public long part2(CharSource input, int rule11Repeats) throws IOException
	{
		var rulesAndMessages = Splitter.on("\r\n\r\n")
						.trimResults()
						.omitEmptyStrings()
						.splitToList(input.read());

		buildMegaRegexPart2(rulesAndMessages.get(0), rule11Repeats);

		long matches = Splitter.on("\r\n")
						.splitToStream(rulesAndMessages.get(1))
						.filter(v -> matchingRules.matcher(v).matches())
						.count();

		return matches;
	}

	Pattern buildMegaRegexPart1(String input)
	{
		var lineSplit = Splitter.on("\r\n")
						.trimResults()
						.omitEmptyStrings()
						.withKeyValueSeparator(":")
						.split(input);

		// key = rule Id, value = rule definition in regex with placeholders that refer to other rules
		var rules = Maps.transformValues(lineSplit, this::ruleWithPlaceholders);
		var stringSubstitutor = new StringSubstitutor(rules);
		var megaRegex = stringSubstitutor.replace(rules.get("0"));

		matchingRules = Pattern.compile(megaRegex);

		return matchingRules;
	}

	Pattern buildMegaRegexPart2(String input, int rule11Repeats)
	{
		var lineSplit = Splitter.on("\r\n")
						.trimResults()
						.omitEmptyStrings()
						.withKeyValueSeparator(":")
						.split(input);

		// key = rule Id, value = rule definition in regex with placeholders that refer to other rules
		var rules = Maps.transformValues(lineSplit, this::ruleWithPlaceholders);
		rules = Maps.newHashMap(rules);

		//		//8: 42 | 42 8
		//		// 42
		//		// 42 42
		//		// 42 42 42
		//		// (42+)
		rules.put("8", "((${42})+)");
		//		rules.put("8", "(${42})");
		//
		//		//11: 42 31 | 42 11 31 =>
		//		//42 31
		//		//42 42 31 31
		//		//42 42 42 42 31 31 31 31
		//		//42 42 42 42 42 31 31 31 31 31
		//		rules.put("11", "((${42})(${31}))|((${42})(${42})(${31})(${31}))|((${42})(${42})(${42})(${31})(${31})(${31}))|((${42})(${42})(${42})(${42})(${31})(${31})(${31})(${31}))");
		//rules.put("11", "((${42})(${31}))");

		String r42 = "(${42})";
		String r31 = "(${31})";

		String rule11Regex = IntStream.rangeClosed(1, rule11Repeats)
						.mapToObj(v -> "(" + StringUtils.repeat(r42, v) + StringUtils.repeat(r31, v) + ")")
						.collect(Collectors.joining("|"));

		rules.put("11", "(" + rule11Regex + ")");

		var stringSubstitutor = new StringSubstitutor(rules);
		var megaRegex = stringSubstitutor.replace(rules.get("0"));

		matchingRules = Pattern.compile(megaRegex);

		return matchingRules;
	}

	String ruleWithPlaceholders(String rule)
	{
		return Splitter.on(" ")
						.trimResults()
						.splitToStream(rule)
						.map(this::convertToRegex)
						.collect(Collectors.joining());
	}

	String convertToRegex(String rulePart)
	{
		String build = "";

		if (NumberUtils.isDigits(rulePart))
		{
			build += "(${" + rulePart + "})";
		}
		else
		{
			build += StringUtils.removeEnd(StringUtils.removeStart(rulePart, "\""), "\"");
		}
		return build;
	}
}
