import java.util.*;

public class RPSLS {
    enum Move {
        ROCK,
        PAPER,
        SCISSORS,
        LIZARD,
        SPOCK
    }

    public static int winner(Move player, Move computer) {
        if (player == computer) {
            return 0;
        }
        switch (player) {
            case ROCK:
                if (computer == Move.SCISSORS || computer == Move.LIZARD)
                    return 1;
                else
                    return -1;
            case PAPER:
                if (computer == Move.ROCK || computer == Move.SPOCK)
                    return 1;
                else
                    return -1;
            case SCISSORS:
                if (computer == Move.PAPER || computer == Move.LIZARD)
                    return 1;
                else
                    return -1;
            case LIZARD:
                if (computer == Move.PAPER || computer == Move.SPOCK)
                    return 1;
                else
                    return -1;
            case SPOCK:
                if (computer == Move.ROCK || computer == Move.SCISSORS)
                    return 1;
                else
                    return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Random r = new Random();
        int player_score = 0, computer_score = 0;
        for (int i = 1; i <= 5; i++) {
            System.out.println("Round " + i);
            System.out.print("Enter your move(rock,scissors,paper,lizard,spock):");
            String input = s.nextLine().toUpperCase();
            Move player;
            if (input.equals("ROCK")) {
                player = Move.ROCK;
            } else if (input.equals("PAPER")) {
                player = Move.PAPER;
            } else if (input.equals("SCISSORS")) {
                player = Move.SCISSORS;
            } else if (input.equals("LIZARD")) {
                player = Move.LIZARD;
            } else if (input.equals("SPOCK")) {
                player = Move.SPOCK;
            } else {
                System.out.println("Invalid Move!");
                i--;
                continue;
            }
            Move computer = Move.values()[r.nextInt(5)];
            System.out.println("Player Move : " + player);
            System.out.println("Computer Move : " + computer);
            int result = winner(player, computer);
            if (result == 1) {
                System.out.println("You win this round");
                player_score++;
            } else if (result == -1) {
                System.out.println("Computer win this round");
                computer_score++;
            } else {
                System.out.println("It is Tie");
            }
        }
        System.out.println("\nFinal Score");
        System.out.println("Player:" + player_score);
        System.out.println("Computer:" + computer_score);
        if (player_score > computer_score) {
            System.out.println("You win the game");
        } else if (computer_score > player_score) {
            System.out.println("Computer win the game");
        } else {
            System.out.println("Match draw");
        }
        s.close();
    }
}

