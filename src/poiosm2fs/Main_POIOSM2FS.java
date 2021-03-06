/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.QualifiedSwitch;
import com.martiansoftware.jsap.Switch;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import poiosm2fs.primaryfunctions.ModifyFromJSON;
import poiosm2fs.primaryfunctions.ParseCSV;
import poiosm2fs.primaryfunctions.ParseJSONAll;
import poiosm2fs.primaryfunctions.ParseOSM;

/**
 *
 * @author Marcin
 */
public class Main_POIOSM2FS {

    /* Main function */

    public static void main(String[] args) throws IOException, JSAPException, FileNotFoundException, ParserConfigurationException {
        
        /*XStreamInteraction xsi = new XStreamInteraction();                    //Might work on it later
        ConfigX loadedConfig = xsi.getConfigFromXML();
        System.out.println(loadedConfig.getOwner() + " " + loadedConfig.getLabel()+ " " + loadedConfig.getAltitude() + " " + loadedConfig.getStep() + " " + loadedConfig.getTexture_width());*/

        JSAP jsap = new JSAP();             /*Used for command line inputs */
       
        jsap = initializeJSAP(jsap);        /*Function initializing input flags */
        
        JSAPResult config = jsap.parse(args);  /* Encapsulates the results of JSAP's parse() methods. */
        
        chooseWhatToDo(config);               /*Based on the command line input, choose which function to run*/
        
        System.out.println("*** FINISHED ALL TASKS ***\n");    /* Message if end of program was reached */
        
    }
    
    /**********************************************************************************************************************************************/
    
    private static JSAP initializeJSAP(JSAP jsap) throws JSAPException{
        
        /*Function initializes all the necessary flags for command line interaction*/
     
        FlaggedOption opt1 = new FlaggedOption("STEP")         /* Interval between nodes for JSON */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("10") 
                                .setRequired(true) 
                                .setShortFlag('s') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt1);
        
        FlaggedOption opt2 = new FlaggedOption("JSON")              /* JSON (short/rivers) input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('j') 
                                .setLongFlag("jr");
        
        jsap.registerParameter(opt2);
        
        FlaggedOption opt3 = new FlaggedOption("LABEL")           /* Additional LABEL for elements ex. ruins, river */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('l') 
                                .setLongFlag(JSAP.NO_LONGFLAG);     
        
        jsap.registerParameter(opt3);
        
