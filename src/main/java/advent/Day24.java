package advent;

import com.google.common.collect.Sets;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day24
{
	public long part1(CharSource input) throws IOException
	{
		return input.readLines(new Flipper()).size();
	}

	public long part2(CharSource input, int days) throws IOException
	{
		Set<TileCoord> flippedTiles = input.readLines(new Flipper());

		for (int day = 1; day <= days; day++)
		{
			Set<TileCoord> afterState = new HashSet<>();

			for (TileCoord flippedTile : flippedTiles)
			{
				// flip if 0 or >2 black tiles adjacent

				Set<TileCoord> neighbours = neighbours(flippedTile);
				int adjacentFlippedTiles = Sets.intersection(neighbours, flippedTiles).size();
				if (adjacentFlippedTiles == 0 || adjacentFlippedTiles > 2)
				{
					// flip back - do nothing
				}
				else
				{
					// remains flipped
					afterState.add(flippedTile);
				}

				Set<TileCoord> unflippedNeighbours = unflippedNeighbours(flippedTile, flippedTiles);
				for (TileCoord unflippedNeighbour : unflippedNeighbours)
				{
					Set<TileCoord> neighbourNeighbours = neighbours(unflippedNeighbour);
					int adjacentFlippedNeighboursTiles = Sets.intersection(neighbourNeighbours, flippedTiles).size();
					if (adjacentFlippedNeighboursTiles == 2)
					{
						// 2 neighbours unflipped - flip this
						afterState.add(unflippedNeighbour);
					}
				}
			}

			flippedTiles = afterState;
		}

		return flippedTiles.size();
	}

	private Set<TileCoord> unflippedNeighbours(TileCoord tc, Set<TileCoord> flippedTiles)
	{
		Set<TileCoord> neighbours = neighbours(tc);
		neighbours.removeAll(flippedTiles);
		return neighbours;
	}

	private Set<TileCoord> neighbours(TileCoord tc)
	{
		return Sets.newHashSet(
						new TileCoord(tc.x() - 1, tc.y()), // w
						new TileCoord(tc.x() + 1, tc.y()), // e
						new TileCoord(tc.y() % 2 == 0 ? tc.x() + 1 : tc.x(), tc.y() + 1), // se
						new TileCoord(tc.y() % 2 == 0 ? tc.x() : tc.x() - 1, tc.y() + 1), // sw
						new TileCoord(tc.y() % 2 == 0 ? tc.x() : tc.x() - 1, tc.y() - 1), // nw
						new TileCoord(tc.y() % 2 == 0 ? tc.x() + 1 : tc.x(), tc.y() - 1)); // ne
	}

	class Flipper implements LineProcessor<Set<TileCoord>>
	{
		private final Set<TileCoord> flippedTiles = new HashSet<>();

		@Override
		public boolean processLine(String line) throws IOException
		{
			int x = 0;
			int y = 0;

			String normalizedLine = StringUtils.replaceEach(line,
							new String[] { "se", "sw", "nw", "ne" },
							new String[] { "a", "b", "c", "d" });

			for (int i = 0; i < normalizedLine.length(); i++)
			{
				char ch = normalizedLine.charAt(i);
				if (ch == 'e')
					x++;
				else if (ch == 'w')
					x--;
				else if (ch == 'a') // se
				{
					if (y % 2 == 0)
						x++;
					y++;
				}
				else if (ch == 'b') // sw
				{
					if (y % 2 != 0)
						x--;
					y++;
				}
				else if (ch == 'c') // nw
				{
					if (y % 2 != 0)
						x--;
					y--;
				}
				else if (ch == 'd') // ne
				{
					if (y % 2 == 0)
						x++;
					y--;
				}
				else
					assert false : ch;
			}

			TileCoord tc = new TileCoord(x, y);
			if (flippedTiles.contains(tc))
				flippedTiles.remove(tc);
			else
				flippedTiles.add(tc);

			return true;
		}

		@Override
		public Set<TileCoord> getResult()
		{
			return flippedTiles;
		}
	}

	record TileCoord(int x, int y)
	{

	}
}
