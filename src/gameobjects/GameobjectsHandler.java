package gameobjects;

import game.Game;
import game.ID;
import levels.tiles.Tile;

import java.util.LinkedList;

public class GameobjectsHandler {

    private LinkedList<Gameobject> gameObjects;
    private Game game;
    private boolean addBullets;
    private boolean removeBullets;

    public GameobjectsHandler(Game game) {
        this.game = game;
        this.gameObjects = new LinkedList<>();
    }

    public void tickGameobjects(){
        for(Gameobject object: gameObjects) {
            object.tick();
        }
        if(addBullets) bulletAdder(1.0);
        if(removeBullets) bulletRemover();
    }

    public void renderGameobjects(){
        for(Gameobject object: gameObjects) object.render();
    }


    private void bulletRemover() {
        LinkedList<Gameobject> bulletsToBeRemoved= new LinkedList<>();
        for(Gameobject obj: gameObjects){
           if(obj.getId().equals(ID.Bullet)) bulletsToBeRemoved.add(obj);
        }
        gameObjects.removeAll(bulletsToBeRemoved);
        removeBullets = false;
    }

    private void bulletAdder(double bulletSpeed) {
        for(Tile tile: game.getCurrentLevel().getTiles()){
            if(tile.getId().equals(ID.EnemyTileUpper))createNewBullet(tile.getxPixel() + 6, tile.getyPixel() - 4, -bulletSpeed);
            else if(tile.getId().equals(ID.EnemyTileLower)) createNewBullet(tile.getxPixel() + 6, tile.getyPixel() + 16, bulletSpeed);
        }
        addBullets = false;
    }

    private void createNewBullet(int xPixel, int yPixel, double velY){
        gameObjects.add(new Bullet(xPixel, yPixel, velY, game, ID.Bullet));
    }


    public void setAddBullets(boolean addBullets) {
        this.addBullets = addBullets;
    }

    public void setRemoveBullets(boolean removeBullets) {
        this.removeBullets = removeBullets;
    }

    public void addGameObject(Gameobject object){
        gameObjects.add(object);
    }

    public void removeGameObject(Gameobject object){
        gameObjects.remove(object);
    }

    public LinkedList<Gameobject> getGameObjects() {
        return gameObjects;
    }
}
