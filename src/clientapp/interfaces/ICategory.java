package clientapp.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interface for managing  entities via RESTful services.
 * <p>
 * This interface provides various methods for performing CRUD operations
 * and retrieving categories based on different filters such as creation date,
 * PEGI rating, and description.
 * </p>
 * <p>
 * All methods in this interface may throw {@link ClientErrorException} for client-side
 * errors or {@link WebApplicationException} for server-side errors during the interaction
 * with the REST API.
 * </p>
 */
public interface ICategory {

    /**
     * Retrieves the count of categories from the server.
     * 
     * @return the count of categories as a String.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public String countREST() throws ClientErrorException;

    /**
     * Edits an existing category with the provided request entity and category ID.
     * 
     * @param requestEntity the category entity to be updated.
     * @param id the ID of the category to be edited.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException;

    /**
     * Finds a category by its ID and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param id the ID of the category to be found.
     * @param <T> the type of the response.
     * @return the category as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Finds a category by its ID and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param id the ID of the category to be found.
     * @param <T> the type of the response.
     * @return the category as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Finds a range of categories and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param from the starting index of the range.
     * @param to the ending index of the range.
     * @param <T> the type of the response.
     * @return a list of categories as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Finds a range of categories and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param from the starting index of the range.
     * @param to the ending index of the range.
     * @param <T> the type of the response.
     * @return a list of categories as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Lists categories sorted by creation date in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of categories sorted by creation date as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listCategoriesbyCreationDate_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists categories sorted by creation date in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of categories sorted by creation date as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listCategoriesbyCreationDate_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Creates a new category with the provided request entity.
     * 
     * @param requestEntity the category entity to be created.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void create(Object requestEntity) throws ClientErrorException;

    /**
     * Lists categories filtered by PEGI rating in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of categories filtered by PEGI rating as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listCategoriesbyPegi_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists categories filtered by PEGI rating in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of categories filtered by PEGI rating as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listCategoriesbyPegi_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists all categories in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all categories as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists all categories in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all categories as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Removes a category by its ID.
     * 
     * @param id the ID of the category to be removed.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void remove(String id) throws ClientErrorException;

    /**
     * Lists categories filtered by description and PEGI 18 rating in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of categories filtered by description and PEGI 18 rating as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listCategoriesByDescriptionAndPegi18_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists categories filtered by description and PEGI 18 rating in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of categories filtered by description and PEGI 18 rating as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listCategoriesByDescriptionAndPegi18_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Closes the connection to the server.
     */
    public void close();
}
