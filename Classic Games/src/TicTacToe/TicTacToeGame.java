package TicTacToe;

import GameCenter.GameMenu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.WindowEvent;

public class TicTacToeGame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu mGame;
    private JMenuItem miRestart, miGameCenter;
    private WindowEvent closingEvent;
    private Random random = new Random();
    private JFrame frame = new JFrame();
    private JPanel title_panel = new JPanel();
    private JPanel button_panel = new JPanel();
    private JLabel textField = new JLabel();
    private JButton[] buttons = new JButton[9];
    private boolean player1_turn;
    private boolean win = false;
    private int p;

    public TicTacToeGame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        this.closingEvent = new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING);
        this.p = 0;

        this.menuBar = new JMenuBar();
        this.mGame = new JMenu("Game");
        this.miRestart = new JMenuItem("Restart");
        miRestart.addActionListener(e -> closingEvent.getWindow().dispose());
        miRestart.addActionListener(e -> new TicTacToeGame());
        this.miGameCenter = new JMenuItem("Back to Menu");
        miGameCenter.addActionListener(e -> closingEvent.getWindow().dispose());
        miGameCenter.addActionListener(e -> new GameMenu());

        mGame.add(miRestart);
        mGame.add(miGameCenter);
        menuBar.add(mGame);

        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(255, 0, 150));
        textField.setFont(new Font("Ink free", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic Tac Toe");
        textField.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);
        title_panel.add(textField);

        button_panel.setLayout(new GridLayout(3, 3));
        //button_panel.setBackground(new Color(255, 50, 0));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setBorder(BorderFactory.createEtchedBorder());
            buttons[i].setBackground(new Color(255, 50, 100));
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        frame.setJMenuBar(menuBar);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        firstTurn();
    }

    private void firstTurn() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textField.setText("Turn: O");
        } else {
            player1_turn = false;
            textField.setText("Turn: X");
        }

    }

    private void plus() {
        this.p++;
    }

    private int getP() {
        return this.p;
    }

    private void check() {

        // O Win
        if ((buttons[0].getText() == "O") &&
                (buttons[1].getText() == "O") &&
                (buttons[2].getText() == "O")) {
            oWins(0, 1, 2);
        }
        if ((buttons[3].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[5].getText() == "O")) {
            oWins(3, 4, 5);
        }
        if ((buttons[6].getText() == "O") &&
                (buttons[7].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(6, 7, 8);
        }
        if ((buttons[0].getText() == "O") &&
                (buttons[3].getText() == "O") &&
                (buttons[6].getText() == "O")) {
            oWins(0, 3, 6);
        }
        if ((buttons[1].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[7].getText() == "O")) {
            oWins(1, 4, 7);
        }
        if ((buttons[2].getText() == "O") &&
                (buttons[5].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(2, 5, 8);
        }
        if ((buttons[0].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(0, 4, 8);
        }
        if ((buttons[2].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[6].getText() == "O")) {
            oWins(2, 4, 6);
        }
        // X Win
        if ((buttons[0].getText() == "X") &&
                (buttons[1].getText() == "X") &&
                (buttons[2].getText() == "X")) {
            xWins(0, 1, 2);
        }
        if ((buttons[3].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[5].getText() == "X")) {
            xWins(3, 4, 5);
        }
        if ((buttons[6].getText() == "X") &&
                (buttons[7].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(6, 7, 8);
        }
        if ((buttons[0].getText() == "X") &&
                (buttons[3].getText() == "X") &&
                (buttons[6].getText() == "X")) {
            xWins(0, 3, 6);
        }
        if ((buttons[1].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[7].getText() == "X")) {
            xWins(1, 4, 7);
        }
        if ((buttons[2].getText() == "X") &&
                (buttons[5].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(2, 5, 8);
        }
        if ((buttons[0].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(0, 4, 8);
        }
        if ((buttons[2].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[6].getText() == "X")) {
            xWins(2, 4, 6);
        }


    }

    private void xodraw() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textField.setText("Draw!!!");
    }

    private void xWins(int a, int b, int c) {
        win = true;
        buttons[a].setBackground(new Color(50, 250, 100));
        buttons[b].setBackground(new Color(50, 250, 100));
        buttons[c].setBackground(new Color(50, 250, 100));
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textField.setText("X Win!!!");
    }

    private void oWins(int a, int b, int c) {
        win = true;
        buttons[a].setBackground(new Color(50, 250, 100));
        buttons[b].setBackground(new Color(50, 250, 100));
        buttons[c].setBackground(new Color(50, 250, 100));
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textField.setText("O Win!!!");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (getP() != 9) {
                if (e.getSource() == buttons[i]) {
                    if (player1_turn) {
                        if (buttons[i].getText() == "") {
                            buttons[i].setForeground(new Color(25, 50, 250));
                            buttons[i].setText("O");
                            player1_turn = false;
                            textField.setText("Turn: X");
                            check();
                            plus();
                            System.out.println("p = " + getP());
                        }
                    } else {
                        if (buttons[i].getText() == "") {
                            buttons[i].setForeground(new Color(250, 230, 10));
                            buttons[i].setText("X");
                            player1_turn = true;
                            textField.setText("Turn: O");
                            check();
                            plus();
                            System.out.println("p = " + getP());
                        }
                    }
                }
            } else if (!win){
                xodraw();
            }

        }

    }


}
