import java.util.ArrayList;
import java.util.List;

public class SimpleBoard {

	private Card[] cards;
	private Deck deck;

	/**
	 * Creates a new <code>Board</code> instance.
	 *
	 * @param size        the number of cards in the board
	 * @param ranks       the names of the card ranks needed to create the deck
	 * @param suits       the names of the card suits needed to create the deck
	 * @param pointValues the integer values of the cards needed to create the deck
	 */
	public SimpleBoard(int size, String[] ranks, String[] suits, int[] pointValues) {
		cards = new Card[size];
		deck = new Deck(ranks, suits, pointValues);
		dealMyCards();
	}

	/**
	 * Start a new game by shuffling the deck and dealing some cards to this board.
	 */
	public void newGame() {
		deck.shuffle();
		dealMyCards();
	}

	/**
	 * Accesses the size of the board.
	 *
	 * @return the size of the board
	 */
	public int size() {
		return cards.length;
	}

	/**
	 * Determines if the board is empty (has no cards).
	 *
	 * @return true if this board is empty; false otherwise.
	 */
	public boolean isEmpty() {
		for (Card card : cards) {
			if (card != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Deal a card to the kth position in this board. If the deck is empty, the kth
	 * card is set to null.
	 *
	 * @param k the index of the card to be dealt.
	 */
	public void deal(int k) {
		cards[k] = deck.deal();
	}

	/**
	 * Accesses the deck's size.
	 *
	 * @return the number of undealt cards left in the deck.
	 */
	public int deckSize() {
		return deck.size();
	}

	/**
	 * Accesses a card on the board.
	 *
	 * @return the card at position k on the board.
	 * @param k is the board position of the card to return.
	 */
	public Card cardAt(int k) {
		return cards[k];
	}

	/**
	 * Replaces selected cards on the board by dealing new cards.
	 *
	 * @param selectedCard is the index of the card to be replaced.
	 */
	public void replaceSelectedCards(int selectedCard) {
		prevCard = cards[selectedCard];
		deal(selectedCard);
	}

	/**
	 * Gets the indexes of the actual (non-null) cards on the board.
	 *
	 * @return a List that contains the locations (indexes) of the non-null entries
	 *         on the board.
	 */
	public List<Integer> cardIndexes() {
		List<Integer> selected = new ArrayList<>();
		for (int k = 0; k < cards.length; k++) {
			if (cards[k] != null) {
				selected.add(k);
			}
		}
		return selected;
	}

	/**
	 * Determine whether or not the game has been won, i.e. neither the board nor
	 * the deck has any more cards.
	 *
	 * @return true when the current game has been won; false otherwise.
	 */
	public boolean gameIsWon() {
		if (deck.isEmpty()) {
			for (Card c : cards) {
				if (c != null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Deal cards to this board to start the game.
	 */
	private void dealMyCards() {
		for (int k = 0; k < cards.length; k++) {
			cards[k] = deck.deal();
		}
	}

	public Card prevCard = null;

	public void setPrev(Card card) {
		prevCard = card;
	}

	/**
	 * Determines if the selected cards form a valid group for removal.
	 *
	 * @param selectedCard is the index of the card to be replaced.
	 * @return true if the selected cards form a valid group for removal; false
	 *         otherwise.
	 */
	public boolean isLegal(int selectedCard) {
		if (selectedCard == -1) {
			return false;
		} else if (prevCard == null) {
			return true;
		} else if (cards[selectedCard].pointValue() == prevCard.pointValue()) {
			return true;
		} else if (cards[selectedCard].pointValue() == prevCard.pointValue() + 1) {
			return true;
		} else if (cards[selectedCard].pointValue() == prevCard.pointValue() - 12) {
			return true;
		} else if (cards[selectedCard].suit().equals(prevCard.suit())
				&& cards[selectedCard].pointValue() == prevCard.pointValue() - 1) {
			return true;
		} else if (cards[selectedCard].suit().equals(prevCard.suit())
				&& cards[selectedCard].pointValue() == prevCard.pointValue() + 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determine if there are any legal plays left on the board.
	 *
	 * @return true if there is a legal play left on the board; false otherwise.
	 */
	public boolean anotherPlayIsPossible() {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] != null && isLegal(i)) {
				return true;
			}
		}
		return false;
	}

}
