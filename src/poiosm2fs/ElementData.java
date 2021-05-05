/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcin
 */
public class ElementData {
    
    
    private String name, name_en, type;
    private List <Long> nodes_list;
    private long middle_node;
    
    public ElementData(String name, String name_en, String type){
        this.name = name;
        this.name_en = name_en;
        this.type = type;
        this.middle_node = 0;
        this.nodes_list = new ArrayList<Long>();
    }
    
    public void setMiddle(long newMiddle){
        
        this.middle_node = newMiddle;
        
    }
    
    public void addToNodesList(long newNode){
        
        this.nodes_list.add(newNode);
        
    }
    
    public String getName(){
        return name;
    }
    
    public String getEnName(){
        return name_en;
    }
    
    public String getType(){
        return type;
    }
    
    public long getMiddleNode(){
        return middle_node;
    }
    
    public List<Long> getNodeList(){
        return nodes_list;
    }
    
    
}
