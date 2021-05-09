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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import static poiosm2fs.Main_POIOSM2FS.checkFname;
import static poiosm2fs.Main_POIOSM2FS.checkTags;
import static poiosm2fs.Main_POIOSM2FS.modifyAlt;
import static poiosm2fs.Main_POIOSM2FS.readFile;
import static poiosm2fs.Main_POIOSM2FS.updateProgress;
import static poiosm2fs.GraphicsInteraction.texttoGraphics;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Marcin
 */
public class ParseJSONAll {
    
    protected static void parseJSONAll (JSAPResult config) throws IOException{
        
        /*function takes the chosen long JSON file and turns it into POI xml file suitable for MSFS*/
        
        List <ModifiedData> JSONAll_list = new ArrayList<>();          /* In this case elements won't be modified, since the picked node will be center, so ElementData can be skipped */
        
        String json_name = config.getString("JSON_ALL");
        
        String filepath = "./" + json_name;
               
        String jsonstring = readFile(filepath, StandardCharsets.UTF_8);
        
        JSONObject obj = new JSONObject(jsonstring);                    /* Using the org.json library for JSON parsing */
        JSONArray arr = obj.getJSONArray("elements");
        
        String outputfile;
        
        if (config.getString("OUTPUT").equals("output")){               /* Setting the name of output file */
            String jsn = config.getString("JSON_ALL").substring(0,config.getString("JSON_ALL").indexOf(".")+".".length());
            jsn = jsn.substring(0, jsn.length() - 1);
            outputfile = jsn + ".txt";
        }
        else {outputfile = config.getString("OUTPUT") + ".txt";}
        
        System.out.println("\n***********************************");                /*Information for the user about selected parameters*/
        System.out.println("* POIOSM2FS JSON / CSV Converter  *");
        System.out.println("***********************************\n");
                    System.out.println("Chosen parameters:");
                    System.out.println("FILE: " + filepath);
                    System.out.println("LABEL: " + config.getString("LABEL"));
                    System.out.println("OWNER: " + config.getString("OWNER"));
                    System.out.println("ALT: " + config.getDouble("ALT"));
                    System.out.println("OUTPUT FILE: " + outputfile);
                    System.out.println("REMOVE_EMPTY: " + config.getBoolean("remove_empty"));
                    System.out.println("REMOVE_NONLATIN: " + config.getBoolean("remove_nonlatin"));
                    if (config.getBoolean("textures")){
                        String jsn = outputfile.substring(0,outputfile.indexOf(".")+".".length());
                        jsn = jsn.substring(0, jsn.length() - 1);
                        System.out.println("TEXTURES: ./texture_" + jsn);
                        System.out.println("TEXTURE_WIDTH: " + config.getInt("TEXTURE_WIDTH"));
                    }
                    System.out.println("");
        
        
        System.out.println("");
        
        Double progressPercentage;
        
        Double elementcounter = 0.0;
        
        for (int i=0;i<arr.length();i++){
            if (arr.getJSONObject(i).has("tags"))
                elementcounter++;
        }
        
        for (int i=0;i<arr.length();i++){               /*Looking for elements within JSON*/
            
            if (arr.getJSONObject(i).getString("type").equals("way") || arr.getJSONObject(i).getString("type").equals("relation")){              /* Element contains multiple nodes */
            
                String name, nameen, type = null, ele;
                
                Double lat, lon;

                if (arr.getJSONObject(i).has("center")){
                    JSONObject center = arr.getJSONObject(i).getJSONObject("center");            /* Get center node for current element */
                    
                    if(center.has("lat")){
                        lat = center.getDouble("lat");
                    } else {
                        lat = 0.0;
                    }
                
                    if(center.has("lon")){
                        lon = center.getDouble("lon");
                    } else {
                        lon = 0.0;
                    }
                } else {
                    lat = 0.0;
                    lon = 0.0;
                }
                
                //System.out.println(lat);
                //System.out.println(lon);
                
                
                if (arr.getJSONObject(i).has("tags")){
                    
                    JSONObject tags = arr.getJSONObject(i).getJSONObject("tags");               /* Contains information like name, english name etc.*/

                    if(tags.has("name")){
                        name = tags.getString("name");
                    }
                    else {
                        name = null;
                    }

                    type = checkTags(type, tags);

                    if(tags.has("name:en")){
                        nameen = tags.getString("name:en");
                    }
                    else{
                        nameen = null;    
                    }
                    
                    if(tags.has("ele")){
                        ele = tags.getString("ele");
                    }
                    else{
                        ele = null;    
                    }
                    
                } else {
                    name = null;
                    type = null;
                    nameen = null;  
                    ele = null;
                }

                /* Creating an element */

                ModifiedData tempElement = new ModifiedData(name, nameen, type, lat, lon);           /* type used here */  
                
                if (type != null && type.equals("place_of_worship")) {
                    type = "Temple";
                    int NT = arr.getJSONObject(i).getJSONObject("tags").length();
                    tempElement.setType(type);
                    tempElement.setNT(NT);
                    //System.out.println(NT);
                }
                
                if (type != null && type.equals("village")){
                    int NT = arr.getJSONObject(i).getJSONObject("tags").length();
                    tempElement.setNT2(NT);
                }

                tempElement.setEle(ele);
                
                JSONAll_list.add(tempElement);            /* Add element to the list */
                
                Double d = new Double(i);
                
                progressPercentage = d/elementcounter;   
                        
                updateProgress(progressPercentage*0.99);
                
            }
            
            else if (arr.getJSONObject(i).getString("type").equals("node")){                        /*Element is a singular node */
                
                String name, nameen, type = null, ele;
                
                Double lat, lon;
                
                if (arr.getJSONObject(i).has("lat")){
                    lat = arr.getJSONObject(i).getDouble("lat");
                } else {
                    lat = 0.0;
                }
                
                if (arr.getJSONObject(i).has("lon")){
                    lon = arr.getJSONObject(i).getDouble("lon");
                } else {
                    lon = 0.0;
                }
                
                if (arr.getJSONObject(i).has("tags")){
                    
                    JSONObject tags = arr.getJSONObject(i).getJSONObject("tags");               /* Contains information like name, english name etc.*/

                    if(tags.has("name")){
                        name = tags.getString("name");
                    }
                    else {
                        name = null;
                    }

                    type = checkTags(type, tags);

                    if(tags.has("name:en")){
                        nameen = tags.getString("name:en");
                    }
                    else{
                        nameen = null;    
                    }
                    
                    if(tags.has("ele")){
                        ele = tags.getString("ele");
                    }
                    else{
                        ele = null;    
                    }
                    
                } else {
                    break;              /* If no tags exist, than it's just a singular node referenced somewhere else and can be ignored */
                }
                

                
                ModifiedData tempElement = new ModifiedData(name, nameen, type, lat, lon);           /* type used here */  
                
                if (type != null && type.equals("place_of_worship")) {
                    type = "Temple";
                    int NT = arr.getJSONObject(i).getJSONObject("tags").length();
                    tempElement.setType(type);
                    tempElement.setNT(NT);
                    //System.out.println(NT);
                }
                
                if (type != null && type.equals("village")){
                    int NT = arr.getJSONObject(i).getJSONObject("tags").length();
                    tempElement.setNT2(NT);
                }
                
                tempElement.setEle(ele);
                
                JSONAll_list.add(tempElement);            /* Add element to the list */
                
                Double d = new Double(i);
                
                progressPercentage = d/elementcounter;   
                        
                updateProgress(progressPercentage*0.99);
                
            }
                
        
        }
        
        System.out.print("\r");
        System.out.print("[..................................................] 100%");
        System.out.println("");
        System.out.println("");
        
        
        try {                                   /* Creating an output file */
            File myObj = new File(outputfile);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
         } else {
                System.out.println("File already exists.");
         }
         } catch (IOException e) {
             System.out.println("An error occurred.");
                //e.printStackTrace();
         }
        
