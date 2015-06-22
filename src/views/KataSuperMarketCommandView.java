/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import java.nio.charset.Charset;


/**
 *
 * @author Obaro
 */
public abstract class KataSuperMarketCommandView extends Window {

    private final Panel pnlMain;
    private final Panel pnlLeft;
    private final Panel pnlRight;

    //The forms
    private Button btnApplication;
    private Button btnStockManager;
    private Button btnSaleManager;
    private Button btnPromoManager;

  
  

    public void launch() {
        Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
        Screen screen = new Screen(terminal);
        GUIScreen guiScreen = new GUIScreen(screen, " Main Window");

        screen.startScreen();
        guiScreen.showWindow(this,Position.CENTER);
        screen.stopScreen();

    }

    public KataSuperMarketCommandView(String title) {
        super(title);
        pnlMain = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
        pnlLeft = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
        pnlRight = new Panel(Panel.Orientation.VERTICAL);

       
        this._initGUI();
    }

    protected void _initGUI() {
        this.pnlMain.addComponent(this.__menuGui());   
        this.addComponent(this.pnlMain);
    }


  

    private Panel __menuGui() {
        this.pnlLeft.setTitle("Kata Super Market Main Screen");
        
        btnStockManager = new Button("Stock Manager", new Action() {

            @Override
            public void doAction() {
                onStockManagerClicked();
            }

        });
        btnSaleManager = new Button("Sale Manager", new Action() {

            @Override
            public void doAction() {
                onSaleManagerClicked();
            }

        });

        btnApplication = new Button("Exit Application", new Action() {

            @Override
            public void doAction() {
                onExitWindow();
            }

        });

        btnPromoManager = new Button("My Shopping Basket", new Action() {

            @Override
            public void doAction() {
                onShoppingBasketClick();
            }

        });
        this.pnlLeft.addComponent(btnStockManager);
        this.pnlLeft.addComponent(btnSaleManager);
        this.pnlLeft.addComponent(btnPromoManager);
        this.pnlLeft.addComponent(btnApplication);
        return pnlLeft;
    }

    protected void update() {

        
    }

    abstract protected void onStockManagerClicked();
    abstract protected void onExitWindow();
    abstract protected void onSaleManagerClicked();
    abstract protected void onShoppingBasketClick();

   
}
