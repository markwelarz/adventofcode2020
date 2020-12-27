package advent;

import com.google.common.collect.Lists;
import com.google.common.io.CharSource;
import com.google.common.primitives.Chars;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import uk.co.openkappa.bitrules.Classifier;
import uk.co.openkappa.bitrules.ImmutableClassifier;
import uk.co.openkappa.bitrules.MatchingConstraint;
import uk.co.openkappa.bitrules.schema.Schema;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day11
{
	public long part1(CharSource input) throws IOException
	{
		SeatingArea seatingArea = new SeatingAreaPart1(input.read());
		seatingArea.print();
		System.out.println();
		while (seatingArea.applyRules())
		{
			seatingArea.print();
			System.out.println();
		}

		return seatingArea.countUnoccupied();
	}

	public long part2(CharSource input) throws IOException
	{
		SeatingArea seatingArea = new SeatingAreaPart2(input.read());
		int i = 0;
		while (seatingArea.applyRules())
		{
			seatingArea.print();
			System.out.println();
		}

		return seatingArea.countUnoccupied();
	}
}

abstract class SeatingArea
{
	public static final int occupied = '#';
	public static final int empty = 'L';
	private static final int nothing = '.';

	public char[] seatingAreaData;
	protected int seatingAreaWidth;
	private Classifier<Integer, Integer> seatingAreaRules;

	SeatingArea(String input, int geVisibleOccupied)
	{
		this.seatingAreaWidth = input.lines().limit(1).mapToInt(String::length).sum(); // there is only 1 entry
		this.seatingAreaData = StringUtils.deleteWhitespace(input).toCharArray();
		this.seatingAreaRules = buildRules(geVisibleOccupied);
	}

	private Classifier<Integer, Integer> buildRules(int visibleOccupied)
	{
		return ImmutableClassifier.<String, Integer, Integer>builder(
						Schema.<String, Integer>create()
										.withAttribute("thisSeat", this::thisSeat)
										.withAttribute("above", this::aboveOccupied)
										.withAttribute("below", this::belowOccupied)
										.withAttribute("left", this::leftOccupied)
										.withAttribute("right", this::rightOccupied)
										.withAttribute("aboveLeft", this::aboveLeftOccupied)
										.withAttribute("aboveRight", this::aboveRightOccupied)
										.withAttribute("belowLeft", this::belowLeftOccupied)
										.withAttribute("belowRight", this::belowRightOccupied)
										.withAttribute("countOccupied", this::countOccupied))
						.build(List.of(
										MatchingConstraint.<String, Integer>named("becomes empty")
														.eq("thisSeat", SeatingArea.occupied)
														.ge("countOccupied", visibleOccupied)
														.classification(SeatingArea.empty)
														.build(),

										MatchingConstraint.<String, Integer>named("becomes occupied")
														.eq("thisSeat", SeatingArea.empty)
														.eq("above", false)
														.eq("below", false)
														.eq("left", false)
														.eq("right", false)
														.eq("aboveLeft", false)
														.eq("aboveRight", false)
														.eq("belowLeft", false)
														.eq("belowRight", false)
														.classification(SeatingArea.occupied)
														.build()));
	}

	abstract boolean aboveRightOccupied(int currentPosition);

	abstract boolean aboveLeftOccupied(int currentPosition);

	abstract boolean rightOccupied(int currentPosition);

	abstract boolean leftOccupied(int currentPosition);

	abstract boolean belowLeftOccupied(int currentPosition);

	abstract boolean belowOccupied(int currentPosition);

	abstract boolean belowRightOccupied(int currentPosition);

	abstract boolean aboveOccupied(int currentPosition);

