package uk.ac.rhul.project.expectimax;

import java.io.IOException;

/**
 * Function used to dump an expectimax tree (numbered with key) to a file
 */
@FunctionalInterface
public interface DmpTxt {
    /**
     * Write an expectimax tree (indexed with key) to a file.
     * @param key Number to identify the tree with.
     * @throws IOException Unable to write to file
     */
    void dmpTxt(int key) throws IOException;
}
