/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.texturesxml;

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
    
    public void texttoGraphics(String text, JSAPResult config, String formatted, int width, String type, boolean isIcao) throws IOException {         /* Makes .png files containing tags+names which can be used as textures */
        
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial Bold", Font.PLAIN, ((90*width)/2048));                                          /* Set font and font size here */
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        //int width = fm.stringWidth(text)+20;
        int height = width/16;                                                                    /* Height is width/16 so for example 2048x128 or 1024x64 */
        g2d.dispose();

        
        int[] rgbvalues = {0, 0, 0, 255, 255, 255};              /* RGB Values for Texture text and background */
        rgbvalues = setRGBValues(rgbvalues, config, type);
            
        Color c=new Color(rgbvalues[3],rgbvalues[4],rgbvalues[5],255);                                        /* Setting background color and transparency */    
        Color c2=new Color(rgbvalues[0],rgbvalues[1],rgbvalues[2],255);                                       /* Text color */
        
        //g2d.drawString(text, 0, fm.getAscent());
        if(fm.stringWidth(text)<=(width-((height*5)/2))){                                                    /* Case for single lines */
            
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d = handleRenderingHints(g2d);                                                                /* Controlling the quality of rendering */
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(c);                                            
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(c2);                                                  /* Text color */
            //Rectangle rect = new Rectangle(0,0,width,height);                     /* Texture without icon, comment the createTextnIcon */
            createTextnIcon(g2d, width, height, font, text, type, config, isIcao, rgbvalues);                  /* Texture with Icon */
            //drawCenteredString(g2d, text, rect, font);
        }
        
        else {                                                                             /* Case for multiple lines */
            //height = height*((fm.stringWidth(text)/width));
            Font font2 = new Font("Arial Bold", Font.PLAIN, ((52*width)/2048));                 /* Smaller font for multiple lines */                         
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d = handleRenderingHints(g2d);
            g2d.setFont(font2);
            fm = g2d.getFontMetrics();
            BufferedImage bi;
            bi = chooseIcon(type, isIcao);
            if(fm.stringWidth(text)<=width-(width/14)){
                g2d.setColor(c);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(c); 
                int x = (width/2)-(g2d.getFontMetrics().stringWidth(text)/2) - ((height*11)/10);
                int y = 0;
                g2d.drawRect(x,y,height,height); 
                g2d.setColor(c2); 
                g2d.drawImage(bi, x, y+(height/12), ((height*7)/8), ((height*7)/8), null);
                Rectangle rect = new Rectangle(0,0,width,height);
                drawCenteredString(g2d, text, rect, font2);
            } else {
                g2d.setColor(c);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(c); 
                g2d.drawRect(0,0,height,height);                                                  
                g2d.setColor(c2); 
                g2d.drawImage(bi, 0, (height/8), ((height*7)/8), ((height*7)/8), null);
                drawStringMultiLine(g2d, text, width-((height*9)/8), ((height*9)/8), ((54*width)/2048));
            }
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
   
    static void createTextnIcon(Graphics g2d, int width, int height, Font font, String text, String type, JSAPResult config, boolean isIcao, int[] rgbvalues) throws IOException{            /* Work in progress */
        
        /* Create text and icon for a single line case */
        
        BufferedImage bi;
        
        bi = chooseIcon(type, isIcao);
                   
            Color c=new Color(rgbvalues[3],rgbvalues[4],rgbvalues[5],255);                                        /* Setting background color and transparency */    
            Color c2=new Color(rgbvalues[0],rgbvalues[1],rgbvalues[2],255);                                       /* Text color */
       
        
        if (g2d.getFontMetrics().stringWidth(text) > ((width*6)/7)){    
            
            g2d.setColor(c); 
            g2d.drawRect(0,0,height,height);                                                  
            g2d.setColor(c2); 
            g2d.drawImage(bi, 0, (height/8), ((height*7)/8), ((height*7)/8), null);
            Rectangle rect = new Rectangle(height,0,(width-height),height);
            drawCenteredString(g2d, text, rect, font);
        
        }
        
        else {
            
            g2d.setColor(c); 
            int x = (width/2)-(g2d.getFontMetrics().stringWidth(text)/2) - ((height*11)/10);
            int y = 0;
            g2d.drawRect(x,y,height,height); 
            g2d.setColor(c2); 
            g2d.drawImage(bi, x, y+(height/12), ((height*7)/8), ((height*7)/8), null);
            Rectangle rect = new Rectangle(0,0,width,height);
            drawCenteredString(g2d, text, rect, font);
            
        }
        
        
    }
    
    /**********************************************************************************************************************************************/
    
    static BufferedImage chooseIcon(String type, boolean isIcao) throws IOException{            /* Chooses which icon to add to the texture */
        
        BufferedImage bi;

        if (new File(System.getProperty("user.dir") + "/asset").exists()){
        
            if (isIcao){                        /* This icao flag is very annoying, but that's how the client wanted it */
                bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/airport.png"));
            }
            else {

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
                    case "Residential":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/suburb.png"));
                        break;        
                    case "Town":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/town.png"));
                        break;
                    case "Attraction":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/attraction.png"));
                        break;    
                    case "Castle":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/castle.png"));
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
                    case "Archeo":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/archaeological_site.png"));
                        break;    
                    case "Reservoir":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/reservoir.png"));
                        break;      
                    case "Industrial":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/industrial.png"));
                        break;  
                    case "Forest":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/forest.png"));
                        break;  
                    case "River":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/river.png"));
                        break;  
                    case "Water":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/river.png"));
                        break;   
                    case "Lake":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/lake.png"));
                        break;  
                    case "Monument":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/monument.png"));
                        break;    
                    case "Military":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/military.png"));
                        break;  
                    case "Stadium":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/stadium.png"));
                        break;    
                    case "Pond":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/pond.png"));
                        break; 
                    case "Canal":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/canal.png"));
                        break; 
                    case "Cemetery":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/cemetery.png"));
                        break; 
                    case "Tomb":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/cemetery.png"));
                        break;     
                    case "Railway_station":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/railway_station.png"));
                        break;      
                    case "Airfield":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/airport.png"));
                        break; 
                    case "Aerodrome":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/airport.png"));
                        break;  
                    case "Airport":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/airport.png"));
                        break;    
                    case "Mosque":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/mosque.png"));
                        break;   
                    case "Aircraft":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/aircraft.png"));
                        break;   
                    case "Bus_station":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/bus_station.png"));
                        break;      
                    case "Battlefield":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/battlefield.png"));
                        break;    
                    case "Museum":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/museum.png"));
                        break;   
                    case "Zoo":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/zoo.png"));
                        break;   
                    case "University":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/university.png"));
                        break;   
                    case "Barracks":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/military.png"));
                        break;      
                    case "Retail":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/retail.png"));
                        break;   
                    case "Office":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/office.png"));
                        break;   
                    case "Park":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/park.png"));
                        break;   
                    case "Glacier":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/glacier.png"));
                        break;   
                    case "Synagogue":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/synagogue.png"));
                        break;   
                    case "Ferry_terminal":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/ferry.png"));
                        break;   
                    case "Manor":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/manor.png"));
                        break;   
                    case "Lagoon":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/lagoon.png"));
                        break;   
                    case "Wetland":
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/wetland.png"));
                        break;       
                    default:
                        bi = ImageIO.read(new File(System.getProperty("user.dir") + "/asset/blank.png"));
                        break;
                }
            }
        } else {
            System.out.println("/asset folder does not exist, make sure to add it to the same directory as the program");
            return null;
        }
        return bi;
    }
    
    /**********************************************************************************************************************************************/
    
    static int[] setRGBValues(int[] rgbvalues, JSAPResult config, String type){                   /* Setting RGB Values for Texture text and background */ switch (type) {
            
            case "City":
            case "Town":
                rgbvalues[0] = 255;                                                                 /* For cities and towns dark blue background / white text */
                rgbvalues[1] = 255;
                rgbvalues[2] = 255;
                rgbvalues[3] = 0;
                rgbvalues[4] = 0;
                rgbvalues[5] = 128;
                break;
            case "Village":
                rgbvalues[0] = 255;                                                                /* For villages slightly dark green background / white text */
                rgbvalues[1] = 255;
                rgbvalues[2] = 255;
                rgbvalues[3] = 0;
                rgbvalues[4] = 77;
                rgbvalues[5] = 0;
                break;
            case "Island":
                rgbvalues[0] = 255;                                                                /* For islands orange background / white text */
                rgbvalues[1] = 255;
                rgbvalues[2] = 255;
                rgbvalues[3] = 137;
                rgbvalues[4] = 67;
                rgbvalues[5] = 0;
                break;  
            case "Peak":
                rgbvalues[0] = 255;                                                                /* For peaks khaki background / white text */
                rgbvalues[1] = 255;
                rgbvalues[2] = 255;
                rgbvalues[3] = 66;
                rgbvalues[4] = 66;
                rgbvalues[5] = 31;
                break;    
            default:
                /* Use either white on black, or chosen by the user */
                try {
                    if(config.getBoolean("COLOR_TEXT")){
                        rgbvalues[0] = config.getIntArray("COLOR_TEXT")[0]; 
                        rgbvalues[1] = config.getIntArray("COLOR_TEXT")[1];
                        rgbvalues[2] = config.getIntArray("COLOR_TEXT")[2];
                    }

                    else {
                        rgbvalues[0] = 255; 
                        rgbvalues[1] = 255;
                        rgbvalues[2] = 255;
                    }
                    if(config.getBoolean("COLOR_BACKGROUND")){
                        rgbvalues[3] = config.getIntArray("COLOR_BACKGROUND")[0];
                        rgbvalues[4] = config.getIntArray("COLOR_BACKGROUND")[1];
                        rgbvalues[5] = config.getIntArray("COLOR_BACKGROUND")[2];
                    }

                    else {
                        rgbvalues[3] = 0;
                        rgbvalues[4] = 0;
                        rgbvalues[5] = 0;
                    }
                    break;
                }
                catch (ArrayIndexOutOfBoundsException ar){
                    System.out.println("\n\nText color and background color require three arguments, check if they are added the right way (--ct:100,100,100)\n");
                    System.exit(1);
                }
        }
        
        return rgbvalues;
    }
    
    /**********************************************************************************************************************************************/
}
