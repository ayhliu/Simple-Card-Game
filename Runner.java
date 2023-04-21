public class Runner {

	public static void main(String[] args) {
		int BOARD_SIZE = 20;
		start(BOARD_SIZE);
	}

	public static void start(int boardSize) {
		String[] RANKS = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		String[] SUITS = { "Spades", "Clubs", "Hearts", "Diamonds" };
		int[] POINT_VALUES = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		SimpleBoard board = new SimpleBoard(boardSize, RANKS, SUITS, POINT_VALUES);
		CardGameGUI gui = new CardGameGUI(board);
		gui.displayGame();
	}

}
