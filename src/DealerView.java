import javax.swing.JPanel;
import java.awt.*;
public class DealerView extends JPanel {
    public DealerView() {
	this.setBackground(Color.black);
    }
    public void paintComponent(Graphics g){
	g.setColor(Color.black);
	g.fillRect(0, 0, 600, 200);
    }
}

