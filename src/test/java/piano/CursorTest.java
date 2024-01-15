package piano;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class CursorTest {
    @Test
    public void testCursor() {/* test constructor */
        Cursor cursor = new Cursor(1, 2, 1, null);
        assertNotNull(cursor);
    }

    @Test
    public void testGet_SetXVel() {/* test getter setter for xVel */
        Cursor cursor = new Cursor(1, 2, 1, null);
        assertEquals(1, cursor.getXVel());
        cursor.setX(3);
        assertEquals(3, cursor.getX());
    }

    @Test
    public void testTick() {/* test cursor moves */
        Cursor cursor = new Cursor(1, 2, 1, null);
        cursor.tick();
        assertEquals(2, cursor.getX());
    }

    @Test
    public void testManageBlocks() {/* test manageBlocks() */
        Cursor cursor = new Cursor(1, 2, 1, null);
        ArrayList<GameObject> blocks = new ArrayList<>();
        ArrayList<ArrayList<Integer>> noteQ = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            noteQ.add(new ArrayList<>());
        }
        blocks.add(new GameObject(75, 100, null));
        cursor.manageBlocks(blocks, noteQ);
        assertTrue(noteQ.get(1).contains(71));/* test blocks manage to noteHeight */
        cursor.manageBlocks(blocks, noteQ);
        assertTrue(noteQ.get(1).size() == 1);
    }

    @Test
    public void testManageNoteQ() {/* test manageNoteQ() */
        Cursor cursor = new Cursor(1, 2, 1, null);
        GameObject o = new GameObject(90, 95, null);
        ArrayList<ArrayList<Integer>> noteQ = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            noteQ.add(new ArrayList<>());
        }
        noteQ.get(2).add(71);
        cursor.manageNoteQ(new GameObject(100, 200, null), noteQ);
        assertEquals(1, noteQ.get(2).size());
        cursor.manageNoteQ(o, noteQ);
        assertEquals(0, noteQ.get(2).size());/* test when click the same block again, remove this block's note height */

    }

    @Test
    public void testPlaySound() {/* test playSound() */
        Cursor cursor = new Cursor(49, 95, 1, null);
        Cursor cursor2 = new Cursor(63, 95, 1, null);
        Cursor cursor3 = new Cursor(69, 95, 1, null);
        Cursor cursor4 = new Cursor(528, 95, 1, null);
        boolean isOn = false;
        try {
            Synthesizer player = MidiSystem.getSynthesizer();
            player.open();
            MidiChannel channel = player.getChannels()[0];
            ArrayList<ArrayList<Integer>> current = new ArrayList<>();
            current.add(new ArrayList<>());
            current.add(new ArrayList<>());
            assertFalse(cursor.playSound(cursor, isOn, channel, current));
            current.get(0).add(71);
            assertFalse(cursor.playSound(cursor, isOn, channel, current));
            assertFalse(cursor2.playSound(cursor2, isOn, channel, current));
            assertFalse(cursor4.playSound(cursor4, isOn, channel, current));
            assertFalse(cursor3.playSound(cursor3, isOn, channel, current));
            current.get(1).add(60);
            assertTrue(cursor2.playSound(cursor2, isOn, channel, current));/* test cursor overlap and note on */
            assertFalse(cursor.playSound(cursor, isOn, channel, current));/* test cursor overlap and allnoteoff */
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }
}
