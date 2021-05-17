/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

import com.martiansoftware.jsap.JSAPResult;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Marcin
 */
public class GraphicsInteraction {
    
    /* Interacting with Graphics2D Library to create Textures */
    
    public void texttoGraphics(String text, JSAPResult config, String formatted, int width, String type) throws IOException {         /* Makes .png files containing tags+names which can be used as textures */
        
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial Bold", Font.PLAIN, 90);                                          /* Set font and font size here */
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        //int width = fm.stringWidth(text)+20;
        int height = 128;                                                                    /* Might need changing later, was set to mostly fit well with output */
        g2d.dispose();

        
        //g2d.drawString(text, 0, fm.getAscent());
        if(fm.stringWidth(text)<=width-30){                                                    /* Case for single lines */
            
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d = handleRenderingHints(g2d);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            Color c=new Color(255,255,255,255);                                        /* Setting background color and transparency */
            g2d.setColor(c);                                            
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.BLACK);                                                  /* Text color */
            //Rectangle rect = new Rectangle(0,0,width,height);                     /* Texture without icon, comment the createTextnIcon */
            createTextnIcon(g2d, width, height, font, text, type);                  /* Texture with Icon */
            //drawCenteredString(g2d, text, rect, font);
            
        }
        else {                                                                             /* Case for multiple lines */
            //height = height*((fm.stringWidth(text)/width));
            Font font2 = new Font("Arial Bold", Font.PLAIN, 52);                                          
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d = handleRenderingHints(g2d);
            g2d.setFont(font2);
            fm = g2d.getFontMetrics();
            Color c=new Color(255,255,255,255);
            g2d.setColor(c);
            g2d.fillRect(128, 0, width, height);
            g2d.setColor(Color.WHITE); 
            g2d.drawRect(0,0,128,128);                                                  
            g2d.setColor(Color.BLACK); 
            BufferedImage bi;
            bi = chooseIcon(type);
            g2d.drawImage(bi, 0, 0, 128, 128, null);
            drawStringMultiLine(g2d, text, width, 140, 54);
        }
        
        g2d.dispose();
        try {
            String jsn = config.getString("JSON_ALL").substring(0,config.getString("JSON_ALL").indexOf(".")+".".length());
            jsn = jsn.substring(0, jsn.length() - 1);
            jsn = jsn.replace("target","");                             /* For testing purposes json strings start with target, so let's get rid of it */
            jsn = jsn.replace("Target","");
            jsn = jsn.replace("_","-");
            ImageIO.write(img, "png", new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/" + "poi_" + formatted + "-modellib/Texture/poi_" + formatted + ".png"));
        } catch (IOException ex) {
            
        }
        
    }
    
    /**********************************************************************************************************************************************/
    
    static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {            /* Drawing a centered string */
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
    
    /**********************************************************************************************************************************************/
    
    static void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {       /* Drawing multiple lines of strings */
        FontMetrics m = g.getFontMetrics();
        if(m.stringWidth(text) < lineWidth) {
            g.drawString(text, x, y);
        } else {
            String[] words = text.split(" ");
            String currentLine = words[0];
            for(int i = 1; i < words.length; i++) {
                if(m.stringWidth(currentLine+words[i]) < lineWidth) {
                    currentLine += " "+words[i];
                } else {
                    g.drawString(currentLine, x, y);
                    y += m.getHeight();
                    currentLine = words[i];
                }
            }
            if(currentLine.trim().length() > 0) {
                g.drawString(currentLine, x, y);
            }
        }
    }
    
    /**********************************************************************************************************************************************/
    
    static Graphics2D handleRenderingHints(Graphics2D g2d){                 /* Controlling the quality of rendering */
        
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        return g2d;   
    }
    
    /**********************************************************************************************************************************************/
   
    static void createTextnIcon(Graphics g2d, int width, int height, Font font, String text, String type) throws IOException{            /* Work in progress */
        
        BufferedImage bi;
        
        bi = chooseIcon(type);
       
        
        if (g2d.getFontMetrics().stringWidth(text) > ((width*6)/7)){    
            
            g2d.setColor(Color.WHITE); 
            g2d.drawRect(0,0,128,128);                                                  
            g2d.setColor(Color.BLACK); 
            g2d.drawImage(bi, 0, 0, 128, 128, null);
            Rectangle rect = new Rectangle(128,0,width-128,height);
            drawCenteredString(g2d, text, rect, font);
        
        }
        
        else {
            
            g2d.setColor(Color.WHITE); 
            int x = (width/2)-(g2d.getFontMetrics().stringWidth(text)/2) - 144;
            int y = 0;
            g2d.drawRect(x,y,128,128); 
            g2d.setColor(Color.BLACK); 
            g2d.drawImage(bi, x, y+10, 108, 118, null);
            Rectangle rect = new Rectangle(0,0,width,height);
            drawCenteredString(g2d, text, rect, font);
            
        }
        
        
    }
    
    /**********************************************************************************************************************************************/
    
    static BufferedImage chooseIcon(String type) throws IOException{
        
        BufferedImage bi;
        
        switch (type) {                     /* Icons for tags */
            case "Peak":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/peak.png"));
                break;
            case "City":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/city.png"));
                break;
            case "Ruins":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/ruins.png"));
                break;
            case "Temple":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/temple.png"));
                break;
            case "Church":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/temple.png"));
                break;    
            case "Village":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/village.png"));
                break;
            case "Suburb":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/suburb.png"));
                break;    
            case "Town":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/town.png"));
                break;
            case "Attraction":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/attraction.png"));
                break;    
            case "Castle":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/Castle.png"));
                break;       
            case "Island":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/island.png"));
                break;      
            case "Chapel":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/chapel.png"));
                break;      
            case "Nature_reserve":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/nature_reserve.png"));
                break;      
            case "Archaeological_site":
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/archaeological_site.png"));
                break;       
            default:
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/blank.png"));
                break;
        }
        
        return bi;
    }
    
    
}
