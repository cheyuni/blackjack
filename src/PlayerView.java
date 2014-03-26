import javax.swing.JPanel;
import java.awt.*;
public class PlayerView extends JPanel {
    public PlayerView() {
    }
    public void paintComponent(Graphics g){
	g.setColor(Color.black);
	g.fillRect(0, 0, 600, 200);
    }
}
