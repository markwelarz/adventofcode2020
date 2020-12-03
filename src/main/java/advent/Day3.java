package advent;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import com.diffplug.common.base.Errors;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.util.stream.Stream;

public class Day3
{
	public long part1(CharSource input) throws IOException
	{
		final Traversal part1Traversal = new Traversal(3, 1);

		return StreamUtils.zipWithIndex(input.lines())
						.map(v -> myPositionIs(v, part1Traversal))
						.filter(this::haveIHitTree)
						.count();
	}

	public long part2(CharSource input)
	{
		return part2Traversals()
						.mapToLong(v -> part2Move(input, v))
						.reduce(1, (l, r) -> l * r);
	}

	public long part2Move(CharSource input, Traversal traversal)
	{
		return StreamUtils.zipWithIndex(Errors.rethrow().get(input::lines))
						.filter(v -> (v.getIndex() % traversal.down()) == 0)
						.map(v -> myPositionIs(v, traversal))
						.filter(this::haveIHitTree)
						.count();
	}

	private Stream<Traversal> part2Traversals()
	{
		return Stream.of(new Traversal(1, 1),
						new Traversal(3, 1),
						new Traversal(5, 1),
						new Traversal(7, 1),
						new Traversal(1, 2));
	}

	private PositionAndTrees myPositionIs(Indexed<String> line, Traversal traversal)
	{
		int widthOfLandscape = line.getValue().length();
		long absolutePosition = (traversal.right() * line.getIndex()) / traversal.down();
		long cycledPosition = absolutePosition % widthOfLandscape;
		return new PositionAndTrees(line.getValue(), cycledPosition);
	}

	private boolean haveIHitTree(PositionAndTrees positionAndTrees)
	{
		return positionAndTrees.trees.charAt((int) positionAndTrees.position()) == '#';
	}

	record Traversal(int right, int down)
	{
	}

	record PositionAndTrees(String trees, long position)
	{
	}
}
