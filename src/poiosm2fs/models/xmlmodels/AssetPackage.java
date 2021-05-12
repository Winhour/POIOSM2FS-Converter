/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models.xmlmodels;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.List;

/**
 *
 * @author Marcin
 */

@XStreamAlias("AssetPackage")
public class AssetPackage {
    
    @XStreamAsAttribute
    @XStreamAlias("Name")
    String name;
    
    @XStreamAsAttribute
    @XStreamAlias("ReleaseNotes")
    String releaseNotes;
    
    @XStreamAsAttribute
    @XStreamAlias("Version")
    String version;
    
    @XStreamAlias("ItemSettings")
    ItemSettings itemSettings;
    
    @XStreamAlias("Flags")
    FlagsAP flags;
    
    @XStreamAlias("AssetGroups")
    List<AssetGroup> assetGroups;

    public AssetPackage(String name, String releaseNotes, String version, ItemSettings itemSettings, FlagsAP flags, List<AssetGroup> assetGroups) {
        this.name = name;
        this.releaseNotes = releaseNotes;
        this.version = version;
        this.itemSettings = itemSettings;
        this.flags = flags;
        this.assetGroups = assetGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ItemSettings getItemSettings() {
        return itemSettings;
    }

    public void setItemSettings(ItemSettings itemSettings) {
        this.itemSettings = itemSettings;
    }

    public FlagsAP getFlags() {
        return flags;
    }

    public void setFlags(FlagsAP flags) {
        this.flags = flags;
    }

    public List<AssetGroup> getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(List<AssetGroup> assetGroups) {
        this.assetGroups = assetGroups;
    }
    
    
    
}
