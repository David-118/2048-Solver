package uk.ac.rhul.project.expectimax;

/**
 * DepthFunction is a function used to determine the depth of an expectation tree.
 */
@FunctionalInterface
public interface DepthFunction {
    /**
     * Call the depth function
     * @param k number of nodes on the baseline level of the tree.
     * @return depth of the next tree.
     */
    int depth(int k);
}
