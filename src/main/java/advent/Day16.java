package advent;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16
{
	public long part1(CharSource input) throws IOException
	{
		var notes = parseNotes(input);
		int errorRate = 0;
		errorRate += errorRate(notes.rules, notes.nearbyTickets());

		return errorRate;
	}

	public long part2(CharSource input) throws IOException
	{
		var notes = parseNotes(input);
		var rules = notes.rules().stream()
						.collect(Collectors.toMap(v -> v.fieldName, Function.identity()));

		Map<String, Set<Integer>> fieldCounters = notes.rules().stream()
						.collect(Collectors.toMap(v -> v.fieldName, v -> IntStream.rangeClosed(0, rules.size() - 1)
										.boxed().collect(Collectors.toSet())));

		System.out.println("eliminating impossible tickets. before: " + notes.nearbyTickets().size());

		var nearBy = notes.nearbyTickets.stream()
						.filter(v -> anyImpossibleFieldsInTicket(v, rules))
						.collect(Collectors.toList());

		System.out.println("eliminating impossible tickets. after: " + nearBy.size());

		System.out.println("fieldCounters: " + fieldCounters);
		System.out.println("cutting down fields");

		for (Ticket ticket : nearBy)
		{
			for (int i = 0; i < ticket.fieldValues().size(); i++)
			{
				for (var e : rules.entrySet())
				{
					if (!e.getValue().isValid(ticket.fieldValues().get(i)))
					{
						fieldCounters.get(e.getKey()).remove(i);
					}
				}
			}
		}

		System.out.println("fieldCounters: " + fieldCounters);
		System.out.println("eliminating");

		// if there is only 1, then eliminate from the other fields
		var filteredFieldCounters = fieldCounters;
		boolean removedSomething;
		do
		{
			removedSomething = false;
			filteredFieldCounters = Maps.filterEntries(fieldCounters, e -> e.getValue().size() == 1);

			for (var e : filteredFieldCounters.entrySet())
			{
				var removeIndexFrom = Maps.filterKeys(fieldCounters, v -> !v.equals(e.getKey()));
				for (var f : removeIndexFrom.entrySet())
				{
					removedSomething |= f.getValue().remove(Iterables.getOnlyElement(e.getValue()));
				}
			}

			System.out.println("fieldCounters: " + fieldCounters);
		}
		while (removedSomething);

		assert fieldCounters.values().stream().allMatch(v -> v.size() == 1);

		long answer = Maps.filterKeys(fieldCounters, v -> v.startsWith("departure"))
						.values().stream()
						.flatMap(v -> v.stream())
						.mapToLong(v -> notes.myTicket().fieldValues().get(v))
						.reduce((u, v) -> u * v).getAsLong();

		return answer;
	}

	private boolean anyImpossibleFieldsInTicket(Ticket ticket, Map<String, Rule> rules)
	{
		for (int ticketFieldValue : ticket.fieldValues())
		{
			if (rules.values().stream().noneMatch(v -> v.isValid(ticketFieldValue)))
			{
				return false;
			}
		}
		return true;
	}

	private List<String> allFieldNames(List<Rule> rules)
	{
		return rules.stream()
						.map(Rule::fieldName)
						.collect(Collectors.toList());
	}

	private int errorRate(List<Rule> rules, List<Ticket> tickets)
	{
		int errorRate = 0;

		for (var ticket : tickets)
		{
			for (int ticketFieldValue : ticket.fieldValues())
			{
				if (rules.stream().noneMatch(v -> v.isValid(ticketFieldValue)))
					errorRate += ticketFieldValue;
			}
		}

		return errorRate;
	}

	private ParsedNotes parseNotes(CharSource input) throws IOException
	{
		List<Rule> rules = new ArrayList<>();

		var sections = Splitter.on("\r\n\r\n")
						.omitEmptyStrings()
						.trimResults()
						.splitToList(input.read());

		assert sections.size() == 3;

		// section 1 is rules
		var rulesText = Splitter.on("\r\n")
						.withKeyValueSeparator(":")
						.split(sections.get(0));
		for (var entry : rulesText.entrySet())
		{
			var fff = Splitter.on(CharMatcher.anyOf("- ,or"))
							.omitEmptyStrings()
							.trimResults()
							.splitToStream(entry.getValue())
							.map(Integer::parseInt)
							.collect(Collectors.toList());

			assert fff.size() == 4;

			rules.add(new Rule(entry.getKey(), fff.get(0), fff.get(1), fff.get(2), fff.get(3)));
		}

		// section 2 is my ticket
		var myTicket = Splitter.on(",")
						.omitEmptyStrings()
						.trimResults()
						.splitToStream(StringUtils.substringAfter(sections.get(1), ":"))
						.map(Integer::parseInt)
						.collect(Collectors.collectingAndThen(Collectors.toList(), Ticket::new));

		// section 3 is nearby tickets
		var nearbyTickets = Splitter.on("\r\n")
						.omitEmptyStrings()
						.trimResults()
						.splitToStream(StringUtils.substringAfter(sections.get(2), ":"))
						.map(this::mapToInts)
						.map(Ticket::new)
						.collect(Collectors.toList());

		return new ParsedNotes(rules, myTicket, nearbyTickets);
	}

	private List<Integer> mapToInts(String commaSeparatedNumbers)
	{
		return Splitter.on(",").splitToStream(commaSeparatedNumbers).map(Integer::parseInt).collect(Collectors.toList());
	}

	record Rule(String fieldName, int range1From, int range1To, int range2From, int range2To)
	{
		public boolean isValid(int fieldValue)
		{
			if ((fieldValue >= range1From && fieldValue <= range1To) ||
							(fieldValue >= range2From && fieldValue <= range2To))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	record Ticket(List<Integer>fieldValues)
	{

	}

	record ParsedNotes(List<Rule>rules, Ticket myTicket, List<Ticket>nearbyTickets)
	{

	}
}
