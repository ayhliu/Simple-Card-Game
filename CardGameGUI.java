import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class provides a GUI for solitaire games related to Simple Card Game.
 */
public class CardGameGUI extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Height of the game frame. */
	private static final int DEFAULT_HEIGHT = 302;
	/** Width of the game frame. */
	private static final int DEFAULT_WIDTH = 800;
	/** Width of a card. */
	private static final int CARD_WIDTH = 73;
	/** Height of a card. */
	private static final int CARD_HEIGHT = 97;
	/** Row (y coord) of the upper left corner of the first card. */
	private static final int LAYOUT_TOP = 30;
	/** Column (x coord) of the upper left corner of the first card. */
	private static final int LAYOUT_LEFT = 30;
	/**
	 * Distance between the upper left x coords of two horizonally adjacent cards.
	 */
	private static final int LAYOUT_WIDTH_INC = 100;
	/**
	 * Distance between the upper left y coords of two vertically adjacent cards.
	 */
	private static final int LAYOUT_HEIGHT_INC = 125;
	/** y coord of the "Replace" button. */
	private static final int BUTTON_TOP = 30;
	/** x coord of the "Replace" button. */
	private static final int BUTTON_LEFT = 540;
	/** Distance between the tops of the "Replace" and "Restart" buttons. */
	private static final int BUTTON_HEIGHT_INC = 50;
	/** y coord of the "n undealt cards remain" label. */
	private static final int LABEL_TOP = 160;
	/** x coord of the "n undealt cards remain" label. */
	private static final int LABEL_LEFT = 540;
	/**
	 * Distance between the tops of the "n undealt cards" and the "You lose/win"
	 * labels.
	 */
	private static final int LABEL_HEIGHT_INC = 35;

	/** The board (Board subclass). */
	private SimpleBoard board;

	/** The main panel containing the game components. */
	private JPanel panel;
	/** The Replace button. */
	private JButton replaceButton;
	/** The Restart button. */
	private JButton restartButton;
	/** The "number of undealt cards remain" message. */
	private JLabel statusMsg;
	/** The "you've won n out of m games" message. */
	private JLabel totalsMsg;
	/** The card displays. */
	private JLabel[] displayCards;
	/** The win message. */
	private JLabel winMsg;
	/** The loss message. */
	private JLabel lossMsg;
	/** The prev card message. */
	private JLabel prevMsg;
	/** The coordinates of the card displays. */
	private Point[] cardCoords;
	/** The Rules button. */
	private JButton rulesButton;
	/** The Difficulty button. */
	private JButton difficultyButton;
	/** The illegal replacement message. */
	private JLabel illegalMsg;

	/** The user has selected card #k. */
	private int selected;
	/** The number of games won. */
	private int totalWins;
	/** The number of games played. */
	private int totalGames;

	/**
	 * Initialize the GUI.
	 *
	 * @param gameBoard is a <code>Board</code> subclass.
	 */
	public CardGameGUI(SimpleBoard gameBoard) {
		board = gameBoard;
		totalWins = 0;
		totalGames = 0;

		// Initialize cardCoords using 5 cards per row.
		cardCoords = new Point[board.size()];
		int x = LAYOUT_LEFT;
		int y = LAYOUT_TOP;
		for (int i = 0; i < cardCoords.length; i++) {
			cardCoords[i] = new Point(x, y);
			if (i % 5 == 4) {
				x = LAYOUT_LEFT;
				y += LAYOUT_HEIGHT_INC;
			} else {
				x += LAYOUT_WIDTH_INC;
			}
		}

		selected = -1;
		initDisplay();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		repaint();
	}

	/**
	 * Initialize the display.
	 */
	private void initDisplay() {
		panel = new JPanel() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};

		setTitle("Simple Card Game");

		int numCardRows = (board.size() + 4) / 5;
		int height = DEFAULT_HEIGHT;
		if (numCardRows > 2) {
			height += (numCardRows - 2) * LAYOUT_HEIGHT_INC;
		}

		this.setSize(new Dimension(DEFAULT_WIDTH, height));
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, height - 20));
		displayCards = new JLabel[board.size()];
		for (int k = 0; k < board.size(); k++) {
			displayCards[k] = new JLabel();
			panel.add(displayCards[k]);
			displayCards[k].setBounds(cardCoords[k].x, cardCoords[k].y, CARD_WIDTH, CARD_HEIGHT);
			displayCards[k].addMouseListener(new MyMouseListener());
			selected = -1;
		}
		replaceButton = new JButton();
		replaceButton.setText("Replace");
		panel.add(replaceButton);
		replaceButton.setBounds(BUTTON_LEFT, BUTTON_TOP, 100, 30);
		replaceButton.addActionListener(this);

		restartButton = new JButton();
		restartButton.setText("Restart");
		panel.add(restartButton);
		restartButton.setBounds(BUTTON_LEFT, BUTTON_TOP + BUTTON_HEIGHT_INC, 100, 30);
		restartButton.addActionListener(this);

		statusMsg = new JLabel(board.deckSize() + " Undealt Cards Remain");
		panel.add(statusMsg);
		statusMsg.setBounds(LABEL_LEFT, LABEL_TOP, 250, 30);

		winMsg = new JLabel();
		winMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		winMsg.setFont(new Font("", Font.BOLD, 25));
		winMsg.setForeground(Color.GREEN);
		winMsg.setText("You Win!");
		panel.add(winMsg);
		winMsg.setVisible(false);

		lossMsg = new JLabel();
		lossMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		lossMsg.setFont(new Font("", Font.BOLD, 25));
		lossMsg.setForeground(Color.RED);
		lossMsg.setText("You Lose.");
		panel.add(lossMsg);
		lossMsg.setVisible(false);

		prevMsg = new JLabel();
		prevMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		prevMsg.setFont(new Font("", Font.BOLD, 12));
		prevMsg.setForeground(Color.RED);
		prevMsg.setText("");
		panel.add(prevMsg);
		prevMsg.setVisible(false);

		totalsMsg = new JLabel("You've Won " + totalWins + " out of " + totalGames + " Games");
		totalsMsg.setBounds(LABEL_LEFT, LABEL_TOP + 2 * LABEL_HEIGHT_INC, 250, 30);
		panel.add(totalsMsg);

		rulesButton = new JButton();
		rulesButton.setText("Rules");
		panel.add(rulesButton);
		rulesButton.setBounds(BUTTON_LEFT + 120, BUTTON_TOP, 100, 30);
		rulesButton.addActionListener(this);

		difficultyButton = new JButton();
		difficultyButton.setText("Difficulty");
		panel.add(difficultyButton);
		difficultyButton.setBounds(BUTTON_LEFT + 120, BUTTON_TOP + BUTTON_HEIGHT_INC, 100, 30);
		difficultyButton.addActionListener(this);

		illegalMsg = new JLabel("Illagal replacement!");
		illegalMsg.setBounds(LABEL_LEFT, LABEL_TOP + 3 * LABEL_HEIGHT_INC, 250, 30);
		illegalMsg.setFont(new Font("", Font.BOLD, 12));
		illegalMsg.setForeground(Color.RED);
		panel.add(illegalMsg);
		illegalMsg.setVisible(false);

		if (!board.anotherPlayIsPossible()) {
			signalLoss();
		}

		pack();
		getContentPane().add(panel);
		getRootPane().setDefaultButton(replaceButton);
		panel.setVisible(true);
	}

	/**
	 * Draw the display (cards and messages).
	 */
	@Override
	public void repaint() {
		for (int k = 0; k < board.size(); k++) {
			String cardImageFileName = imageFileName(board.cardAt(k), k == selected);
			URL imageURL = getClass().getResource(cardImageFileName);
			if (imageURL != null) {
				ImageIcon icon = new ImageIcon(imageURL);
				displayCards[k].setIcon(icon);
				displayCards[k].setVisible(true);
			} else {
				throw new RuntimeException("Card Image Not Found: \"" + cardImageFileName + "\"");
			}
		}
		statusMsg.setText(board.deckSize() + " Undealt Cards Remain");
		statusMsg.setVisible(true);
		totalsMsg.setText("You've Won " + totalWins + " out of " + totalGames + " Games");
		totalsMsg.setVisible(true);
		pack();
		panel.repaint();
	}

	/**
	 * Run the game.
	 */
	public void displayGame() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
			}
		});
	}

	/**
	 * Deal with the user clicking on something other than a button or a card.
	 */
	private void signalError() {
		Toolkit t = panel.getToolkit();
		t.beep();
	}

	/**
	 * Returns the image that corresponds to the input card. Image names have the
	 * format "[Rank][Suit].GIF" or "[Rank][Suit]S.GIF". The "S" indicates that the
	 * card is selected.
	 *
	 * @param c          Card to get the image for
	 * @param isSelected flag that indicates if the card is selected
	 * @return String representation of the image
	 */
	private String imageFileName(Card c, boolean isSelected) {
		String str = "Cards/";
		if (c == null) {
			return "Cards/Back.GIF";
		}
		str += c.rank() + c.suit();
		if (isSelected) {
			str += "S";
		}
		str += ".GIF";
		return str;
	}

	/**
	 * Respond to a button click (on either the "Replace" button or the "Restart"
	 * button).
	 *
	 * @param e the button click action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(replaceButton)) {
			// Gather all the selected cards.
			// Make sure that the selected cards represent a legal replacement.
			if (!board.isLegal(selected)) {
				illegalMsg.setVisible(true);
				signalError();
				return;
			}
			// Do the replace.
			board.replaceSelectedCards(selected);
			selected = -1;
			if (board.prevCard.suit().equals("Spades") || board.prevCard.suit().equals("Clubs")) {
				prevMsg.setForeground(Color.BLACK);
			} else {
				prevMsg.setForeground(Color.RED);
			}
			prevMsg.setText("PREV. Card: " + board.prevCard.toString());
			prevMsg.setVisible(true);
			if (board.isEmpty()) {
				signalWin();
			} else if (!board.anotherPlayIsPossible()) {
				signalLoss();
			}
			repaint();
		} else if (e.getSource().equals(restartButton)) {
			board.newGame();
			getRootPane().setDefaultButton(replaceButton);
			winMsg.setVisible(false);
			lossMsg.setVisible(false);
			prevMsg.setVisible(false);
			illegalMsg.setVisible(false);
			board.prevCard = null;
			selected = -1;
			repaint();
		} else if (e.getSource().equals(rulesButton)) {
			JOptionPane.showMessageDialog(null, "Simple Card Game Project\n"
					+ "Many lines of code of this project are from the NCVPS Activity Starter Code as boiler plate.\n\n"
					+ "Simple Card Game Rules:\n" + "Cards A, 2~10, J, Q, K have values 1~13 respectively.\n"
					+ "Define the value difference of card X and card Y as the value of Y subtracted by the value of X.\n"
					+ "Define two exceptions:\n" + "- The value difference of a K card and an A card is 1.\n"
					+ "- The value difference of an A card and a K card is -1.\n"
					+ "The player chooses the difficulty of this game by defining the amount of cards to deal.\n"
					+ "Less cards means more difficult.\n"
					+ "The player chooses a dealt card and replaces it with an undealt card.\n"
					+ "Cards are repeatedly removed and replaced by an undealt card, satisfying one of the following:\n"
					+ "- The value difference of the previously replaced card and the selected card to be replaced is 0.\n"
					+ "- The value difference of the previously replaced card and the selected card to be replaced is 1.\n"
					+ "- The selected card to be replaced has the same suit as the previously replaced card,\n"
					+ "  and the value difference of the previously replaced card and the selected card is -1.\n"
					+ "If no undealt card is left, the selected card to be replaced is removed without replacement.\n"
					+ "The player wins when all cards are removed.\n"
					+ "The player loses when there are remaining cards and cannot be removed.\n");
		} else if (e.getSource().equals(difficultyButton)) {
			while (true) {
				try {
					String m = JOptionPane.showInputDialog(
							"Current difficulty is " + board.size() + ".\nSet amount of cards to deal (range 1~52):");
					if (m == null) {
						break;
					}
					int boardSize = Integer.parseInt(m);
					if (boardSize > 0 && boardSize <= 52) {
						Runner.start(boardSize);
						this.dispose();
						break;
					}
				} catch (NumberFormatException nfe) {
				}
			}
		} else {
			signalError();
			return;
		}
	}

	/**
	 * Display a win.
	 */
	private void signalWin() {
		getRootPane().setDefaultButton(restartButton);
		prevMsg.setVisible(false);
		winMsg.setVisible(true);
		totalWins++;
		totalGames++;
	}

	/**
	 * Display a loss.
	 */
	private void signalLoss() {
		getRootPane().setDefaultButton(restartButton);
		prevMsg.setVisible(false);
		lossMsg.setVisible(true);
		totalGames++;
	}

	/**
	 * Receives and handles mouse clicks. Other mouse events are ignored.
	 */
	private class MyMouseListener implements MouseListener {

		/**
		 * Handle a mouse press on a card by toggling its "selected" property. Each card
		 * is represented as a label.
		 *
		 * @param e the mouse event.
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			for (int k = 0; k < board.size(); k++) {
				if (e.getSource().equals(displayCards[k]) && board.cardAt(k) != null) {
					if (selected != k) {
						selected = k;
					} else {
						selected = -1;
					}
					illegalMsg.setVisible(false);
					repaint();
					return;
				}
			}
			signalError();
		}

		/**
		 * Ignore a mouse exited event.
		 *
		 * @param e the mouse event.
		 */
		@Override
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * Ignore a mouse released event.
		 *
		 * @param e the mouse event.
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * Ignore a mouse entered event.
		 *
		 * @param e the mouse event.
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * Ignore a mouse clicked event.
		 *
		 * @param e the mouse event.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
		}

	}

}
