/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import services.Helper;

/**
 *
 * @author Obaro
 */
public abstract class Item extends IError {

    private String __id;
    private String __name;
    private String __desc;

    //The method check if the fields for the item is valid
    Item(String name, String desc) {
        this.__desc = desc;
        this.__id = Helper.generateId(10);
        this.__name = name;
    }

    boolean validate() {
        boolean okay = true;
        if (!Helper.isNamingFormat(this.getName())) {
            this.setError("Enter Item's name");
            okay = false;
        } else if (this.getId().trim().equals("")) {//should never happen
            this.setError("Reset Item identical number");
            okay = false;
        }
        return okay;
    }
    //getter and setters

    public String getId() {
        return __id;
    }

    public void setId(String __id) {
        this.__id = __id;
    }

    public String getName() {
        return __name;
    }

    public void setName(String __name) {
        this.__name = __name;
    }

    public String getDesc() {
        return __desc;
    }

    public void setDesc(String __desc) {
        this.__desc = __desc;
    }

}
