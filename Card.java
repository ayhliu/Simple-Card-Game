public class Card {

	private String suit;
	private String rank;
	private int pointValue;

	public Card(String cardRank, String cardSuit, int cardPointValue) {
		rank = cardRank;
		suit = cardSuit;
		pointValue = cardPointValue;
	}

	public String suit() {
		return suit;
	}

	public String rank() {
		return rank;
	}

	public int pointValue() {
		return pointValue;
	}

	public boolean matches(Card otherCard) {
		return otherCard.suit().equals(this.suit()) && otherCard.rank().equals(this.rank())
				&& otherCard.pointValue() == this.pointValue();
	}

	@Override
	public String toString() {
		String suitSymbol = "";
		if (suit.equals("Spades")) {
			suitSymbol = "♠";
		} else if (suit.equals("Clubs")) {
			suitSymbol = "♣";
		} else if (suit.equals("Hearts")) {
			suitSymbol = "♥";
		} else if (suit.equals("Diamonds")) {
			suitSymbol = "♦";
		}
		String rankChar = rank;
		if (rank.equals("Ace")) {
			rankChar = "A";
		} else if (rank.equals("Jack")) {
			rankChar = "J";
		} else if (rank.equals("Queen")) {
			rankChar = "Q";
		} else if (rank.equals("King")) {
			rankChar = "K";
		}
		return suitSymbol + rankChar;
	}

}
