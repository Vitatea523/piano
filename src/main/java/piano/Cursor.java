package piano;

import java.util.ArrayList;
import javax.sound.midi.MidiChannel;
import processing.core.PImage;

public class Cursor extends GameObject {
    /**
     * The velocity of cursor
     */
    private int xVel;

    /**
     * Constructor for a cursor extends game object,requires x,y,p and requires the
     * velocity of cursor
     * @param x    coordinate x of the game object
     * @param y    coordinate y of the game object
     * @param xVel velocity of cursor
     * @param p    image of the game object
     */
    public Cursor(int x, int y, int xVel, PImage p) {
        super(x, y, p);
        this.xVel = xVel;
    }

    /**
     * cursor move in its velocity
     */
    public void tick() {
        this.x += this.xVel;
    }

    /**
     * Get the velocity of cursor
     * @return velocity of cursor
     */
    public int getXVel() {
        return xVel;
    }

    /**
     * Given actived blocks and quaver note list; convert blocks information to note
     * height and add to list.
     * @param blocks the actived blocks on the app
     * @param noteQ  list contains every quaver note heights information
     * @return noteQ
     */
    public ArrayList<ArrayList<Integer>> manageBlocks(ArrayList<GameObject> blocks,
            ArrayList<ArrayList<Integer>> noteQ) {
        for (GameObject block : blocks) {
            int blockL = block.getX();
            int quaver = (blockL - 60) / 15;
            int note = (block.getY() - 75) / 20;
            if (!noteQ.get(quaver).contains(72 - note)) {
                noteQ.get(quaver).add(72 - note);
            }
        }
        return noteQ;
    }

    /**
     * Set cursor's x
     * @param x the coordinate x of the cursor
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Given a game object, if list contains the note that it represents, remove it.
     * Return noteQ.
     * @param o     a game object
     * @param noteQ list contains every quaver note heights information
     * @return noteQ
     */
    public ArrayList<ArrayList<Integer>> manageNoteQ(GameObject o, ArrayList<ArrayList<Integer>> noteQ) {
        for (int i = 0; i < noteQ.get((o.getX() - 60) / 15).size(); i++) {
            if (noteQ.get((o.getX() - 60) / 15).get(i) == 72 - ((o.getY() - 75) / 20)) {
                noteQ.get((o.getX() - 60) / 15).remove(i);
            }
        }
        return noteQ;
    }

    /**
     * Given the cursor, the note state,channel, current quaver to play. If cursor at
     * initial and has note to play, note on. If cursor's x at the left begin of a
     * quavor,note on, when x at right threshold oh this quavor, all note off.Return
     * note state.
     * @param cursor  cursor
     * @param isOn    the note state
     * @param channel diffenert program to play
     * @param current current quaver to play
     * @return The note state
     */
    public boolean playSound(Cursor cursor, boolean isOn, MidiChannel channel, ArrayList<ArrayList<Integer>> current) {
        if (cursor.getX() == 49 && current.get(0).size() > 0) {
            for (int j = 0; j < current.get(0).size(); j++) {
                channel.noteOn(current.get(0).get(j), 90);
            }
        }
        if ((cursor.getX() + 12 - 60) % 15 == 0) {
            if ((cursor.getX() + 12 - 60) / 15 < 32) {
                ArrayList<Integer> on = current.get((cursor.getX() + 12 - 60) / 15);
                if (on.size() > 0) {
                    isOn = true;
                    for (int j = 0; j < on.size(); j++) {
                        channel.noteOn(on.get(j), 90);
                    }
                }
            }
        }
        if ((cursor.getX() + 6 - 60) % 15 == 0) {
            channel.allNotesOff();
            isOn = false;
        }
        return isOn;
    }
}
