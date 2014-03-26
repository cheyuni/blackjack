import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlackJack extends JFrame implements ActionListener{
    private Deck deck;
    private Player player = new Player("player");
    private Player dealer = new Player("dealer");

    private JButton hit_btn = new JButton("Hit"); // footer button list
    private JButton stay_btn = new JButton("Stay");
    private JButton deal_btn = new JButton("Deal");
    private JButton double_btn = new JButton("Double");    

    private JButton bet10 = new JButton("10"); // JOption pane button list
    private JButton bet20 = new JButton("20");
    private JButton bet30 = new JButton("30");
    
    PlayerView player_view = new PlayerView(); // player view for grid layout
    DealerView dealer_view = new DealerView(); // dealer view for grid layout
    
    FooterPanel footer = new FooterPanel();
    HeaderPanel header = new HeaderPanel(player);    
    JPanel body = new JPanel(new GridLayout(2,1)); // for player and dealer view
    String[] bet_choice = {"30", "20", "10"};
   
    public BlackJack(){
    	setTitle("BlackJack _Cheyuni");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	setLayout(new BorderLayout());
	setBackground(Color.white);
	
	footer.add(hit_btn);
	footer.add(stay_btn);
	footer.add(double_btn);
	footer.add(deal_btn);
	
	hit_btn.addActionListener(this);
	stay_btn.addActionListener(this);
	double_btn.addActionListener(this);
	deal_btn.addActionListener(this);

	hit_btn.setEnabled(false); //When it started hit double and stay buttons are unnecessary
	double_btn.setEnabled(false);
	stay_btn.setEnabled(false);

	body.add(dealer_view); //add dealer and player view in body
	body.add(player_view);

	add(body, BorderLayout.CENTER);
	add(header, BorderLayout.NORTH);
	add(footer, BorderLayout.SOUTH);

	setSize(600,480);
    	setVisible(true);
    }

    private void hitPlayer() {
        Card card = player.receiveCard(deck.popCard());
        player_view.add(new JLabel(new ImageIcon("../images/" + card.getImageName())));
        player_view.updateUI();
    }

    private void hitDealerHide() { //show fliped card
        Card card = dealer.receiveCard(deck.popCard());
        dealer_view.add(new JLabel(new ImageIcon("../images/fliped.png")));
        dealer_view.updateUI();
    }

    private void hitDealer() {
        Card card = dealer.receiveCard(deck.popCard());
        dealer_view.add(new JLabel(new ImageIcon("../images/" + card.getImageName())));
        dealer_view.updateUI();
    }

    
    public void deal() {
	int response = JOptionPane.showOptionDialog( // JOption pane for select betting cash
						    null                       // Center in window.
						    , "please input next bet?"        // Message
						    , "next bet"               // Title in titlebar
						    , JOptionPane.YES_NO_OPTION  // Option type
						    , JOptionPane.PLAIN_MESSAGE  // messageType
						    , null                       // Icon (none)
						    , bet_choice                    // Button text as above.
						    , "Hello world!!"    // Default button's label
						    );
            
	//... Use a switch statement to check which button was clicked.
	switch (response) {
	case 0:
	    if(player.getCash() < 30){
		player.setBet(player.getBet());
		JOptionPane.showMessageDialog(null, "not enough money, betting your all money");		
	    }else{
		player.setBet(30);		
	    }
	    break;
	case 1:
	    if(player.getCash() < 30){
		player.setBet(player.getBet());
		JOptionPane.showMessageDialog(null, "not enough money, betting your all money");		
	    }else{
		player.setBet(20);
	    }
	    break;
	case 2:
	    if(player.getCash() < 30){
		player.setBet(player.getBet());
		JOptionPane.showMessageDialog(null, "not enough money, betting your all money");		
	    }else{
		player.setBet(10);
	    }
	    break;
	// case 3:
	// case -1:
	//     //... Both the quit button (3) and the close box(-1) handled here.
	//     System.exit(0);     // It would be better to exit loop, but...
	default:
	    //... If we get here, something is wrong.  Defensive programming.
	    JOptionPane.showMessageDialog(null, "Unexpected response " + response);
	}

	System.out.println(response);

        player_view.removeAll(); //reset all of thing
        dealer_view.removeAll();
        player_view.updateUI();
        dealer_view.updateUI();
        player.reset();
        dealer.reset();
	
	deck = new Deck(); // create new deck and suffle
	deck.shuffle();
	
        hitPlayer(); //start game
        hitDealerHide();
        hitPlayer();
        hitDealer();
    }

    public void winnerCheck(int bet){
	dealer_view.removeAll();
	dealer_view.updateUI();//reset dealerview for erase fliped card
	for(int i = 0; i < dealer.cardCount(); i++){
	    dealer_view.add(new JLabel(new ImageIcon("../images/" + dealer.cards[i].getImageName())));
 	}
	if(player.getValue() > 21){
	    header.setStatus("     player busted!!");	    
	    player.setCash(player.getCash() - bet);
	    header.setCash("     Cash : " + player.getCash());
	}else if(dealer.getValue() > 21){
	    header.setStatus("     dealer busted!!");
	    player.setCash(player.getCash() + bet);
	    header.setCash("     Cash : " + player.getCash());	    
	}else if(player.getValue() == dealer.getValue()){
	    header.setStatus("     push!!");
	    header.setCash("     Cash : " + player.getCash());	    
	}else if(player.getValue() > dealer.getValue()){
	    header.setStatus("     player win!!");
	    player.setCash(player.getCash() + bet);
	    header.setCash("     Cash : " + player.getCash());	    
	}else{
	    header.setStatus("     dealer win...!!");
	    player.setCash(player.getCash() - bet);
	    header.setCash("     Cash : " + player.getCash());	    
	}
	if(player.getCash() < 0){
	    JOptionPane.showMessageDialog(null, "you lose");
	    System.exit(0);
	}
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == deal_btn) {
            deal();
            hit_btn.setEnabled(true);
            stay_btn.setEnabled(true);
            double_btn.setEnabled(true);	    
            deal_btn.setEnabled(false);
        }

	if(e.getSource() == hit_btn) {
	    hitPlayer();
	    if(player.getValue() > 21){
		winnerCheck(player.getBet());
		hit_btn.setEnabled(false);
		stay_btn.setEnabled(false);
		double_btn.setEnabled(false);	    		
		deal_btn.setEnabled(true);
	    }
	    double_btn.setEnabled(false);	    			    
	}
	
	if(e.getSource() == double_btn){
	    hitPlayer();
	    if(player.getValue() <= 21){
		while (dealer.getValue() < 17 || player.getValue() > dealer.getValue()){
		    hitDealer();
		}
	    }
	    winnerCheck(player.getBet() * 2);	    		
	    hit_btn.setEnabled(false);
	    stay_btn.setEnabled(false);
	    double_btn.setEnabled(false);	    		
	    deal_btn.setEnabled(true);
	}

	if(e.getSource() == stay_btn){
            while (dealer.getValue() < 17 || player.getValue() > dealer.getValue()) {
                hitDealer();
            }
            winnerCheck(player.getBet());
            hit_btn.setEnabled(false);
            stay_btn.setEnabled(false);
            double_btn.setEnabled(false);
            deal_btn.setEnabled(true);
	}
    }
    
    public static void main(String [] args){
	new BlackJack();
    }
}
