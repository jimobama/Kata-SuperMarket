/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author Obaro
 */
public final class SaleItemPromotion extends ItemBasket {
   private String groupID;
   private boolean isPayable=true;
 public SaleItemPromotion(){
     
 }

    public SaleItemPromotion(String group,ItemBasket itemB) {
        this.setItemId(itemB.getItemId());
        this.setCurrentPrices(itemB.getCurrentPrices());
        this.setNoOfItems(itemB.getNoOfItems());
        this.setIsPayable(true);
        this.setGroupID(group);
     }
    public boolean isIsPayable() {
        return isPayable;
    }

    public void setIsPayable(boolean isPayable) {
        this.isPayable = isPayable;
    }
  
   public SaleItemPromotion(String id){
       this.setItemId(id);
   }
   
    public SaleItemPromotion(String itemID, String groupId){
       this.setItemId(itemID);
       this.setGroupID(groupId);
   }
    
    public String getGroupID() {
        return groupID;
    }

    public final void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    
}
