import java.util.Scanner;

public class Runner{
    public static void main(String[] args){
        System.out.println("Simpe Card Game Project");
        System.out.println("Many lines of code of this project are from the NCVPS Activity Starter Code as boiler plate. \n");
        System.out.println("Simpe Card Game Rules: ");
        System.out.println("Cards A, 2~10, J, Q, K have values 1~13 respectively. ");
        System.out.println("Define the value difference of card X and card Y as the value of Y subtracted by the value of X. ");
        System.out.println("Define two exceptions: ");
        System.out.println("- The value difference of a K card and an A card is 1. ");
        System.out.println("- The value difference of an A card and a K card is -1. ");
        System.out.println("The player chooses the difficulty of this game by defining the amount of cards to deal. ");
        System.out.println("Less cards means more difficult. ");
        System.out.println("The player chooses a dealt card and replaces it with an undealt card. ");
        System.out.println("Cards are repeatedly removed and replaced by an undealt card, satisfying one of the following: ");
        System.out.println("- The value difference of the previously replaced card and the selected card to be replaced is 0. ");
        System.out.println("- The value difference of the previously replaced card and the selected card to be replaced is 1. ");
        System.out.println("- The selected card to be replaced has the same suit as the previously replaced card, ");
        System.out.println("  and the value difference of the previously replaced card and the selected card is -1. ");
        System.out.println("If no undealt card is left, the selected card to be replaced is removed without replacement. ");
        System.out.println("The player wins when all cards are removed. ");
        System.out.println("The player loses when there are remaining cards and cannot be removed. \n");

        Scanner scan = new Scanner(System.in);
        int BOARD_SIZE;
        do{
            System.out.print("Amount of Cards to Deal (range 2~52): ");
            BOARD_SIZE = scan.nextInt();
        }
        while(BOARD_SIZE < 2 || BOARD_SIZE > 52);
        scan.close();
        String[] RANKS = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] SUITS = {"Spades", "Clubs", "Hearts", "Diamonds"};
        int[] POINT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        SimpleBoard board = new SimpleBoard(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
        CardGameGUI gui = new CardGameGUI(board);
        gui.displayGame();
    }
}
