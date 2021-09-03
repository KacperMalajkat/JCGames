package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener {

    private final int SCREEN_WIDTH = 700;
    private final int SCREEN_HEIGHT = 700;
    private static final int UNIT_SIZE = 25;
    private int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private int delay;
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
    private int bodyParts = 3;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private int p = 0;
    private char direction = 'R';
    private boolean running = false;
    private boolean web = true;
    private boolean pause = false;
    private boolean control = true;
    private boolean walls = false;
    private int pointsToWin;
    private boolean youWin = false;
    private Timer timer;
    private Random random;
    private Color snakeBodyColor = Color.GREEN;
    private Color snakeHeadColor = Color.MAGENTA;
    private ScoreNote scoreNote;

    public SnakeGamePanel(int delay) {
        this.delay = delay;
        this.pointsToWin = 0;
        this.scoreNote = new ScoreNote();
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.timer = new Timer(this.delay, this);
        startGame(this.timer);
    }

    void setPointsToWin(int x) {
        this.pointsToWin = x;
    }

    void setWalls(boolean x) {
        this.walls = x;
    }

    void setSnakeHeadColor(Color c) {
        this.snakeHeadColor = c;
    }

    void setSnakeBodyColor(Color c) {
        this.snakeBodyColor = c;
    }

    void setP(int x) {
        this.p = x;
    }

    int getP() {
        return this.p;
    }

    void setWeb(boolean x) {
        this.web = x;
    }

    void setPause(boolean x) {
        this.pause = x;
    }

    void setControl(boolean x) {
        this.control = x;
    }

    private void startGame(Timer timer) {
        newApple();
        this.running = true;
        this.timer = timer;
        this.timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw(Graphics g) throws IOException {
        if (!pause) {
            if (running) {

                if (web) {
                    for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                        g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                        g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                    }
                    for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                        g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                        g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                    }
                }

                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(snakeHeadColor);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        if ((applesEaten > 0) && (applesEaten % 10 == 0)) {
                            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                        } else {
                            g.setColor(snakeBodyColor);
                            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                        }
                    }
                    points(g);
                }
                if (pointsToWin != 0 && applesEaten == pointsToWin) {
                    youWin = true;
                    running = false;
                }
            } else if (!youWin) {
                if (pointsToWin == 0) {
                    savePoints(applesEaten);
                }
                gameOver(g);
            } else {
                winGame(g);
            }

        } else {
            stopGame(g);
        }
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    private void newApple() {
        this.appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        this.appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    private void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    private void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            // check if head collides with body
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            if (walls) {
                // check if head touches left border
                if (x[0] < 0) {
                    running = false;
                }
                // check if head touches right border
                if (x[0] > SCREEN_WIDTH) {
                    running = false;
                }
                // check if head touches top border
                if (y[0] < 0) {
                    running = false;
                }
                // check if head touches bottom border
                if (y[0] > SCREEN_HEIGHT) {
                    running = false;
                }
            } else {
                // Left wall
                if (x[0] < 0) {
                    x[0] = SCREEN_WIDTH;
                }
                // Right wall
                if (x[0] > SCREEN_WIDTH) {
                    x[0] = 0;
                }
                // top wall
                if (y[0] < 0) {
                    y[0] = SCREEN_HEIGHT;
                }
                // down wall
                if (y[0] > SCREEN_HEIGHT) {
                    y[0] = 0;
                }
            }


            if (!running) {
                timer.stop();
            }
        }

    }

    private void points(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics p = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - p.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }

    private void savePoints(int x) throws IOException {
        scoreNote.saveScore(x);
    }

    void showBestScore() throws IOException {
        scoreNote.readScores();
        scoreNote.getRecord();
        System.out.println(scoreNote.getMax());
    }

    private void stopGame(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics gm = getFontMetrics(g.getFont());
        g.drawString("Pause", (SCREEN_WIDTH - gm.stringWidth("Pause")) / 2, SCREEN_HEIGHT / 2);
    }

    private void winGame(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics wg = getFontMetrics(g.getFont());
        g.drawString("You Win!", (SCREEN_WIDTH - wg.stringWidth("You Win!")) / 2, SCREEN_HEIGHT / 2);
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics gm = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH - gm.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
        points(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            if (!pause) {
                move();
            }
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (control) {
                // W-S-A-D
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if (p == 0) {
                            setPause(true);
                            p = 1;
                        } else if (p == 1) {
                            setPause(false);
                            p = 0;
                        }
                        break;
                }
            } else {
                // U-D-L-R
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if (p == 0) {
                            setPause(true);
                            p = 1;
                        } else if (p == 1) {
                            setPause(false);
                            p = 0;
                        }
                        break;
                }
            }
        }

    }

    public static class ScoreNote {
        private int size = 0;
        private String[] tab = new String[50];
        private int[] tab1 = new int[10];
        private int max;

        void saveScore(int points) throws IOException {
            FileWriter fileWriter = null;

            try {
                fileWriter = new FileWriter("C:\\Jprojekty\\Snake Game\\SnakeV2\\src\\Score.txt", true);
                fileWriter.write(points + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert fileWriter != null;
                fileWriter.close();
            }
        }

        void readScores() throws IOException {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("C:\\Jprojekty\\Snake Game\\SnakeV2\\src\\Score.txt"));
                String linia;
                while ((linia = reader.readLine()) != null) {
                    tab[size] = Arrays.toString(linia.split("\n"));
                    try {
                        tab1[size] = Integer.parseInt(tab[size]);
                        size++;
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        void showAll() {
            for (String s : tab) {
                if (s != null) {
                    System.out.println(s);
                } else {
                    break;
                }
            }
        }

        void getRecord() {
            int p = 0;
            for (int j = 0; j < size; j++) {
                if (tab1[j] < tab1[j + 1]) {
                    p = tab1[j + 1];
                } else {
                    p = tab1[j];
                }
            }
            this.max = p;
        }

        int getMax() {
            return this.max;
        }

    }

}

