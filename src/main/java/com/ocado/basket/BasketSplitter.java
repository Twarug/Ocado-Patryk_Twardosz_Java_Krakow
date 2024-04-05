package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        if (absolutePathToConfigFile == null) {
            throw new RuntimeException("Unable to create the basket splitter. The path to the configuration file is null.");
        }
        
        Gson gson = new Gson();

        // Read the configuration file
        var fileReader = new JsonReader(new FileReader(absolutePathToConfigFile));
        productTypeToDeliveryTypesMap = gson.fromJson(fileReader, DELIVEY_MAP_TYPE);
    }

    /**
     * Splits the basket into multiple baskets based on the delivery method.
     * 
     * @param basket The basket to split
     * @return Map of a basket delivery method to a list of product in the baskets 
     */
    public Map<String, List<String>> splitBasket(List<String> basket) {
        if (basket == null) {
            throw new RuntimeException("Unable to split the basket. The basket is null.");
        }
        
        Map<String, List<String>> splitBasket = new HashMap<>();

        // Mae a copy of the basket to avoid modifying the original one & to be able to remove products from it
        basket = new ArrayList<>(basket);

        int iterationCounter = 0;
        // Split the basket into multiple baskets based on the delivery method until the basket is empty
        while (!basket.isEmpty()) {

            // Fail if basket contains products that cannot be delivered
            if (iterationCounter >= productTypeToDeliveryTypesMap.size()) {
                throw new RuntimeException("Unable to split the basket. The basket contains products that cannot be delivered.");
            }

            // Map of delivery method to the number of remaining products delivered with that method
            Map<String, Integer> deliveryMap = new HashMap<>();

            // Sort all products into the deliveryMap
            int theMostProducts = 0;
            String theBiggesProductsDeliveryMethod = "";
            for (String product : basket) {
                List<String> deliveryTypes = productTypeToDeliveryTypesMap.get(product);
                
                // Check if the product can be delivered
                if (deliveryTypes == null) {
                    throw new RuntimeException("Unable to split the basket. The basket contains products that cannot be delivered.");
                }

                for (String deliveryMethod : deliveryTypes) {
                    if (deliveryMap.containsKey(deliveryMethod)) {
                        int size = deliveryMap.get(deliveryMethod);
                        size++;
                        deliveryMap.put(deliveryMethod, size);

                        if (size > theMostProducts) {
                            theMostProducts = size;
                            theBiggesProductsDeliveryMethod = deliveryMethod;
                        }
                    } else {
                        deliveryMap.put(deliveryMethod, 1);

                        if (theMostProducts == 0) {
                            theMostProducts = 1;
                            theBiggesProductsDeliveryMethod = deliveryMethod;
                        }
                    }
                }
            }

            // List to store the biggest delivery basket
            var theBiggestDelivery = new ArrayList<String>();
            splitBasket.put(theBiggesProductsDeliveryMethod, theBiggestDelivery);

            // Add corresponding products to the biggest delivery basket & remove them from the original basket
            for (int i = 0; i < basket.size(); i++) {
                String product = basket.get(i);
                List<String> deliveryTypes = productTypeToDeliveryTypesMap.get(product);

                if (deliveryTypes.contains(theBiggesProductsDeliveryMethod)) {
                    theBiggestDelivery.add(product);
                    basket.remove(i--);
                }
            }
            iterationCounter++;
        }

        return splitBasket;
    }
}
