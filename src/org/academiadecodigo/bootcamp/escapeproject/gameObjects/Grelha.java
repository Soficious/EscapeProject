package org.academiadecodigo.bootcamp.escapeproject.gameObjects;

//import org.academiadecodigo.bootcamp.escapeproject.CollisionDetector;
import org.academiadecodigo.bootcamp.escapeproject.position.Direction;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

/**
 * Created by codecadet on 12/02/17.
 */
public class Grelha {



    private Rectangle theMap;       //the bigger rectangle
    private static int PADDING = 10;
    private Room[] rooms;           //each Room has an array of Pictures on it. Their position must be set from the beginning .
    private Wall[] walls;           //the group of walls;
    private GridDoor[] doors;       //the group of doors;
    private Sprite sprite;
    //    private Picture[] spritePictures;
    private Collider collider;
    private boolean sceneOff = true;                       //When true, the sprite can move around.
    private boolean[] inRoom = new boolean[9];             //When true, the room will stop being dark permanently;
    private int mov = 10;                                  //number of pixels the sprite moves each time.
    private KeyboardInput keyboardInput;






    /**
     *        0      300     600     900
     *     0 -|-------|-------|-------|-
     *       _|_     _|_      |       |
     *       _E_     ___      |       |
     *        |       |       |       |
     *   300 -|--|J|--|--| |--|--| |--|-
     *        |      _|_     _|_      |
     *        |      ___     ___      |
     *        |       |       |       |
     *   600 -|-------|--| |--|-------|-
     *        |      _|_     _|_      |
     *        |      ___     ___      |
     *        |       |       |       |
     *   900 -|-------|-------|-------|-
     */


    public Grelha(){

        sprite = new Sprite();
        collider = new Collider();
        keyboardInput = new KeyboardInput(this);
        //keyboardInput.setSprite(sprite);
    }


//    public static int getPADDING() {
//        return PADDING;
//    }

    public boolean isInRoom(int roomNumber) {
        return inRoom[roomNumber];
    }

    public void setInRoom(int roomNumber, boolean inRoom) {
        this.inRoom[roomNumber] = inRoom;
    }

    public boolean isSceneOff() {
        return sceneOff;
    }

    public void setSceneOff(boolean sceneOff) {     //The boolean to switch between map mode and scene mode!!!
        this.sceneOff = sceneOff;
    }


    public void init() {

        theMap = new Rectangle(PADDING, PADDING, 900, 900);
        theMap.draw();

        walls = new Wall[17];           //17 walls
        defineWalls();

        doors = new GridDoor[10];       //10 doors
        defineDoors();

        rooms = new Room[9];            //9 rooms
        defineRooms();
        rooms[8].setHidden(false);


        //rooms[8].changePic(rooms[8].getPicture(0));
//
//        Sprite extra = new Sprite(new Picture(865,865,"resources/sprite and rooms/spritefront.jpg"),10);
//
//        sprite = extra;

        //todo: sprite pictures spritePictures = {the 4 pictures};
//        sprite = new Sprite(spritePictures, 10);

    }



    private void defineRooms() {
        int counter =0;
        for (int i = PADDING; i < (900 - PADDING); i += 300) {
            for (int j = PADDING; j < (900 - PADDING); j += 300) {

                Rectangle roomRec = new Rectangle(i, j, 300, 300);
                if (counter < 8) {
                    roomRec.setColor(Color.BLACK);
                    roomRec.fill();
                }

                rooms[counter] = new Room(roomRec);
                rooms[counter].setHidden(true);

                counter++;

            }
        }
    }


    //auxiliary method to put all the Doors into its array
    private void defineDoors() {

        //10 doors
        //VERTICAL DOORS
        Rectangle EXIT = new Rectangle(PADDING,100 +  PADDING, 25, 100);   //The exit door
        //group 1, the left
        Rectangle V1D1 = new Rectangle(275 + PADDING, PADDING + 100, 50, 100);
        Rectangle V1D2 = new Rectangle(275 + PADDING, PADDING + 400, 50, 100);
        Rectangle V1D3 = new Rectangle(275 + PADDING, PADDING + 700, 50, 100);
        //group 2, the right
        //Rectangle V2D1 = new Rectangle(575 + PADDING, PADDING + 100, 50, 100);    //not used
        Rectangle V2D2 = new Rectangle(575 + PADDING, PADDING + 400, 50, 100);
        Rectangle V2D3 = new Rectangle(575 + PADDING, PADDING + 700, 50, 100);

        //HORIZONTAL DOORS
        //group 1, the top
        Rectangle JOKE = new Rectangle(PADDING + 100, PADDING + 275, 100, 50);  //The Fake door (i = 6 in the array)
        Rectangle H1D2 = new Rectangle(400 + PADDING, PADDING + 275, 100, 50);
        Rectangle H1D3 = new Rectangle(700 + PADDING, PADDING + 275, 100, 50);

        //group 2, the bottom
        Rectangle H2D2 = new Rectangle(PADDING + 400, 575 + PADDING, 100, 50);

        Rectangle[] doorsrec = {EXIT, V1D1,V1D2,V1D3,V2D2,V2D3,JOKE,H1D2,H1D3,H2D2};
        for (int i = 0; i <doorsrec.length; i++) {
            doorsrec[i].setColor(Color.BLUE);
            doorsrec[i].fill();
            doors[i] = new GridDoor(doorsrec[i]);
        }

    }


