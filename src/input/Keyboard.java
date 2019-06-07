package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
    private boolean up, down, left, right;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) up = true;
        if(key == KeyEvent.VK_S) down = true;
        if(key == KeyEvent.VK_A) left = true;
        if(key == KeyEvent.VK_D) right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) up = false;
        if(key == KeyEvent.VK_S) down = false;
        if(key == KeyEvent.VK_A) left = false;
        if(key == KeyEvent.VK_D) right = false;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}
