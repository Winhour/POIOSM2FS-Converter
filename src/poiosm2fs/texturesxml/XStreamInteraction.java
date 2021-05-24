/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.texturesxml;

import com.thoughtworks.xstream.XStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import poiosm2fs.models.ConfigX;
import poiosm2fs.models.xmlmodels.AssetPackage;
import poiosm2fs.models.xmlmodels.FSData;
import poiosm2fs.models.xmlmodels.ModelInfo;
import poiosm2fs.models.xmlmodels.Project;

/**
 *
 * @author Marcin
 */
public class XStreamInteraction {
    
    /* Interaction with XStream library for XML handling */
    
    public ConfigX getConfigFromXML() throws FileNotFoundException{             /* Potential future implementation */
           
        FileReader fileReader = new FileReader(System.getProperty("user.dir") + "/config.xml");  // load our xml file  
        
        Class<?>[] classes = new Class[] { ConfigX.class };
        
        XStream xstream = new XStream();     // init XStream
        
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // define root alias so XStream knows which element and which class are equivalent
        xstream.alias("config", ConfigX.class);
        ConfigX loadedConfig = (ConfigX) xstream.fromXML(fileReader);
        return loadedConfig;
    }
    
    /**
     * @return ********************************************************************************************************************************************/
    
    public int writeConfigToXML(){                      /* Potential future implementation */
        
        return 0;
    }
    
    /**
     * @param project*
     * @return *******************************************************************************************************************************************/
    
    public String generateMainXML(Project project){                 /* Generate Main XML */
        
        XStream xstream = new XStream();     // init XStream
        xstream.processAnnotations(Project.class);
        String dataXml = xstream.toXML(project);
        //System.out.println(dataXml);
        
        return dataXml;
        
    }
    
    /**
     * @param assetPackage*
     * @return *******************************************************************************************************************************************/
    
    public String generatePackageDefinitionsXML(AssetPackage assetPackage){         /* Generate Package Definitions XML */
        
        XStream xstream = new XStream();     // init XStream
        xstream.processAnnotations(AssetPackage.class);
        String dataXml = xstream.toXML(assetPackage);
        //System.out.println(dataXml);
        
        return dataXml;
        
    }
    
    /**
     * @param fsdata*
     * @return *******************************************************************************************************************************************/
    
    public String generatePackageSourcesDataXML (FSData fsdata){                /* Generate PackageSources/data XML */
        
        XStream xstream = new XStream();
        
        String XML_HEADER = "<?xml version=\"1.0\"?>\n";
        
        xstream.processAnnotations(FSData.class);
        String dataXml = XML_HEADER + xstream.toXML(fsdata);
        //System.out.println(dataXml);
        
        return dataXml;
        
    }
    
    /**
     * @param modelInfo*
     * @return *******************************************************************************************************************************************/
    
    public String generateModelInfoXML(ModelInfo modelInfo){                /* Generate Model Info XML */
        
        XStream xstream = new XStream();
        
        String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        
        xstream.processAnnotations(ModelInfo.class);
        String dataXml = XML_HEADER + xstream.toXML(modelInfo);
        //System.out.println(dataXml);
        
        return dataXml;
        
    }
    
    
    
    
}
