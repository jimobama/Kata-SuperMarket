/*
 The file contains the main user views , that contains other subview 
 */
package views;

import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.gui.component.Button;
import controllers.InterfaceController;
import controllers.KataSuperMarketController;
import structures.ItemGroup;

/**
 *
 * @author Obaro
 */
public class KataSuperMarketView extends KataSuperMarketCommandView implements IView {

    private KataSuperMarketController __controller;

    private final StockManagerView __stockView;
    private final SaleManagerView __saleView;
    private final ShoppingView __shoppingView;

    private static final boolean LOOP_MENU = true;

    private Button btnApplication;

    public KataSuperMarketView(String title) {
        super(title);

        __stockView = new StockManagerView("Stock Manager", this);
        __saleView = new SaleManagerView("Sale Manager",this);
        __shoppingView = new ShoppingView();

    }

  
    @Override
    public void attach(InterfaceController controller) {
        this.__controller = (KataSuperMarketController) controller;
        //share the main controller with the subviews
        this.__stockView.attach(__controller);
        this.__saleView.attach(__controller);
        this.__shoppingView.attach(__controller);
    }

    @Override
    public void show() {
        super.launch();
      
    }

    @Override
    protected void onStockManagerClicked() {

        this.getOwner().setTitle("Stock Management Screen");
        this.__stockView.show();
    }

    @Override
    protected void onExitWindow() {
        this.close();
        System.exit(0);
    }

    @Override
    protected void onSaleManagerClicked() {
        this.getOwner().setTitle("Sale/Promotion  Management Screen");
        this.getOwner().getActiveWindow().close();
        SalesItemListView saleWindow= new SalesItemListView("Sale Items List", this);
        this.getOwner().showWindow(saleWindow);
        
        
    }

    @Override
    protected void onShoppingCenterClick() {
        this.getOwner().setTitle("Shopping Center");
        this.getOwner().getActiveWindow().close();
        ShoppingCenterView shoppingCenter= new ShoppingCenterView("Available Items ",this);
        this.getOwner().showWindow(shoppingCenter,Position.NEW_CORNER_WINDOW);
        
    }

    @Override
    public InterfaceController getController() {
        return this.__controller;
    }

    boolean isUpdateItemGroup(ItemGroup __itemGroup) {
       return  this.__controller.isUpdateItemGroup(__itemGroup);
    }

}
