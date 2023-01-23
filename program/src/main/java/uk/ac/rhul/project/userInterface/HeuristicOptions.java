package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.heursitics.Heuristic;
import uk.ac.rhul.project.heursitics.LargestLower;
import uk.ac.rhul.project.heursitics.SumCells;
import uk.ac.rhul.project.heursitics.Monotonic;

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
