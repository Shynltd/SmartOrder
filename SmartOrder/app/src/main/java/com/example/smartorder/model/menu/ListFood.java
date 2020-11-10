
package com.example.smartorder.model.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListFood {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private Object amount;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
