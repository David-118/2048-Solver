package ProofOfConcept.Heuristic2048;

public class Driver
{
    public static void main(String[] args)
    {
        Node2048 root = new MoveNode();
        root.init();

        while (root != null)
        {
            System.out.printf("Score : %d\n", root.getScore());
            root.expectimax(4);
            root.print();
            root = root.nextNode();
        }
    }
}
