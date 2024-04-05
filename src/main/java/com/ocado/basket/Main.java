package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public final class Main {
    private final static Type BASKET_TYPE = new TypeToken<List<String>>() {}.getType();

    public static void main(String[] args) throws Exception {
        // Crate a BasketSplitter object
        BasketSplitter splitter = new BasketSplitter("config.json");

        // Load an example basket from the file
        List<String> basket = new Gson().fromJson(new FileReader("basket-1.json"), BASKET_TYPE);
        
        // Split the basket
        var splitBasket = splitter.splitBasket(basket);

        // Print out the split basket
        for (var entry : splitBasket.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

