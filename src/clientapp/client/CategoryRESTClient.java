/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.client;

import clientapp.interfaces.ICategory;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for interacting with the REST resource CategoryEntityFacadeREST.
 * This client facilitates operations for managing category entities, such as creating, editing, and fetching category data.
 * The client supports multiple formats including XML and JSON.
 * 
 * USAGE:
 * <pre>
 * CategoryRESTClient client = new CategoryRESTClient();
 * Object response = client.XXX(...);
 * // Do whatever with the response
 * client.close();
 * </pre>
 * 
 * @author 2dam
 */
public class CategoryRESTClient implements ICategory {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.Config").getString("URL");

    /**
     * Creates a new instance of the CategoryRESTClient.
     * Initializes the client and sets up the target URI for the category entity resources.
     */
    public CategoryRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.categoryentity");
    }

    /**
     * Fetches the count of category entities.
     * 
     * @return The count of category entities as a String.
     * @throws ClientErrorException If an error occurs during the HTTP request.
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Edits a category entity by sending a PUT request.
     * 
     * @param requestEntity The entity to be updated.
     * @param id The ID of the category entity to be edited.
     * @throws ClientErrorException If an error occurs during the HTTP request.
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                 .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                 .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Fetches a category entity by its ID in XML format.
     * 
     * @param responseType The response type.
     * @param id The ID of the category entity to fetch.
     * @param <T> The type of the response.
     * @return The category entity in XML format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Fetches a category entity by its ID in JSON format.
     * 
     * @param responseType The response type.
     * @param id The ID of the category entity to fetch.
     * @param <T> The type of the response.
     * @return The category entity in JSON format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Fetches a range of category entities in XML format.
     * 
     * @param responseType The response type.
     * @param from The starting index of the range.
     * @param to The ending index of the range.
     * @param <T> The type of the response.
     * @return The list of category entities in XML format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Fetches a range of category entities in JSON format.
     * 
     * @param responseType The response type.
     * @param from The starting index of the range.
     * @param to The ending index of the range.
     * @param <T> The type of the response.
     * @return The list of category entities in JSON format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Lists all categories sorted by creation date in XML format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of categories sorted by creation date in XML format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T listCategoriesbyCreationDate_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("listCategoriesbyCreationDate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists all categories sorted by creation date in JSON format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of categories sorted by creation date in JSON format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T listCategoriesbyCreationDate_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("listCategoriesbyCreationDate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Creates a new category entity by sending a POST request.
     * 
     * @param requestEntity The category entity to be created.
     * @throws ClientErrorException If an error occurs during the HTTP request.
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                 .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Lists categories by PEGI rating in XML format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of categories filtered by PEGI rating in XML format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T listCategoriesbyPegi_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("listCategoriesbyPegi");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists categories by PEGI rating in JSON format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of categories filtered by PEGI rating in JSON format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T listCategoriesbyPegi_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("listCategoriesbyPegi");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Fetches all category entities in XML format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of all category entities in XML format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Fetches all category entities in JSON format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of all category entities in JSON format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Removes a category entity by its ID.
     * 
     * @param id The ID of the category entity to be removed.
     * @throws ClientErrorException If an error occurs during the HTTP request.
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Lists categories by description and PEGI 18 rating in XML format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of categories filtered by description and PEGI 18 in XML format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T listCategoriesByDescriptionAndPegi18_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("listCategoriesByDescriptionAndPegi18");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists categories by description and PEGI 18 rating in JSON format.
     * 
     * @param responseType The response type.
     * @param <T> The type of the response.
     * @return The list of categories filtered by description and PEGI 18 in JSON format.
     * @throws WebApplicationException If an error occurs during the HTTP request.
     */
    public <T> T listCategoriesByDescriptionAndPegi18_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("listCategoriesByDescriptionAndPegi18");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Closes the client and releases all resources.
     */
    public void close() {
        client.close();
    }

}
