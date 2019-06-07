package game;

import gameobjects.GameobjectsHandler;
import gameobjects.Player;
import input.Keyboard;
import levels.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable {

    //Game dimensions
    public static final int WIDTH = 256; // Pixels horizontally
    public static final int HEIGHT = WIDTH / 16 * 9; // Pixels vertically
    private final int SCALE = 3;

    //Thread stuff
    private Thread thread;
    private boolean running = false;

    //Screen stuff
    private BufferedImage image;
    private int[] pixels;

    //levels
    private Level currentLevel;
    private LinkedList<Level> levels;

    //input stuff
    private Keyboard key;

    //Gameobjects stuff
    private GameobjectsHandler gameobjectsHandler;

    private Game() {
        loadLevels();
        setupInput();
        setupGameobjects();
        createScreen();
        start();
    }

    private synchronized void  start(){
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    private synchronized void  stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //game loop
    @Override
    public void run() {
        int updates = 0;
        int frames = 0;
        double delta = 0;
        double amountOfTicks = 60.0; //updates cap
        double ns = 1000000000.0 / amountOfTicks;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        requestFocus();
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1){
                tick();
                updates++;
                delta--;
                render();
                frames++;
            }
            // render() here to unlock fps (currently 60)
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " UPS: " + updates);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        gameobjectsHandler.tickGameobjects();
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        currentLevel.renderTiles(getPixels());
        gameobjectsHandler.renderGameobjects();

        g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        bs.show();
    }

    private void createScreen(){
        JFrame frame = new JFrame("Game");
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        frame.getContentPane().add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }

    private void loadLevels(){
        levels = new LinkedList<>();
        levels.add(new Level(5, 0, ID.StartingLevel, false));
        levels.add(new Level(6, 0, ID.UpperLevel, true));
        levels.add(new Level(7, 0, ID.LeftLevel, true));
        currentLevel = getLevel(ID.StartingLevel);
    }

    private void setupInput(){
        key = new Keyboard();
        addKeyListener(key);
    }

    private void setupGameobjects(){
        gameobjectsHandler = new GameobjectsHandler(this);
        gameobjectsHandler.addGameObject(new Player(this, ID.player));
    }

    public Level getLevel(ID levelId){
        for(Level level: levels){
            if (level.getLevelId().equals(levelId)) return level;
        }
        return null;
    }

    private BufferedImage getImage() {
        return image;
    }

    public Keyboard getKey() {
        return key;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int[] getPixels() {
        return pixels;
    }

    public GameobjectsHandler getHandler() {
        return gameobjectsHandler;
    }

    public static void main(String[] args) {
        new Game();
    }
}
