package ProofOfConcept.Game2048;

import java.util.Random;
import java.util.Scanner;

public class Driver
{
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        Random rnd = new Random();

        int w, h;
        w = scan.nextInt();
        h = scan.nextInt();

        Game2048 game2048 = new Game2048(w, h, rnd);
        game2048.init();

        while (true)
        {
            System.out.println(game2048.getScore());
            game2048.print();

            char i = scan.next("[wWaAsSdD]").charAt(0);

            switch (i)
            {
                case 'w':
                case 'W':
                    if (game2048.move(DirectionVect.UP)) game2048.addRndCell();
                    break;

                case 's':
                case 'S':
                    if (game2048.move(DirectionVect.DOWN)) game2048.addRndCell();
                    break;

                case 'a':
                case 'A':
                    if (game2048.move(DirectionVect.LEFT)) game2048.addRndCell();
                    break;

                case 'd':
                case 'D':
                    if (game2048.move(DirectionVect.RIGHT)) game2048.addRndCell();
                    break;
            }
        }
    }
}
