/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Iterator;
import structures.ItemPromo;

/**
 *
 * @author Obaro
 */
public class PromoManager implements InterfaceModel<ItemPromo> {
private ArrayList<ItemPromo> __itemPromos;
private boolean __isDone=false;
private Database __db;

   public PromoManager(){
       __itemPromos=new ArrayList<>();
       this.__db= new Database("promotions.txt");
       this.load();
   }

    @Override
    public void add(ItemPromo t) {
        this.__isDone=false;
        if(!this.isExist(t)){
            this.__itemPromos.add(t);
            this.__db.SaveObject(this.__itemPromos);
            __isDone=true;
        }
    }

    @Override
    public void remove(ItemPromo t) {
        if(this.isExist(t) && this.__itemPromos !=null){
            
            for(Iterator<ItemPromo>it= this.__itemPromos.iterator(); it.hasNext() ;)
            {
                ItemPromo _itemP= it.next();
                
            }
        }
      }

    @Override
    public boolean isExist(ItemPromo t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemPromo getItemById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
   
    
}
