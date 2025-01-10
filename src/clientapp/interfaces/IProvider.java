/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.ProviderEntity;
import java.util.List;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author 2dam
 */
public interface IProvider {


    public List<ProviderEntity> listByContractEnd() throws ClientErrorException;

    public List<ProviderEntity> listByContractInit() throws ClientErrorException;

    public void edit(ProviderEntity provider, String id) throws ClientErrorException;

    public ProviderEntity find(String id) throws ClientErrorException;

    public List<ProviderEntity> listByPrice() throws ClientErrorException;

    public void create(ProviderEntity provider) throws ClientErrorException;

    public List<ProviderEntity> findAll() throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    
}

