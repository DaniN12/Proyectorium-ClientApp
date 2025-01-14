/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.ProviderRESTClient;
import clientapp.interfaces.IProvider;
import clientapp.model.ProviderEntity;

/**
 *
 * @author 2dam
 */
public class ProviderManagerFactory {
    
    private static IProvider providerEntity;
    
    public static IProvider getIProvider(){
        if(providerEntity==null)
            providerEntity=new ProviderRESTClient();
        
        return providerEntity;
    }
    
}
