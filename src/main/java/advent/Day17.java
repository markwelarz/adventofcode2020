package advent;

import com.google.common.collect.Sets;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17<CT extends Day17.Cube<CT>>
{
	public static long part1(CharSource input) throws IOException
	{
		return new Day17<CubePart1>().solve(input, (x, y, z, notUsed) -> new CubePart1(x, y, z));
	}

	public static long part2(CharSource input) throws IOException
	{
		return new Day17<HyperCube>().solve(input, HyperCube::new);
	}

	public long solve(CharSource input, CubeFactory<CT> factory) throws IOException
	{
		Set<CT> activeCubePart1s = parseInitialState(input, factory);

		for (int cycle = 0; cycle < 6; cycle++)
		{
			System.out.println("active cubes:" + activeCubePart1s);

			Set<CT> newState = new HashSet<>();

			for (CT active : activeCubePart1s)
			{
				Set<CT> neighbours = active.neighbouring();
				int activeNeighbours = Sets.intersection(activeCubePart1s, neighbours).size();
				if (activeNeighbours == 2 || activeNeighbours == 3)
				{
					// remain active
					newState.add(active);
				}

				// non-actives
				for (CT inactive : neighbours)
				{
					if (!activeCubePart1s.contains(inactive))
					{
						Set<CT> neighboursOfInactive = inactive.neighbouring();
						if (Sets.intersection(activeCubePart1s, neighboursOfInactive).size() == 3)
						{
							newState.add(inactive);
						}
					}
				}
			}
			activeCubePart1s = newState;
		}

		return activeCubePart1s.size();
	}

	Set<CT> parseInitialState(CharSource input, CubeFactory<CT> factory) throws IOException
	{
		Set<CT> state = new HashSet<>();
		List<String> lines = input.readLines();
		for (int y = 0; y < lines.size(); y++)
		{
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++)
			{
				if (line.charAt(x) == '#')
				{
					state.add(factory.create(x, y, 0, 0));
				}
			}
		}

		return state;
	}

	interface Cube<CT>
	{
		Set<CT> neighbouring();
	}

	@FunctionalInterface
	interface CubeFactory<CT>
	{
		CT create(int p1, int p2, int p3, int p4);
	}

	record CubePart1(int x, int y, int z) implements Cube<CubePart1>
	{
		@Override
		public Set<CubePart1> neighbouring()
		{
			int[] diffs = new int[] { -1, 0, 1 };
			Set<CubePart1> neighbours = new HashSet<>();

			for (int neigbourX : diffs)
			{
				for (int neigbourY : diffs)
				{
					for (int neigbourZ : diffs)
					{
						if (!(neigbourX == 0 && neigbourY == 0 && neigbourZ == 0))
						{
							neighbours.add(new CubePart1(x + neigbourX, y + neigbourY, z + neigbourZ));
						}
					}
				}
			}
			return neighbours;
		}
	}

	record HyperCube(int x, int y, int z, int w) implements Cube<HyperCube>
	{
		@Override
		public Set<HyperCube> neighbouring()
		{
			int[] diffs = new int[] { -1, 0, 1 };
			Set<HyperCube> neighbours = new HashSet<>();

			for (int neigbourX : diffs)
			{
				for (int neigbourY : diffs)
				{
					for (int neigbourZ : diffs)
					{
						for (int neigbourW : diffs)
						{
							if (!(neigbourX == 0 && neigbourY == 0 && neigbourZ == 0 && neigbourW == 0))
							{
								neighbours.add(new HyperCube(x + neigbourX, y + neigbourY, z + neigbourZ, w + neigbourW));
							}
						}
					}
				}
			}
			return neighbours;
		}
	}
}

