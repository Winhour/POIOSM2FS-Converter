/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.handlers;

import com.martiansoftware.jsap.JSAPResult;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import poiosm2fs.MiscFunctions;
import poiosm2fs.models.ModifiedData;
import poiosm2fs.models.xmlmodels.AssetGroup;
import poiosm2fs.models.xmlmodels.SceneryObject;
import poiosm2fs.primaryfunctions.ParseJSONAll;

/**
 *
 * @author Marcin
 */
public class XMLHandler extends DefaultHandler{
    
    String name;                                /* Variables for a ModifiedData object */
    String name_en;
    String type;
    String lattmp;
    String lontmp;
    String elevation;
    String k, v;                                /* Temporary containers for key value pairs from osm */
    
    double lat, lon;
    
    JSAPResult config = null;                   /* Set it from outside */
    
    //private StringBuilder elementValue;
    
    String osm_name;
        
    String filepath;

    MiscFunctions mFunc = new MiscFunctions();                          /* Object to use miscellanous functions */

    ParseJSONAll pja = new ParseJSONAll();                              /*Need access to ParseJsonAll methods*/
    
    List<AssetGroup> assetGroups = new ArrayList<>();               /* List for collection of asset groups for package definition xml */
        
    List<SceneryObject> sceneryObjects = new ArrayList<>();         /* List for collection of scenery objects for data xml */

    String outputfile;
    
    String outputfileT;
    
    String test_outputString = "";
    
    int elementcounter = 0;                                     /* For counting the number of output elements */
    
    FileWriter myWriter;
        
    FileWriter myWriter2;
    
    ModifiedData y;                                             /* Container for data values: name, type etc. */
    
    String capitalTag = "";
    
    Double modifiedAlt = 0.0;
    
    String id;
    
    int motorwayflag = 0;                                       /* motorwayflag and MOTOR_LIMIT used to only take in a fraction of all motorways, currently set to half of them */
    
    final int MOTOR_LIMIT = 2;
    
    String ref = null;                                          /* Signifier for motorways */
    
    boolean icaoFlag = false;                                   /* ICAO for aviation adjacent POI types */
    
    String icaoString;
    
    int streamflag = 0;                                         /* Used to cut down on the total number of streams in the ouput */
    
    final int STREAM_LIMIT = 5;
    
    int riverflag = 0;                                            /* Used to cut down on the total number of rivers in the ouput */
    
    final int RIVER_LIMIT = 0;
    
    
    
    
    
    
   /**
     * @throws org.xml.sax.SAXException********************************************************************************************************************************************/  

