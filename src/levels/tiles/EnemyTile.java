package levels.tiles;

import game.ID;

public class EnemyTile extends Tile {

    public EnemyTile(int positionOnSpritesheetX, int positionOnSpritesheetY, int xPixel, int yPixel, ID id) {
        super(positionOnSpritesheetX, positionOnSpritesheetY, xPixel, yPixel, id);
        setSolid(true);
    }
}
