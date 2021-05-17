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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import poiosm2fs.GLTFWriter;
import poiosm2fs.GraphicsInteraction;
import poiosm2fs.MiscFunctions;
import poiosm2fs.XStreamInteraction;
import poiosm2fs.models.ModifiedData;
import poiosm2fs.models.xmlmodels.AssetGroup;
import poiosm2fs.models.xmlmodels.AssetPackage;
import poiosm2fs.models.xmlmodels.FSData;
import poiosm2fs.models.xmlmodels.FlagsAG;
import poiosm2fs.models.xmlmodels.FlagsAP;
import poiosm2fs.models.xmlmodels.ItemSettings;
import poiosm2fs.models.xmlmodels.LibraryObject;
import poiosm2fs.models.xmlmodels.ModelInfo;
import poiosm2fs.models.xmlmodels.Packages;
import poiosm2fs.models.xmlmodels.Project;
import poiosm2fs.models.xmlmodels.SceneryObject;

/**
 *
 * @author Marcin
 */
public class ParseJSONAll {
    
    public void parseJSONAll (JSAPResult config) throws IOException{
        
        /*function takes the chosen long JSON file and turns it into POI xml file suitable for MSFS*/
        
        List <ModifiedData> JSONAll_list = new ArrayList<>();          /* In this case elements won't be modified, since the picked node will be center, so ElementData can be skipped */
        
        String json_name = config.getString("JSON_ALL");
        
        String filepath = "./" + json_name;
        
        MiscFunctions mFunc = new MiscFunctions();                      /* Object to use miscellanous functions */
        
        List<AssetGroup> assetGroups = new ArrayList<>();               /* List for collection of asset groups for package definition xml */
        
        List<SceneryObject> sceneryObjects = new ArrayList<>();         /* List for collection of scenery obejects for data xml */
        
        //makePackageDefinitionsXML(config);                            /* Testing XML functions */
        //makeMainXML();
        //makePackageSourcesDataXML();
        //makeModelInfoXML();
               
        String jsonstring = mFunc.readFile(filepath, StandardCharsets.UTF_8);
        
        JSONObject obj = new JSONObject(jsonstring);                    /* Using the org.json library for JSON parsing */
        JSONArray arr = obj.getJSONArray("elements");
        
        String outputfile;
        
        if (config.getString("OUTPUT").equals("output")){               /* Setting the name of output file */
            String jsn = config.getString("JSON_ALL").substring(0,config.getString("JSON_ALL").indexOf(".")+".".length());
            jsn = jsn.substring(0, jsn.length() - 1);
            jsn = jsn.replace("target","");                             /* For testing purposes json strings start with target, so let's get rid of it */
            jsn = jsn.replace("Target","");
            outputfile = jsn + ".txt";
        }
        else {outputfile = config.getString("OUTPUT") + ".txt";}
        
        System.setProperty("org.jline.terminal.dumb", "true");                          /*Supressing a warning for dumb terminal, since it's always there when using IDE*/
        
        int terminalWidth = org.jline.terminal.TerminalBuilder.terminal().getWidth();   /* Getting the width of a terminal using the jline library */
        
        if(terminalWidth >= 100)
            mFunc.makeAsciiArt();                 /* ASCII Header */
        else
            mFunc.makeSimpleHeader();              /* Simple Header */
        
                    System.out.println("Chosen parameters:");                           /*Information for the user about selected parameters*/
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
        
        /* Creating directories for textures and models, initializing some elements */
        
        if(config.getBoolean("textures")){

            initializeTextureFolders(outputfile, assetGroups);
            
        }
        
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
                
                if (arr.getJSONObject(i).has("tags")){
                    
                    JSONObject tags = arr.getJSONObject(i).getJSONObject("tags");               /* Contains information like name, english name etc.*/

                    if(tags.has("name")){
                        name = tags.getString("name");
                    }
                    else {
                        name = null;
                    }

                    type = mFunc.checkTags(type, tags);

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
                        
                mFunc.updateProgress(progressPercentage*0.99);
                
            }
            
            else if (arr.getJSONObject(i).getString("type").equals("node")){                        /*Element is a singular node */
                
                String name, nameen, type = null, ele;
                
                Double lat, lon;
                
                if (arr.getJSONObject(i).has("lat")){                                               /* Latitude */
                    lat = arr.getJSONObject(i).getDouble("lat");
                } else {
                    lat = 0.0;
                }
                
                if (arr.getJSONObject(i).has("lon")){                                               /* Longitude */
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

                    type = mFunc.checkTags(type, tags);

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
                        
                mFunc.updateProgress(progressPercentage*0.99);
                
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
                System.out.println("File " + myObj.getName() + " already exists.");
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
                    
                    fname = mFunc.checkFname(fname);                      /* Making sure that fname doesn't contain unnecessary characters and isn't null */
                    
                    String clean = Normalizer.normalize(fname, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                    boolean valid = (clean.substring(0,1).matches("\\w+") || clean.substring(0,1).matches("[0-9]") || clean.substring(0,1).matches("\"")
                                || clean.substring(0,1).matches("\\(") || clean.substring(0,1).matches("\\["));
                    
                    if(y.getNT()>=config.getInt("REMOVE_SENSITIVITY_THRESHOLD") && y.getNT2()>=config.getInt("REMOVE_SENSITIVITY_THRESHOLD_VL")){
                        if(y.getLat() != 0.0 || y.getLon() != 0.0){
                            if(!(config.getBoolean("remove_empty") && fname.equals("(empty)"))){  
                                if(!(config.getBoolean("remove_nonlatin") && !valid)){
                                    modifiedAlt = mFunc.modifyAlt(config.getDouble("ALT"), y.getType());
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
                                            
                                            /* Making XML's and Textures for each single POI */
                                            createSinglePOIXMLsandTextures(linecount, outputfile, fname, config, capitalTag, assetGroups, sceneryObjects, uuid, fuuid, modifiedAlt, y);
                                            
                                        }
                                }
                            } 
                        }
                    }
                }
            }
                System.out.println("\nSuccessfully wrote to the file: " + outputfile);
            } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
            
