package piano;

import processing.core.PApplet;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testConstructor() {/* testConstructor */
        App app = new App();
        assertNotNull(app);
    }

    @Test
    public void testReplace() {/* testReplace play and pause buttons will exchange */
        App app = new App();
        GameObject test1 = new GameObject(0, 0, null);
        GameObject test2 = new GameObject(0, 0, null);
        test1 = app.play;
        test2 = app.pause;
        assertEquals(test1, app.play);
        app.replace();
        assertEquals(test2, app.play);
        assertEquals(test1, app.pause);
    }

    @Test
    public void testBack() {/* testBack cursor will be back to the initial place */
        App app = new App();
        PApplet.runSketch(new String[] { "Test Sketch" }, app);
        app.setup();
        app.settings();
        GameObject test1 = new GameObject(0, 0, null);
        GameObject test2 = new GameObject(0, 0, null);
        test1 = app.play;
        test2 = app.pause;
        assertEquals(test1, app.play);
        app.pd = true;
        app.back();
        assertEquals(test2, app.play);
        assertEquals(test1, app.pause);
        assertFalse(app.pd);
    }

    @Test
    public void testMousePressed() {/* testMousePressed */
        App app = new App();
        PApplet.runSketch(new String[] { "Test Sketch" }, app);
        app.setup();
        app.settings();
        GameObject test1 = new GameObject(0, 0, null);
        GameObject test2 = new GameObject(0, 0, null);
        test1 = app.play;
        test2 = app.pause;
        app.mouseY = 30;
        app.mouseX = 30;
        app.pd = false;
        app.mousePressed();/* test when click play button it will change to pause */
        assertEquals(true, app.pd);
        assertEquals(test2, app.play);
        assertEquals(test1, app.pause);
        app.mousePressed();
        assertEquals(false, app.pd);
        assertEquals(test1, app.play);/* test change back to play button */
        app.mouseX = 60;
        app.mousePressed();/* test stop */
        assertEquals(48, app.cursor.getX());
        app.mouseX = 100;/* test clear */
        app.blocks.add(new GameObject(1, 1, null));
        app.mousePressed();
        assertEquals(0, app.blocks.size());
        app.mouseX = 150;
        app.pd = true;
        assertEquals(48, app.cursor.getX());/* when clear, cursor goes back to initial place */
        app.mousePressed();/* test save */
        assertEquals(app.saveBlocks, app.blocks);/* save blocks */
        assertEquals(app.proIndex, app.savePro);/* save proIndex */
        app.pd = false;
        app.mousePressed();
        assertEquals(48, app.cursor.getX());/* cursor back */
        app.mouseX = 200;
        app.pd = false;
        app.mousePressed();/* test load */
        assertEquals(0, app.blocks.size());
        app.pd = true;
        app.blocks.add(new GameObject(10, 20, null));
        app.mousePressed();
        assertEquals(0, app.blocks.size());
        app.mouseX = 300;
        app.proIndex = 1;
        app.mousePressed();/* test next pro */
        assertEquals(0, app.proIndex);
        app.mouseX = 400;
        app.mousePressed();/* test prev pro */
        assertEquals(1, app.proIndex);
        app.proIndex = 3;
        app.mouseX = 420;
        app.mousePressed();/* test click out of prev button area */
        assertEquals(3, app.proIndex);
        app.mouseX = 250;
        app.mousePressed();/* test undo(go to undoTest.java to see more details) */
        assertTrue(app.hasClicked);
        app.mouseX = 300;
        app.mousePressed();
        assertTrue(app.hasClicked);
        app.mouseY = 100;
        app.mousePressed();/* test click block */
        assertFalse(app.hasClicked);
        assertEquals(1, app.blocks.size());/* add block to blocks */
        app.mousePressed();
        assertEquals(0, app.blocks.size());/* click the same block again and blocks remove it */
        app.mouseY = 74;
        app.mousePressed();/* test click out of block area */
        assertEquals(0, app.blocks.size());
    }

    @Test
    public void testOtherBranches() {/* test edge mouse click place */
        App app = new App();
        PApplet.runSketch(new String[] { "Test Sketch" }, app);
        app.setup();
        app.settings();
        app.mouseX = 3;
        app.mouseY = 40;
        app.pd = false;
        app.mousePressed();
        assertFalse(app.pd);
        app.mouseY = 50;
        app.mousePressed();
        assertFalse(app.pd);
        app.mouseX = 10;
        app.mousePressed();
        assertFalse(app.pd);
    }

    @Test
    public void testDraw() {
        App app = new App();
        PApplet.runSketch(new String[] { "Test Sketch" }, app);
        app.setup();
        app.settings();
        app.mousePressed = true;
        app.draw();/* test manageBlocks */
        assertFalse(app.mousePressed);
        app.pd = true;
        app.blocks.add(new GameObject(90, 50, app.loadImage("src/main/resources/block.png")));
        app.draw();/* test draw block */
        assertFalse(app.isOn);
        app.cursor.setX(550);
        app.pd = false;
        app.draw();
        assertEquals(48, app.cursor.getX());/* test cursor out of window loop back */
    }
}
