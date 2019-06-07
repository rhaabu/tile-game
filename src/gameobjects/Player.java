package gameobjects;

import game.Game;
import game.ID;
import levels.tiles.GrassTile;
import levels.tiles.Tile;

public class Player extends Gameobject {

    public Player(Game game, ID id) {
        super(0, 1, 16, 16, game, id);
        setVelX(0);
        setVelY(0);
        setX(Game.WIDTH / 2 - getWidth() / 2);
        setY(Game.HEIGHT / 2 - getHeight() / 2);
    }

    @Override
    public void tick() {
        if(getGame().getKey().isDown()) setVelY(2);
        if(getGame().getKey().isUp()) setVelY(-2);
        if((getGame().getKey().isUp() || getGame().getKey().isDown()) && collision()) setVelY(0);

        if(getGame().getKey().isRight())  setVelX(2);
        if(getGame().getKey().isLeft()) setVelX(-2);

        if((getGame().getKey().isRight() || getGame().getKey().isLeft()) && collision()) setVelX(0);

        if(!getGame().getKey().isLeft() && !getGame().getKey().isRight()) setVelX(0);
        if(!getGame().getKey().isUp() && !getGame().getKey().isDown()) setVelY(0);

        setX(getX() + getVelX());
        setY(getY() + getVelY());
        checkIfLevelChange();
    }


    @Override
    protected boolean collision() { // Check player collision with solid AND collectable tiles.
        for(int i = 0; i < getGame().getCurrentLevel().getTiles().length; i++){
            Tile tile = getGame().getCurrentLevel().getTiles()[i];
            double rightSideOfPlayer = getX() + getWidth() + getVelX();
            double leftSideOfPlayer = getX() + getVelX();
            double topSideOfPlayer = getY() + getVelY();
            double bottomSideOfPlayer = getY() + getHeight() + getVelY();

            int rightSideOfTile = tile.getxPixel() + tile.getTileWidth();
            int lefttSideOfTile = tile.getxPixel();
            int topSideOfTile = tile.getyPixel();
            int bottomSideOfTile = tile.getyPixel() + tile.getTileHeight();

            if (leftSideOfPlayer < rightSideOfTile && topSideOfPlayer < bottomSideOfTile
                    && rightSideOfPlayer > lefttSideOfTile && bottomSideOfPlayer > topSideOfTile) {
                if (tile.isSolid()) return true;
                else if(tile.isCollectable()) getGame().getCurrentLevel().getTiles()[i] = new GrassTile(tile.getxPixel(), tile.getyPixel());
            }
        }
        return false;
    }

    private void checkIfLevelChange(){
        if((getY() < 0) && (getGame().getCurrentLevel().getLevelId().equals(ID.StartingLevel))){  // Starting Level --> Upper Level
            levelSetup(ID.UpperLevel, getX(), 128);
        }

        if((getX() < 0) && (getGame().getCurrentLevel().getLevelId().equals(ID.StartingLevel))){  // Starting Level --> Left Level
            levelSetup(ID.LeftLevel, 240, getY());
        }

        if((getY() + 16 > 144) && (getGame().getCurrentLevel().getLevelId().equals(ID.UpperLevel))){  // Upper Level --> Starting Level
            levelSetup(ID.StartingLevel, getX(), 0);
        }

        if((getX() + 16 > 256) && (getGame().getCurrentLevel().getLevelId().equals(ID.LeftLevel))){  // Left Level --> Starting Level
            levelSetup(ID.StartingLevel, 0, getY());
        }
    }

    private void levelSetup(ID nextLevelId, double setPlayerPositionX, double setPlayerPositionY){
        getGame().setCurrentLevel(getGame().getLevel(nextLevelId));
        if(getGame().getCurrentLevel().hasBullets()) getGame().getHandler().setAddBullets(true);
            else getGame().getHandler().setRemoveBullets(true);
        setY(setPlayerPositionY);
        setX(setPlayerPositionX);
    }
}
