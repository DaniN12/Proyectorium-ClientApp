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


    public <T> T listByContractEnd_XML(GenericType<T> responseType) throws WebApplicationException;
    public <T> T listByContractEnd_JSON(GenericType<T> responseType) throws WebApplicationException;

    public <T> T listByContractInit_XML(GenericType<T> responseType) throws WebApplicationException;
    public <T> T listByContractInit_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    public String countREST() throws WebApplicationException;

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException;
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException;
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException;
    
    public <T> T listByPrice_XML(GenericType<T> responseType) throws WebApplicationException;
    public <T> T listByPrice_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void create_XML(Object requestEntity) throws WebApplicationException;
    public void create_JSON(Object requestEntity) throws WebApplicationException;

    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void remove(String id) throws WebApplicationException;

    public void close();

    
}

