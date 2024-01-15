package piano;

import org.junit.Test;
import processing.core.PApplet;
import static org.junit.Assert.*;

public class GameObjectTest {
    @Test
    public void testConstructor() {/* test constructor */
        GameObject o = new GameObject(1, 1, null);
        assertNotNull(o);
    }

    @Test
    public void testGetLength() {/* test getX() and getY() */
        GameObject o = new GameObject(10, 20, null);
        assertEquals(10, o.getX());
        assertEquals(20, o.getY());
    }

    @Test
    public void testEqual() {/* test equal() */
        GameObject o = new GameObject(10, 20, null);
        GameObject o2 = new GameObject(10, 20, null);
        GameObject o3 = new GameObject(50, 20, null);
        assertTrue(o.equals(o2));
        assertFalse(o.equals(o3));
        assertFalse(o.equals(new GameObject(10, 30, null)));
    }

    @Test
    public void testDraw() {/* test getWidth()&getHight() */
        App app = new App();
        PApplet.runSketch(new String[] { "Test Sketch" }, app);
        app.setup();
        app.settings();
        assertEquals(40, app.play.getWidth());
        assertEquals(40, app.play.getHeight());
    }
}