    //auxiliary method to put all the walls into its array
    private void defineWalls() {

        //(17 walls, 4 groups)
        //VERTICAL WALLS

        //group 0, the leftmost
        Rectangle V0 = new Rectangle(PADDING, PADDING, 25, 900);

        //group 1
        Rectangle V1a1 = new Rectangle(275 + PADDING, PADDING, 50, 100);
        Rectangle V1a2 = new Rectangle(275 + PADDING, PADDING + 200, 50, 200);
        Rectangle V1b1 = new Rectangle(275 + PADDING, PADDING + 500, 50, 200);
        Rectangle V1b2 = new Rectangle(275 + PADDING, PADDING + 800, 50, 100);

        //group 2
        Rectangle V2a = new Rectangle(575 + PADDING, PADDING, 50, 400);
        Rectangle V2b = new Rectangle(575 + PADDING, PADDING + 500, 50, 200);
        Rectangle V2c = new Rectangle(575 + PADDING, PADDING + 800, 50, 100);

        //group 3, the rightmost
        Rectangle V3 = new Rectangle(875 + PADDING, PADDING, 25, 900);


        //HORIZONTAL WALLS (4 groups)

        //group 0, the top
        Rectangle H0 = new Rectangle(PADDING, PADDING, 900, 25);

        //group 1
        Rectangle H1 = new Rectangle(PADDING, 275 + PADDING, 100, 50);
        Rectangle H1a = new Rectangle(PADDING + 200, 275 + PADDING, 200, 50);
        Rectangle H1b = new Rectangle(PADDING + 500, 275 + PADDING, 200, 50);
        Rectangle H1c = new Rectangle(PADDING + 800, 275 + PADDING, 100, 50);

        //group2
        Rectangle H2 = new Rectangle(PADDING, 575 + PADDING, 400, 50);
        Rectangle H2a = new Rectangle(PADDING + 500, 575 + PADDING, 400, 50);

        //group 3
        Rectangle H3 = new Rectangle(PADDING, 875 + PADDING, 900, 25);


        //Converting them to walls and painting them (they won't be visible though - it's just handy for testing)

        Rectangle [] wallRec = {V0, V1a1, V1a2, V1b1, V1b2, V2a, V2b, V2c, V3, H0, H1, H1a, H1b, H1c, H2, H2a, H3}; // aux array

        for (int i = 0; i < wallRec.length; i++) {

            wallRec[i].setColor(Color.LIGHT_GRAY);
            wallRec[i].fill();
            walls[i] = new Wall(wallRec[i]);

        }
    }



//    private boolean intersectsDoors (GridDoor [] doors, double x, double y) {
//
//        for (int i = 0; i < doors.length; i++) {
//
//            if (collider.intersects(doors[i].getHitBoxPrompt(), x, y)) {
//
//                return true;
//            }
//        }
//        return false;
//    }


//    private boolean intersectsWalls (Wall [] walls, double x, double y) {
//
//        for (int i = 0; i < walls.length; i++) {
//
//            if (collider.intersects(walls[i].getRectangle(), x, y)) {
//
//                return true;
//            }
//        }
//        return false;
//    }

    public void move(Direction direction) {
//        int x = sprite.getShape().getX();
//        int y = sprite.getShape().getY();
//        int w = sprite.getShape().getWidth() + x;
//        int h = sprite.getShape().getHeight() + y;
//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(w);
//        System.out.println(h);


        if(collider.intersects(walls, sprite.getShape(),direction,mov)){
            return;
        }

        for (int i = 0; i < doors.length ; i++) {
            if(collider.intersects(doors[i].getRoomDoor(), sprite.getShape(),direction,mov) &&
                    doors[i].isLocked()){

                return;
            }
        }

        switch (direction){
            case UP:
                sprite.getShape().translate(0, -mov);
                sprite.getCurrentSprite().translate(0, -mov);
                break;
            case DOWN:
                sprite.getShape().translate(0, mov);
                sprite.getCurrentSprite().translate(0, mov);
                break;
            case LEFT:
                sprite.getShape().translate(-mov,0);
                sprite.getCurrentSprite().translate(-mov,0);
                break;
            case RIGHT:
                sprite.getShape().translate(mov,0);
                sprite.getCurrentSprite().translate(mov,0);
                break;
        }

    }

    public boolean isItTimeToPrompt(Direction direction){
        for(GridDoor hitdoor: doors){
            if(collider.intersects(hitdoor.getHitBoxPrompt(), sprite.getShape())) {
                return true;
            }
        }
        return false;
    }


    //method that checks if sprite collides with elements



//    public void colideDoors(){
//
//        if(){
//
//
//            if(intersectsDoor
//        }
//
//
//
//
//
//    }


}
