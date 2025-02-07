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
 * Factory for provider
 * @author Dani
 * @version 1.0
 * @see IProvider
 * @see ProviderRESTClient
 */
public class ProviderManagerFactory {
    
    /**
     * Instancia única de la interfaz {@link IProvider}.
     */
    private static IProvider providerEntity;
    
    /**
     * Obtiene la instancia única de la interfaz {@link IProvider}.
     * Si la instancia no ha sido creada, se inicializa con una nueva instancia de
     * {@link ProviderRESTClient}.
     *
     * @return Instancia única de {@link IProvider}.
     */
    public static IProvider getIProvider(){
        if(providerEntity==null)
            providerEntity=new ProviderRESTClient();
        
        return providerEntity;
    }
    
}