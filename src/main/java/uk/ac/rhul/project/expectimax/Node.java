package uk.ac.rhul.project.expectimax;

public interface Node
{
    float getScore();
    Node nextNode();
    float getWeight();
    boolean validate();
}