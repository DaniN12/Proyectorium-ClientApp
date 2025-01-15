/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.ProviderEntity;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface IProvider {


    public <T> T listByContractEnd(Class<T> responseType) throws WebApplicationException;

    public <T> T listByContractInit(Class<T> responseType) throws WebApplicationException;

    public String countREST() throws WebApplicationException;

    public void edit(Object requestEntity, String id) throws WebApplicationException;

    public <T> T find(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T listByPrice(Class<T> responseType) throws WebApplicationException;

    public void create(Object requestEntity) throws WebApplicationException;

    public <T> T findAll(GenericType<T> responseType) throws WebApplicationException;

    public void remove(String id) throws WebApplicationException;

    public void close();

    
}

