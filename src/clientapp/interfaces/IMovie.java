package clientapp.interfaces;

import clientapp.model.MovieEntity;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Interface for managing {@link MovieEntity} entities via RESTful services.
 * <p>
 * This interface provides methods to perform CRUD operations and retrieve movies based on various filters,
 * such as provider, release date, categories, and movie hours. The methods support both XML and JSON formats for data exchange.
 * </p>
 * <p>
 * All methods in this interface may throw {@link WebApplicationException} for server-side errors during the interaction
 * with the REST API.
 * </p>
 */
public interface IMovie {

    /**
     * Retrieves the count of movies from the server.
     * 
     * @return the count of movies as a String.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public String countREST() throws WebApplicationException;

    /**
     * Edits an existing movie by sending the provided request entity in XML format.
     * 
     * @param requestEntity the movie entity to be updated.
     * @param id the ID of the movie to be edited.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Edits an existing movie by sending the provided request entity in JSON format.
     * 
     * @param requestEntity the movie entity to be updated.
     * @param id the ID of the movie to be edited.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Lists movies by provider in XML format.
     * 
     * @param responseType the type of the response.
     * @param provider the provider to filter the movies.
     * @param <T> the type of the response.
     * @return a list of movies filtered by provider as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByProvider_XML(GenericType<T> responseType, String provider) throws WebApplicationException;

    /**
     * Lists movies by provider in JSON format.
     * 
     * @param responseType the type of the response.
     * @param provider the provider to filter the movies.
     * @param <T> the type of the response.
     * @return a list of movies filtered by provider as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByProvider_JSON(GenericType<T> responseType, String provider) throws WebApplicationException;

    /**
     * Finds a movie by its ID and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param id the ID of the movie to be found.
     * @param <T> the type of the response.
     * @return the movie as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Finds a movie by its ID and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param id the ID of the movie to be found.
     * @param <T> the type of the response.
     * @return the movie as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Finds a range of movies and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param from the starting index of the range.
     * @param to the ending index of the range.
     * @param <T> the type of the response.
     * @return a list of movies as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Finds a range of movies and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param from the starting index of the range.
     * @param to the ending index of the range.
     * @param <T> the type of the response.
     * @return a list of movies as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Lists movies by release date in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of movies sorted by release date as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByReleaseDate_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists movies by release date in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of movies sorted by release date as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByReleaseDate_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Lists movies by categories in XML format.
     * 
     * @param responseType the type of the response.
     * @param categoryCount the number of categories to filter by.
     * @param categories a comma-separated list of category names to filter by.
     * @param <T> the type of the response.
     * @return a list of movies filtered by categories as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listMoviesByCategories_XML(GenericType<T> responseType, String categoryCount, String categories) throws WebApplicationException;

    /**
     * Lists movies by categories in JSON format.
     * 
     * @param responseType the type of the response.
     * @param categoryCount the number of categories to filter by.
     * @param categories a comma-separated list of category names to filter by.
     * @param <T> the type of the response.
     * @return a list of movies filtered by categories as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listMoviesByCategories_JSON(GenericType<T> responseType, String categoryCount, String categories) throws WebApplicationException;

    /**
     * Creates a new movie by sending the provided request entity in XML format.
     * 
     * @param requestEntity the movie entity to be created.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Creates a new movie by sending the provided request entity in JSON format.
     * 
     * @param requestEntity the movie entity to be created.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Lists movies by movie hour in XML format.
     * 
     * @param responseType the type of the response.
     * @param movieHour the movie hour to filter the movies.
     * @param <T> the type of the response.
     * @return a list of movies filtered by movie hour as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByMovieHour_XML(GenericType<T> responseType, String movieHour) throws WebApplicationException;

    /**
     * Lists movies by movie hour in JSON format.
     * 
     * @param responseType the type of the response.
     * @param movieHour the movie hour to filter the movies.
     * @param <T> the type of the response.
     * @return a list of movies filtered by movie hour as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T listByMovieHour_JSON(GenericType<T> responseType, String movieHour) throws WebApplicationException;

    /**
     * Finds all movies and returns the result in XML format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all movies as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Finds all movies and returns the result in JSON format.
     * 
     * @param responseType the type of the response.
     * @param <T> the type of the response.
     * @return a list of all movies as an object of type {@link T}.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Removes a movie by its ID.
     * 
     * @param id the ID of the movie to be removed.
     * @throws WebApplicationException if there is a server-side error during the request.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Closes the connection to the server.
     */
    public void close();
}
