import javax.swing.*;
import java.awt.*;
public class HeaderPanel extends JPanel {
    int cash;
    JLabel cash_label = new JLabel("");
    JLabel status_label = new JLabel("");
    public HeaderPanel(Player player) {
	cash = player.getCash();
	setLayout(new GridLayout(1,2));
	add(cash_label);
	add(status_label);
	setCash("     Cach : " + player.getCash());
	setStatus("     Shuffled...");	
    }

    public void setStatus(String status){
	cash_label.setText("");
	cash_label.setText(status);
    }

    public void setCash(String cash){
	status_label.setText("");	
	status_label.setText(cash);
    }
    
}

