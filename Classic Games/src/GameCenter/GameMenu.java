package GameCenter;

import Pong.PongGameFrame;
import Snake.SnakeGameFrame;
import TicTacToe.TicTacToeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JFrame {

    private JTextField textField = new JTextField();
    private JButton game1, game2, game3;
    private JPanel button_panel = new JPanel();
    private final Font myFont = new Font("Consolas",Font.PLAIN,40);
    private final Font myButtonFont = new Font("Consolas",Font.ITALIC,40);
    private WindowEvent closingEvent;

    public GameMenu(){
        this.setTitle("Classic Games");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,350);
        this.setLayout(new BorderLayout());

        textField.setForeground(new Color(255,20,150));
        textField.setBackground(Color.BLACK);
        textField.setFont(myFont);
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("choose a game");
        textField.setEditable(false);
        textField.setOpaque(true);

        button_panel.setLayout(new GridLayout(3,1,2,0));

        game1 = new JButton("Snake");
        game1.setFont(myButtonFont);
        game1.setForeground(new Color(20, 165, 20));
        game1.setBackground(new Color(169,255,169));
        game1.setBorder(BorderFactory.createEtchedBorder());
        game1.addActionListener(e -> closingEvent.getWindow().dispose());
        game1.addActionListener(e -> new SnakeGameFrame(100));
        game1.setFocusable(false);
        button_panel.add(game1);

        game2 = new JButton("Tic Tac Toe");
        game2.setFont(myButtonFont);
        game2.setForeground(new Color(255,0,0));
        game2.setBackground(new Color(255,169,169));
        game2.setBorder(BorderFactory.createEtchedBorder());
        game2.addActionListener(e -> closingEvent.getWindow().dispose());
        game2.addActionListener(e -> new TicTacToeGame());
        game2.setFocusable(false);
        button_panel.add(game2);

        game3 = new JButton("Pong");
        game3.setFont(myButtonFont);
        game3.setForeground(new Color(0,0,255));
        game3.setBackground(new Color(169,169,255));
        game3.setBorder(BorderFactory.createEtchedBorder());
        game3.addActionListener(e -> closingEvent.getWindow().dispose());
        game3.addActionListener(e -> new PongGameFrame());
        game3.setFocusable(false);
        button_panel.add(game3);

        this.add(textField, BorderLayout.NORTH);
        this.add(button_panel);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
    }


}
