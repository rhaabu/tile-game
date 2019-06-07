package gameobjects;

import game.Game;
import game.ID;

public class Bullet extends Gameobject {

    private int startingY, startingX;

    public Bullet(int xPixel, int yPixel, double velY, Game game, ID id) {
        super(9, 0, 4, 4, game, id);
        this.startingY = yPixel;
        this.startingX = xPixel;
        setX(xPixel);
        setY(yPixel);
        setVelY(velY);
    }

    @Override
    public void tick() {
        if(collision()) {
            setVisible(true);
            setX(startingX);
            setY(startingY);
        }
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        checkCollisionWithPlayer();

    }

    private void checkCollisionWithPlayer(){
        for(Gameobject obj: getGame().getHandler().getGameObjects()){
            if(obj.getId().equals(ID.player)){
                double rightSideOfPlayer = obj.getX() + obj.getWidth();
                double leftSideOfPlayer = obj.getX();
                double topSideOfPlayer = obj.getY() + 1; // +1 to fix offset
                double bottomSideOfPlayer = obj.getY() + obj.getHeight();

                double rightSideOfBullet = getX() + getWidth();
                double leftSideOfBullet = getX();
                double topSideOfBullet = getY();
                double bottomSideOfBullet = getY() + getHeight();

                if (leftSideOfBullet < rightSideOfPlayer && topSideOfBullet < bottomSideOfPlayer
                        && rightSideOfBullet > leftSideOfPlayer && bottomSideOfBullet > topSideOfPlayer){
                    setVisible(false);
                }
            }
        }
    }

    @Override
    public void render() {
        if(isVisible()) super.render();
    }
}
