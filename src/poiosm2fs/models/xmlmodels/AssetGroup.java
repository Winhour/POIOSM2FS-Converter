/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models.xmlmodels;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 *
 * @author Marcin
 */

@XStreamAlias("AssetGroup")
public class AssetGroup {
    
    @XStreamAsAttribute
    @XStreamAlias("Name")
    String name;
   
    @XStreamAlias("Type")
    String type;
    
    @XStreamAlias("Flags")
    FlagsAG flags;
    
    @XStreamAlias("AssetDir")
    String assetDir;
    
    @XStreamAlias("OutputDir")
    String outputDir;

    public AssetGroup(String name, String type, FlagsAG flags, String assetDir, String outputDir) {
        this.name = name;
        this.type = type;
        this.flags = flags;
        this.assetDir = assetDir;
        this.outputDir = outputDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FlagsAG getFlags() {
        return flags;
    }

    public void setFlags(FlagsAG flags) {
        this.flags = flags;
    }

    public String getAssetDir() {
        return assetDir;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }
    
    
    
}
