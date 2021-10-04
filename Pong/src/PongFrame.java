import javax.swing.*;

public class PongFrame extends JFrame {

    public PongFrame() {
        this.add(new PongPanel());
        this.setTitle("Pong");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
