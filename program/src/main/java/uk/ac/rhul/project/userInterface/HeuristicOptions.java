package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.heursitics.*;

abstract class HeuristicOptions
{
    private final String label;
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
        return new FailSetter(new Monotonic(), Float.MIN_VALUE);
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
        return new DynamicSnake(rows, cols);
    }
}

class Snake4x4Option extends HeuristicOptions
{
    public Snake4x4Option()
    {
        super("Snake 4x4");
    }

    @Override
    public Heuristic make(int rows, int cols) {
        return new Snake4x4();
    }
}