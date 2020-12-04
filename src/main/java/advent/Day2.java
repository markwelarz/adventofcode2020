package advent;

import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.Var;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2
{
	public long part1(CharSource input) throws IOException
	{
		PasswordListParser parser = Parboiled.createParser(PasswordListParser.class);
		ParsingResult<Object> result = new ReportingParseRunner<>(parser.PasswordPolicyList()).run(input.read());

		List<PasswordEntry> passwordEntries = (List) result.resultValue;
		return passwordEntries.stream()
						.filter(PasswordEntry::isValidPart1)
						.count();
	}

	public long part2(CharSource input) throws IOException
	{
		PasswordListParser parser = Parboiled.createParser(PasswordListParser.class);
		ParsingResult<Object> result = new ReportingParseRunner<>(parser.PasswordPolicyList()).run(input.read());

		List<PasswordEntry> passwordEntries = (List) result.resultValue;
		return passwordEntries.stream()
						.filter(PasswordEntry::isValidPart2)
						.count();
	}
}

record PasswordEntry(Integer low, Integer high, Character ch, String password)
{
	boolean isValidPart1()
	{
		int occurences = StringUtils.countMatches(password, ch);
		return occurences >= low && occurences <= high;
	}

	boolean isValidPart2()
	{
		return password.charAt(low - 1) == ch ^ password.charAt(high - 1) == ch;
	}
}

@BuildParseTree
class PasswordListParser extends BaseParser<Object>
{
	Rule PasswordPolicyList()
	{
		Var<List<PasswordEntry>> policyList = new Var<>(new ArrayList<>());
		return OneOrMore(PasswordEntry(policyList), String("\r\n"), push(policyList.get()));
	}

	Rule PasswordEntry(Var<List<PasswordEntry>> policyList)
	{
		return Sequence(
						RequiredRange(), Ch(' ').suppressNode(),
						CharRange('a', 'z'), push(matchedChar()), String(": "),
						Password(), policyList.get().add(new PasswordEntry((Integer) pop(2), (Integer) pop(1), (Character) pop(), match())));
	}

	Rule RequiredRange()
	{
		return Sequence(
						Number(), push(Integer.parseInt(match())), Ch('-').suppressNode(), Number(), push(Integer.parseInt(match())));
	}

	Rule Number()
	{
		return OneOrMore(CharRange('0', '9'));
	}

	Rule Password()
	{
		return OneOrMore(CharRange('a', 'z'));
	}
}
