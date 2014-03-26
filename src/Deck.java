import java.util.Random;

public class Deck {
    final static int DECK_SIZE = Card.ONE_SUIT * 4;
    private Card[] cards;
    private int card_count = 0;
    private Random ran;

    public Deck(){
	ran = new Random();
	cards = new Card[DECK_SIZE];
	for(int i = 0; i < 4; ++i){
	    for(int j = 0; j < Card.ONE_SUIT; ++j){
		cards[card_count] = new Card(card_count, j, i + "" + j + ".png"); // validate image name
		++card_count;
	    }
	}
    }

    public Card popCard(){
	return cards[--card_count];
    }

    public int count(){ 
	return card_count;
    }

    public void shuffle(){ //Suffle!!!!!
	for(int i = 0; i < card_count; i++){
	    int random = ran.nextInt(DECK_SIZE);
	    Card temp = cards[i];
	    cards[i] = cards[random];
	    cards[random] = temp;
	}
    }
    
}
