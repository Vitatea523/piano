package piano;

import java.util.*;
import processing.core.PImage;

public class Undo extends GameObject {
    /**
     * Constructor for a undo extends game object,requires x,y,p
     * @param x coordinate x of the game object
     * @param y coordinate y of the game object
     * @param p image of the game object
     */
    public Undo(int x, int y, PImage p) {
        super(x, y, p);
    }

    /**
     * Given actived blocks,the list to save every step of blocks, list will save
     * the blocks. If the size of list less than 16, add blocks, otherwise remove first and add
     * blocks.
     * @param blocks   the actived blocks on the app
     * @param undoList the list to save every step of blocks
     */
    public void undo(ArrayList<GameObject> blocks, LinkedList<ArrayList<GameObject>> undoList) {
        ArrayList<GameObject> temp = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            temp.add(blocks.get(i));
        }
        if (undoList.size() < 16) {
            undoList.add(temp);
        } else {
            undoList.removeFirst();
            undoList.add(temp);
        }
    }

    /**
     * Given the actived blocks,the list to save every step of blocks,a object can
     * use copy method and the previous step of clicking undo whether is undo. If
     * previous step is not undo,remove the last record of blocks. If undoList more than 0,
     * blocks undo last record of undoList,otherwise print "no existing
     * record"(limit for 15 times records).
     * @param hasClicked the previous step of clicking undo whether is undo
     * @param undoList   the list to save every step of blocks
     * @param blocks     the actived blocks on the app
     * @param save       object can use copy method
     * @return the previous step of clicking undo whether is undo
     */
    public boolean manageUndoList(boolean hasClicked, LinkedList<ArrayList<GameObject>> undoList,
            ArrayList<GameObject> blocks, Copy save) {
        if (!hasClicked) {
            undoList.remove(undoList.size() - 1);
            hasClicked = true;
        }
        if (undoList.size() > 0) {
            save.copy(undoList.removeLast(), blocks);
        } else {
            System.out.println("no existing record");
        }
        return hasClicked;
    }
}