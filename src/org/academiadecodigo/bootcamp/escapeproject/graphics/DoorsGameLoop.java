package org.academiadecodigo.bootcamp.escapeproject.graphics;

import org.academiadecodigo.bootcamp.escapeproject.Scenable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

/**
 * Created by codecadet on 10/02/17.
 */
public class DoorsGameLoop implements KeyboardHandler, Runnable, Scenable {

    private Doors door;
    private Questions question;
    private boolean caughtAttention;
    private Rectangle scene;

    public DoorsGameLoop() throws InterruptedException {
        door = new Doors();
        question = new Questions();

//        Mouse m = new Mouse(this);
//        m.addEventListener(MouseEventType.MOUSE_CLICKED);
//        m.addEventListener(MouseEventType.MOUSE_MOVED);

        Keyboard k = new Keyboard(this);
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_1);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event);

        KeyboardEvent event1 = new KeyboardEvent();
        event1.setKey(KeyboardEvent.KEY_2);
        event1.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event1);

        KeyboardEvent event2 = new KeyboardEvent();
        event2.setKey(KeyboardEvent.KEY_3);
        event2.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event2);

    }

    public void test() throws InterruptedException {



        scene = new Rectangle(10.0, 10.0, 900, 900.0);
        scene.setColor(Color.BLACK);
        scene.draw();
    }

    //TODO set para boolean de boneco a bloquear movimento e set para boolean de porta a abrir e fechar


    public void prompt() {
        run();
    }

    public void run() {

        caughtAttention = false;

        //Enter cycle of showing random pictures of Padawans
        while(true) {

            door.deletePadPictures();

            door.randomizeCharacters();

            try {
                Thread.sleep(DoorsUtil.randomTime());
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (caughtAttention) {

                try {
                    question.start(door);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            }

        }
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        closePrompt();
        return;


    }


    public void closePrompt() {
        door.deleteDoors();
        door.getBackground().delete();
        door.deleteNumbers();
        question.deleteEverything();
        door.resetIsPayingAttention();
        return;
    }

    /*@Override
    public void mouseClicked(MouseEvent e){

        if (door.getTheOnePayingAttention() != -1 && DoorsUtil.isWithin(e,
                door.getPicsPadawan()[door.getTheOnePayingAttention()][3])
                && door.getIsPayingAttention()[door.getTheOnePayingAttention()]) {
            if (!caughtAttention) {
                door.getPicsPadawan()[door.getTheOnePayingAttention()][3].grow(20.0, 50.0);
            }
            caughtAttention = true;

        }
        clickCounter++;
        System.out.println(e);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(e);

    }*/

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_1:
                if (door.getTheOnePayingAttention() != -1 && door.getIsPayingAttention()[0]) {
                    if (!caughtAttention) {
                        door.getPicsPadawan()[door.getTheOnePayingAttention()][3].grow(16.0, 50.0);
                    }
                    caughtAttention = true;

                }
                break;


            case KeyboardEvent.KEY_2:
                if (door.getTheOnePayingAttention() != -1 && door.getIsPayingAttention()[1]) {
                    if (!caughtAttention) {
                        door.getPicsPadawan()[door.getTheOnePayingAttention()][3].grow(20.0, 50.0);
                    }
                    caughtAttention = true;

                }
                break;



            case KeyboardEvent.KEY_3:
                if (door.getTheOnePayingAttention() != -1 && door.getIsPayingAttention()[2]) {
                    if (!caughtAttention) {
                        door.getPicsPadawan()[door.getTheOnePayingAttention()][3].grow(20.0, 50.0);
                    }
                    caughtAttention = true;

                }
                break;


        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}