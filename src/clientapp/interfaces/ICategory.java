/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.CategoryEntity;
import java.util.List;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author 2dam
 */
public interface ICategory {
    
     public String countREST() throws ClientErrorException;
     public void edit(Object requestEntity, String id) throws ClientErrorException;
     public CategoryEntity find(CategoryEntity responseType, String id) throws ClientErrorException;
     public CategoryEntity findRange(CategoryEntity responseType, String from, String to) throws ClientErrorException;
     public List <CategoryEntity>  listCategoriesbyCreationDate(CategoryEntity responseType) throws ClientErrorException;
     public void create(Object requestEntity) throws ClientErrorException;
     public List <CategoryEntity>  listCategoriesbyPegi(CategoryEntity responseType) throws ClientErrorException;
     public CategoryEntity findAll(CategoryEntity responseType) throws ClientErrorException;
     public void remove(String id) throws ClientErrorException;
     
}
