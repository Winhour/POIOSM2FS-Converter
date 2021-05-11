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

    private final String name;          /* Default name */
    private final String name_en;       /* English name */
    private final String type;          /* Type of element */
    private final List <Long> nodes_list;   /* List of nodes */
    private long middle_node;               /* Central node */
    
    public ElementData(String name, String name_en, String type){
        this.name = name;
        this.name_en = name_en;
        this.type = type;
        this.middle_node = 0;
        this.nodes_list = new ArrayList<>();
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
