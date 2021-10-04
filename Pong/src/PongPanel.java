import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PongPanel extends JPanel implements ActionListener {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 500;
    static final int BLOCK_WIDTH = 10;
    static final int BLOCK_HEIGHT = 50;
    static final int BALL_DIAMETER = 10;
    static final int DELAY = 1;
    final int redX = BLOCK_WIDTH*3;
    int redY = 250;
    final int blueX = GAME_WIDTH - 10 - BLOCK_WIDTH*3;
    int blueY = 250;
    int redScore = 0;
    int blueScore = 0;
    int ballX = GAME_WIDTH / 2;
    int ballY = GAME_HEIGHT / 2;
    int ballSpeedX;
    int ballSpeedY;
    int speedA = -3;
    int speedB = -3;
    int blockSpeed = 10;
    boolean running = false;
    Timer timer;
    Random random;

    public PongPanel() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());

        startGame();
    }

    public void startGame() {
        random = new Random();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        ballSpeedX = random.nextBoolean() ? speedA : speedB;
        ballSpeedY = random.nextBoolean() ? speedA : speedB;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {

            g.setColor(Color.red);
            g.fillRect(0, 0, 5, GAME_HEIGHT);

            g.setColor(Color.blue);
            g.fillRect(GAME_WIDTH - 5, 0, 5, GAME_HEIGHT);

            g.setColor(Color.gray);
            g.fillRect((GAME_WIDTH/2) -2, 0, 5, GAME_HEIGHT);

            g.setColor(Color.white);
            g.setFont(new Font("Helvetica",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(redScore + " - " + blueScore, (GAME_WIDTH - metrics.stringWidth(redScore + " - " + blueScore))/2, g.getFont().getSize());

            g.setColor(Color.red);
            g.fillRect(redX, redY, BLOCK_WIDTH, BLOCK_HEIGHT);

            g.setColor(Color.blue);
            g.fillRect(blueX, blueY, BLOCK_WIDTH, BLOCK_HEIGHT);

            g.setColor(Color.white);
            g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        }
        else {
            endGame(g);
        }
    }

    public void moveBall() {

            ballX += ballSpeedX;
            ballY += ballSpeedY;

            if(ballX <= 0) {
                ballSpeedX = -ballSpeedX;
            }
            else if (ballX >= GAME_WIDTH) {
                ballSpeedX = -ballSpeedX;
            }
            if(ballY <= 0) {
                ballSpeedY = -ballSpeedY;
            }
            else if (ballY >= GAME_HEIGHT - BALL_DIAMETER) {
                ballSpeedY = -ballSpeedY;
            }
            if (ballX - BALL_DIAMETER/2 == redX + BLOCK_WIDTH && (ballY - BALL_DIAMETER/2 >= redY && ballY - BALL_DIAMETER/2 <= redY + BLOCK_HEIGHT)) {
                ballSpeedX = -ballSpeedX;
                ballSpeedY = -ballSpeedY;
            }
            else if (ballX - BALL_DIAMETER/2 == blueX - BLOCK_WIDTH && (ballY + BALL_DIAMETER/2 >= blueY && ballY + BALL_DIAMETER/2 <= blueY + BLOCK_HEIGHT)) {
                ballSpeedX = -ballSpeedX;
                ballSpeedY = -ballSpeedY;
            }


    }

    public void newBall() {

       ballX = GAME_WIDTH / 2;
       ballY = GAME_HEIGHT / 2;
    }

    public void ballCheck() throws InterruptedException {

        if (ballX <= 0) {
            blueScore++;
            Thread.sleep(500);
            newBall();
        }
        else if (ballX >= GAME_WIDTH) {
            redScore++;
            Thread.sleep(500);
            newBall();
        }

    }

    public void scoreCheck() {
        if (redScore == 7 || blueScore == 7) {
            running = false;
        }
    }

    public void endGame(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Helvetica",Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(redScore + " - " + blueScore, (GAME_WIDTH - metrics.stringWidth(redScore + " - " + blueScore))/2, g.getFont().getSize());

        g.setColor(Color.white);
        g.setFont(new Font("Helvetica",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics2.stringWidth("Game Over"))/2, GAME_HEIGHT/2);

        if (redScore > blueScore) {
            g.setColor(Color.red);
            g.setFont(new Font("Helvetica",Font.BOLD, 40));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("Red Wins!", (GAME_WIDTH - metrics3.stringWidth("Red Wins!")) / 2, (2 * GAME_HEIGHT) / 3);
        }
        else {
            g.setColor(Color.blue);
            g.setFont(new Font("Helvetica",Font.BOLD, 40));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("Blue Wins!", (GAME_WIDTH - metrics3.stringWidth("Blue Wins!")) / 2, (2 * GAME_HEIGHT) / 3);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Helvetica",Font.BOLD, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press Space to Play Again", (GAME_WIDTH - metrics3.stringWidth("Press Space to Play Again")) / 2, (4 * GAME_HEIGHT) / 5);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            moveBall();
            try {
                ballCheck();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            scoreCheck();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (redY <= 0) {
                        redY = redY;
                    }
                    else {
                        redY -= blockSpeed;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (redY >= GAME_HEIGHT - BLOCK_HEIGHT) {
                        redY = redY;
                    }
                    else {
                        redY += blockSpeed;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (blueY <= 0) {
                        blueY = blueY;
                    }
                    else {
                        blueY -= blockSpeed;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (blueY >= GAME_HEIGHT - BLOCK_HEIGHT) {
                        blueY = blueY;
                    }
                    else {
                        blueY += 4;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    new PongFrame();
                    break;
            }

        }
    }

}
