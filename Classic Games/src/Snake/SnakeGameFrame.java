package Snake;

import GameCenter.GameMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class SnakeGameFrame extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu mGame, mOptions, mDifficultyLevel, mControl, mPointsToWin;
    private JMenuItem miRestart, miSnakeBodyColor, miSnakeHeadColor, miPause, miHard, miMedium, miEasy, miBestScore, miGameCenter;
    private JCheckBox web, walls;
    private ButtonGroup bgControl, bgWin;
    private JRadioButton arrows, wsad, points0, points1, points2, points3, points4, points5;
    private SnakeGamePanel gamePanel;
    private WindowEvent closingEvent;
    private Color bodyColor = Color.GREEN;
    private Color headColor = Color.MAGENTA;
    private int speed;

    public SnakeGameFrame(int speed){
        this.closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        this.menuBar = new JMenuBar();

        this.mGame = new JMenu("Game");
        this.miRestart = new JMenuItem("Restart");
        miRestart.addActionListener(this);
        this.miPause = new JMenuItem("Pause");
        miPause.addActionListener(this);
        this.miGameCenter = new JMenuItem("Back to Menu");
        miGameCenter.addActionListener(e -> closingEvent.getWindow().dispose());
        miGameCenter.addActionListener(e -> new GameMenu());

        this.mPointsToWin = new JMenu("Apple Limit");
        this.bgWin = new ButtonGroup();
        this.points0 = new JRadioButton("No limits", true);
        points0.addActionListener(this);
        this.points1 = new JRadioButton("20", false);
        points1.addActionListener(this);
        this.points2 = new JRadioButton("40", false);
        points2.addActionListener(this);
        this.points3 = new JRadioButton("60", false);
        points3.addActionListener(this);
        this.points4 = new JRadioButton("80", false);
        points4.addActionListener(this);
        this.points5 = new JRadioButton("100", false);
        points5.addActionListener(this);
        bgWin.add(points0);
        bgWin.add(points1);
        bgWin.add(points2);
        bgWin.add(points3);
        bgWin.add(points4);
        bgWin.add(points5);
        mPointsToWin.add(points0);
        mPointsToWin.add(points1);
        mPointsToWin.add(points2);
        mPointsToWin.add(points3);
        mPointsToWin.add(points4);
        mPointsToWin.add(points5);
        mGame.add(miRestart);
        mGame.add(miPause);
        mGame.add(mPointsToWin);
        mGame.add(miGameCenter);

        this.mOptions = new JMenu("Options");
        this.miSnakeHeadColor = new JMenuItem("Snake Head Color");
        miSnakeHeadColor.addActionListener(this);
        this.miSnakeBodyColor = new JMenuItem("Snake Body Color");
        miSnakeBodyColor.addActionListener(this);
        this.web = new JCheckBox("Web", true);
        web.addActionListener(this);
        this.walls = new JCheckBox("Walls", false);
        walls.addActionListener(this);

        this.mDifficultyLevel = new JMenu("Level");
        this.miHard = new JMenuItem("Hard");
        miHard.addActionListener(this);
        this.miMedium = new JMenuItem("Medium");
        miMedium.addActionListener(this);
        this.miEasy = new JMenuItem("Easy");
        miEasy.addActionListener(this);
        mDifficultyLevel.add(miHard);
        mDifficultyLevel.add(miMedium);
        mDifficultyLevel.add(miEasy);

        this.mControl = new JMenu("Control");
        this.bgControl = new ButtonGroup();
        this.arrows = new JRadioButton("Arrows", false);
        arrows.addActionListener(this);
        this.wsad = new JRadioButton("W-S-A-D", true);
        bgControl.add(arrows);
        bgControl.add(wsad);
        mControl.add(arrows);
        mControl.add(wsad);

        this.miBestScore = new JMenuItem("Best Score");
        miBestScore.addActionListener(this);

        mOptions.add(miSnakeHeadColor);
        mOptions.add(miSnakeBodyColor);
        mOptions.add(miBestScore);
        mOptions.add(web);
        mOptions.add(walls);
        mOptions.add(mDifficultyLevel);
        mOptions.add(mControl);

        menuBar.add(mGame);
        menuBar.add(mOptions);
        setJMenuBar(menuBar);

        this.speed = speed;
        this.gamePanel = new SnakeGamePanel(this.speed);
        setContentPane(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object z = e.getSource();

        if (z==miRestart){
            closingEvent.getWindow().dispose();
            new SnakeGameFrame(100);
        }
        else if (z == miPause){
            if (gamePanel.getP() == 0){
                gamePanel.setPause(true);
                gamePanel.setP(1);
            } else {
                gamePanel.setPause(false);
                gamePanel.setP(0);
            }

        }
        else if (z==web){
            if (web.isSelected()){
                gamePanel.setWeb(true);
            } else {
                gamePanel.setWeb(false);
            }
        }
        else if (z==wsad){
            gamePanel.setControl(true);
        }
        else if (z==arrows){
            gamePanel.setControl(false);
        }
        else if (z==walls){
            if (walls.isSelected()){
                gamePanel.setWalls(true);
            } else {
                gamePanel.setWalls(false);
            }
        }
        else if (z==miSnakeHeadColor){
            headColor = JColorChooser.showDialog(miSnakeHeadColor,"Snake Head color", headColor);
            gamePanel.setSnakeHeadColor(headColor);
        }
        else if (z==miSnakeBodyColor){
            bodyColor = JColorChooser.showDialog(miSnakeBodyColor, "Snake Body color", bodyColor);
            gamePanel.setSnakeBodyColor(bodyColor);
        }
        else if (z==miHard){
            closingEvent.getWindow().dispose();
            new SnakeGameFrame(30);
        }
        else if (z==miMedium){
            closingEvent.getWindow().dispose();
            new SnakeGameFrame(100);
        }
        else if (z==miEasy){
            closingEvent.getWindow().dispose();
            new SnakeGameFrame(150);
        }
        else if (z==points0){
            gamePanel.setPointsToWin(0);
        }
        else if (z==points1){
            gamePanel.setPointsToWin(20);
        }
        else if (z==points2){
            gamePanel.setPointsToWin(40);
        }
        else if (z==points3){
            gamePanel.setPointsToWin(60);
        }
        else if (z==points4){
            gamePanel.setPointsToWin(80);
        }
        else if (z==points5){
            gamePanel.setPointsToWin(100);
        }
        else if (z==miBestScore){
            try {
                gamePanel.showBestScore();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}