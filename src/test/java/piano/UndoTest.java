package piano;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class UndoTest {
    @Test
    public void testUndo() {/** test constructor */
        Undo re = new Undo(1, 2, null);
        assertNotNull(re);
    }

    @Test
    public void testUndoMethod() {/* test undo() */
        Undo re = new Undo(1, 2, null);
        ArrayList<GameObject> blocks = new ArrayList<>();
        LinkedList<ArrayList<GameObject>> undoList = new LinkedList<>();
        re.undo(blocks, undoList);
        assertEquals(1, undoList.size());
        blocks.add(new GameObject(10, 200, null));
        re.undo(blocks, undoList);
        assertEquals(2, undoList.size());
        for (int i = 0; i < 14; i++) {
            undoList.add(new ArrayList<>());
        }
        re.undo(blocks, undoList);/* test undoList.size() >= 16 */
        assertEquals(16, undoList.size());
    }

    @Test
    public void testManageUndoList() {/* test manageUndoList() */
        Undo re = new Undo(1, 2, null);
        boolean hasClicked = false;
        LinkedList<ArrayList<GameObject>> undoList = new LinkedList<>();
        Copy load = new Copy(1, 1, null);
        undoList.add(new ArrayList<>());
        ArrayList<GameObject> blocks = new ArrayList<>();
        assertTrue(re.manageUndoList(hasClicked, undoList, blocks, load));/* test undoList.size() = 0 */
        undoList.add(new ArrayList<GameObject>());
        undoList.add(new ArrayList<GameObject>());
        assertEquals(2, undoList.size());/* undoList.size() > 0 */
        assertTrue(re.manageUndoList(hasClicked, undoList, blocks, load));
        hasClicked = true;
        assertTrue(re.manageUndoList(hasClicked, undoList, blocks, load));/* test the previous step is redo */
    }
}