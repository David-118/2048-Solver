package ProofOfConcept.Auto2048;

public class Driver
{
    public static void main(String[] args)
    {
        Node2048 root = new MoveNode();
        root.init();
        root.getChildren();

        while (root != null)
        {
            System.out.printf("Score : %d\n", root.getScore());
            root.print();
            root = root.nextNode();
        }
    }
}
