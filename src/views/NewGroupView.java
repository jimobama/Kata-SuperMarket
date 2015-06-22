/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.TerminalSize;
import services.Helper;
import structures.ItemGroup;

/**
 *
 * @author Obaro
 */
public class NewGroupView extends Window {
private SalesItemListView __parent;

    private Label   __lblGroupId ;
    private TextBox __txtGroupName;
    private TextBox __txtDescription;
    //buttons]
    private Button __btnBack;
    private Button __btnCreate;    
    private Panel __pnlMain;
    


    public NewGroupView(String title,SalesItemListView view) {
        super(title);
        this.__parent=(SalesItemListView)view;        
        this.__initGui();
        this.addComponent(this.__pnlMain);
    }

    private void __initGui() {
        this.__pnlMain= new Panel(Panel.Orientation.VERTICAL);
        TerminalSize lblSize= new TerminalSize (15,2);
        //create first row of form
        Panel row1= new Panel(Panel.Orientation.HORISONTAL);
        Label lbl1= new Label("Group Id");
        lbl1.setAlignment(Component.Alignment.TOP_LEFT);
        lbl1.setPreferredSize(lblSize);
        row1.addComponent(lbl1);
        this.__lblGroupId= new Label(Helper.generateId(5));
        row1.addComponent( this.__lblGroupId);
        this.__pnlMain.addComponent(row1);
        //row2
        Panel row2= new Panel(Panel.Orientation.HORISONTAL);
        Label lbl2= new Label("Group Name");
        lbl2.setAlignment(Component.Alignment.TOP_LEFT);
        lbl2.setPreferredSize(lblSize);
        
        row2.addComponent(lbl2);
        this.__txtGroupName= new TextBox("",30);
        row2.addComponent( this.__txtGroupName);
        this.__pnlMain.addComponent(row2);
        
        //row3
        Panel row3= new Panel(Panel.Orientation.HORISONTAL);
        Label lbl3= new Label("Description");
        lbl3.setAlignment(Component.Alignment.TOP_LEFT);
        lbl3.setPreferredSize(lblSize);
        row3.addComponent(lbl3);
        this.__txtDescription= new TextBox("",30);
        row3.addComponent(this.__txtDescription);
        this.__pnlMain.addComponent(row3);
        
        //Add empty space
        this.__pnlMain.addComponent(new EmptySpace());
        
        Panel  row4= new Panel(Panel.Orientation.HORISONTAL);
        
        this.__btnBack= new Button ("Back", ()->{
            this.onBackClicked();
        }) ;
         row4.addComponent(this.__btnBack);
       
       this.__btnCreate= new Button ("Create", ()->{
            this.onCreateClicked();
        }) ;
         row4.addComponent(this.__btnCreate);
        
      this.__pnlMain.addComponent(row4);    
        
        
     }

    private void onBackClicked() {
        this.close();
        if(this.__parent !=null){
            this.__parent.getOwner().showWindow(this.__parent);
        }
    }
    

    private void onCreateClicked() {
        
        String groupID= this.__lblGroupId.getText();
        String name = this.__txtGroupName.getText();
        String desc= this.__txtDescription.getText();
        
        ItemGroup item= new ItemGroup(groupID);
        item.setGroupname(name);
        item.setPromoStatus(false);
        item.setDescPromo(desc);
        
        this.close();
        if(item.validated()){
            //now item is validted 
            this.__parent.onCreateGroupEvent(item);          
            this.__lblGroupId.setText(Helper.generateId(5));
            this.__txtGroupName.setText("");
            this.__txtDescription.setText("");
        }else{           
            IView.report(this.getOwner(), 1, item.getError());            
        }
        if(this.__parent!=null){
               this.__parent.getOwner().showWindow(this,Position.CENTER);
          }
        
    }
    
}
