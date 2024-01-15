package piano;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import processing.core.PApplet;

public class App extends PApplet {
    /**
     * A movable cursor
     */
    public Cursor cursor;
    /**
     * Game object needed in the app
     */
    public GameObject middleBanner, pause, buttonBack, play, stop, banner, key, grid, reset;
    /**
     * Undo
     */
    public Undo undo;
    /**
     * Things can copy
     */
    public Copy save, load;
    /**
     * Blocks list
     */
    public ArrayList<GameObject> blocks, saveBlocks;
    /**
     * Things to judge some specific state
     */
    public boolean pd = false, isOn = false, hasClicked = false;
    /**
     * List store note height information
     */
    public ArrayList<ArrayList<Integer>> current = new ArrayList<>(), noteQ = new ArrayList<>();
    /**
     * Channel
     */
    public MidiChannel channel;
    /**
     * Synthesizer
     */
    public Synthesizer player;
    /**
     * List to store undo blocks information
     */
    public LinkedList<ArrayList<GameObject>> undoList = new LinkedList<>();
    /**
     * Program index
     */
    public int savePro, proIndex;
    /**
     * Program
     */
    public Program next, prev, currentPro, bButten, pButten, mButten, sButten;

    /**
     * Constructor for app default all ArrayList, boolean variables to false, index
     * as 0. default noteQ size to 32, open Synthesizer player and get piano channel
     */
    public App() {
        blocks = new ArrayList<>();
        saveBlocks = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            noteQ.add(new ArrayList<>());
        }
        try {
            player = MidiSystem.getSynthesizer();
            player.open();
            channel = player.getChannels()[0];
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        proIndex = 0;
        undoList.add(new ArrayList<>());
    }

    /**
     * Set size of window
     */
    public void settings() {
        size(540, 335);
    }

    /**
     * Load images here
     */
    public void setup() {
        frameRate(60);
        this.banner = new GameObject(0, 0, this.loadImage("src/main/resources/banner.png"));
        this.play = new GameObject(5, 5, this.loadImage("src/main/resources/play.png"));
        this.stop = new GameObject(50, 5, this.loadImage("src/main/resources/stop.png"));
        this.buttonBack = new GameObject(5, 5, this.loadImage("src/main/resources/buttonBack.png"));
        this.undo = new Undo(230, 5, this.loadImage("src/main/resources/undo.png"));
        this.pause = new GameObject(5, 5, this.loadImage("src/main/resources/pause.png"));
        this.grid = new GameObject(60, 75, this.loadImage("src/main/resources/grid.png"));
        this.key = new GameObject(0, 75, this.loadImage("src/main/resources/keyboard.png"));
        this.middleBanner = new GameObject(0, 0, this.loadImage("src/main/resources/middleBanner.png"));
        this.currentPro = new Program(325, 5, this.loadImage("src/main/resources/P.png"));
        this.reset = new GameObject(95, 5, this.loadImage("src/main/resources/reset.png"));
        this.save = new Copy(140, 5, this.loadImage("src/main/resources/save.png"));
        this.load = new Copy(185, 5, this.loadImage("src/main/resources/load.png"));
        this.prev = new Program(280, 5, this.loadImage("src/main/resources/prev.png"));
        this.pButten = new Program(325, 5, this.loadImage("src/main/resources/P.png"));
        this.sButten = new Program(325, 5, this.loadImage("src/main/resources/S.png"));
        this.bButten = new Program(325, 5, this.loadImage("src/main/resources/B.png"));
        this.mButten = new Program(325, 5, this.loadImage("src/main/resources/M.png"));
        this.next = new Program(370, 5, this.loadImage("src/main/resources/next.png"));
        this.cursor = new Cursor(48, 59, 1, this.loadImage("src/main/resources/pointer.png"));
    }