    @Override
    public void startDocument() throws SAXException {                       /* Activates at the start of the document */
        super.startDocument(); 
        
        System.out.println("OSM PARSING START");
        
        osm_name = config.getString("OSM");
        
        filepath = "./" + osm_name;
        
        outputfile = createStringOutputfile(config);
        
        outputfileT = outputfile;                    /* To handle the target in input names */
        outputfile = outputfile.replace("target","");
        outputfile = outputfile.replace("Target","");
        
        if(config.getBoolean("textures")){

            try {
                pja.initializeTextureFolders(outputfile, assetGroups);
            } catch (IOException ex) {
                Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
//        try {
//            myWriter = new FileWriter("osm_test.txt");                                              /* Extra file with information for testing, will get rid of it later */
//        } catch (IOException ex) {
//            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        try {
            myWriter2 = new FileWriter(outputfileT);
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * @throws org.xml.sax.SAXException********************************************************************************************************************************************/ 

    
    @Override
    public void endDocument() throws SAXException {                                    /* Activates at the end of the document */
        super.endDocument(); 
        
        test_outputString += "\n\n" + "Number of nodes: " + elementcounter;
//        try {
//            myWriter.write(test_outputString);
//        } catch (IOException ex) {
//            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            myWriter.close();
//        } catch (IOException ex) {
//            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        try {
            myWriter2.close();
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("");
        
        if(config.getBoolean("textures")){

            pja.createOverarchingXMLs(outputfile, assetGroups, sceneryObjects);     /* Data XML and Package Definitions XML */

        }
        
        System.out.println("OSM PARSING END");
        
    }
    
    /**
     * @throws org.xml.sax.SAXException********************************************************************************************************************************************/ 
    
    
    
    @Override
    public void startElement(                                                   /* Activates at the start of every element, in case of these OSMs, <node> and <tag> are the only options  */
      String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
        
        super.startElement(uri, localName, qName, attributes);

        if(qName.equals("node")){
            
            id = attributes.getValue("id");
            
            lattmp = attributes.getValue("lat");
            lontmp = attributes.getValue("lon");
           
        } else if (qName.equals("tag")){
            
            k = attributes.getValue("k");
            v = attributes.getValue("v");
            
            parsekandv();                                                   /* Check whether k and v are ones we want to include in the output */
             
        }
      
      /*if (qName.equalsIgnoreCase("node")) {
            lat = attributes.getValue("lat");
            lon = attributes.getValue("lon");
            elementValue = new StringBuilder();
        } else if (qName.equalsIgnoreCase("tag")){
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");
        }
      
      elementValue.setLength(0);*/
      
    }
    
    /**
     * @throws org.xml.sax.SAXException********************************************************************************************************************************************/ 

   @Override
   public void endElement(String uri,                                           /* Activates at the end of every element, in case of these OSMs, </node> and </tag> are the only options  */
      String localName, String qName) throws SAXException {
      
       super.endElement(uri, localName, qName);
       
       if(qName.equals("node")){
           
           if (ref != null && icaoFlag == false && name != null){                /* case for motorways etc. */
                
           
            String tmpname = name;
            name = ref;
            name += " " + tmpname;
            
            ref = null;
            
           } else if (ref != null && icaoFlag == false){
               
               name = ref;
               
               ref = null;
           }
           
           if (icaoFlag){
               
               
                
                if(type != null && (type.equals("aerodrome") || type.equals("airport"))){
                    type = "Airport";
                }
                //System.out.println(name + " " + name_en);
                if(name != null){
                    name = icaoString + " " + name;
                }
                else {
                    name = icaoString;
                }
                if(name_en != null){
                    name_en = icaoString + " " + name_en;
                } else {
                    //name_en = icaoString;
                }
                
                icaoFlag = false;
                
            }  
           
           if (type != null && type.equals("ownpoi")){
               type = "OWNPOI";
           }
           
           
           try {
               checktheNode();
           } catch (IOException ex) {
               Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
           }

            /* Zeroing all the values for ModifiedData objects */
           
            name = null;             
            name_en = null;          
            type = null;     
            lattmp = null;
            lontmp = null;
            lat = 0;               
            lon = 0;               
            elevation = null;
           
       } else if (qName.equals("tag")){
           
           //System.out.println("test end tag");
           
       }
      
   }
   
   /**
     * @throws org.xml.sax.SAXException********************************************************************************************************************************************/ 
   
   
   @Override
    public void characters(char[] ch, int start, int length) throws SAXException {          /* Not necessary to make any changes right now, part of SAXparser */
        
        super.characters(ch, start, length);
        
        /*if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }*/
    }
    
    /**********************************************************************************************************************************************/ 
    
    public void parsekandv(){                                       /* Check whether k and v are ones we want to include in the output */
        
        /* Fill the variables name, name_en, type + special cases with appropiate values */
        
        
        //NAME
        
        if (k.equals("name")){
            if (name == null){
                name = v;
            }
        }

        if (k.equals("name:en") || k.equals("name_en")){
            name_en = v;
            name = v;
        }

        
        //TYPE
        switch (k) {
            
            case "place":
                if (v.equals("village") || v.equals("city") || v.equals("town") || v.equals("suburb") || v.equals("island")){
                    type = v;
                }   
                break;
                
            case "historic":
                if (v.equals("archaeological_site") || v.equals("ruins") || v.equals("monument") || v.equals("castle") || v.equals("battlefield") || v.equals("memorial") 
                        || v.equals("aircraft")){
                    type = v;
                }   
                break;
                
            case "heritage":
                if(!v.equals("residential") && !v.equals("2") && !v.equals("1") && !v.equals("3") && !v.equals("4") && !v.equals("5") && !v.equals("6") && !v.equals("7") 
                        && !v.equals("8") && !v.equals("9") && !v.equals("yes") && !v.equals("no")){
                    type = v;
                }
                break;
                
            case "amenity":
                
                /* Keeping in case there's a need to go back to the solution which takes everything instead of selected cases, rather than only taking what we want */
                
                //            if(!v.equals("restaurant") && !v.equals("cafe") && !v.equals("bank") && !v.equals("fast_food") && !v.equals("school") && !v.equals("pub")
//              && !v.equals("bar") && !v.equals("grave_yard") && !v.equals("theatre") && !v.equals("kindergarten") && !v.equals("pharmacy")
//              && !v.equals("library") && !v.equals("dentist") && !v.equals("nightclub") && !v.equals("cinema") && !v.equals("toilets")
//              && !v.equals("taxi") && !v.equals("parking") && !v.equals("hospital") && !v.equals("doctors") && !v.equals("childcare")
//              && !v.equals("veterinary")){
                
                if (v.equals("place_of_worship") || v.equals("events_centre") || v.equals("stadium") || v.equals("university") || v.equals("fire_station")
                        || v.equals("helipad") || v.equals("fuel") || v.equals("townhall") || v.equals("charging_station") || v.equals("prison") || v.equals("marketplace")
                        || v.equals("arts_centre") || v.equals("hangar") || v.equals("fountain") || v.equals("ownpoi") || v.equals("hospital")
                        || v.equals("police")){
                    type = v;
                }   
                break;
                
            case "natural":
                if (v.equals("wetland") || v.equals("water") || v.equals("glacier") || v.equals("peak") || v.equals("battlefield")){
                    if(type == null){
                        type = v;
                    }
                }   
                break;
                
            case "leisure":
                if (v.equals("stadium") || v.equals("nature_reserve") || v.equals("sports_centre")){
                    type = v;
                }  
                break;
                
            case "public_transport":
                if (type == null){
                    if (v.equals("station")){
                        type = "bus_station";
                    }
                }   
                break;
                
            case "railway":
                if (v.equals("station")){
                    type = "railway_station";
                }   
                break;
                
            case "waterway":
                if (v.equals("river") || v.equals("canal") || v.equals("lake") || v.equals("dam") || v.equals("waterfall") || v.equals("drain")){
                    type = v;
                }   if (v.equals("stream") && streamflag == STREAM_LIMIT){
                    type = v;
                    streamflag = 0;
                } else if (v.equals("stream")){
                    streamflag++;
                }
                if (v.equals("river") && riverflag == RIVER_LIMIT){
                    type = v;
                    riverflag = 0;
                } else if (v.equals("river")){
                    riverflag++;
                }
                break;
                
            case "landuse":
                if (v.equals("military") || v.equals("industrial") || v.equals("retail") || v.equals("forest") || v.equals("railway") || v.equals("residential")){
                    if (type == null){
                        type = v;
                    }
                }   
                break;
                
            case "aeroway":
                if (v.equals("aerodrome") || v.equals("airport") || v.equals("helioport")|| v.equals("helipad") || v.equals("hangar")){
                    type = v;
                }   
                break;
                
            case "tourism":
                if (v.equals("attraction") || v.equals("zoo") || v.equals("museum")){
                    if(type == null){
                        type = v;
                    }
                }   
                break;
                
            case "government":
                if (v.equals("parliament")){
                    type = v;
                }   
                break;
                
            case "water":
                if (v.equals("lake") || v.equals("river") || v.equals("canal") || v.equals("pond") || v.equals("reservoir")){
                    type = v;
                }   if (v.equals("river") && riverflag == RIVER_LIMIT){
                    type = v;
                    riverflag = 0;
                } else if (v.equals("river")){
                    riverflag++;
                }
                break;
                
            case "highway":
                if ((v.equals("motorway") || v.equals("trunk") || v.equals("motorway_junction")) && motorwayflag == MOTOR_LIMIT){
                    type = v;
                    motorwayflag = 0;
                } else if (v.equals("motorway") || v.equals("trunk") || v.equals("motorway_junction")){
                    motorwayflag++;
                }   
                break;
                
            case "shop":
                if (v.equals("supermarket")){
                    type = v;
                }   
                break;
                
            default:
                
                break;
        }
        
        
        //SPECIAL CASES
        
        if (k.equals("icao")){
            
            icaoFlag = true;
            icaoString = v;
            
        }


        if (k.equals("ele")){
            elevation = v;
        } 
        
        if (k.equals("ref")){
            
            ref = v;
        } 
        
    }
    
    /**
     * @return ********************************************************************************************************************************************/ 
    
    public JSAPResult getConfig() {
        return config;
    }

    public void setConfig(JSAPResult config) {                          /* Need to set the config from outside, there might be a better solution, but for now this works */
        this.config = config;
    }
    
    
    /**********************************************************************************************************************************************/ 
    
        static String formatOutString(String outputfile){                           /* Making a useful version of output string thet can be put in names etc. */

            
            String osmString = outputfile.substring(0,outputfile.indexOf(".")+".".length());  /* Name without file extension */
            osmString = osmString.substring(0, osmString.length() - 1);
            osmString = osmString.replace("_","-");
            
            return osmString;
        }
    
    /**********************************************************************************************************************************************/
        
        String createStringOutputfile (JSAPResult config){    
            
            /* Create output string/ output file name */
            
            String outputfile="";
            
            if (config.getString("OUTPUT").equals("output")){               /* Setting the name of output file */
                String osmString = config.getString("OSM").substring(0,config.getString("OSM").indexOf(".")+".".length());
                osmString = osmString.substring(0, osmString.length() - 1);
                //jsn = jsn.replace("target","");                             
                //jsn = jsn.replace("Target","");
                outputfile = osmString + ".txt";
            }
            else {outputfile = config.getString("OUTPUT") + ".txt";}
            
            return outputfile;
            
        }
        
        
    /**********************************************************************************************************************************************/   
        
        private void doMainOSM(ModifiedData y, MiscFunctions mFunc, Double modifiedAlt, JSAPResult config, String capitalTag, FileWriter myWriter, int elementcounter,
        String outputfile, List<AssetGroup> assetGroups, List<SceneryObject> sceneryObjects, ParseJSONAll pja) throws IOException{
            
            /* Create output text file with POI for the .osm */
            
            //doMainOSM(y, mFunc, modifiedAlt, config, capitalTag, myWriter2, elementcounter, outputfile, assetGroups, sceneryObjects, pja);
            
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
                            double altRandomizer = (double)Math.floor(Math.random()*(30.00-(-30.00)+1)+(-30.00));   /*Randomizing alt +/- 30%*/
                            modifiedAlt = modifiedAlt + modifiedAlt*(altRandomizer/100);

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
                                
                                if(config.getBoolean("textures")){

                                    /* Making XML's and Textures for each single POI */
                                    /* Using the method from ParseJSONALL, seems to be working fine, but need to check whether any disrepancies exist */
                                    pja.createSinglePOIXMLsandTextures(elementcounter, outputfile, fname, config, capitalTag, assetGroups, sceneryObjects, uuid, fuuid, modifiedAlt, y);
                                    capitalTag = "";
                                    //isIcao = false;
                                }
                        }
                    } 
                }
            }
            
            
        }
        
     
        
    /**********************************************************************************************************************************************/     
        
        
    private void checktheNode() throws IOException{                                     /* See whether the node is one we want to include, write info to osm_test.txt */
        
        if (type != null && type.equals("place_of_worship") ) type = "temple"; 
        if (type != null && type.equals("archaeological_site") ) type = "archeo";            
                    
            if ((name != null || name_en != null) && type != null){                                                  /* Usual case, element is fine if it has a name and type */

//                myWriter.write("id : " + id + "\n");
//                myWriter.write("lat: " + lattmp + " lon: " + lontmp + "\n");
//
//                myWriter.write("Name: " + name + "   Type: " + type + "\n");
//
//                myWriter.write("\n");

                lat = Double.parseDouble(lattmp);
                lon = Double.parseDouble(lontmp);

                y = new ModifiedData(name,name_en,type,lat,lon);
                
                if (elevation != null && type.equals("peak")){
                    y.setEle(elevation);
                }
                
                if(y.getLat() != 0.0 || y.getLon() != 0.0){

                    elementcounter++;
                    if(elementcounter % 100 == 0) System.out.println("number of parsed elements: " + elementcounter);

                    /* Main functionality creating the output text file and possibly textures and everything related to that */

                    doMainOSM(y, mFunc, modifiedAlt, config, capitalTag, myWriter2, elementcounter, outputfile, assetGroups, sceneryObjects, pja);

                }


            }
            else if (name == null && elevation != null && type != null){                                                    /* Special case for peaks */

//                myWriter.write("id : " + id + "\n");
//                myWriter.write("lat: " + lattmp + " lon: " + lontmp + "\n");
//
//                myWriter.write("Elevation: " + elevation + "   Type: " + type + "\n");
//
//                myWriter.write("\n");

                y = new ModifiedData(name,name_en,type,lat,lon);
                y.setEle(elevation);
                
                    if(y.getLat() != 0.0 || y.getLon() != 0.0){

                        elementcounter++;
                        if(elementcounter % 100 == 0) System.out.println("number of parsed elements: " + elementcounter);

                        /* Main functionality creating the output text file and possibly textures and everything related to that */

                        doMainOSM(y, mFunc, modifiedAlt, config, capitalTag, myWriter2, elementcounter, outputfile, assetGroups, sceneryObjects, pja);

                    }
                }
                else if (name == null && type != null){
                    
                    if(type.equals("helioport") || type.equals("helipad") || type.equals("hangar")){                                /* Special case for helipads/ hangars without names */
                        
                        lat = Double.parseDouble(lattmp);
                        lon = Double.parseDouble(lontmp);
                        
                        name = "";
                        name_en= "";

                        y = new ModifiedData(name,name_en,type,lat,lon);
                        
                        if (elevation != null){
                            y.setEle(elevation);
                        }

                        if(y.getLat() != 0.0 || y.getLon() != 0.0){

                            elementcounter++;
                            if(elementcounter % 100 == 0) System.out.println("number of parsed elements: " + elementcounter);

                            /* Main functionality creating the output text file and possibly textures and everything related to that */

                            doMainOSM(y, mFunc, modifiedAlt, config, capitalTag, myWriter2, elementcounter, outputfile, assetGroups, sceneryObjects, pja);

                        }
                    }
                }
        
        
    }    
        
        
/**********************************************************************************************************************************************/
   
}
