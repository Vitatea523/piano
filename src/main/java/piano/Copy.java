package piano;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PImage;

public class Copy extends GameObject {
    /**
     * Constructor for a copy extends game object,requires x,y,p
     * @param x coordinate x of the game object
     * @param y coordinate y of the game object
     * @param p image of the game object
     */
    public Copy(int x, int y, PImage p) {
        super(x, y, p);
    }

    /**
     * Given blocks want to copy,make a copy of those blocks
     * @param copyThings blocks want to copy
     * @param saveBlocks a copy of blocks
     */
    public void copy(ArrayList<GameObject> copyThings, ArrayList<GameObject> saveBlocks) {

        saveBlocks.clear();
        for (int i = 0; i < copyThings.size(); i++) {
            saveBlocks.add(copyThings.get(i));
        }
    }

    /**
     * Given actived blocks,program index write blocks and program index information
     * in the file
     * @param blocks   the actived blocks on the app
     * @param proIndex program index
     * @return success
     * @throws IOException File not found exception
     */
    public boolean saveFile(ArrayList<GameObject> blocks, int proIndex) throws IOException {
        try {
            FileWriter f = new FileWriter("./src/main/java/piano/load.txt");
            BufferedWriter b = new BufferedWriter(f);
            for (GameObject o : blocks) {
                b.write(o.getX() + "k" + o.getY());
                b.flush();
                b.newLine();
            }
            b.write(proIndex + "");
            b.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Read file, and add to load list then return it
     * @return the note height and program information stored in the file
     */
    public ArrayList<ArrayList<Integer>> readFile() {
        ArrayList<ArrayList<Integer>> saveBlocks = new ArrayList<>();
        try {
            File f = new File("./src/main/java/piano/load.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                ArrayList<Integer> a = new ArrayList<>();
                String[] line = sc.nextLine().split("k");
                for (String str : line) {
                    a.add(Integer.parseInt(str));
                }
                saveBlocks.add(a);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return saveBlocks;
    }
}