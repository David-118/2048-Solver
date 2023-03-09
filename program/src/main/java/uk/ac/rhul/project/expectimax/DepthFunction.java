package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;

@FunctionalInterface
public interface DepthFunction { 
    int depth(int cells);
}
