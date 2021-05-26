/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.texturesxml;

import com.martiansoftware.jsap.JSAPResult;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Marcin
 */
public class GraphicsInteractionIT {
    
    public GraphicsInteractionIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setup() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of texttoGraphics method, of class GraphicsInteraction.
     */
    @Test
    public void testTexttoGraphics() throws Exception {
        System.out.println("texttoGraphics");
        String text = "";
        JSAPResult config = null;
        String formatted = "";
        int width = 100;
        String type = "Temple";
        boolean isIcao = false;
        GraphicsInteraction instance = new GraphicsInteraction();
        instance.texttoGraphics(text, config, formatted, width, type, isIcao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawCenteredString method, of class GraphicsInteraction.
     */
    @Test
    public void testDrawCenteredString() {
        System.out.println("drawCenteredString");
        Graphics g = null;
        String text = "";
        Rectangle rect = null;
        Font font = null;
        GraphicsInteraction.drawCenteredString(g, text, rect, font);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawStringMultiLine method, of class GraphicsInteraction.
     */
    @Test
    public void testDrawStringMultiLine() {
        System.out.println("drawStringMultiLine");
        Graphics2D g = null;
        String text = "";
        int lineWidth = 0;
        int x = 0;
        int y = 0;
        GraphicsInteraction.drawStringMultiLine(g, text, lineWidth, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleRenderingHints method, of class GraphicsInteraction.
     */
    @Test
    public void testHandleRenderingHints() {
        System.out.println("handleRenderingHints");
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Graphics2D expResult = null;
        Graphics2D result = GraphicsInteraction.handleRenderingHints(g2d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTextnIcon method, of class GraphicsInteraction.
     */
    @Test
    public void testCreateTextnIcon() throws Exception {
        System.out.println("createTextnIcon");
        Graphics g2d = null;
        int width = 0;
        int height = 0;
        Font font = null;
        String text = "";
        String type = "";
        JSAPResult config = null;
        boolean isIcao = false;
        int[] rgbvalues = null;
        GraphicsInteraction.createTextnIcon(g2d, width, height, font, text, type, config, isIcao, rgbvalues);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chooseIcon method, of class GraphicsInteraction.
     */
    @Test
    public void testChooseIcon() throws Exception {
        System.out.println("chooseIcon");
        String type = "";
        boolean isIcao = false;
        BufferedImage expResult = null;
        BufferedImage result = GraphicsInteraction.chooseIcon(type, isIcao);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRGBValues method, of class GraphicsInteraction.
     */
    @Test
    public void testSetRGBValues() {
        System.out.println("setRGBValues");
        int[] rgbvalues = null;
        JSAPResult config = null;
        String type = "";
        int[] expResult = null;
        int[] result = GraphicsInteraction.setRGBValues(rgbvalues, config, type);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