	public boolean applyRules()
	{
		StringBuilder newSeatingArea = new StringBuilder();
		for (int i = 0; i < seatingAreaData.length; i++)
		{
			//			System.out.println("apply rules: " + i + " above" + this.aboveOccupied(i) +
			//							" thisSeat " + this.thisSeat(i) +
			//							" below " + this.belowOccupied(i) +
			//							" left " + this.leftOccupied(i) +
			//							" right " + this.rightOccupied(i) +
			//							" aboveLeft " + this.aboveLeftOccupied(i) +
			//							" aboveRight " + this.aboveRightOccupied(i) +
			//							" belowLeft " + this.belowLeftOccupied(i) +
			//							" belowRight " + this.belowRightOccupied(i) +
			//							" countOccupied " + this.countOccupied(i));

			Optional<Integer> oi = seatingAreaRules.classification(i);

			int newChar = oi.orElse((int) seatingAreaData[i]);
			newSeatingArea.append((char) newChar);
		}

		char[] newSeatingAreaData = newSeatingArea.toString().toCharArray();
		boolean changed = !Arrays.equals(newSeatingAreaData, this.seatingAreaData);
		this.seatingAreaData = newSeatingAreaData;
		return changed;
	}

	public int thisSeat(int currentPosition)
	{
		if (currentPosition < 0 || currentPosition >= seatingAreaData.length)
			return SeatingArea.nothing;

		return seatingAreaData[currentPosition];
	}

	public int countOccupied(int currentPosition)
	{
		int occupied = 0;

		occupied += leftOccupied(currentPosition) ? 1 : 0;
		occupied += rightOccupied(currentPosition) ? 1 : 0;
		occupied += aboveOccupied(currentPosition) ? 1 : 0;
		occupied += belowOccupied(currentPosition) ? 1 : 0;
		occupied += aboveLeftOccupied(currentPosition) ? 1 : 0;
		occupied += aboveRightOccupied(currentPosition) ? 1 : 0;
		occupied += belowRightOccupied(currentPosition) ? 1 : 0;
		occupied += belowLeftOccupied(currentPosition) ? 1 : 0;

		return occupied;
	}

	public long countUnoccupied()
	{
		return Arrays.stream(ArrayUtils.toObject(this.seatingAreaData))
						.filter(v -> v.equals(Character.valueOf('#')))
						.count();
	}

	Coord currentPosition2d(int currentPosition)
	{
		return new Coord(currentPosition % seatingAreaWidth, currentPosition / seatingAreaWidth);
	}

	int currentPosition1d(Coord above)
	{
		return above.y() * seatingAreaWidth + above.x();
	}

	public void print()
	{
		Iterable<List<Character>> rows = Lists.partition(Chars.asList(seatingAreaData), seatingAreaWidth);
		for (List<Character> row : rows)
		{
			String rowString = row.stream().map(Object::toString).collect(Collectors.joining());
			System.out.println(rowString);
		}
	}
}

class SeatingAreaPart1 extends SeatingArea
{
	public SeatingAreaPart1(String input)
	{
		super(input, 4);
	}

	@Override
	public boolean aboveOccupied(int currentPosition)
	{
		Coord above = currentPosition2d(currentPosition).above();
		int position = currentPosition1d(above);
		return thisSeat(position) == '#';
	}

	@Override
	public boolean leftOccupied(int currentPosition)
	{
		Coord current = currentPosition2d(currentPosition);
		Coord left = current.left();
		int position = currentPosition1d(left);
		if (current.x() > 0)
			return thisSeat(position) == '#';
		else
			return false;
	}

	@Override
	public boolean belowOccupied(int currentPosition)
	{
		Coord below = currentPosition2d(currentPosition).below();
		int position = currentPosition1d(below);
		if (position >= 0 && position < seatingAreaData.length)
			return thisSeat(position) == '#';
		else
			return false;
	}

	@Override
	public boolean rightOccupied(int currentPosition)
	{
		Coord current = currentPosition2d(currentPosition);
		Coord right = current.right();
		int position = currentPosition1d(right);
		if (current.x() < this.seatingAreaWidth - 1)
			return thisSeat(position) == '#';
		else
			return false;
	}

	@Override
	public boolean aboveLeftOccupied(int currentPosition)
	{
		Coord current = currentPosition2d(currentPosition);
		Coord aboveLeft = current.above().left();
		int position = currentPosition1d(aboveLeft);
		if (current.x() > 0)
			return thisSeat(position) == '#';
		else
			return false;
	}

