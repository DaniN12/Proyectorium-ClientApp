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
public interface ICategory {

    public String countREST() throws ClientErrorException;

    public void edit(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T listCategoriesbyCreationDate_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T listCategoriesbyCreationDate_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T listCategoriesbyPegi_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T listCategoriesbyPegi_JSON(GenericType<T> responseType) throws WebApplicationException;

    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void remove(String id) throws ClientErrorException;

    public <T> T listCategoriesByDescriptionAndPegi18_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T listCategoriesByDescriptionAndPegi18_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void close();
}
