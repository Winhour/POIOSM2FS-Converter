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
import java.text.Normalizer;
import java.util.List;
import java.util.UUID;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import poiosm2fs.MiscFunctions;
import poiosm2fs.models.xmlmodels.AssetGroup;
import poiosm2fs.models.xmlmodels.SceneryObject;
import poiosm2fs.handlers.XMLHandler;
import poiosm2fs.models.ModifiedData;

/**
 *
 * @author Marcin
 */
public class ParseOSM {
    
//    private String name;             /* Default name */
//    private String name_en;          /* English name */
//    private String type;                   /* Type of element */
//    private double lat;               /* Latitude */
//    private double lon;               /* Longitude */
//    private String elevation = null;        /* Elevation */
    
    public void parseOSM (JSAPResult config) throws IOException, ParserConfigurationException{
     
        
        /* This module needs cleaning up, since most of the functionality has been moved to XMLHandler, I'm leaving it for now in case I have to go back to previous solution */
        
        
        //System.out.println("Test OSM");
        
        String osm_name = config.getString("OSM");
        
        String filepath = "./" + osm_name;
        
        MiscFunctions mFunc = new MiscFunctions();                          /* Object to use miscellanous functions */
        
//        ParseJSONAll pja = new ParseJSONAll();                              /*Need access to ParseJsonAll methods*/
        
        //String osmstring = mFunc.readFile(filepath, StandardCharsets.UTF_8);
        
//        List<AssetGroup> assetGroups = new ArrayList<>();               /* List for collection of asset groups for package definition xml */
//        
//        List<SceneryObject> sceneryObjects = new ArrayList<>();         /* List for collection of scenery objects for data xml */
        
        String outputfile;
        
        outputfile = createStringOutputfile(config);
        
        System.setProperty("org.jline.terminal.dumb", "true");                          /*Supressing a warning for 'dumb' terminal from jline, since it's always there when using IDE*/
        
        int terminalWidth = org.jline.terminal.TerminalBuilder.terminal().getWidth();   /* Getting the width of a terminal using the jline library */
        
        
        
        if(terminalWidth >= 100)
            mFunc.makeAsciiArt();                 /* ASCII Header */
        else
            mFunc.makeSimpleHeader();              /* Simple Header */
        
        printInfoOSM_ALL(config, outputfile, filepath);     /* Prints out information abouth chosen parameters */
        
        String outputfileT = outputfile;                    /* To handle 'target' cases */
        outputfile = outputfile.replace("target","");
        outputfile = outputfile.replace("Target","");
        
        try {
         File inputFile = new File(filepath);
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         XMLHandler xmlhandler = new XMLHandler();                  /* Custom handler in poiosm2fs.handlers */
         
         xmlhandler.setConfig(config);
         
         saxParser.parse(inputFile, xmlhandler);                    /* Parse using the logic in XMLHandler */
         
      } catch (Exception e) {
         e.printStackTrace();
      }
        
        
//        if(config.getBoolean("textures")){
//
//            pja.initializeTextureFolders(outputfile, assetGroups);
//            
//        }
//        
//        String test_outputString = "";
//        int elementcounter = 0;
//        
//        String id;
//        String lattmp;
//        String lontmp;
//        String k,v;
//        ModifiedData y;
//        String capitalTag = "";
//        Double modifiedAlt = 0.0;
//        
//        try {
//            File myObj = new File("osm_test.txt");                             
//            if (myObj.createNewFile()) {
//              System.out.println("File created: " + myObj.getName());
//         } else {
//                System.out.println("File " + myObj + " already exists.");
//         }
//         } catch (IOException e) {
//             System.out.println("An error occurred.");
//                //e.printStackTrace();
//         }
//        
//        FileWriter myWriter = new FileWriter("osm_test.txt");
//        
//        FileWriter myWriter2 = new FileWriter(outputfileT);
//        
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        
//        try {
//
//          // optional, but recommended
//          // process XML securely, avoid attacks like XML External Entities (XXE)
//          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//
//          // parse XML file
//          DocumentBuilder db = dbf.newDocumentBuilder();
//
//          Document doc = db.parse(new File(filepath));
//
//          // optional, but recommended
//          // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//          doc.getDocumentElement().normalize();
//
//          //System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//          //System.out.println("------");
//
//          // get <nodes>
//          NodeList list = doc.getElementsByTagName("node");
//
//          for (int temp = 0; temp < list.getLength(); temp++) {
//
//              Node node = list.item(temp);
//              
//              if (node.getNodeType() == Node.ELEMENT_NODE) {
//
//                  Element element = (Element) node;
//
//                  // get node's attribute
//                  id = element.getAttribute("id");
//
//                  NodeList taglist = element.getElementsByTagName("tag");
//                  
//                  if(taglist.getLength()> 0){
//                      
//                    lattmp = element.getAttribute("lat");
//                    lontmp = element.getAttribute("lon");
//                  
//                    for (int temp2 = 0; temp2 < taglist.getLength(); temp2++) {
//
//                      Node node2 = taglist.item(temp2);
//                      if (node2.getNodeType() == Node.ELEMENT_NODE) {
//
//                          Element element2 = (Element) node2;
//
//                          k = element2.getAttribute("k");
//                          v = element2.getAttribute("v");
//                          
//                          if (k.equals("name")){
//                              name = v;
//                          }
//                          
//                          if (k.equals("name_en")){
//                              name_en = v;
//                          }
//                          
//                          if (k.equals("place")){
//                              if (v.equals("village") || v.equals("city") || v.equals("town") || v.equals("quarter") || v.equals("suburb") || v.equals("island")){
//                                type = v;
//                              }
//                          } else if (k.equals("historic")){
//                              if (v.equals("archaeological_site") || v.equals("ruins") || v.equals("monument") || v.equals("castle") || v.equals("battlefield")){
//                                type = v;
//                              }
//                          } else if (k.equals("heritage")){
//                              type = v;
//                          } else if (k.equals("amenity")){
//                              if(!v.equals("restaurant") && !v.equals("cafe") && !v.equals("bank") && !v.equals("fast_food") && !v.equals("school") && !v.equals("pub")
//                                && !v.equals("bar") && !v.equals("grave_yard") && !v.equals("theatre") && !v.equals("kindergarten") && !v.equals("pharmacy")
//                                && !v.equals("library") && !v.equals("dentist") && !v.equals("nightclub") && !v.equals("cinema") && !v.equals("toilets")
//                                && !v.equals("taxi") && !v.equals("parking") && !v.equals("hospital") && !v.equals("doctors") && !v.equals("childcare")
//                                && !v.equals("veterinary")){
//                                type = v;
//                              }
//                          } else if (k.equals("natural")){
//                              if (v.equals("wetland") || v.equals("water") || v.equals("glacier") || v.equals("peak") || v.equals("battlefield")){
//                                if(type == null){
//                                  type = v;
//                                }
//                              }
//                          } else if (k.equals("leisure")){
//                              if (v.equals("stadium") || v.equals("nature_reserve")){
//                                type = v;
//                              }
//                          } else if (k.equals("public_transport")){
//                              if (v.equals("station")){
//                                type = v;
//                              }
//                          } else if (k.equals("railway")){
//                              type = v;
//                          } else if (k.equals("waterway")){
//                              if (v.equals("river") || v.equals("canal")){
//                                type = v;
//                              }
//                          } else if (k.equals("landuse")){
//                              if (v.equals("military") || v.equals("industrial") || v.equals("retail") || v.equals("residential")){
//                                type = v;
//                              }
//                          } else if (k.equals("aeroway")){
//                              type = v;
//                          } else if (k.equals("tourism")){
//                              if (v.equals("attraction") || v.equals("hotel")){
//                                type = v;
//                              }
//                          } else if (k.equals("government")){
//                              type = v;
//                          } else if (k.equals("water")){
//                              type = v;
//                          }
//                          
//                          
//                          if (k.equals("ele")){
//                              elevation = v;
//                          } 
//
//                      }
//
//                    }
//
//                    
//                    if (type != null && type.equals("place_of_worship") ) type = "temple"; 
//                    
//                    
//                    if (name != null && type != null){
//                    
//                        myWriter.write("id : " + id + "\n");
//                        myWriter.write("lat: " + lattmp + " lon: " + lontmp + "\n");
//
//                        myWriter.write("Name: " + name + "   Type: " + type + "\n");
//
//                        myWriter.write("\n");
//                        
//                        lat = Double.parseDouble(lattmp);
//                        lon = Double.parseDouble(lontmp);
//                        
//                        y = new ModifiedData(name,name_en,type,lat,lon);
//                        
//                        elementcounter++;
//                        if(elementcounter % 100 == 0) System.out.println(elementcounter);
//                        
//                        
//                        
//
//                        /* Main functionality creating the output text file and possibly textures and everything related to that */
//
//                        doMainOSM(y, mFunc, modifiedAlt, config, capitalTag, myWriter2, elementcounter, outputfile, assetGroups, sceneryObjects, pja);
//
//                            
//
//                        
//                    }
//                    if (name == null && elevation != null && type != null){
//                        
//                        myWriter.write("id : " + id + "\n");
//                        myWriter.write("lat: " + lattmp + " lon: " + lontmp + "\n");
//
//                        myWriter.write("Elevation: " + elevation + "   Type: " + type + "\n");
//
//                        myWriter.write("\n");
//                        
//                        y = new ModifiedData(name,name_en,type,lat,lon);
//                        y.setEle(elevation);
//                        
//                        elementcounter++;
//                        if(elementcounter % 100 == 0) System.out.println(elementcounter);
//                        
//                        /* Main functionality creating the output text file and possibly textures and everything related to that */
//
//                        doMainOSM(y, mFunc, modifiedAlt, config, capitalTag, myWriter2, elementcounter, outputfile, assetGroups, sceneryObjects, pja);
//                        
//                        
//                        
//                        
//                    }
//                    
//                    name = null;             
//                    name_en = null;          
//                    type = null;                   
//                    lat = 0;               
//                    lon = 0;               
//                    elevation = null;
//
//                  } else {
//                      
//                  }
//              }
//
//              
//          }
//
//      } catch (ParserConfigurationException | SAXException | IOException e) {
//          e.printStackTrace();
//      }
//        
//        
//        test_outputString += "\n\n" + "Number of nodes: " + elementcounter;
//        myWriter.write(test_outputString);
//        myWriter.close();
//        
//        myWriter2.close();
//        
//        System.out.println("");
//        
//        if(config.getBoolean("textures")){
//
//            pja.createOverarchingXMLs(outputfile, assetGroups, sceneryObjects);     /* Data XML and Package Definitions XML */
//
//        }
        
        
        
    }
    
    
    /**********************************************************************************************************************************************/ 
        
