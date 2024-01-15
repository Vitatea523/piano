package piano;

import javax.sound.midi.MidiChannel;
import processing.core.PImage;

public class Program extends GameObject {
    /**
     * Constructor for a program extends game object,requires x,y,p
     * 
     * @param x coordinate x of the game object
     * @param y coordinate y of the game object
     * @param p image of the game object
     */
    public Program(int x, int y, PImage p) {
        super(x, y, p);
    }

    /**
     * Given four program piano,marimba,Banjo,saxophone, a program index,current
     * program and channel; if program index is 0,return piano; if program index is
     * 1,return marimba; if program index is 2,return Banjo; if program index is
     * 3,return saxophone.
     * @param proIndex   program index
     * @param pButten    program marimba
     * @param mButten    program piano
     * @param bButten    program Banjo
     * @param sButten    program saxophone
     * @param currentPro current program
     * @param channel    channel to play program
     * @return current program
     */
    public Program changePro(int proIndex, Program pButten, Program mButten, Program bButten, Program sButten,
            Program currentPro, MidiChannel channel) {
        if (proIndex == 0) {
            currentPro = pButten;
            channel.programChange(0);
        } else if (proIndex == 1) {
            currentPro = mButten;
            channel.programChange(12);
        } else if (proIndex == 2) {
            currentPro = bButten;
            channel.programChange(105);
        } else {
            currentPro = sButten;
            channel.programChange(66);
        }
        return currentPro;
    }

    /**
     * Given program index, it will get next program. If program index=3,it will
     * return 0 otherwise index+1
     * @param proIndex program index
     * @return program index
     */
    public int nex(int proIndex) {
        if (proIndex == 3)
            proIndex = 0;
        else
            proIndex++;
        return proIndex;
    }

    /**
     * Given program index, it will get previous program. If program index=0,it will
     * return 3, otherwise return index-1
     * @param proIndex program index
     * @return program index
     */
    public int pre(int proIndex) {
        if (proIndex == 0)
            proIndex = 3;
        else
            proIndex--;
        return proIndex;
    }
}
