package levels.tiles;

import game.ID;
import game.Spritesheet;

public abstract class Tile {

    private int[] pixels;
    private int tileWidth, tileHeight;
    private boolean isSolid, isCollectable;
    private int xPixel, yPixel;
    private ID id;

    // For positionOnSpritesheetX read tile number from left to right, start counting from 0 not 1
    // For positionOnSpritesheetY read tile number from up to down, start counting from 0 not 1

    protected Tile(int positionOnSpritesheetX, int positionOnSpritesheetY, int xPixel, int yPixel, ID id) {
        this.id = id;
        this.tileWidth = 16; // How many pixels horizontally
        this.tileHeight = 16; // How many pixels vertically
        this.xPixel = xPixel;
        this.yPixel = yPixel;
        pixels = new int[tileWidth * tileHeight];
        Spritesheet.textures.getImage().getRGB((positionOnSpritesheetX * 16), (positionOnSpritesheetY * 16), tileWidth,
                tileHeight, pixels, 0, tileWidth);
    }

    public int[] getPixels() {
        return pixels;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isCollectable() {
        return isCollectable;
    }

    public int getxPixel() {
        return xPixel;
    }

    public int getyPixel() {
        return yPixel;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public void setCollectable(boolean collectable) {
        isCollectable = collectable;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public ID getId() {
        return id;
    }
}
