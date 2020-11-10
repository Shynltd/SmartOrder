
package com.example.smartorder.model.menu;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("listFood")
    @Expose
    private List<ListFood> listFood = null;
    @SerializedName("listDrink")
    @Expose
    private List<ListDrink> listDrink = null;

    public List<ListFood> getListFood() {
        return listFood;
    }

    public void setListFood(List<ListFood> listFood) {
        this.listFood = listFood;
    }

    public List<ListDrink> getListDrink() {
        return listDrink;
    }

    public void setListDrink(List<ListDrink> listDrink) {
        this.listDrink = listDrink;
    }

}
