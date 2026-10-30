public class Driver {
	public static void main(String[] args) {
		Card[] cards = new Card[5];
		int count = 0;

		Card[] input = {
		    new Card("Ace", "Spades"), new Card("King", "Hearts"),
		    new Card("Queen", "Diamonds"), new Card("Ace", "Clubs"),
		    new Card("Ace", "Spades")};

		for (Card card : input) {
			boolean duplicate = false;

			for (int i = 0; i < count; i++) {
				if (card.equals(cards[i])) {
					duplicate = true;
					break;
				}
			}

			if (duplicate) {
				System.out.println("Duplicate found: " + card);
				break;
			}

			cards[count] = card;
			count++;
		}
	}
}
