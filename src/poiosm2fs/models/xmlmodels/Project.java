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

/* Project object for Main XML */

@XStreamAlias("Project")
public class Project {
    
    @XStreamAsAttribute
    @XStreamAlias("Version")
    String version;
    
    @XStreamAsAttribute
    @XStreamAlias("Name")
    String name;
    
    @XStreamAsAttribute
    @XStreamAlias("FolderName")
    String folderName;
    
    @XStreamAlias("OutputDirectory")
    String outputDirectory;
    
    @XStreamAlias("TemporaryOutputDirectory")
    String temporaryOutputDirectory;
    
    @XStreamAlias("Packages")
    Packages packagesx;

    public Project(String version, String name, String folderName, String outputDirectory, String temporaryOutputDirectory, Packages packagesx) {
        this.version = version;
        this.name = name;
        this.folderName = folderName;
        this.outputDirectory = outputDirectory;
        this.temporaryOutputDirectory = temporaryOutputDirectory;
        this.packagesx = packagesx;
    }
    
    
    
    
    
    
}
