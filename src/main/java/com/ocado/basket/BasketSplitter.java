package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketSplitter {

    private final static Type DELIVEY_MAP_TYPE = new TypeToken<Map<String, List<String>>>() {}.getType();

    private final Map<String, List<String>> productTypeToDeliveryTypesMap;

    /**
     * Constructor
     * 
     * @param absolutePathToConfigFile The absolute path to the configuration file
     */    
    public BasketSplitter(String absolutePathToConfigFile) throws FileNotFoundException {
        Gson gson = new Gson();

        // Read the configuration file
        var fileReader = new JsonReader(new FileReader(absolutePathToConfigFile));
        productTypeToDeliveryTypesMap = gson.fromJson(fileReader, DELIVEY_MAP_TYPE);
    }

    /**
     * Splits the basket into multiple baskets based on the delivery method.
     * 
     * @param basket The basket to split
     * @return A map where the key is a basket delivery method and the value is a list of product in the baskets 
     */
    public Map<String, List<String>> splitBasket(List<String> basket) {        
        Map<String, List<String>> splitBasket = new HashMap<>();

        return splitBasket;
    }
}
