package uk.ac.rhul.project.expectimax;

@FunctionalInterface
public interface DepthFunction { 
    int depth(int cells);
}
