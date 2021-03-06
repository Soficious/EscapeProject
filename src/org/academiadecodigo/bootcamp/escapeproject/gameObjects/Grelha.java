package org.academiadecodigo.bootcamp.escapeproject.gameObjects;

//import org.academiadecodigo.bootcamp.escapeproject.CollisionDetector;

import org.academiadecodigo.bootcamp.escapeproject.graphics.ComputerPhoto;
import org.academiadecodigo.bootcamp.escapeproject.graphics.DoorsGameLoop;
import org.academiadecodigo.bootcamp.escapeproject.graphics.FinalDoor;
import org.academiadecodigo.bootcamp.escapeproject.position.Direction;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

/**
 * Created by codecadet on 12/02/17.
 */
public class Grelha {

    public static int QUESTIONCOUNTER = 0;

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
    private Rectangle[] doorsRectangles;
    private DoorsGameLoop doorsGameLoop;



    Thread testing;
    Thread finalDoorPrompt;
    boolean threadAlive = true;
    private int stepCount = 0;


    /**
     * 0      300     600     900
     * 0 -|-------|-------|-------|-
     * _|_     _|_      |       |
     * _E_     ___      |       |
     * |       |       |       |
     * 300 -|--|J|--|--| |--|--| |--|-
     * |      _|_     _|_      |
     * |      ___     ___      |
     * |       |       |       |
     * 600 -|-------|--| |--|-------|-
     * |      _|_     _|_      |
     * |      ___     ___      |
     * |       |       |       |
     * 900 -|-------|-------|-------|-
     */


    public Grelha() throws InterruptedException {

        collider = new Collider();
        keyboardInput = new KeyboardInput(this, doorsGameLoop);

        //keyboardInput.setSprite(sprite);
    }

    public boolean isInRoom(int roomNumber) {
        return inRoom[roomNumber];
    }

    public void setInRoom(int roomNumber, boolean inRoom) {
        this.inRoom[roomNumber] = inRoom;
    }

    public boolean isSceneOff() {
        return sceneOff;
    }

    public void setSceneOff(boolean sceneOff) {     //The boolean to switch between map mode and prompt mode!!!
        this.sceneOff = sceneOff;
    }


