package advent;

import advent.support.BagRule;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7Part1
{
	public int part1(CharSource input) throws IOException
	{
		List<BagRule> allBagRules = parse(input);
		Set<String> canHoldShinyGold = whatCanThisBagBeInsideOf(allBagRules, "shiny gold");
		Set<String> canEventuallyContain = new HashSet<>(canHoldShinyGold);
		Queue<String> toCheck = new LinkedList<>(canHoldShinyGold);

		while (!toCheck.isEmpty())
		{
			String inside = toCheck.poll();
			Set<String> canHoldThis = whatCanThisBagBeInsideOf(allBagRules, inside);

			canEventuallyContain.addAll(canHoldThis);
			toCheck.addAll(canHoldThis);
		}

		return canEventuallyContain.size();
	}

	private Set<String> whatCanThisBagBeInsideOf(List<BagRule> allBagRules, String inside)
	{
		Set<String> canBeInTheseBags = new HashSet<>();
		for (var outside : allBagRules)
		{
			if (StringUtils.equals(outside.insideColour(), inside))
			{
				canBeInTheseBags.add(outside.outsideColour());
			}
		}
		return canBeInTheseBags;
	}

	public List<BagRule> parse(CharSource input) throws IOException
	{
		return input.lines()
						.filter(StringUtils::isNotBlank)
						.flatMap(v -> parseBagLine(v).stream())
						.collect(Collectors.toList());
	}

	public List<BagRule> parseBagLine(String bagLine)
	{
		String containerBag = StringUtils.substringBefore(bagLine, " bags contain ");
		String containeeBagsText = StringUtils.substringAfter(bagLine, " bags contain ");
		List<BagRule> rules = new ArrayList<>();

		// containeeBagsText contains e.g: "1 bright white bag, 2 muted yellow bags."
		List<String> containeeBagTexts = Splitter.on(CharMatcher.anyOf(",."))
						.trimResults()
						.omitEmptyStrings()
						.splitToList(containeeBagsText);

		for (String containeeBagText : containeeBagTexts)
		{
			String containeeAmount = StringUtils.substringBefore(containeeBagText, " ");
			if (containeeAmount.startsWith("no"))
			{
				rules.add(new BagRule(containerBag, null, 0));
			}
			else
			{
				String withoutNumber = StringUtils.substringAfter(containeeBagText, " ");
				String containeeBagColour = StringUtils.substringBeforeLast(withoutNumber, " ");

				rules.add(new BagRule(containerBag, containeeBagColour, Integer.parseInt(containeeAmount)));
			}
		}
		return rules;
	}
}
