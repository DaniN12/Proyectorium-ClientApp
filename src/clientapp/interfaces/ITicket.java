package clientapp.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interface for managing  entities via RESTful services.
 * <p>
 * This interface provides methods for creating, editing, and removing tickets, 
 * as well as retrieving lists of tickets sorted by different criteria such as 
 * movie, buy date, and price. The methods support both XML and JSON formats for data exchange.
 * </p>
 * <p>
 * Methods in this interface may throw {@link WebApplicationException} or {@link ClientErrorException} 
 * for errors during the interaction with the REST API.
 * </p>
 */
public interface ITicket {

    /**
     * Lists all tickets sorted by movie in ascending order and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of tickets sorted by movie as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByMovieASC_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists all tickets sorted by movie in ascending order and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of tickets sorted by movie as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByMovieASC_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists all tickets sorted by buy date in ascending order and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of tickets sorted by buy date as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByBuyDateASC_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists all tickets sorted by buy date in ascending order and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of tickets sorted by buy date as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByBuyDateASC_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Edits an existing ticket by sending the provided request entity in XML format.
     * 
     * @param requestEntity the ticket entity to be updated.
     * @param id the ID of the ticket to be edited.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;

    /**
     * Edits an existing ticket by sending the provided request entity in JSON format.
     * 
     * @param requestEntity the ticket entity to be updated.
     * @param id the ID of the ticket to be edited.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;

    /**
     * Creates a new ticket by sending the provided request entity in XML format.
     * 
     * @param requestEntity the ticket entity to be created.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void create_XML(Object requestEntity) throws ClientErrorException;

    /**
     * Creates a new ticket by sending the provided request entity in JSON format.
     * 
     * @param requestEntity the ticket entity to be created.
     * @throws ClientErrorException if there is a client-side error during the request.
     */
    public void create_JSON(Object requestEntity) throws ClientErrorException;

    /**
     * Lists all tickets sorted by price in ascending order and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of tickets sorted by price as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByPriceASC_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists all tickets sorted by price in ascending order and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of tickets sorted by price as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByPriceASC_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Finds all tickets and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all tickets as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Finds all tickets and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all tickets as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Removes a ticket by its ID.
     * 
     * @param id the ID of the ticket to be removed.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void remove(String id) throws WebApplicationException;
}