    public void init() {

        theMap = new Rectangle(PADDING, PADDING, 900, 900);
        theMap.draw();

        walls = new Wall[17];           //17 walls
        defineWalls();

        doors = new GridDoor[11];       //10 doors + 1 computer
        defineDoors();

        rooms = new Room[9];            //9 rooms
        defineRooms();
        setRoomPic();
        rooms[8].setHidden(false);
        rooms[8].printCurrentPic();

        try {
            testing = new Thread(new DoorsGameLoop(keyboardInput, this));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finalDoorPrompt = new Thread(new FinalDoor());




        //todo: sprite pictures spritePictures = {the 4 pictures};
        sprite = new Sprite();
    }

    private void defineRooms() {
        int counter = 0;
        for (int i = PADDING; i < (900 - PADDING); i += 300) {
            for (int j = PADDING; j < (900 - PADDING); j += 300) {

                Rectangle roomRec = new Rectangle(j, i, 300, 300);
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

    private void setRoomPic() {

        //stores all the sceneries
// TODO: 12/02/17 in a more advanced version, have an array of pictures for each room;

        Picture p8grass0 = new Picture(rooms[8].getRoomPosition().getX(), rooms[8].getRoomPosition().getY(), "resources/Rooms/defaults/8grass0.jpg");
        rooms[8].setCurrentPic(p8grass0);

        Picture p7class0 = new Picture(rooms[7].getRoomPosition().getX(), rooms[7].getRoomPosition().getY(), "resources/Rooms/defaults/7classroom0.jpg");
        rooms[7].setCurrentPic(p7class0);

        Picture p6copa0 = new Picture(rooms[6].getRoomPosition().getX(), rooms[6].getRoomPosition().getY(), "resources/Rooms/defaults/6copa0.jpg");
        rooms[6].setCurrentPic(p6copa0);

        Picture p5wc0 = new Picture(rooms[5].getRoomPosition().getX(), rooms[5].getRoomPosition().getY(), "resources/Rooms/defaults/5wc0.jpg");
        rooms[5].setCurrentPic(p5wc0);

        Picture p4hall0 = new Picture(rooms[4].getRoomPosition().getX(), rooms[4].getRoomPosition().getY(), "resources/Rooms/defaults/4hall0.jpg");
        rooms[4].setCurrentPic(p4hall0);

        Picture p3boss0 = new Picture(rooms[3].getRoomPosition().getX(), rooms[3].getRoomPosition().getY(), "resources/Rooms/defaults/3bossClue.jpg");
        rooms[3].setCurrentPic(p3boss0);

        Picture p2toilet0 = new Picture(rooms[2].getRoomPosition().getX(), rooms[2].getRoomPosition().getY(), "resources/Rooms/defaults/2toiletClue.jpg");
        rooms[2].setCurrentPic(p2toilet0);


        Picture p1secret0 = new Picture(rooms[1].getRoomPosition().getX(), rooms[1].getRoomPosition().getY(), "resources/Rooms/defaults/1secretroom0.jpg");
        rooms[1].setCurrentPic(p1secret0);

        Picture p0exit0 = new Picture(rooms[0].getRoomPosition().getX(), rooms[0].getRoomPosition().getY(), "resources/Rooms/defaults/0exit0.jpg");
        rooms[0].setCurrentPic(p0exit0);

    }

    //auxiliary method to put all the Doors into its array
    private void defineDoors() {
        //10 doors
        //VERTICAL DOORS
        Rectangle EXIT = new Rectangle(PADDING + 25, 100 + PADDING, 25, 100);   //The exit door
//        EXIT.setColor(Color.BLUE);
//        EXIT.draw();
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

        //The computer where the image will appear
        Rectangle COMPUTER = new Rectangle(500 + PADDING, PADDING + 50, 50, 50);
//        COMPUTER.setColor(Color.YELLOW);
//        COMPUTER.draw();

        Rectangle[] doorsrec = {EXIT, V1D1, V1D2, V1D3, V2D2, V2D3, JOKE, H1D2, H1D3, H2D2, COMPUTER};
        doorsRectangles = doorsrec;
        for (int i = 0; i < doorsrec.length; i++) {
//            doorsrec[i].setColor(Color.BLUE);
//            doorsrec[i].fill();
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

        Rectangle[] wallRec = {V0, V1a1, V1a2, V1b1, V1b2, V2a, V2b, V2c, V3, H0, H1, H1a, H1b, H1c, H2, H2a, H3}; // aux array

        for (int i = 0; i < wallRec.length; i++) {

//            wallRec[i].setColor(Color.LIGHT_GRAY);
//            wallRec[i].fill();
            walls[i] = new Wall(wallRec[i]);

        }
    }

    //checks all the moves to see if sprite collides with anything that need to changed or invoqque methods
    public void move(Direction direction) {

        if (stepCount > 5) {
            threadAlive = true;
        }

        //checks if sprite is near walls and stops him to go in that direction
        if (collider.intersectsWall(walls, sprite.getShape(), direction, mov)) {
            return;
        }

        //Checks if the Sprite is near the computer table in computer room and prompts computer photo
        if (collider.intersects(doors[10].getRoomDoor(), sprite.getShape(), direction, mov)) {
            ComputerPhoto computer = new ComputerPhoto();

            try {
                computer.prompt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        //if sprite is near the doors, doesn't let him pass
        for (int i = 0; i < doors.length; i++) {

            if (collider.intersects(doors[i].getRoomDoor(), sprite.getShape(), direction, mov) &&
                    doors[i].isLocked()) {
                return;
            }
        }

        //if sprite is near the doors, and prompts questions

//        for (int i = 0; i < doorsRectangles.length; i++) {
//
//            if (collider.intersects(doorsRectangles[i], sprite.getShape(), direction, mov)) {
//                try {
//                    Doors a = new Doors();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                return;
//            }


        //cheks if sprite is in the rooms, and unblocks it

        for (int i = 0; i < rooms.length; i++) {
            if (collider.intersects(rooms[i].getRoomPosition(), sprite.getShape(), direction, mov)) {
                rooms[i].printCurrentPic();

            }
        }

        //Checks the movement
        switch (direction) {
            case UP:
                if (sceneOff) {
                    stepCount++;
                    sprite.getShape().translate(0, -mov);
                    sprite.getCurrentSprite().translate(0, -mov);
                    sprite.getCurrentSprite().delete();
                    sprite.getCurrentSprite().draw();
                }
                break;
            case DOWN:
                if (sceneOff) {
                    stepCount++;
                    sprite.getShape().translate(0, mov);
                    sprite.getCurrentSprite().translate(0, mov);
                    sprite.getCurrentSprite().delete();
                    sprite.getCurrentSprite().draw();
                }
                break;
            case LEFT:
                if (sceneOff) {
                    stepCount++;
                    sprite.getShape().translate(-mov, 0);
                    sprite.getCurrentSprite().translate(-mov, 0);
                    sprite.getCurrentSprite().delete();
                    sprite.getCurrentSprite().draw();
                }
                break;
            case RIGHT:
                if (sceneOff) {
                    stepCount++;
                    sprite.getShape().translate(mov, 0);
                    sprite.getCurrentSprite().translate(mov, 0);
                    sprite.getCurrentSprite().delete();
                    sprite.getCurrentSprite().draw();
                }
                break;

        }
    }

    public boolean isItTimeToPrompt(Direction direction) {


        for (int i = 1; i < doorsRectangles.length; i++) {
            if (collider.intersects(doorsRectangles[i], sprite.getShape()) && stepCount >= 5) {
                {
                    System.out.println("Aqui");
                    System.out.println(Thread.currentThread());
                    stepCount = 0;
                    if (threadAlive) {
                        try {
                            testing = new Thread(new DoorsGameLoop(keyboardInput, this));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        threadAlive = false;

                    }

                    testing.start();


                    //doorsGameLoop = new DoorsGameLoop();
                }/* catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                // doorsGameLoop.run();
                //TODO todas as portas abrem o mesmo objecto de DoorsGameLoop
                return true;
            }
            //TODO fazer varias condicoes para cada sitio onde é para fazer prompt


        }

        if (collider.intersects(doorsRectangles[0], sprite.getShape()) && stepCount >= 5 && QUESTIONCOUNTER>=6) {

            {
                System.out.println("Aqui");
                System.out.println(Thread.currentThread());
                stepCount = 0;
                if (threadAlive) {
                    finalDoorPrompt = new Thread(new FinalDoor());
                    threadAlive = false;

                }

                finalDoorPrompt.start();


                //doorsGameLoop = new DoorsGameLoop();
            }/* catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            // doorsGameLoop.run();
            //TODO todas as portas abrem o mesmo objecto de DoorsGameLoop
            return true;
        }
        //TODO fazer varias condicoes para cada sitio onde é para fazer prompt


        return false;
    }


    //TODO: EURICO WORK

//    public boolean isItTimeToPrompt(Direction direction) {
//        for(GridDoor hitdoor: doors){
//            if(collider.intersects(hitdoor.getHitBoxPrompt(), sprite.getShape())) {
//                 {
//                    System.out.println("Aqui");
//                    System.out.println(Thread.currentThread());
//
//                     if (!testing.isAlive()){
//
//                         testing.start();
//                     }
//                    //doorsGameLoop = new DoorsGameLoop();
//                }/* catch (InterruptedException e) {
//                    e.printStackTrace();
//                }*/
//               // doorsGameLoop.run();
//                //TODO todas as portas abrem o mesmo objecto de DoorsGameLoop
//                return true;
//            }
//            //TODO fazer varias condicoes para cada sitio onde é para fazer prompt
//
//
//        }
//        return false;
//    }


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
