package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PongGamePanel extends JPanel implements Runnable {

    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private static final int BALL_DIAMETER = 20;
    private static final int PADDLE_WIDTH = 25;
    private static final int PADDLE_HEIGHT = 100;
    private boolean endGame = false;
    private boolean player1Win = false;
    private boolean player2Win = false;
    private int pointsToWin = 0;
    private boolean pause = false;
    private int p = 0;
    private int speed = 0;
    private int allPoints=0;
    protected Color p1Color = Color.BLUE;
    protected Color p2Color = Color.red;
    private Thread gameThread;
    private Image image;
    private Graphics graphics;
    private Random random;
    protected Paddle paddle1;
    protected Paddle paddle2;
    private Ball ball;
    protected Score score;

    protected PongGamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    protected void sleep() throws InterruptedException {
        Thread.sleep(150);
    }

    protected void setPause(boolean x) {
        this.pause = x;
    }

    protected int getP() {
        return this.p;
    }

    protected void setP(int p) {
        this.p = p;
    }

    public void setPointsToWin(int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }

    protected void setP1Color(Color color){
        this.p1Color = color;
    }

    protected void setP2Color(Color color){
        this.p2Color = color;
    }

    protected Color getP1Color(){
        return this.p1Color;
    }

    protected Color getP2Color(){
        return this.p2Color;
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle1.setPlayer1Color(getP1Color());
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
        paddle2.setPlayer2Color(getP2Color());
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        if (!pause) {
            if (pointsToWin != 0) {
                if (score.player1 == pointsToWin) {
                    player1Win = true;
                    endGame = true;
                    gameOver(g, 1);
                }
                if (score.player2 == pointsToWin) {
                    player2Win = true;
                    endGame = true;
                    gameOver(g, 2);
                }
            }
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            score.draw(g);
            Toolkit.getDefaultToolkit().sync();
        } else {
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            score.draw(g);
            Toolkit.getDefaultToolkit().sync();
            pause(g);
        }
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {

        //bounce ball off top & bottom window edges
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        //bounce ball off paddles
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            if (speed <= 3){
                ball.xVelocity = ball.xVelocity + speed; //optional for more difficulty
                speed++;
            }
            if (ball.yVelocity > 0){
                if (speed <= 3){
                    ball.yVelocity = ball.yVelocity + speed; //optional for more difficulty
                    speed++;
                }
            }
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            if (speed <= 3){
                ball.xVelocity = ball.xVelocity + speed; //optional for more difficulty
                speed++;
            }
            if (ball.yVelocity > 0)
                if (speed <= 3){
                    ball.yVelocity= ball.yVelocity + speed; //optional for more difficulty
                    speed++;
                }
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //stops paddles at window edges
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        //give a player 1 point and creates new paddles & ball
        if (ball.x <= 0) {
            allPoints++;
            score.player2++;
            newPaddles();
            newBall();
            try {
                sleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Player 2: " + score.player2);
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            allPoints++;
            score.player1++;
            newPaddles();
            newBall();
            try {
                sleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Player 1: " + score.player1);
        }
    }

    protected void pause(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Consolas", Font.BOLD, 80));
        FontMetrics gm = getFontMetrics(g.getFont());
        g.drawString("Pause", (GAME_WIDTH - gm.stringWidth("Pause")) / 2, GAME_HEIGHT / 2);
    }

    private void gameOver(Graphics g, int z) {
        if (z == 1) {
            g.setColor(paddle1.getPlayer1Color());
            g.setFont(new Font("Consolas", Font.BOLD, 75));
            FontMetrics gm = getFontMetrics(g.getFont());
            g.drawString("Player 1 Win!", (GAME_WIDTH - gm.stringWidth("Player 1 Win!")) / 2, GAME_HEIGHT / 2);
        }
        if (z == 2) {
            g.setColor(paddle2.getPlayer2Color());
            g.setFont(new Font("Consolas", Font.BOLD, 75));
            FontMetrics gm = getFontMetrics(g.getFont());
            g.drawString("Player 2 Win!", (GAME_WIDTH - gm.stringWidth("Player 2 Win!")) / 2, GAME_HEIGHT / 2);
        }
    }

    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (!endGame) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                if (!pause){
                    move();
                }
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (p == 0) {
                    setPause(true);
                    setP(1);
                } else {
                    try {
                        sleep();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    setPause(false);
                    setP(0);
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
