package piano;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class ProgramTest {
    @Test
    public void testConstructor() {/* test constructor */
        Program p = new Program(1, 1, null);
        assertNotNull(p);
    }

    @Test
    public void testChangePro() {/* test changePro() */
        Program p = new Program(1, 1, null);
        try {
            Synthesizer player = MidiSystem.getSynthesizer();/* test every program */
            player.open();
            MidiChannel channel = player.getChannels()[0];
            Program pButten = new Program(1, 2, null);
            Program mButten = new Program(2, 2, null);
            Program bButten = new Program(3, 2, null);
            Program sButten = new Program(4, 2, null);
            Program currentPro = new Program(1, 0, null);
            assertEquals(pButten, p.changePro(0, pButten, mButten, bButten, sButten, currentPro, channel));
            assertEquals(mButten, p.changePro(1, pButten, mButten, bButten, sButten, currentPro, channel));
            assertEquals(bButten, p.changePro(2, pButten, mButten, bButten, sButten, currentPro, channel));
            assertEquals(sButten, p.changePro(3, pButten, mButten, bButten, sButten, currentPro, channel));
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNex() {/* test nex() */
        Program p = new Program(1, 1, null);
        assertTrue(p.nex(1) == 2);/* test every proIndex */
        assertTrue(p.nex(2) == 3);
        assertTrue(p.nex(3) == 0);
        assertTrue(p.nex(0) == 1);
    }

    @Test
    public void testPro() {/* test pro() */
        Program p = new Program(1, 1, null);
        assertTrue(p.pre(1) == 0);/* test every proIndex */
        assertTrue(p.pre(2) == 1);
        assertTrue(p.pre(3) == 2);
        assertTrue(p.pre(0) == 3);
    }
}