package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {
    
    private final static Type BASKET_TYPE = new TypeToken<List<String>>() {}.getType();
    
    private static BasketSplitter splitter;
    
    @BeforeAll
    static void createBasketSplitter() throws FileNotFoundException {
        splitter = new BasketSplitter("run/config.json");
    }

    @ParameterizedTest
    @ValueSource(strings = {"run/basket-1.json", "run/basket-2.json"})
    void basketSplitterTest_FromFiles(String fileName) throws FileNotFoundException {
        // Load an example basket from the file
        List<String> basket = new Gson().fromJson(new FileReader(fileName), BASKET_TYPE);

        // Split the basket
        var splitBasket = splitter.splitBasket(basket);

        // Validate the split basket
        assertFalse(splitBasket.isEmpty());
        assertTrue(splitBasket.values().stream().noneMatch(List::isEmpty));
        
        // Print out the split basket
        for (var entry : splitBasket.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("\n====================================\n");
    }
    
    @Test
    void basketSplitterTest_EmptyBasket() {
        // Split the empty basket
        var splitBasket = splitter.splitBasket(List.of());
        
        // Validate the split basket
        assertTrue(splitBasket.isEmpty());
    }

    @Test
    void basketSplitterTest_InvalidBasket() {
        // Split the invalid basket
        RuntimeException exception = assertThrows(RuntimeException.class, () -> splitter.splitBasket(List.of("invalid product")));
        
        // Validate the split basket
        assertTrue(exception.getMessage().contains("Unable to split the basket. The basket contains products that cannot be delivered."));
    }

    @Test
    void basketSplitterTest_NullBasket() {
        // Split the null basket
        RuntimeException exception = assertThrows(RuntimeException.class, () -> splitter.splitBasket(null));
        
        // Validate the split basket
        assertTrue(exception.getMessage().contains("The basket is null."));
    }
}
