/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Iterator;
import structures.SaleItemGroup;


/**
 *
 * @author Obaro
 */
public class SaleItemGroupManager implements InterfaceModel<SaleItemGroup> {
    private ArrayList<SaleItemGroup> __list;
    
    boolean __isDone=false;
    
    SaleItemGroupManager(){
        __list= new ArrayList<>();
    }

    @Override
    public void add(SaleItemGroup t) {
        this.__isDone=false;
        if(t !=null){
            if(!this.isExist(t)){
                this.__list.add(t);
                this.__isDone=true;
            }
        }
        
      
     }

    @Override
    public void remove(SaleItemGroup t) {
         this.__isDone=false;
        if(t !=null){
             if(this.isExist(t)){
                 
                 for(Iterator<SaleItemGroup> it= this.__list.iterator(); it.hasNext();){
                     
                     SaleItemGroup sp= it.next();
                     if(sp.getGroupId().equalsIgnoreCase(t.getGroupId())){
                         it.remove();
                         this.__isDone=true;
                     }
                     
                 }
             }
        }
    }

    @Override
    public boolean isExist(SaleItemGroup t) {
        boolean okay=false;
        if(t !=null &&  this.__list !=null){
            //check if the item exists linearly
            for (SaleItemGroup sp : this.__list) {
                if(sp==null)continue;
                if(sp.getGroupId().equalsIgnoreCase(t.getGroupId())){
                    okay=true;
                }
            }
            
        }
        return okay;
    }

    @Override
    public SaleItemGroup getItemById(String id) {
        SaleItemGroup result=null;
       
        if(this.isExist(new SaleItemGroup(id))){
          
             for (SaleItemGroup sp : this.__list) {                  
                if(sp.getGroupId().equalsIgnoreCase(id)){
                    result= sp;
                }
            }
        }
        return result;
     }

    @Override
    public void update(SaleItemGroup group) {
        this.__isDone=false;
        
        if(group !=null && this.isExist(group)){
            
            for(int i=0; i < this.__list.size(); i ++){
                SaleItemGroup temp= this.__list.get(i);
                if(temp.getGroupId().equalsIgnoreCase(group.getGroupId()))
                {
                    this.__list.set(i, group);
                    this.__isDone=true;
                    break;
                }
            }
            
        }
     }

    boolean isDone() {
        return this.__isDone;
    }

    @Override
    public ArrayList<SaleItemGroup> getList() {
        if(this.__list==null){
            this.__list= new ArrayList<>();
        }
        return this.__list;
    }

    void clear() {
        this.__list.clear();
      }

  
    
}
