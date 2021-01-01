package advent;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.apache.commons.collections4.iterators.LoopingListIterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day23
{
	public String part1(String labelling, int moves)
	{
		LinkedList<Integer> circle = labelling.chars()
						.mapToObj(v -> Character.digit(v, 10))
						.collect(Collectors.toCollection(LinkedList::new));

		NavigableSet<Integer> allLabels = new TreeSet<>(circle);
		ListIterator<Integer> circularIterator = new LoopingListIterator<>(circle);

		int currentLabel = circle.getFirst();
		circularIterator.next(); // position on currentLabel

		for (int move = 0; move < moves; move++)
		{
			System.out.println(circle);
			System.out.println("current cup is " + currentLabel);

			int p1 = circularIterator.next();
			circularIterator.remove();
			int p2 = circularIterator.next();
			circularIterator.remove();
			int p3 = circularIterator.next();
			circularIterator.remove();
			System.out.println("picked up " + p1 + "," + p2 + "," + p3);

			int destLabel = destinationCup(allLabels, currentLabel, p1, p2, p3);
			System.out.println("destination is " + destLabel);

			// move to the destination
			advanceToLabel(circularIterator, destLabel);

			// and insert the chosen cups after
			circularIterator.add(p1);
			circularIterator.add(p2);
			circularIterator.add(p3);

			// next currentLabel is one-past the currentLabel
			advanceToLabel(circularIterator, currentLabel);
			currentLabel = circularIterator.next();
		}

		StringBuilder answer = new StringBuilder();
		advanceToLabel(circularIterator, 1);
		circularIterator.remove(); // remove cup=1
		Iterator<Integer> answerIterator = Iterators.consumingIterator(circularIterator);

		answerIterator.forEachRemaining(answer::append);

		return answer.toString();
	}

	public long part2(String labelling, int moves)
	{
		List<Integer> firstDigits = labelling.chars()
						.mapToObj(v -> Character.digit(v, 10))
						.collect(Collectors.toList());

		// first cup is the current cup
		int currentLabel = firstDigits.get(0);

		// cup label -> next cup label
		TreeMap<Integer, Integer> labelNextLinks = new TreeMap<>();
		for (int i = 1; i < firstDigits.size(); i++)
		{
			labelNextLinks.put(firstDigits.get(i - 1), firstDigits.get(i));
		}
		labelNextLinks.put(Iterables.getLast(firstDigits), firstDigits.size() + 1);
		for (int i = firstDigits.size() + 2; i <= 1_000_000; i++)
		{
			labelNextLinks.put(i - 1, i);
		}
		labelNextLinks.put(1_000_000, currentLabel);

		for (int move = 0; move < moves; move++)
		{
			if (move % 100000 == 0)
				System.out.println("move " + move);

			int p1 = labelNextLinks.get(currentLabel);
			int p2 = labelNextLinks.get(p1);
			int p3 = labelNextLinks.get(p2);
			int afterChosen = labelNextLinks.get(p3);

			int destLabel = destinationCup(labelNextLinks.navigableKeySet(), currentLabel, p1, p2, p3);
			int labelAfterDest = labelNextLinks.get(destLabel);

			// repoint the currentLabel cup's next value
			labelNextLinks.put(currentLabel, afterChosen);
			// repoint the 3rd chosen cup's next value
			labelNextLinks.put(p3, labelAfterDest);
			// repoint the cup that was previously before the first chosen cup
			labelNextLinks.put(destLabel, p1);

			// next currentLabel is one-past the currentLabel
			currentLabel = labelNextLinks.get(currentLabel);
		}

		Integer afterCup1 = labelNextLinks.get(1);
		Integer afterThat = labelNextLinks.get(afterCup1);

		return afterCup1.longValue() * afterThat.longValue();
	}

	private void advanceToLabel(ListIterator<Integer> circularIterator, int label)
	{
		Iterators.find(circularIterator, v -> v == label);
	}

	private int destinationCup(NavigableSet<Integer> allLabels, int currentLabel, int p1, int p2, int p3)
	{
		Integer lower = currentLabel;

		do
		{
			lower = allLabels.lower(lower);
			if (lower == null)
				lower = allLabels.last();
		}
		while (lower == p1 || lower == p2 || lower == p3);

		return lower;
	}
}
