package clientapp.interfaces;

import clientapp.model.UserEntity;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Interface for managing user-related operations, such as creating, editing, 
 * and removing user entities, as well as user authentication (sign-in).
 * <p>
 * This interface provides methods to perform CRUD operations on user data,
 * as well as a method to handle user sign-in functionality. It works with 
 * RESTful web services, supporting operations such as finding users by ID, 
 * listing users, and counting the number of users.
 * </p>
 * <p>
 * Methods in this interface may throw {@link WebApplicationException} or 
 * {@link ClientErrorException} for errors during the interaction with the REST API.
 * </p>
 */
public interface Signable {

    /**
     * Returns the count of user entities available through the RESTful service.
     * 
     * @return the count of users as a string.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public String countREST() throws WebApplicationException;

    /**
     * Edits an existing user entity by sending the provided request entity.
     * 
     * @param requestEntity the user entity to be updated.
     * @param id the ID of the user to be edited.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void edit(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Finds a user by its ID and returns the result in the specified response type.
     * 
     * @param responseType the type of the response.
     * @param id the ID of the user to find.
     * @param <T> the type of the response.
     * @return the user entity as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T find(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Finds a range of user entities, given the specified start and end range, and 
     * returns the result in the specified response type.
     * 
     * @param responseType the type of the response.
     * @param from the starting index for the range.
     * @param to the ending index for the range.
     * @param <T> the type of the response.
     * @return a list of user entities as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Creates a new user entity by sending the provided request entity.
     * 
     * @param requestEntity the user entity to be created.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void create(Object requestEntity) throws WebApplicationException;

    /**
     * Finds all users and returns the result in the specified response type.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all user entities as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Removes a user entity by its ID.
     * 
     * @param id the ID of the user to be removed.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Signs in a user by sending the provided credentials as the request entity
     * and returning the result in the specified response type.
     * 
     * @param requestEntity the credentials of the user to sign in.
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return the result of the sign-in operation as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T signIn(Object requestEntity, GenericType<T> responseType) throws WebApplicationException;

    /**
     * Closes any open resources associated with this interface.
     * This should be called when the interface is no longer needed to release resources.
     */
    public void close();
}
