package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.heursitics.*;

abstract class HeuristicOptions
{
    private String label;
    public HeuristicOptions(String label)
    {
        this.label = label;
    }
     public abstract Heuristic make(int rows, int cols);

    public String toString()
    {
        return label;
    }
}

class SumCellsOption extends HeuristicOptions
{
    public SumCellsOption()
    {
        super("Sum Cells");
    }

    @Override
    public Heuristic make(int rows, int cols)
    {
        return new SumCells();
    }
}

class LargestLowerOption extends HeuristicOptions
{
    public LargestLowerOption()
    {
        super("Largest Lower");
    }

    @Override
    public Heuristic make(int rows, int cols)
    {
        return new LargestLower();
    }
}

class LargestRightOption extends HeuristicOptions
{
    public LargestRightOption()
    {
        super("Largest Right");
    }

    @Override
    public Heuristic make(int rows, int cols)
    {
        return new LargestRight();
    }
}

class MonotonicOption extends HeuristicOptions
{
    public MonotonicOption()
    {
        super("Monotonic");
    }

    @Override
    public Heuristic make(int rows, int cols)
    {
        return new Monotonic();
    }
}

class DynamicSnakeOption extends HeuristicOptions
{
    public DynamicSnakeOption()
    {
        super("Dynamic Snake");
    }

    @Override
    public Heuristic make(int rows, int cols) {
        return new DynamicSnake(Math.max(rows, cols));
    }
}
