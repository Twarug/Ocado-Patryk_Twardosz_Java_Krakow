package com.ocado.basket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketSplitter {
    
    /**
     * Constructor
     * 
     * @param absolutePathToConfigFile The absolute path to the configuration file
     */    
    public BasketSplitter(String absolutePathToConfigFile) {
        
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
