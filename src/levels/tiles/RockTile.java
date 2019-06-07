package levels.tiles;

import game.ID;

public class RockTile extends Tile {
    public RockTile(int xPixel, int yPixel) {
        super(1, 0, xPixel, yPixel, ID.RockTile);
        setSolid(true);
    }
}
