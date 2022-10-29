package ProofOfConcept.Auto2048;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MoveNodeTest
{
    Node2048 rootNode;

    @BeforeEach
    void setUp()
    {
        this.rootNode = new MoveNode();
        rootNode.loadCustomGame(new int[][]{{0, 0}, {2, 2}});
    }

    @Test
    void getChildren()
    {
        Node2048[] children = this.rootNode.expectimax();
        assertEquals("[[2, 2], [0, 0]]", Arrays.deepToString(children));
        assertEquals("[[0, 0], [4, 0]]", Arrays.deepToString(children));
    }
}