        static void printInfoOSM_ALL(JSAPResult config, String outputfile, String filepath){
            
            System.out.println("Chosen parameters:");                           /*Information for the user about selected parameters*/
                System.out.println("FILE: " + filepath);
                System.out.println("OWNER: " + config.getString("OWNER"));
                System.out.println("ALT: " + config.getDouble("ALT"));
                System.out.println("OUTPUT FILE: " + outputfile);
                System.out.println("REMOVE_EMPTY: " + config.getBoolean("remove_empty"));
                System.out.println("REMOVE_NONLATIN: " + config.getBoolean("remove_nonlatin"));
                if (config.getBoolean("textures")){
                    String osmString=config.getString("OSM");
                    osmString = formatOutString(osmString);
                    osmString = osmString.replace("target","");                             /* For testing purposes json strings start with target, so let's get rid of it */
                    osmString = osmString.replace("Target","");
                    System.out.println("TEXTURES: ./3DSP-POIOSM2fS-" + osmString);
                    System.out.println("TEXTURE_WIDTH: " + config.getInt("TEXTURE_WIDTH"));
                    System.out.println("TEXTURE_FONT: " + config.getString("TEXTURE_FONT"));
                }
                System.out.println("");
            
        }
        
    /**********************************************************************************************************************************************/ 
    
