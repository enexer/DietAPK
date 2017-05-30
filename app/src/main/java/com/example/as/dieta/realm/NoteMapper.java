package com.example.as.dieta.realm;

/**
 * Created by as on 20.05.2017.
 */

// klasa w pakiecie data/db/NoteMapper
public class NoteMapper {
    SelectedProducts fromRealm(SelectedProductsRealm selectedProductsRealm) {
        SelectedProducts selectedProducts = new SelectedProducts();
        selectedProducts.setId(selectedProductsRealm.getId());
        selectedProducts.setDayId(selectedProductsRealm.getDayId());
        selectedProducts.setMealId(selectedProductsRealm.getMealId());
        selectedProducts.setProductId(selectedProductsRealm.getProductId());
        selectedProducts.setWeight(selectedProductsRealm.getWeight());
        return selectedProducts;
    }
}