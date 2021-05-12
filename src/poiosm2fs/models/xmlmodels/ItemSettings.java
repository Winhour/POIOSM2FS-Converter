/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs.models.xmlmodels;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Marcin
 */

@XStreamAlias("ItemSettings")
public class ItemSettings {
    
    @XStreamAlias("ContentType")
    String contentType;
    
    @XStreamAlias("Title")
    String title;
    
    @XStreamAlias("Manufacturer")
    String manufacturer;
    
    @XStreamAlias("Creator")
    String creator;

    public ItemSettings(String contentType, String title, String manufacturer, String creator) {
        this.contentType = contentType;
        this.title = title;
        this.manufacturer = manufacturer;
        this.creator = creator;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

   
    
    
    
}
