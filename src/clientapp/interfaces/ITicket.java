/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface ITicket {
    
    public <T> T listByMovieASC_XML(GenericType<T> responseType) throws WebApplicationException;
    
    public <T> T listByMovieASC_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    public <T> T listByBuyDateASC_XML(GenericType<T> responseType) throws WebApplicationException;
    
    public <T> T listByBuyDateASC_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;
    
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;
    
    public void create_XML(Object requestEntity) throws ClientErrorException;
    
    public void create_JSON(Object requestEntity) throws ClientErrorException;
    
    public <T> T listByPriceASC_XML(GenericType<T> responseType) throws WebApplicationException;
    
    public <T> T listByPriceASC_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;
    
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    public void remove(String id) throws WebApplicationException;
    
}
