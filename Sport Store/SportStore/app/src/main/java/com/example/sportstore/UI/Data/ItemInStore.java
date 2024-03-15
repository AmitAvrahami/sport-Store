package com.example.sportstore.UI.Data;

import java.util.ArrayList;
import java.util.List;

public class ItemInStore {

    int itemImage;
    String price,count,itemName,itemDescription;


    public ItemInStore(){

    }
    public ItemInStore(int itemImage, String price, String count, String itemName, String itemDescription) {
        this.itemImage = itemImage;
        this.price = price;
        this.count = count;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public Integer getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
