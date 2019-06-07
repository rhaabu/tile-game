package levels.tiles;

import game.ID;

public class DiamondTile extends Tile {
    public DiamondTile(int xPixel, int yPixel) {
        super(2, 0, xPixel, yPixel, ID.DiamondTile);
        setCollectable(true);
    }
}
