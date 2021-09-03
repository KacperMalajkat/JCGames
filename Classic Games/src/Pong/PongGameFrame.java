package Pong;

import GameCenter.GameMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class PongGameFrame extends JFrame implements ActionListener {

    private PongGamePanel panel;
    private WindowEvent closingEvent;
    private JMenuBar menuBar;
    private JMenu mGame, mPoints, mOptions;
    private ButtonGroup points;
    private JRadioButton points0, points1, points2, points3, points4, points5;
    private JMenuItem miRestart, miPause, miColorP1, miColorP2, miGameCenter;
    private Color player1Color;
    private Color player2Color;

    public PongGameFrame() {
        this.panel = new PongGamePanel();
        this.closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        this.player1Color = panel.paddle1.getPlayer1Color();
        this.player2Color = panel.paddle2.getPlayer2Color();

        this.menuBar = new JMenuBar();
        this.mGame = new JMenu("Game");
        this.miRestart = new JMenuItem("Restart");
        miRestart.addActionListener(e -> closingEvent.getWindow().dispose());
        miRestart.addActionListener(e -> new PongGameFrame());
        this.miPause = new JMenuItem("Pause");
        miPause.addActionListener(this);

        this.miGameCenter = new JMenuItem("Back to Menu");
        miGameCenter.addActionListener(e -> closingEvent.getWindow().dispose());
        miGameCenter.addActionListener(e -> new GameMenu());

        mGame.add(miRestart);
        mGame.add(miPause);
        mGame.add(miGameCenter);

        this.mOptions = new JMenu("Options");
        this.miColorP1 = new JMenuItem("Player 1 Color");
        miColorP1.setForeground(player1Color);
        miColorP1.addActionListener(this);
        this.miColorP2 = new JMenuItem("Player 2 Color");
        miColorP2.setForeground(player2Color);
        miColorP2.addActionListener(this);

        this.mPoints = new JMenu("Points");
        this.points = new ButtonGroup();
        this.points0 = new JRadioButton("No limits",true);
        points0.addActionListener(e -> panel.setPointsToWin(0));
        this.points1 = new JRadioButton("10",false);
        points1.addActionListener(e -> panel.setPointsToWin(10));
        this.points2 = new JRadioButton("15",false);
        points2.addActionListener(e -> panel.setPointsToWin(15));
        this.points3 = new JRadioButton("20",false);
        points3.addActionListener(e -> panel.setPointsToWin(20));
        this.points4 = new JRadioButton("25",false);
        points4.addActionListener(e -> panel.setPointsToWin(25));
        this.points5 = new JRadioButton("30",false);
        points5.addActionListener(e -> panel.setPointsToWin(30));
        points.add(points0);
        points.add(points1);
        points.add(points2);
        points.add(points3);
        points.add(points4);
        points.add(points5);

        mPoints.add(points0);
        mPoints.add(points1);
        mPoints.add(points2);
        mPoints.add(points3);
        mPoints.add(points4);
        mPoints.add(points5);

        mOptions.add(miColorP1);
        mOptions.add(miColorP2);
        mOptions.add(mPoints);

        menuBar.add(mGame);
        menuBar.add(mOptions);
        setJMenuBar(menuBar);

        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    protected Color getPlayer1Color(){
        return this.player1Color;
    }
    protected Color getPlayer2Color(){
        return this.player2Color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miColorP1){
            player1Color = JColorChooser.showDialog(miColorP1,"Snake Head color", player1Color);
            panel.setP1Color(player1Color);
            panel.paddle1.setPlayer1Color(player1Color);
            miColorP1.setForeground(player1Color);
        }
        else if (e.getSource() == miColorP2){
            player2Color = JColorChooser.showDialog(miColorP2,"Snake Head color", player2Color);
            panel.setP2Color(player2Color);
            panel.paddle2.setPlayer2Color(player2Color);
            miColorP2.setForeground(player2Color);
        }
        else if (e.getSource() == miPause){
            if (panel.getP() == 0){
                panel.setPause(true);
                panel.setP(1);
            }else {
                try {
                    panel.sleep();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                panel.setPause(false);
                panel.setP(0);
            }
        }


    }

}