        int linecount = 0;                 /* Counter for lines in finished document */
        String capitalTag = "";
        Double modifiedAlt;
        
        
        try {
            try (FileWriter myWriter = new FileWriter(outputfile) /*Writing to the output file */ ) {
                for (ModifiedData y: JSONAll_list){
                    UUID uuid = UUID.randomUUID();                      /* Random UUID (universally unique identifier)*/
                    String fuuid = uuid.toString().toUpperCase();       /* UUID changed to upper-case */
                    String fname;                                       /* English name will be used if possible, if not the default name */
                    if (y.getEnName() != null){
                        fname = y.getEnName();
                    } else
                    {fname = y.getName();}
                    
                    fname = checkFname(fname);                      /* Making sure that fname doesn't contain unnecessary characters and isn't null */
                    
                    String clean = Normalizer.normalize(fname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                    boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                    
                    if(y.getNT()>=config.getInt("REMOVE_SENSITIVITY_THRESHOLD") && y.getNT2()>=config.getInt("REMOVE_SENSITIVITY_THRESHOLD_VL")){
                        if(y.getLat() != 0.0 || y.getLon() != 0.0){
                            if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){  
                                if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                    modifiedAlt = modifyAlt(config.getDouble("ALT"), y.getType());
                                    if (y.getEle() == null){
                                        if(y.getType() != null){
                                            capitalTag = y.getType().substring(0, 1).toUpperCase() + y.getType().substring(1);      /*Capitalize first letter of tag*/
                                            myWriter.write("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" + capitalTag + ": "
                                                    + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                                    + modifiedAlt +"\"/> \n");
                                        } else {
                                            myWriter.write("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" 
                                                    + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                                    + modifiedAlt +"\"/> \n");
                                        }
                                    } else {     
                                        if(y.getType() != null){
                                            capitalTag = y.getType().substring(0, 1).toUpperCase() + y.getType().substring(1);
                                            myWriter.write("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" + capitalTag + ": "
                                                    + fname + " " + y.getEle() + " m"
                                                    + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                                    + modifiedAlt +"\"/> \n");
                                        } else {
                                            myWriter.write("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" 
                                                    + fname + " " + y.getEle() + " m"
                                                    + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                                    + modifiedAlt +"\"/> \n");
                                        }
                                    }
                                        linecount++;
                                        if(config.getBoolean("textures")){
                                            String formatted = String.format("%05d", linecount);
                                            String jsn = config.getString("JSON_ALL").substring(0,config.getString("JSON_ALL").indexOf(".")+".".length());
                                            jsn = jsn.substring(0, jsn.length() - 1);
                                            new File(System.getProperty("user.dir") + "/texture_" + jsn  + "/POI_" + formatted).mkdirs();
                                            texttoGraphics(capitalTag + ": " + fname, config, formatted, config.getInt("TEXTURE_WIDTH"));
                                            capitalTag = "";
                                        }
                                }
                                //Files.createDirectories(Paths.get("/test/test" + linecount));   
                            } 
                        }
                    }
                }
            }
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
            
            System.out.println("Number of lines: " + linecount);
        
    }
    
}
