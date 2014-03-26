public class Card {
    private int num;
    private int card_value;
    private String image;
    public static final String SPADES = "spades";  
    public static final String HEARTS = "hearts"; 
    public static final String DIAMONDS = "diamonds"; 
    public static final String CLUBS = "clubs";
    public static final int ACE = 1; 
    public static final int JACK = 10; 
    public static final int QUEEN = 10; 
    public static final int KING = 10;
    public static final int ONE_SUIT = 13;

    Card(int _num, int _card_value, String _image) {
        this.num = _num;
        this.card_value = _card_value;
        this.image = _image;
    }

    public boolean isAce() {
        return card_value == 0;
    }

    public int getValue() { // cause card image name, I start value from 0
        if (card_value == 0) {
            return 1;
        }
        if (card_value >= 9) {
            return 10;
        }
        return card_value + 1;
    }

    public String getImageName() { // for return image name
        return this.image;
    }
}
