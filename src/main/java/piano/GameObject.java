package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class GameObject {
    /**
     * The coordinate x of the game object
     */
    protected int x;
    /**
     * The coordinate y of the game object
     */
    protected int y;
    /**
     * The image of the game object
     */
    protected PImage p;

    /**
     * Constructor for a game object,requires x,y,p
     * @param x coordinate x of the game object
     * @param y coordinate y of the game object
     * @param p image of the game object
     */
    public GameObject(int x, int y, PImage p) {
        this.x = x;
        this.y = y;
        this.p = p;
    }

    /**
     * Draw a game object on the app
     * @param app the window to display piano
     */
    public void draw(PApplet app) {
        app.image(p, this.x, this.y);
    }

    /**
     * Get game object's x
     * @return: x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get game object's y
     * @return: y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get game object image's width
     * @return: width
     */
    public int getWidth() {
        return this.p.width;
    }

    /**
     * Get game object image's height
     * @return: height
     */
    public int getHeight() {
        return this.p.height;
    }

    /**
     * Given a game object,this object will compare with that object. if they are
     * equal,return true,otherwise false
     * @param o another game object
     * @return: equal
     */
    public boolean equals(GameObject o) {
        if (this.x == o.getX() && this.y == o.getY())
            return true;
        return false;
    }

}
