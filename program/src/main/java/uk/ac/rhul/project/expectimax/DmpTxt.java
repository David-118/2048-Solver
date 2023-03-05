package uk.ac.rhul.project.expectimax;

import java.io.IOException;

@FunctionalInterface
public interface DmpTxt {
    void dmpTxt(int key) throws IOException;
}
