/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.CategoryRESTClient;
import clientapp.interfaces.ICategory;

/**
 * Factory class to manage the creation of an ICategory instance.
 * <p>
 * This class is responsible for ensuring that only one instance of the ICategory 
 * object is created and reused across the application (Singleton pattern).
 * The actual implementation of ICategory is provided by the CategoryRESTClient.
 * </p>
 * 
 * @author 2dam
 */
public class CategoryFactory {

    /**
     * Instance of the ICategory interface.
     * This will hold the singleton instance of the ICategory object.
     */
    private static ICategory categoryEntity;

    /**
     * Returns the singleton instance of the ICategory object.
     * <p>
     * If the instance has not been created yet, it will create a new instance 
     * using the CategoryRESTClient implementation. If the instance already exists, 
     * it simply returns the existing instance.
     * </p>
     *
     * @return the ICategory instance
     */
    public static ICategory getICategory() {
        // If the categoryEntity is not initialized, create a new CategoryRESTClient instance
        if (categoryEntity == null) {
            categoryEntity = new CategoryRESTClient();
        }

        return categoryEntity;
    }
}
