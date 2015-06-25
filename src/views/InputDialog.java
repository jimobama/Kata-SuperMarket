/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.TerminalSize;

/**
 *
 * @author Obaro
 */
class  InputDialog  extends Window{

     private TextBox __txtValue;
    static String showMessageBox(GUIScreen owner, String title, String item_Weight) {
    
       InputDialog  dialog= new InputDialog(title,item_Weight);
       owner.showWindow(dialog, GUIScreen.Position.CENTER);
       return dialog.getValue();
    }
  
    private final String __message;

    protected InputDialog(String title, String message) {
        super( title);       
        this.__message= message;
        this.__initGui();
    }

    private String getValue() {
        
        return this.__txtValue.getText();
    }

    private void __initGui() {
        
        Panel pnlMain= new Panel(Panel.Orientation.VERTICAL);
        pnlMain.addComponent(new EmptySpace());
        
        Panel row1= new Panel(Panel.Orientation.HORISONTAL);
        
        TerminalSize lblsize= new TerminalSize(this.__message.length()+2,1);
        
        Label lblMessage= new  Label(this.__message);
        
        lblMessage.setAlignment(Component.Alignment.LEFT_CENTER);
        lblMessage.setPreferredSize(lblsize);
        row1.addComponent(lblMessage);
        this.__txtValue= new TextBox("",30);
        row1.addComponent(this.__txtValue);
        pnlMain.addComponent(row1);
        //row2
         pnlMain.addComponent(new EmptySpace());
        Panel row2= new Panel(Panel.Orientation.HORISONTAL);
        Label lblEmptyButton= new Label();
        lblEmptyButton.setAlignment(Component.Alignment.LEFT_CENTER);
        lblEmptyButton.setPreferredSize(lblsize);
        row2.addComponent(lblEmptyButton);
        
        Button btnOkay= new Button("Okay", ()->{
            //close the dialog button 
            this.close();
        });
        row2.addComponent(btnOkay);
        pnlMain.addComponent(row2);
        pnlMain.addComponent(new EmptySpace());
        this.addComponent(pnlMain);
        
     }
    
}
