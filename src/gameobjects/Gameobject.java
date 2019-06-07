package gameobjects;

import game.Game;
import game.ID;
import game.Spritesheet;
import levels.tiles.Tile;

public abstract class Gameobject {

    private int width, height;
    private double x, y, velX, velY;
    private boolean isVisible = true;
    private int[] pixels;
    private Game game;
    private ID id;

    // For positionOnSpritesheetX read tile number from left to right, start counting from 0 not 1
    // For positionOnSpritesheetY read tile number from up to down, start counting from 0 not 1

    protected Gameobject(int positionOnSpritesheetX, int positionOnSpritesheetY, int width, int height, Game game, ID id) {
        this.game = game;
        this.id = id;
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
        Spritesheet.textures.getImage().getRGB((positionOnSpritesheetX * 16), (positionOnSpritesheetY * 16), width,
                height, pixels, 0, width);
    }

    public abstract void tick();

    public void render(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                game.getPixels()[(x + (int) this.x) + (y + (int) this.y) * Game.WIDTH] = pixels[x + y * width];
            }
        }
    }


    protected boolean collision() {  // Checks gameobject collision with solid tiles
        for(int i = 0; i < game.getCurrentLevel().getTiles().length; i++){
            Tile tile = game.getCurrentLevel().getTiles()[i];
            double rightSideOfObject = x + width + velX;
            double leftSideOfObject = x + velX;
            double topSideOfObject = y + velY;
            double bottomSideOfObject = y + height + velY;

            int rightSideOfTile = tile.getxPixel() + tile.getTileWidth();
            int lefttSideOfTile = tile.getxPixel();
            int topSideOfTile = tile.getyPixel();
            int bottomSideOfTile = tile.getyPixel() + tile.getTileHeight();

            if (leftSideOfObject < rightSideOfTile && topSideOfObject < bottomSideOfTile
                    && rightSideOfObject > lefttSideOfTile && bottomSideOfObject > topSideOfTile) {
                if (tile.isSolid()) return true;
            }
        }
        return false;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public Game getGame() {
        return game;
    }

    public ID getId() {
        return id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
