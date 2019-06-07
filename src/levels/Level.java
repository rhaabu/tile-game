package levels;

import game.Game;
import game.ID;
import game.Spritesheet;
import levels.tiles.*;

public class Level {
    private Tile[] tiles; // All level tiles
    private ID id;
    private boolean hasBullets;

    // For positionOnSpritesheetX read tile number from left to right, start counting from 0 not 1
    // For positionOnSpritesheetY read tile number from up to down, start counting from 0 not 1

    public Level(int positionOnSpritesheetX, int positionOnSpritesheetY, ID id, boolean hasBullets){
        int levelWidth = 16; // How many tiles horizontally
        int levelHeight = 9; // How many tiles vertically
        this.hasBullets = hasBullets;
        this.id = id;
        tiles = new Tile[levelWidth * levelHeight];
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                tiles[x + y * levelWidth] = getTile((positionOnSpritesheetX * 16) + x, (positionOnSpritesheetY * 16) + y, x * 16, y * 16); // Reading level tiles info from spritesheet
            }
        }
    }

    public void renderTiles(int[] pixels){
        for (int y = 0; y < Game.HEIGHT; y++) {
            for (int x = 0; x < Game.WIDTH; x++) {
                pixels[x + y * Game.WIDTH] = this.tiles[(x / 16) + (y / 16) * 16].getPixels()[(x & 15) + (y & 15) * 16];
            }
        }
    }

    private Tile getTile(int imageCoordinateX, int imageCooridnateY, int xPixel, int yPixel) {
        if (Spritesheet.textures.getImage().getRGB(imageCoordinateX, imageCooridnateY) == 0xFF3BFF2D) return new GrassTile(xPixel, yPixel);
        if (Spritesheet.textures.getImage().getRGB(imageCoordinateX, imageCooridnateY) == 0xFFFFD800) return new DiamondTile(xPixel, yPixel);
        if (Spritesheet.textures.getImage().getRGB(imageCoordinateX, imageCooridnateY) == 0xFFFE0000) return new EnemyTile(3, 0, xPixel, yPixel, ID.EnemyTileLower);
        if (Spritesheet.textures.getImage().getRGB(imageCoordinateX, imageCooridnateY) == 0xFFFF020F) return new EnemyTile(4, 0, xPixel, yPixel, ID.EnemyTileUpper);
        return new RockTile(xPixel, yPixel);
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public ID getLevelId() {
        return id;
    }

    public boolean hasBullets() {
        return hasBullets;
    }
}
