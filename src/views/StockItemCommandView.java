/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Panel;

/**
 *
 * @author Obaro
 */
public abstract class StockItemCommandView extends Window {

    private Button __btnAdd;
    private Button __btnDelete;
    private Button __btnUpdate;
    private Button __btnBack;
    private Button __btnView;

    //Tables
    private Panel __main;
    private Panel __left;

    public StockItemCommandView(String title) {
        super(title);
        this.gui();
    }

    private void gui() {

        this.__main = new Panel(Panel.Orientation.HORISONTAL);
        this.__left = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);

        //buttons
        this.__btnAdd = new Button("Add New", new Action() {

            @Override
            public void doAction() {
                onAddClicked();
            }

        });
        this.__btnBack = new Button("Back", new Action() {

            @Override
            public void doAction() {
                onBackClick();
            }

        });
        this.__btnDelete = new Button("Delete", new Action() {

            @Override
            public void doAction() {
               
            }

        });
        this.__btnUpdate = new Button("Update", new Action() {

            @Override
            public void doAction() {
               
            }

        });

        this.__btnView = new Button("View", new Action() {

            @Override
            public void doAction() {
                onViewClick();
            }

        });

        //Add the panels to the main and the window panel
        this.__main.addComponent(__menu());

        this.addComponent(__main);
    }

    private Panel __menu() {

        //add The buttons 
        this.__left.addComponent(this.__btnAdd);
       // this.__left.addComponent(this.__btnUpdate);
        this.__left.addComponent(this.__btnView);
      //  this.__left.addComponent(this.__btnDelete);
        this.__left.addComponent(this.__btnBack);

        return this.__left;

    }

    //abstract class 
    abstract protected void onAddClicked();

    abstract protected void onBackClick();

    abstract protected void onViewClick();

}
