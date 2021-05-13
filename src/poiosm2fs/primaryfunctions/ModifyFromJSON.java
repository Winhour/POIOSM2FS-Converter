/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.primaryfunctions;

import com.martiansoftware.jsap.JSAPResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import poiosm2fs.models.ElementData;
import poiosm2fs.MiscFunctions;
import poiosm2fs.models.ModifiedData;

/**
 *
 * @author Marcin
 */
public class ModifyFromJSON {
    
    public void modifyFromJSON(JSAPResult config) throws IOException{
        
        /*function takes the chosen short JSON file and turns it into POI xml file suitable for MSFS*/
        
        List <ElementData> listofElements = new ArrayList<>();       /* List of elements containing nodes lists */
        List <ModifiedData> finallist = new ArrayList<>();          /* List of elemenets after modification, each with specific node with lat and lon */

        MiscFunctions mFunc = new MiscFunctions();
        
        int nodeinterval = config.getInt("STEP");
        
        String json_name = config.getString("JSON");
               
        String filepath = "./" + json_name;
        
        String jsonstring = mFunc.readFile(filepath, StandardCharsets.UTF_8);
        
        JSONObject obj = new JSONObject(jsonstring);                    /* Using the org.json library for JSON parsing */
        JSONArray arr = obj.getJSONArray("elements");
        
        String outputfile;
        
        if (config.getString("OUTPUT").equals("output")){               /* Setting the name of output file */
            String jsn = config.getString("JSON").substring(0,config.getString("JSON").indexOf(".")+".".length());
            jsn = jsn.substring(0, jsn.length() - 1);
            outputfile = jsn + ".txt";
        }
        else {outputfile = config.getString("OUTPUT") + ".txt";}
        
        System.setProperty("org.jline.terminal.dumb", "true");          /*Supressing a warning for dumb terminal, since it's always there when using IDE*/
        
        int terminalWidth = org.jline.terminal.TerminalBuilder.terminal().getWidth();           /* Getting the width of a terminal using the jline library */
        
        if(terminalWidth >= 100)
            mFunc.makeAsciiArt();          /* ASCII Header */
        else
            mFunc.makeSimpleHeader();        /* Simple Header */
                    System.out.println("Chosen parameters:");                       /*Information for the user about selected parameters*/
                    System.out.println("FILE: " + filepath);
                    System.out.println("STEP: " + nodeinterval);
                    System.out.println("LABEL: " + config.getString("LABEL"));
                    System.out.println("OWNER: " + config.getString("OWNER"));
                    System.out.println("ALT: " + config.getDouble("ALT"));
                    System.out.println("OUTPUT FILE: " + outputfile);
                    System.out.println("REMOVE_EMPTY: " + config.getBoolean("remove_empty"));
                    System.out.println("REMOVE_NONLATIN: " + config.getBoolean("remove_nonlatin"));
                    System.out.println("");
        
        
        System.out.println("");
        
        for (int i=0;i<arr.length();i++){               /*Looking for elements within JSON*/
            
            if (arr.getJSONObject(i).getString("type").equals("node")) break;           /*When we reach the section with nodes information, exit the loop*/
            
            String name, nameen, type;
            long midnode;
            
            JSONArray arrnodes = arr.getJSONObject(i).getJSONArray("nodes");            /* Get nodes for current element */
            
            midnode = arrnodes.optLong(arrnodes.length()/2);                            /* Get the middle node, which might be used in case there aren't enough nodes for the interval */
            
            JSONObject tags = arr.getJSONObject(i).getJSONObject("tags");               /* Contains information like name, english name etc.*/
            
            if(tags.has("name")){
                name = tags.getString("name");;
            }
            else {
                name = null;
            }

            if(tags.has("waterway")){
                type = tags.getString("waterway");
            }
            else{
                type = null;    
            }

            if(tags.has("name:en")){
                nameen = tags.getString("name:en");
            }
            else{
                nameen = null;    
            }
            
            /* Creating an element */
            
            ElementData tempElement = new ElementData(name, nameen, type);           /* type currently not used, but might be in the future */    
            
            if (arrnodes.length() > nodeinterval+1){
                               
                for (int j = 0; j < arrnodes.length(); j = j+nodeinterval) {         /* filling list of nodes */
                    tempElement.addToNodesList(arrnodes.optLong(j));
                    //System.out.println(arrnodes.optLong(j));
                    if (j+nodeinterval > arrnodes.length()) break;
                }
            } else {
                tempElement.setMiddle(midnode);         /* set middle node */
            }
            
            listofElements.add(tempElement);            /* Add element to the list */
        
        }

        
        listofElements.forEach((ElementData x) -> {
            /* Creating the list of modified elements */
            
            if (x.getMiddleNode() != 0){                /*Case when we need to use the middle node */
                ModifiedData tempmod;
                tempmod = modifyElements(x, arr, x.getMiddleNode());
                finallist.add(tempmod);
            }
            else {
                List<Long> listl = x.getNodeList();      /* Case when we have a list of nodes */
                for (int i=0;i<listl.size();i++){
                    ModifiedData tempmod;
                    tempmod = modifyElements(x, arr, listl.get(i));
                    finallist.add(tempmod);
                }
            }
        });      


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
        
        try {
            try (FileWriter myWriter = new FileWriter(outputfile) /*Writing to the output file */ ) {
                for (ModifiedData y: finallist){
                    UUID uuid = UUID.randomUUID();                      /* Random UUID (universally unique identifier)*/
                    String fuuid = uuid.toString().toUpperCase();       /* UUID to uppercase */
                    String fname;                                       /* English name will be used if possible, if not the default name */
                    if (y.getEnName() != null){
                        fname = y.getEnName();
                    } else
                    {fname = y.getName();}
                    
                    if (fname != null) {
                        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);
                        fname = fname.replace("\"","'");
                    }
                    
                    if (fname == null) fname = "(empty)";               /* If neither name exists, use empty signifier */
                    
                    String clean = Normalizer.normalize(fname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                    boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                    
                    
                    if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){  
                        if(!(config.getBoolean("remove_nonlatin") && !valid)){
                            if(!config.getString("LABEL").equals("none")){
                                myWriter.write("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\"" + config.getString("LABEL") + ": "
                                        + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                        + config.getDouble("ALT") +"\"/> \n");
                                linecount++;
                            } else {
                                myWriter.write("<LandmarkLocation instanceId=\"{" + fuuid + "}\" type=\"POI\" name=\""
                                        + fname + "\" owner=\""+ config.getString("OWNER") + "\" lat=\"" + y.getLat() + "\" lon=\"" + y.getLon() + "\" alt=\"" 
                                        + config.getDouble("ALT") +"\"/> \n");
                                linecount++;
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
    
    private static ModifiedData modifyElements(ElementData ed, JSONArray arr, long id){     /* Turning ElementDatas into ModifiedData */
        
        double lat = 0, lon = 0;
        
        for (int i=0;i<arr.length();i++){
            
            if (arr.getJSONObject(i).getString("type").equals("node")){
                if(arr.getJSONObject(i).getLong("id") == id){
                    lat = arr.getJSONObject(i).getDouble("lat");
                    lon = arr.getJSONObject(i).getDouble("lon");
                }
            }
            
        }
        
        ModifiedData modifiedElement = new ModifiedData(ed.getName(), ed.getEnName(), ed.getType(), lat, lon);
        
        return modifiedElement;
    }
    
    
}
