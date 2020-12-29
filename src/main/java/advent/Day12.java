package advent;

import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

public class Day12
{
	public long part1(CharSource input) throws IOException
	{
		return input.readLines(new ShipPart1());
	}

	public long part2(CharSource input) throws IOException
	{
		return input.readLines(new ShipPart2());
	}

	abstract class Ship implements LineProcessor<Long>
	{
		protected int xPos = 0;
		protected int yPos = 0;

		@Override
		public boolean processLine(String line) throws IOException
		{
			System.out.println(line);
			String[] split = StringUtils.splitByCharacterType(line);
			switch (split[0])
			{
				case "N":
					north(Integer.parseInt(split[1]));
					break;
				case "S":
					south(Integer.parseInt(split[1]));
					break;
				case "E":
					east(Integer.parseInt(split[1]));
					break;
				case "W":
					west(Integer.parseInt(split[1]));
					break;
				case "L":
					rotateLeft(Integer.parseInt(split[1]));
					break;
				case "R":
					rotateRight(Integer.parseInt(split[1]));
					break;
				case "F":
					forward(Integer.parseInt(split[1]));
					break;
				default:
					assert false : split[0];
			}

			System.out.println(this);

			return true;
		}

		protected abstract void west(int delta);

		protected abstract void east(int delta);

		protected abstract void south(int delta);

		protected abstract void north(int delta);

		protected abstract void forward(int delta);

		protected abstract void rotateRight(int delta);

		protected abstract void rotateLeft(int delta);

		@Override
		public Long getResult()
		{
			return (long) (Math.abs(xPos) + Math.abs(yPos));
		}
	}

	class ShipPart2 extends Day12.Ship
	{
		private int waypointX = 10;
		private int waypointY = -1;

		@Override
		protected void west(int delta)
		{
			waypointX -= delta;
		}

		@Override
		protected void east(int delta)
		{
			waypointX += delta;
		}

		@Override
		protected void south(int delta)
		{
			waypointY += delta;
		}

		@Override
		protected void north(int delta)
		{
			waypointY -= delta;
		}

		@Override
		protected void forward(int delta)
		{
			int distanceX = waypointX * delta;
			int distanceY = waypointY * delta;

			xPos += distanceX;
			yPos += distanceY;
		}

		@Override
		protected void rotateRight(int delta)
		{
			for (int rotate90 = 0; rotate90 < delta; rotate90 += 90)
			{
				int oldX = waypointX, oldY = waypointY;
				waypointX = -oldY;
				waypointY = oldX;
			}
		}

		@Override
		protected void rotateLeft(int delta)
		{
			rotateRight(360 - delta);
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder(this)
							.append("xPos", xPos)
							.append("yPos", yPos)
							.append("waypointY", waypointY)
							.toString();
		}
	}

	class ShipPart1 extends Day12.Ship
	{
		protected int direction = 90;

		@Override
		protected void west(int delta)
		{
			xPos -= delta;
		}

		@Override
		protected void east(int delta)
		{
			xPos += delta;
		}

		@Override
		protected void south(int delta)
		{
			yPos += delta;
		}

		@Override
		protected void north(int delta)
		{
			yPos -= delta;
		}

		@Override
		protected void forward(int delta)
		{
			if (direction == 90)
				xPos += delta;
			else if (direction == 0)
				yPos -= delta;
			else if (direction == 180)
				yPos += delta;
			else if (direction == 270)
				xPos -= delta;
			else
				assert false : direction;
		}

		@Override
		protected void rotateRight(int delta)
		{
			direction += delta;
			direction %= 360;
		}

		@Override
		protected void rotateLeft(int delta)
		{
			direction -= delta;
			if (direction < 0)
				direction += 360;
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder(this)
							.append("xPos", xPos)
							.append("yPos", yPos)
							.append("direction", direction)
							.toString();
		}
	}
}
