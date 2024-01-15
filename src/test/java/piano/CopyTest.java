package piano;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.*;

public class CopyTest {
    @Test
    public void testUndo() {/* test constructor */
        Copy play = new Copy(1, 2, null);
        assertNotNull(play);
    }

    @Test
    public void testCopy() {/* test copy() */
        Copy play = new Copy(1, 2, null);
        ArrayList<GameObject> copyThings = new ArrayList<>();
        ArrayList<GameObject> saveBlocks = new ArrayList<>();
        play.copy(copyThings, saveBlocks);
        assertEquals(0, saveBlocks.size());
        copyThings.add(new GameObject(10, 100, null));
        play.copy(copyThings, saveBlocks);
        assertEquals(1, saveBlocks.size());
    }

    @Test
    public void testSaveFile() {/* test saveFile() */
        Copy play = new Copy(1, 2, null);
        ArrayList<GameObject> blocks = new ArrayList<>();
        blocks.add(new GameObject(70, 80, null));
        int proIndex = 1;
        try {
            assertTrue("File is not saved", play.saveFile(blocks, proIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadFile() {/* test readFile() */
        Copy play = new Copy(1, 2, null);
        assertNotNull(play.readFile());
    }
}
