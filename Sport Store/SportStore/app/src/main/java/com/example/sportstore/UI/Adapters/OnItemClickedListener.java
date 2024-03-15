package com.example.sportstore.UI.Adapters;

import com.example.sportstore.UI.Data.ItemInStore;

public interface OnItemClickedListener{
    void increaseCount(ItemInStore itemInStore);
    void decreaseCount(ItemInStore itemInStore);
}