	@Override
	public boolean belowLeftOccupied(int currentPosition)
	{
		Coord current = currentPosition2d(currentPosition);
		Coord belowLeft = current.below().left();
		int position = currentPosition1d(belowLeft);
		if (current.x() > 0)
			return thisSeat(position) == '#';
		else
			return false;
	}

	@Override
	public boolean aboveRightOccupied(int currentPosition)
	{
		Coord current = currentPosition2d(currentPosition);
		Coord aboveRight = current.above().right();
		int position = currentPosition1d(aboveRight);
		if (current.x() < this.seatingAreaWidth - 1)
			return thisSeat(position) == '#';
		else
			return false;
	}

	@Override
	public boolean belowRightOccupied(int currentPosition)
	{
		Coord current = currentPosition2d(currentPosition);
		Coord belowRight = current.below().right();
		int position = currentPosition1d(belowRight);
		if (current.x() < this.seatingAreaWidth - 1)
			return thisSeat(position) == '#';
		else
			return false;
	}
}

class SeatingAreaPart2 extends SeatingArea
{
	public SeatingAreaPart2(String input)
	{
		super(input, 5);
	}

	@Override
	public boolean aboveOccupied(int currentPosition)
	{
		Coord above = currentPosition2d(currentPosition).above();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && above.y() >= 0)
		{
			int position = this.currentPosition1d(above);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			above = above.above();
		}

		return occupied;
	}

	@Override
	public boolean leftOccupied(int currentPosition)
	{
		Coord left = currentPosition2d(currentPosition).left();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && left.x() >= 0)
		{
			int position = this.currentPosition1d(left);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			left = left.left();
		}

		return occupied;
	}

	@Override
	public boolean belowOccupied(int currentPosition)
	{
		Coord below = currentPosition2d(currentPosition).below();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && below.y() < seatingAreaWidth)
		{
			int position = this.currentPosition1d(below);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			below = below.below();
		}

		return occupied;
	}

	@Override
	public boolean rightOccupied(int currentPosition)
	{
		Coord right = currentPosition2d(currentPosition).right();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && right.x() < seatingAreaWidth)
		{
			int position = this.currentPosition1d(right);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			right = right.right();
		}

		return occupied;
	}

	@Override
	public boolean aboveLeftOccupied(int currentPosition)
	{
		Coord aboveLeft = currentPosition2d(currentPosition).above().left();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && aboveLeft.x() >= 0 && aboveLeft.y() >= 0)
		{
			int position = this.currentPosition1d(aboveLeft);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			aboveLeft = aboveLeft.above().left();
		}

		return occupied;
	}

	@Override
	public boolean belowLeftOccupied(int currentPosition)
	{
		Coord belowLeft = currentPosition2d(currentPosition).below().left();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && belowLeft.x() >= 0 && belowLeft.y() < seatingAreaWidth)
		{
			int position = this.currentPosition1d(belowLeft);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			belowLeft = belowLeft.below().left();
		}

		return occupied;
	}

	@Override
	public boolean aboveRightOccupied(int currentPosition)
	{
		Coord aboveRight = currentPosition2d(currentPosition).above().right();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && aboveRight.y() >= 0 && aboveRight.x() < seatingAreaWidth)
		{
			int position = this.currentPosition1d(aboveRight);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			aboveRight = aboveRight.above().right();
		}

		return occupied;
	}

	@Override
	public boolean belowRightOccupied(int currentPosition)
	{
		Coord belowRight = currentPosition2d(currentPosition).below().right();
		boolean occupied = false, seenSeat = false;
		while (!occupied && !seenSeat && belowRight.x() < seatingAreaWidth && belowRight.y() < seatingAreaWidth)
		{
			int position = this.currentPosition1d(belowRight);
			occupied = this.thisSeat(position) == '#';
			seenSeat = this.thisSeat(position) == 'L';
			belowRight = belowRight.below().right();
		}

		return occupied;
	}
}

record Coord(int x, int y)
{
	Coord above()
	{
		return new Coord(x, y - 1);
	}

	Coord below()
	{
		return new Coord(x, y + 1);
	}

	Coord left()
	{
		return new Coord(x - 1, y);
	}

	Coord right()
	{
		return new Coord(x + 1, y);
	}
}
