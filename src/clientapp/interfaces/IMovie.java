/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.MovieEntity;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface IMovie {

    public String countREST() throws WebApplicationException;

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;
    
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    public <T> T listByProvider_XML(GenericType<T> responseType, String provider) throws WebApplicationException;

    public <T> T listByProvider_JSON(GenericType<T> responseType, String provider) throws WebApplicationException;

    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T listByReleaseDate_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T listByReleaseDate_JSON(GenericType<T> responseType) throws WebApplicationException;

    public <T> T listMoviesByCategories_XML(GenericType<T> responseType, String categoryCount, String categories) throws WebApplicationException;

    public <T> T listMoviesByCategories_JSON(GenericType<T> responseType, String categoryCount, String categories) throws WebApplicationException;

    public void create_XML(Object requestEntity) throws WebApplicationException;

    public void create_JSON(Object requestEntity) throws WebApplicationException;
    public <T> T listByMovieHour_XML(GenericType<T> responseType, String movieHour) throws WebApplicationException;

    public <T> T listByMovieHour_JSON(GenericType<T> responseType, String movieHour) throws WebApplicationException;

    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    public void remove(String id) throws WebApplicationException;

    public void close();
    
}
