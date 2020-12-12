package advent;

import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day8
{
	public int part1(CharSource wrap) throws IOException
	{
		List<Instruction> instructions = wrap.readLines(new ProgramLoader());
		try
		{
			new ProgramRunner(instructions).run();
			throw new RuntimeException("should not complete normally");
		}
		catch (ProgramRunner.InfiniteLoopException e)
		{
			return e.getAccumulator();
		}
	}

	public int part2(CharSource wrap) throws IOException
	{
		List<Instruction> originalInstructions = Collections.unmodifiableList(wrap.readLines(new ProgramLoader()));

		for (int i = 0; i < originalInstructions.size(); i++)
		{
			System.out.println("#### swapping instruction at " + i);
			List<Instruction> changedInstructions = changeInstruction(originalInstructions, i);

			try
			{
				return new ProgramRunner(changedInstructions).run();
			}
			catch (ProgramRunner.InfiniteLoopException e)
			{
				// didn't complete
			}
		}

		throw new IllegalStateException("should not get here");
	}

	private List<Instruction> changeInstruction(List<Instruction> changedInstructions, int i)
	{
		List<Instruction> newInstructions = new ArrayList<>(changedInstructions);

		Instruction instruction = newInstructions.get(i);
		if (instruction instanceof JmpInstruction jmp)
		{
			newInstructions.set(i, new NopInstruction(jmp.getValue()));
		}
		else if (instruction instanceof NopInstruction nop)
		{
			newInstructions.set(i, new JmpInstruction(nop.getValue()));
		}
		return newInstructions;
	}

	class ProgramRunner
	{
		private List<Instruction> instructions;

		public ProgramRunner(List<Instruction> instructions)
		{
			this.instructions = instructions;
		}

		public int run() throws InfiniteLoopException
		{
			BitSet seen = new BitSet(instructions.size());
			AtomicInteger accumulator = new AtomicInteger(0);
			AtomicInteger programCounter = new AtomicInteger(0);

			while (programCounter.get() != instructions.size())
			{
				if (seen.get(programCounter.get()))
					throw new InfiniteLoopException(accumulator.get());

				Instruction instruction = instructions.get(programCounter.get());
				seen.set(programCounter.get());

				instruction.execute(accumulator, programCounter);
			}

			return accumulator.get();
		}

		private class InfiniteLoopException extends Exception
		{
			int accumulator;

			public InfiniteLoopException(int accumulator)
			{
				this.accumulator = accumulator;
			}

			public int getAccumulator()
			{
				return accumulator;
			}
		}
	}

	class ProgramLoader implements LineProcessor<List<Instruction>>
	{
		private List<Instruction> instructions = new ArrayList<>();

		@Override
		public boolean processLine(String line) throws IOException
		{
			var instNumSplit = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(line);
			var instruction = switch (instNumSplit.get(0))
							{
								case "nop" -> new NopInstruction(Integer.parseInt(instNumSplit.get(1)));
								case "acc" -> new AccInstruction(Integer.parseInt(instNumSplit.get(1)));
								case "jmp" -> new JmpInstruction(Integer.parseInt(instNumSplit.get(1)));
								default -> throw new RuntimeException(instNumSplit.toString());
							};
			instructions.add(instruction);

			return true;
		}

		@Override
		public List<Instruction> getResult()
		{
			return instructions;
		}
	}

	interface Instruction
	{
		void execute(AtomicInteger accumulator, AtomicInteger programCounter);
	}

	class NopInstruction implements Instruction
	{
		int value;

		public NopInstruction(int value)
		{
			this.value = value;
		}

		@Override
		public void execute(AtomicInteger accumulator, AtomicInteger programCounter)
		{
			programCounter.incrementAndGet();
		}

		public int getValue()
		{
			return value;
		}
	}

	class AccInstruction implements Instruction
	{
		int value;

		public AccInstruction(int value)
		{
			this.value = value;
		}

		@Override
		public void execute(AtomicInteger accumulator, AtomicInteger programCounter)
		{
			programCounter.incrementAndGet();
			accumulator.set(accumulator.get() + value);
		}

		public int getValue()
		{
			return value;
		}
	}

	class JmpInstruction implements Instruction
	{
		private int value;

		public JmpInstruction(int value)
		{
			this.value = value;
		}

		@Override
		public void execute(AtomicInteger accumulator, AtomicInteger programCounter)
		{
			programCounter.set(programCounter.get() + value);
		}

		public int getValue()
		{
			return value;
		}
	}
}
