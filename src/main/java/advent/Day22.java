package advent;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22
{
	private static int gameNumber = 1;

	public long part1(CharSource input) throws IOException
	{
		List<String> decks = Splitter.on("\r\n\r\n")
						.trimResults()
						.omitEmptyStrings()
						.splitToList(input.read());

		var player1Deck = decks.get(0).lines()
						.skip(1)
						.map(Integer::parseInt)
						.collect(Collectors.toCollection(LinkedList::new));

		var player2Deck = decks.get(1).lines()
						.skip(1)
						.map(Integer::parseInt)
						.collect(Collectors.toCollection(LinkedList::new));

		doCombat(player1Deck, player2Deck);

		Iterator<Integer> it = iterateWinnerDeck(player1Deck, player2Deck);
		long cardNum = 1;
		long score = 0;
		while (it.hasNext())
		{
			score += cardNum++ * it.next();
		}

		return score;
	}

	public long part2(CharSource input) throws IOException
	{
		List<String> decks = Splitter.on("\r\n\r\n")
						.trimResults()
						.omitEmptyStrings()
						.splitToList(input.read());

		var player1Deck = decks.get(0).lines()
						.skip(1)
						.map(Integer::parseInt)
						.collect(Collectors.toCollection(LinkedList::new));

		var player2Deck = decks.get(1).lines()
						.skip(1)
						.map(Integer::parseInt)
						.collect(Collectors.toCollection(LinkedList::new));

		doCombatRecursively(player1Deck, player2Deck);

		Iterator<Integer> it = iterateWinnerDeck(player1Deck, player2Deck);
		long cardNum = 1;
		long score = 0;
		while (it.hasNext())
		{
			score += cardNum * it.next();
			cardNum++;
		}

		return score;
	}

	enum Winner
	{
		PLAYER1,
		PLAYER2
	}

	private Winner doCombatRecursively(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck)
	{
		int round = 1;
		int game = gameNumber++;
		Set<String> previousRounds = new HashSet<>();

		System.out.println("new game " + game);
		System.out.println("player 1 deck: " + player1Deck);
		System.out.println("player 2 deck: " + player2Deck);

		while (!player1Deck.isEmpty() && !player2Deck.isEmpty())
		{
			System.out.println("-- round " + round + " game " + game + " --");
			System.out.println("player 1 deck: " + player1Deck);
			System.out.println("player 2 deck: " + player2Deck);

			String hash = player1Deck.stream().map(String::valueOf).collect(Collectors.joining(",")) + "_" +
							player2Deck.stream().map(String::valueOf).collect(Collectors.joining(","));
			if (previousRounds.contains(hash))
			{
				System.out.println("infinite round - player 1 wins");
				return Winner.PLAYER1;
			}

			previousRounds.add(hash);

			int p1card = player1Deck.poll();
			int p2card = player2Deck.poll();

			System.out.println("p1 " + p1card);
			System.out.println("p2 " + p2card);

			Winner winner;

			if (p1card <= player1Deck.size() && p2card <= player2Deck.size())
			{
				LinkedList<Integer> player1DeckCopy = Lists.newLinkedList(player1Deck.subList(0, p1card));
				LinkedList<Integer> player2DeckCopy = Lists.newLinkedList(player2Deck.subList(0, p2card));

				winner = doCombatRecursively(player1DeckCopy, player2DeckCopy);
			}
			else
			{
				if (p1card > p2card)
				{
					winner = Winner.PLAYER1;
				}
				else if (p2card > p1card)
				{
					winner = Winner.PLAYER2;
				}
				else
				{
					assert false : "cards equal???";
					throw new RuntimeException("cards equal???");
				}
			}

			assert winner != null;

			if (winner == Winner.PLAYER1)
			{
				System.out.println("player 1 wins round " + round + " of game " + game);
				player1Deck.addLast(p1card);
				player1Deck.addLast(p2card);
			}
			else if (winner == Winner.PLAYER2)
			{
				System.out.println("player 2 wins round " + round + " of game " + game);
				player2Deck.addLast(p2card);
				player2Deck.addLast(p1card);
			}

			round++;
		}

		return winner(player1Deck, player2Deck);
	}

	private void doCombat(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck)
	{
		while (!player1Deck.isEmpty() && !player2Deck.isEmpty())
		{
			int p1card = player1Deck.poll();
			int p2card = player2Deck.poll();

			if (p1card > p2card)
			{
				player1Deck.addLast(p1card);
				player1Deck.addLast(p2card);
			}
			else if (p2card > p1card)
			{
				player2Deck.addLast(p2card);
				player2Deck.addLast(p1card);
			}
			else
				assert false : "cards equal???";
		}
	}

	private Winner winner(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck)
	{
		if (player1Deck.isEmpty() && !player2Deck.isEmpty())
			return Winner.PLAYER2;
		else if (!player1Deck.isEmpty() && player2Deck.isEmpty())
			return Winner.PLAYER1;
		else
			throw new RuntimeException();
	}

	private Iterator<Integer> iterateWinnerDeck(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck)
	{
		Winner winner = winner(player1Deck, player2Deck);
		if (winner == Winner.PLAYER2)
			return player2Deck.descendingIterator();
		else if (winner == Winner.PLAYER1)
			return player1Deck.descendingIterator();
		else
			throw new RuntimeException();
	}
}