        FlaggedOption opt4 = new FlaggedOption("OWNER")         /* Owner of produced marker */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("3DSpotters") 
                                .setRequired(true) 
                                .setShortFlag('w') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt4);
        
        FlaggedOption opt5 = new FlaggedOption("ALT")           /* Altitude at which the banner will appear */
                                .setStringParser(JSAP.DOUBLE_PARSER)
                                .setDefault("60.00") 
                                .setRequired(true) 
                                .setShortFlag('a') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt5);
        
        FlaggedOption opt6 = new FlaggedOption("OUTPUT")       /* Output file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("output") 
                                .setRequired(true) 
                                .setShortFlag('o') 
                                .setLongFlag("out");
        
        jsap.registerParameter(opt6);
        
        FlaggedOption opt7 = new FlaggedOption("CSV")           /* CSV input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag('c') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt7);
        
        FlaggedOption opt8 = new FlaggedOption("JSON_ALL")           /* JSON (long/all) input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("ja");
        
        jsap.registerParameter(opt8);
        
        FlaggedOption opt9 = new FlaggedOption("REMOVE_SENSITIVITY_THRESHOLD")           /* Sensitivity threshold for Temples */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("0") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("rst");
        
        jsap.registerParameter(opt9);
        
        FlaggedOption opt10 = new FlaggedOption("REMOVE_SENSITIVITY_THRESHOLD_VL")           /* JSON (long/all) input file name */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("0") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("rsv");
        
        jsap.registerParameter(opt10);
        
        FlaggedOption opt11 = new FlaggedOption("TEXTURE_WIDTH")           /* Texture width */
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setDefault("1024") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("txw");
        
        jsap.registerParameter(opt11);
        
        FlaggedOption opt12 = new FlaggedOption("TEXTURE_FONT")           /* Texture font */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("Noto Sans") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("font");
        
        jsap.registerParameter(opt12);
        
        FlaggedOption opt13 = new FlaggedOption("OSM")                   /* OSM input file name */
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault("none") 
                                .setRequired(true) 
                                .setShortFlag(JSAP.NO_SHORTFLAG) 
                                .setLongFlag("osm");
        
        jsap.registerParameter(opt13);
        

        
         /* Text color in texture */
        QualifiedSwitch qs1 = (QualifiedSwitch)
                                (new QualifiedSwitch("COLOR_TEXT")
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setShortFlag(JSAP.NO_SHORTFLAG)
                                .setLongFlag("ct")
                                .setList(true)
                                .setListSeparator(','));
        
        jsap.registerParameter(qs1);
        
        /* Background color in texture */
        QualifiedSwitch qs2 = (QualifiedSwitch)
                                (new QualifiedSwitch("COLOR_BACKGROUND")
                                .setStringParser(JSAP.INTEGER_PARSER)
                                .setShortFlag(JSAP.NO_SHORTFLAG)
                                .setLongFlag("cb")
                                .setList(true)
                                .setListSeparator(','));
        
        jsap.registerParameter(qs2);
        
        Switch sw1 = new Switch("help")                     /* help information flag */
                        .setShortFlag('h')
                        .setLongFlag("help");

        jsap.registerParameter(sw1);
        
        Switch sw2 = new Switch("remove_empty")            /* flag used for removal of elements with empty names */
                        .setShortFlag(JSAP.NO_SHORTFLAG)
                        .setLongFlag("re");

        jsap.registerParameter(sw2);
        
        Switch sw3 = new Switch("remove_nonlatin")        /* flag used for removal of elements starting with nonlatin characters */
                        .setShortFlag(JSAP.NO_SHORTFLAG)
                        .setLongFlag("rn");
        
        jsap.registerParameter(sw3);
        
        Switch sw4 = new Switch("textures")                     /* make texture folders */
                        .setShortFlag('t')
                        .setLongFlag("textures");

        jsap.registerParameter(sw4);

        
        return jsap;
        
    }
    
    /**********************************************************************************************************************************************/
    
    private static void chooseWhatToDo(JSAPResult config) throws IOException, FileNotFoundException, ParserConfigurationException{     /*Based on the command line input, choose which function to run*/
        
        if(!config.success()){
            System.out.println("\nThere was an error found within command line arguments, try again\n");        /*Error printed when a wrong command line argument exists */
            helpInfo();         /*Prints out information on how to use the program*/
        } else {
            
            if(config.getBoolean("help")){
                helpInfo();
            }
            else {        
                if(!config.getString("JSON").equals("none") && config.getString("CSV").equals("none") && config.getString("JSON_ALL").equals("none") && config.getString("OSM").equals("none")){
                    ModifyFromJSON mfj = new ModifyFromJSON();
                    mfj.modifyFromJSON(config);          /* Case when user wants to use short JSON */
                }
                else if(config.getString("JSON").equals("none") && config.getString("CSV").equals("none") && !config.getString("JSON_ALL").equals("none") && config.getString("OSM").equals("none")){
                    ParseJSONAll pja = new ParseJSONAll();
                    pja.parseJSONAll(config);               /* Case when user wants to use long JSON (This is currently the primary functionality, with texture and directory creation) */
                }                
                else if(config.getString("JSON").equals("none") && !config.getString("CSV").equals("none") && config.getString("JSON_ALL").equals("none") && config.getString("OSM").equals("none")){
                    ParseCSV pc = new ParseCSV();
                    pc.parseCSV(config);               /* Case when user wants to use CSV */
                } 
                else if(config.getString("JSON").equals("none") && config.getString("CSV").equals("none") && config.getString("JSON_ALL").equals("none") && !config.getString("OSM").equals("none")){
                    ParseOSM po = new ParseOSM();
                    po.parseOSM(config);               /*Case when user wants to use OSM */
                }
                else if(!config.getString("JSON").equals("none") && !config.getString("JSON_ALL").equals("none")
                        || !config.getString("JSON").equals("none") && !config.getString("CSV").equals("none")
                        || !config.getString("CSV").equals("none") && !config.getString("JSON_ALL").equals("none")
                        || !config.getString("JSON").equals("none") && !config.getString("OSM").equals("none")
                        || !config.getString("CSV").equals("none") && !config.getString("OSM").equals("none")
                        || !config.getString("JSON_ALL").equals("none") && !config.getString("OSM").equals("none")){
                    System.out.println("\nPlease specify only one input file");         /*Two or more files were chosen at the same time*/
                    helpInfo();
                } 
                else {
                    System.out.println("\nPlease select an input file, either JSON (-j + filepath), "
                            + "long JSON (--ja + filepath) or CSV (-c + filepath)");    /*Neither JSON nor CSV were chosen */
                    helpInfo();
                }
            }
        }
        
    }
    
    /**********************************************************************************************************************************************/
    
    private static void helpInfo() throws IOException{                     /*Prints out information on how to use the program*/
        
        MiscFunctions mFunc = new MiscFunctions();                      /* Object to use miscellanous functions */
        
        System.setProperty("org.jline.terminal.dumb", "true");                          /*Supressing a warning for dumb terminal, since it's always there when using IDE*/
        
        int terminalWidth = org.jline.terminal.TerminalBuilder.terminal().getWidth();   /* Getting the width of a terminal using the jline library */
        
        if(terminalWidth >= 100)
            mFunc.makeAsciiArt();                 /* ASCII Header */
        else
            mFunc.makeSimpleHeader();              /* Simple Header */
        System.out.println("\nHow to use:\n");
        System.out.println("-j or --jr (json_file_path) selects a short JSON file to use (used mostly for rivers)");
        System.out.println("--ja (json_file_path) selects a long JSON file to use (containing various elements such as: rivers, ruins, peaks etc.)");
        System.out.println("--osm (osm_file_path) selects an OSM file to use");
        System.out.println("-c (csv_file_path) selects a CSV file to use");
        System.out.println("-s (Integer) selects the interval between chosen nodes, applies only to the small JSON (default is 10)");
        System.out.println("-l (String) alows to add a label in front of element's name ex. Ruins: ruinsname (for small JSONs)");
        System.out.println("-w (String) specifies the owner (default = 3DSpotters)");
        System.out.println("-a (Double) specifies the altitude (default = 70.00)");
        System.out.println("-o (filename) allows the user to choose the output file");
        System.out.println("--rn removes lines with names made up of nonlatin characters");
        System.out.println("--re removes lines with empty names");
        System.out.println("--rst (Integer) removes elements with type 'Temple' which have number of tags less than or equal to the chosen threshold");
        System.out.println("--rsv (Integer) removes elements with type 'Village' which have number of tags less than or equal to the chosen threshold");
        System.out.println("-t makes folders that contain all the necessary xmls, textures etc. for model creation");
        System.out.println("(NOTE needs the /data and /asset folders to create models)");
        System.out.println("--txw (Integer) sets width for the textures (default = 2048)");
        System.out.println("--cb:r,g,b sets texture background color example: --cb:100,0,0");
        System.out.println("--ct:r,g,b sets texture text color example: --ct:100,0,0");
        System.out.println("--font sets texture text font (default = Noto Sans)");
        System.out.println("\nExample:\n");
        System.out.println("java -jar \"POIOSM2FS.jar\" -c ruinsplus.csv -l Ruins -w Winhour -a 356.7890 -o ruins -s 20 --rn --re");
        System.out.println("java -jar \"POIOSM2FS.jar\" -s 25 -j rzeki_IL.json -l Rzeki -w Winhour -a 421.3358 -o rzeki");
        System.out.println("java -jar POIOSM2FS.jar --ja all.json --rst 5 --rsv 10 -t");
        System.out.println("java -jar POIOSM2FS.jar --ja targetGR_Kos_TEST2.json --re --rst 6 --rsv 4 -t --cb:100,100,100 --ct:0,150,0 --font \"Arial Bold\" \n");
        System.out.println("java -jar POIOSM2FS.jar --osm fislands_test.osm -t");
    }
    
    /**********************************************************************************************************************************************/
    
    /* --ja targetGR_Kos_TEST.json --re --rst 6 --rsv 4 -t --font "Arial" */
    
}