            System.out.println("\nNumber of lines: " + linecount + "\n");
            
            
            if(config.getBoolean("textures")){
                
                createOverarchingXMLs(outputfile, assetGroups, sceneryObjects);     /* Data XML and Package Definitions XML */
                
            }
        
    }
    
     /**********************************************************************************************************************************************/
    
    static boolean deleteDirectory(File directoryToBeDeleted) {                /* Recursive function to delete a directory and all subdirectories */
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    
     /**********************************************************************************************************************************************/
    
    static String makePackageDefinitionsXML(String jsn, List<AssetGroup> assetGroups){
        
        String jsn1 = jsn.replace("-","/");
        
        ItemSettings itemSettings = new ItemSettings("SCENERY","POIOSM2FS " + jsn1,"","3DSpotters");
        FlagsAP flags = new FlagsAP();

        AssetPackage assetPackage = new AssetPackage("3dsp-scenery-poiosmtofs-" + jsn.toLowerCase(), "PackageDefinitions\\3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() , "1.0.0", itemSettings, flags, assetGroups);
        
        XStreamInteraction xsi = new XStreamInteraction();
        String xmlData = xsi.generatePackageDefinitionsXML(assetPackage);
        return xmlData;
    }
    
     /**********************************************************************************************************************************************/
    
    static String makeMainXML(String jsn){
        
        Packages packagesx = new Packages("PackageDefinitions\\" + "3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + ".xml");
        
        String jsn1 = jsn.replace("-","/");
        
        Project project = new Project("2", "POIOSM2FS " + jsn1, "Packages", ".", "_PackageInt", packagesx);
        
        XStreamInteraction xsi = new XStreamInteraction();
        String xmlData = xsi.generateMainXML(project);
        return xmlData;
        
    }
    
     /**********************************************************************************************************************************************/
    
    static String makePackageSourcesDataXML(List<SceneryObject> sceneryObjects){

        FSData fsdata = new FSData("9.0", sceneryObjects);
        
        XStreamInteraction xsi = new XStreamInteraction();
        String xmlData = xsi.generatePackageSourcesDataXML(fsdata);
        return xmlData;
    }
    
     /**********************************************************************************************************************************************/
    
    static String makeModelInfoXML(ModelInfo modelInfo){
     
        XStreamInteraction xsi = new XStreamInteraction();
        String xmlData = xsi.generateModelInfoXML(modelInfo);
        return xmlData;
    }
    
    /**
     * @param outputfile*
     * @param assetGroups*
     * @throws java.io.IOException******************************************************************************************************************************************/
    
    public void initializeTextureFolders(String outputfile, List<AssetGroup> assetGroups) throws IOException{
        
            /* Setting up the initial files and folders for textures, including copying readmes etc. from /data */
        
            String jsn = formatOutString(outputfile);
            File dirfile = new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn);
            File dirfile2 = new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/docs");
            System.out.println("");
            if (dirfile.exists()){
                deleteDirectory(dirfile2);      /* Doing it this way to handle an error that sometimes appears with this directory */
                deleteDirectory(dirfile);
                System.out.println("Deleted directory: " + dirfile + "\n");
            }
            System.out.println("Created directory: " + dirfile + "\n");
        
            new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageDefinitions").mkdirs(); 
            String jsnl = jsn.toLowerCase();
            new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageDefinitions/3dsp-scenery-poiosmtofs-" + jsnl + "/ContentInfo").mkdirs(); 
            new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/data").mkdirs(); 
            new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/docs").mkdirs(); 
            
            try {
                try (FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/3DSp-POIOSM2FS-" + jsn + ".xml")) {
                    String xmlData = makeMainXML(jsn);
                    myWriter.write(xmlData);
                }
                System.out.println("File Created: " + System.getProperty("user.dir") + "\\3DSp-POIOSM2FS-" + jsn + "\\3DSp-POIOSM2FS-" + jsn + ".xml\n" );
            } catch (IOException e) {
                System.out.println("An error occurred.");
                //e.printStackTrace();
            }
            
            /* Initial Asset Groups for the Package Definitions xml */
            
            AssetGroup ag1 = new AssetGroup("ContentInfo", "ContentInfo", new FlagsAG(), "PackageDefinitions\\3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + "\\ContentInfo\\", "ContentInfo\\3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + "\\" );
            AssetGroup ag2 = new AssetGroup("poiosmtofs-" + jsn.toLowerCase() + "-docs", "Copy", new FlagsAG(), "PackageSources\\docs\\", ".\\" );
            AssetGroup ag3 = new AssetGroup("poiosmtofs-" + jsn.toLowerCase() + "-scene", "BGL", new FlagsAG(), "PackageSources\\data\\", "scenery\\3dsp\\3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + "\\" );


            assetGroups.add(ag1);
            assetGroups.add(ag2);
            assetGroups.add(ag3);
            
            /* Getting files from /data to their respective destination folders */
            
            if(new File(System.getProperty("user.dir") + "/data").exists()){
            
                /* data/ContentInfo/Thumbnail */
                
                File srcFile = new File(System.getProperty("user.dir") + "/data/ContentInfo/Thumbnail.jpg");
                File destFile = new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageDefinitions/3dsp-scenery-poiosmtofs-" + jsnl + "/ContentInfo/Thumbnail.jpg");

                FileUtils.copyFile(srcFile, destFile);
                
                /* data/docs/3dsp_logo.png */

                srcFile = new File(System.getProperty("user.dir") + "/data/docs/3dsp_logo.png");
                destFile = new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/docs/3dsp_logo.png");

                FileUtils.copyFile(srcFile, destFile);
                
                /* data/docs/LICENSE.txt */

                srcFile = new File(System.getProperty("user.dir") + "/data/docs/LICENSE.txt");
                destFile = new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/docs/LICENSE.txt");

                FileUtils.copyFile(srcFile, destFile);
                
                /* data/docs/README.md */

                srcFile = new File(System.getProperty("user.dir") + "/data/docs/README.md");
                
                String readmestring = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/data/docs/README.md")));
                
                String replacementString = "# 3DSp-POISM2FS-" + jsn;
                
                readmestring = readmestring.replaceFirst("# LEAVE_THIS_HEADER_UNCHANGED", replacementString);
                
                try {
                    try (FileWriter myWriter2 = new FileWriter(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/docs/README.md")) {
                        myWriter2.write(readmestring);
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
                
                 /* data/README.md */
                
                srcFile = new File(System.getProperty("user.dir") + "/data/README.md");
                
                readmestring = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/data/README.md")));
                
                readmestring = readmestring.replaceFirst("# LEAVE_THIS_HEADER_UNCHANGED", replacementString);
                
                try {
                    try (FileWriter myWriter2 = new FileWriter(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/README.md")) {
                        myWriter2.write(readmestring);
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
                
                
                /* data/data/ReleaseNotes.xml */
                
                srcFile = new File(System.getProperty("user.dir") + "/data/ReleaseNotes.xml");
                destFile = new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageDefinitions/3dsp-scenery-poiosmtofs-" + jsnl + "/ReleaseNotes.xml");

                FileUtils.copyFile(srcFile, destFile);
                
            }
            
        }
        
    /**
     * @param outputfile*
     * @param assetGroups
     * @param sceneryObjects*******************************************************************************************************************************************/
    
        public void createOverarchingXMLs (String outputfile, List<AssetGroup> assetGroups, List<SceneryObject> sceneryObjects){
            
            String jsn = formatOutString(outputfile);
                
                /* Creating an xml file for Package Definitions */
                
                try {
                    try (FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageDefinitions/3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + ".xml")) {
                        String xmlData = makePackageDefinitionsXML(jsn, assetGroups);
                        myWriter.write(xmlData);
                    }
                    System.out.println("File Created: " + System.getProperty("user.dir") + "\\3DSp-POIOSM2FS-" + jsn + "\\PackageDefinitions\\3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + ".xml\n" );
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
                
                /* Creating an xml file for Data */
                
                try {
                    try (FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/data/poiosmtofs-" + jsn.toLowerCase() + "-scene.xml")) {
                        String xmlData = makePackageSourcesDataXML(sceneryObjects);
                        myWriter.write(xmlData);
                    }
                    System.out.println("File Created: " + System.getProperty("user.dir") + "\\3DSp-POIOSM2FS-" + jsn + "\\PackageSources\\data\\poiosmtofs-" + jsn.toLowerCase() + "-scene.xml\n" );
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    //e.printStackTrace();
                }
            
        }

    
    /**
     * @param linecount*
     * @param outputfile
     * @param config*
     * @param fname*
     * @param capitalTag*
     * @param assetGroups*
     * @param sceneryObjects*
     * @param uuid*
     * @param fuuid*
     * @param modifiedAlt*
     * @param y*
     * @throws java.io.IOException**********************************************************************************************************************************/
    
        public void createSinglePOIXMLsandTextures (int linecount, String outputfile, String fname, JSAPResult config, String capitalTag, 
                List<AssetGroup> assetGroups, List<SceneryObject> sceneryObjects, UUID uuid, String fuuid, double modifiedAlt,
                ModifiedData y) throws IOException{
            
            /* Creating XML and Textures for a single POI */
            
            String formatted = String.format("%05d", linecount);
            String jsn = formatOutString(outputfile);
            /* Each POI requires a subdirectory for model and texture */ 
            new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/" + "poi_" + formatted + "-modellib/Texture").mkdirs();
            new File(System.getProperty("user.dir") + "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/" + "poi_" + formatted + "-modellib/" + "poi_" + formatted).mkdirs();
            /* Function creating texture for a POI (from GraphicsInteraction) */
            GraphicsInteraction gi = new GraphicsInteraction();
            gi.texttoGraphics(capitalTag + ": " + fname, config, formatted, config.getInt("TEXTURE_WIDTH"));
            capitalTag = "";

            AssetGroup ag = new AssetGroup("poiosmtofs-" + jsn.toLowerCase() + linecount + "-models", "ArtProj", new FlagsAG(), 
                    "PackageSources\\poi_" + formatted + "-modellib\\", 
                    "scenery\\3dsp\\3dsp-scenery-poiosmtofs-" + jsn.toLowerCase() + "\\" );
            assetGroups.add(ag);



            double randomHeading = (double)Math.floor(Math.random()*(180.00-(-179.999)+1)+(-179.999));
            double altRandomizer = (double)Math.floor(Math.random()*(20.00-(-20.00)+1)+(-20.00));

            modifiedAlt = modifiedAlt + modifiedAlt*(altRandomizer/100);

            LibraryObject lo = new LibraryObject(fuuid, 1.00000);
            SceneryObject so = new SceneryObject(y.getLat(), y.getLon(), modifiedAlt, 0.0, 0.0, randomHeading, lo);
            sceneryObjects.add(so);

            /* Creating an xml file for a single POI */

            try {
                try (FileWriter myWriter2 = new FileWriter(System.getProperty("user.dir") + 
                        "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/" + "poi_" + formatted + "-modellib/" + 
                                "poi_" + formatted + "/poi_" + formatted + ".xml")) {
                    ModelInfo modelInfo = new ModelInfo(uuid.toString(), 1.1);
                    String xmlData = makeModelInfoXML(modelInfo);
                    myWriter2.write(xmlData);
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                //e.printStackTrace();
            }

            /* Creating an gltf file for a single POI */

            try {
                try (FileWriter myWriter2 = new FileWriter(System.getProperty("user.dir") + 
                        "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/" + "poi_" + formatted + "-modellib/" + 
                                "poi_" + formatted + "/poi_" + formatted + ".gltf")) {
                    GLTFWriter glw = new GLTFWriter();
                    String gltfData = glw.writeGLTF("poi_" + formatted);
                    myWriter2.write(gltfData);
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                //e.printStackTrace();
            }

            /* Creating an bin file for a single POI using model from /data*/

            if(new File(System.getProperty("user.dir") + "/data").exists()){

                File srcFile = new File(System.getProperty("user.dir") + "/data/model.bin");
                File destFile = new File(System.getProperty("user.dir") + 
                            "/3DSp-POIOSM2FS-" + jsn + "/PackageSources/" + "poi_" + formatted + "-modellib/" + 
                                    "poi_" + formatted + "/poi_" + formatted + ".bin");

                FileUtils.copyFile(srcFile, destFile);
            }
            
            
            
            
        }
        
    /**********************************************************************************************************************************************/ 
    
        static String formatOutString(String outputfile){                           /* Making an useful string thet can be put in names etc. */
            
            String jsn = outputfile.substring(0,outputfile.indexOf(".")+".".length());  /* Getting directory name without .txt */
            jsn = jsn.substring(0, jsn.length() - 1);
            jsn = jsn.replace("_","-");
            
            return jsn;
        }
    
    /**********************************************************************************************************************************************/ 
    
    
    
    
}