    /**
     * Draw your program here If mousePressed, manage blocks information, convert to
     * note height information, then add to correspond quavor in noteQ list. If pd
     * is true, cursor moves, player plays correspond quavor's note height sound. If
     * cursor's x is more than 540, cursor goes back to initial place and moves. If
     * there has any block actived, draw blocks.
     */
    public void draw() {
        if (mousePressed == true) {
            current = cursor.manageBlocks(blocks, noteQ);
            mousePressed = false;
        }
        if (pd) {
            this.cursor.tick();
            this.isOn = this.cursor.playSound(cursor, isOn, channel, current);
        }
        if (this.cursor.getX() > 540) {
            this.cursor = new Cursor(48, 59, 1, this.loadImage("src/main/resources/pointer.png"));
        }
        this.rect(-1, -1, 542, 337);
        this.middleBanner.draw(this);
        this.banner.draw(this);
        for (int i = 0; i < 9; i++) {
            if (i > 5) {
                this.buttonBack = new GameObject((i + 1) * 5 + 40 * i + 5, 5,
                        this.loadImage("src/main/resources/buttonBack.png"));
            } else {
                this.buttonBack = new GameObject((i + 1) * 5 + 40 * i, 5,
                        this.loadImage("src/main/resources/buttonBack.png"));
            }
            this.buttonBack.draw(this);
        }
        this.undo.draw(this);
        this.stop.draw(this);
        this.play.draw(this);
        this.load.draw(this);
        this.reset.draw(this);
        this.save.draw(this);
        this.next.draw(this);
        this.prev.draw(this);
        this.currentPro.draw(this);
        this.key.draw(this);
        this.grid.draw(this);
        this.cursor.draw(this);
        if (blocks.size() > 0) {
            for (GameObject o : blocks) {
                o.draw(this);
            }
        }
        line(cursor.getX() + 12, 75, cursor.getX() + 12, 335);
        stroke(255, 0, 0);
        strokeWeight(1.5f);
    }

    /**
     * If mouse clicks play, play change to pause, all work pause,otherwise pause to
     * play,all work continues; if clicks stop, cursor goes back, all processes
     * stop; if clicks clear,cursor goes back and blocks all clear; if clicks undo,
     * cursor goes back, blocks turn to prevoius step; if clicks save, save current
     * blocks to file; if clicks load, display what file stored blocks; if clicks
     * next or prev,change to next or prev program; if clicks block area, block will
     * be actived or deactived.
     */
    public void mousePressed() {
        if (mouseY >= 5 && mouseY <= 45) {
            if (mouseX >= 5 && mouseX <= 45) {
                replace();
                pd = !pd;
            } else if (mouseX >= 50 && mouseX <= 90) {
                back();
            } else if (mouseX >= 95 && mouseX <= 135) {
                back();
                blocks.clear();
                for (ArrayList<Integer> list : noteQ) {
                    list.clear();
                }
                undo.undo(blocks, undoList);
            } else if (mouseX >= 140 && mouseX <= 180) {
                if (pd)
                    back();
                save.copy(blocks, saveBlocks);
                try {
                    save.saveFile(blocks, proIndex);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (mouseX >= 185 && mouseX <= 230) {
                if (pd)
                    back();
                blocks.clear();
                ArrayList<ArrayList<Integer>> c = load.readFile();
                if (c.size() > 0) {
                    proIndex = (c.get(c.size() - 1).get(0));
                    currentPro = next.changePro(proIndex, pButten, mButten, bButten, sButten, currentPro, channel);
                    for (int i = 0; i < c.size() - 1; i++) {
                        blocks.add(new GameObject(c.get(i).get(0), c.get(i).get(1),
                                this.loadImage("src/main/resources/block.png")));
                    }
                }
            } else if (mouseX >= 280 && mouseX <= 320) {
                this.proIndex = prev.pre(proIndex);
                this.currentPro = prev.changePro(proIndex, pButten, mButten, bButten, sButten, currentPro, channel);
            } else if (mouseX >= 370 && mouseX <= 410) {
                this.proIndex = next.nex(proIndex);
                this.currentPro = next.changePro(proIndex, pButten, mButten, bButten, sButten, currentPro, channel);
            } else if (mouseX >= 230 && mouseX <= 270) {
                this.hasClicked = undo.manageUndoList(hasClicked, undoList, blocks, load);
            }
        } else if (mouseX > 60 && mouseY > 75) {
            hasClicked = false;
            GameObject block = new GameObject((mouseX - 60) / 15 * 15 + 60, (mouseY - 75) / 20 * 20 + 75,
                    this.loadImage("src/main/resources/block.png"));
            for (GameObject o : blocks) {
                if (o.equals(block)) {
                    blocks.remove(o);
                    undo.undo(blocks, undoList);
                    cursor.manageNoteQ(o, noteQ);
                    return;
                }
            }
            blocks.add(block);
            undo.undo(blocks, undoList);
        }
    }

    /**
     * Exchange function between play and pause
     */
    public void replace() {
        GameObject temp = new GameObject(0, 0, null);
        temp = play;
        play = pause;
        pause = temp;
    }

    /**
     * Cursor goes back to initial state
     */
    public void back() {
        cursor = new Cursor(48, 59, 1, this.loadImage("src/main/resources/pointer.png"));
        if (pd) {
            replace();
        }
        pd = false;
    }

    public static void main(final String[] args) {
        PApplet.main("piano.App");
    }
}