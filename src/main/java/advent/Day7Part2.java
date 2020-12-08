package advent;

import advent.support.BagRule;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day7Part2
{
	public int part2(CharSource input) throws IOException
	{
		Map<String, List<BagRule>> allBagRules = new Day7Part1().parse(input).stream()
						.collect(Collectors.groupingBy(BagRule::outsideColour));

		List<BagWithMultiplier> insideShinyGold = whatGoesInThisBag(allBagRules, "shiny gold", 1);
		int totalBags = insideShinyGold.stream().mapToInt(BagWithMultiplier::multiplier).sum();
		Queue<BagWithMultiplier> toCheck = new LinkedList<>(insideShinyGold);

		while (!toCheck.isEmpty())
		{
			BagWithMultiplier outside = toCheck.poll();
			List<BagWithMultiplier> canHoldThis = whatGoesInThisBag(allBagRules, outside.bag(), outside.multiplier());
			totalBags += canHoldThis.stream().mapToInt(BagWithMultiplier::multiplier).sum();
			toCheck.addAll(canHoldThis);
		}

		return totalBags;
	}

	private List<BagWithMultiplier> whatGoesInThisBag(Map<String, List<BagRule>> allBagRules, String outsideBag, int multiplier)
	{
		List<BagWithMultiplier> insideThisBag = new ArrayList<>();
		for (var outsideBagRule : allBagRules.getOrDefault(outsideBag, Collections.emptyList()))
		{
			allBagRules.get(outsideBagRule.insideColour());

			if (outsideBagRule.amount() == 0)
			{
				insideThisBag.add(new BagWithMultiplier(outsideBagRule.insideColour(), 0));
			}
			else
			{
				insideThisBag.add(new BagWithMultiplier(outsideBagRule.insideColour(), multiplier * outsideBagRule.amount()));
			}
		}

		return insideThisBag;
	}

	record BagWithMultiplier(String bag, int multiplier)
	{
	}
}
