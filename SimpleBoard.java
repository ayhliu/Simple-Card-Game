import java.util.List;
import java.util.ArrayList;

public class SimpleBoard {
    private Card[] cards;
    private Deck deck;

    /**
     * Creates a new <code>Board</code> instance.
     * @param size the number of cards in the board
     * @param ranks the names of the card ranks needed to create the deck
     * @param suits the names of the card suits needed to create the deck
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
     * @return the size of the board
     */
    public int size() {
        return cards.length;
    }

    /**
     * Determines if the board is empty (has no cards).
     * @return true if this board is empty; false otherwise.
     */
    public boolean isEmpty() {
        for (int k = 0; k < cards.length; k++) {
            if (cards[k] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deal a card to the kth position in this board. If the deck is empty, the kth card is set to null.
     * @param k the index of the card to be dealt.
     */
    public void deal(int k) {
        cards[k] = deck.deal();
    }

    /**
     * Accesses the deck's size.
     * @return the number of undealt cards left in the deck.
     */
    public int deckSize() {
        return deck.size();
    }

    /**
     * Accesses a card on the board.
     * @return the card at position k on the board.
     * @param k is the board position of the card to return.
     */
    public Card cardAt(int k) {
        return cards[k];
    }

    /**
     * Replaces selected cards on the board by dealing new cards.
     * @param selectedCards is a list of the indices of the cards to be replaced.
     */
    public void replaceSelectedCards(List<Integer> selectedCards) {
        prevCard = cards[selectedCards.get(0)];
        for (Integer k : selectedCards) {
            deal(k.intValue());
        }
    }

    /**
     * Gets the indexes of the actual (non-null) cards on the board.
     * @return a List that contains the locations (indexes) of the non-null entries on the board.
     */
    public List<Integer> cardIndexes() {
        List<Integer> selected = new ArrayList<Integer>();
        for (int k = 0; k < cards.length; k++) {
            if (cards[k] != null) {
                selected.add(new Integer(k));
            }
        }
        return selected;
    }

    /**
     * Determine whether or not the game has been won,
     * i.e. neither the board nor the deck has any more cards.
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
    private String prevSuit = "";

    public void setPrev(Card card) {
        prevCard = card;
    }

    /**
     * Determines if the selected cards form a valid group for removal. 
     * @param selectedCards the list of the indices of the selected cards.
     * @return true if the selected cards form a valid group for removal; false otherwise.
     */
    public boolean isLegal(List<Integer> selectedCards) {
        if (selectedCards.size() != 1) {
            return false;
        } else if (prevCard == null) {
            return true;
        } else if (cards[selectedCards.get(0)].pointValue() == prevCard.pointValue()) {
            return true;
        } else if (cards[selectedCards.get(0)].pointValue() == prevCard.pointValue() + 1) {
            return true;
        } else if (cards[selectedCards.get(0)].pointValue() == prevCard.pointValue() - 12) {
            return true;
        } else if (cards[selectedCards.get(0)].suit().equals(prevCard.suit())
                && cards[selectedCards.get(0)].pointValue() == prevCard.pointValue() - 1) {
            return true;
        } else if (cards[selectedCards.get(0)].suit().equals(prevCard.suit())
                && cards[selectedCards.get(0)].pointValue() == prevCard.pointValue() + 12) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determine if there are any legal plays left on the board.
     * @return true if there is a legal play left on the board; false otherwise.
     */
    public boolean anotherPlayIsPossible() {
        for (int i = 0; i < cards.length; i++) {
            ArrayList<Integer> cardSelect = new ArrayList<Integer>();
            cardSelect.add((Integer) i);
            if (cards[cardSelect.get(0)] != null && isLegal(cardSelect) == true) {
                return true;
            }
        }
        return false;
    }
}