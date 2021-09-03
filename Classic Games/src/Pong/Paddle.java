package Pong;

import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{

    private final int id;
    private int yVelocity;
    private int speed = 10;
    private Color player1Color;
    private Color player2Color;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id=id;
        player1Color = Color.BLUE;
        player2Color = Color.red;
    }

    public void setPlayer1Color(Color color){
        this.player1Color = color;
    }
    public Color getPlayer1Color(){
        return this.player1Color;
    }
    public void setPlayer2Color(Color color){
        this.player2Color = color;
    }
    public Color getPlayer2Color(){
        return this.player2Color;
    }

    public void keyPressed(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) {
                    setYDirection(-speed);
                }
                if(e.getKeyCode()==KeyEvent.VK_S) {
                    setYDirection(speed);
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) {
                    setYDirection(-speed);
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                    setYDirection(speed);
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) {
                    setYDirection(0);
                }
                if(e.getKeyCode()==KeyEvent.VK_S) {
                    setYDirection(0);
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) {
                    setYDirection(0);
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                    setYDirection(0);
                }
                break;
        }
    }
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }
    public void move() {
        y = y + yVelocity;
    }
    public void draw(Graphics g) {
        if(id==1)
            g.setColor(player1Color);
        else
            g.setColor(player2Color);
        g.fillRect(x, y, width, height);
    }
}