/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.CategoryRESTClient;
import clientapp.interfaces.ICategory;


/**
 *
 * @author 2dam
 */
public class CategoryFactory {

    private static ICategory categoryEntity;

    public static ICategory getICategory() {
        if (categoryEntity == null) {
            categoryEntity = new CategoryRESTClient();
        }

        return categoryEntity;
    }
}
