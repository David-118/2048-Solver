package uk.ac.rhul.project.userInterface;

public interface View
{
    void addNewGameObserver(NewGameObserver handelNewGame);

    void addSolveObserver(SolveObserver handelSolve);

    void setValues(int[][] grid, int score);

    void updateGrid(int[][] grid, int score);
}