        static String formatOutString(String outputfile){                           /* Making an useful string thet can be put in names etc. */
            
            String osmString = outputfile.substring(0,outputfile.indexOf(".")+".".length());  /* Name without file extension */
            osmString = osmString.substring(0, osmString.length() - 1);
            osmString = osmString.replace("_","-");
            
            return osmString;
        }
    
    /**********************************************************************************************************************************************/
        
        String createStringOutputfile (JSAPResult config){
            
            String outputfile="";
            
            if (config.getString("OUTPUT").equals("output")){               /* Setting the name of output file */
                String osmString = config.getString("OSM").substring(0,config.getString("OSM").indexOf(".")+".".length());
                osmString = osmString.substring(0, osmString.length() - 1);
                //jsn = jsn.replace("target","");                             /* For testing purposes json strings start with target, so let's get rid of it */
                //jsn = jsn.replace("Target","");
                outputfile = osmString + ".txt";
            }
            else {outputfile = config.getString("OUTPUT") + ".txt";}
            
            return outputfile;
            
        }
        
        
    /**********************************************************************************************************************************************/   
        
        private void doMainOSM(ModifiedData y, MiscFunctions mFunc, Double modifiedAlt, JSAPResult config, String capitalTag, FileWriter myWriter, int elementcounter,
        String outputfile, List<AssetGroup> assetGroups, List<SceneryObject> sceneryObjects, ParseJSONAll pja) throws IOException{
            
            
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
        
        
    
}
