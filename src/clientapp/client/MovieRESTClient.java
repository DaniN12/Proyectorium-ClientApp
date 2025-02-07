/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.client;

import clientapp.interfaces.IMovie;
import clientapp.model.MovieEntity;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:MovieEntityFacadeREST
 * [proyectorium.crud.entities.movie]<br>
 * USAGE:
 * <pre>
 * MovieRESTClient client = new MovieRESTClient();
 * Object response = client.XXX(...);
 * // do whatever with response
 * client.close();
 * </pre>
 * 
 * This class provides methods for interacting with the RESTful API for managing
 * movie entities. It implements the IMovie interface and supports operations like
 * listing, finding, creating, editing, and deleting movies, as well as querying 
 * by specific criteria such as provider, release date, categories, and movie hour.
 * 
 * @see IMovie
 * @see MovieEntity
 * @author 2dam
 */
public class MovieRESTClient implements IMovie {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/cinemapp/webresources";

    /**
     * Constructs a new MovieRESTClient instance with the base URI.
     */
    public MovieRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.movie");
    }

    /**
     * Retrieves the count of movie entities available in the database.
     *
     * @return a string containing the count of movie entities.
     * @throws ClientErrorException if a client-side error occurs.
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Edits a movie entity using XML format.
     *
     * @param requestEntity the entity to be updated.
     * @param id the ID of the movie entity to be updated.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), MovieEntity.class);
    }

    /**
     * Edits a movie entity using JSON format.
     *
     * @param requestEntity the entity to be updated.
     * @param id the ID of the movie entity to be updated.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), MovieEntity.class);
    }

    /**
     * Lists movie entities by a provider using XML format.
     *
     * @param responseType the response type to be returned.
     * @param provider the provider name to filter by.
     * @param <T> the response type.
     * @return a list of movie entities from the specified provider.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T listByProvider_XML(GenericType<T> responseType, String provider) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("provider/{0}", new Object[]{provider}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists movie entities by a provider using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param provider the provider name to filter by.
     * @param <T> the response type.
     * @return a list of movie entities from the specified provider.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T listByProvider_JSON(GenericType<T> responseType, String provider) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("provider/{0}", new Object[]{provider}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Finds a movie entity by its ID using XML format.
     *
     * @param responseType the response type to be returned.
     * @param id the ID of the movie entity to find.
     * @param <T> the response type.
     * @return the movie entity corresponding to the provided ID.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Finds a movie entity by its ID using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param id the ID of the movie entity to find.
     * @param <T> the response type.
     * @return the movie entity corresponding to the provided ID.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Finds a range of movie entities using XML format.
     *
     * @param responseType the response type to be returned.
     * @param from the starting index.
     * @param to the ending index.
     * @param <T> the response type.
     * @return a range of movie entities.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Finds a range of movie entities using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param from the starting index.
     * @param to the ending index.
     * @param <T> the response type.
     * @return a range of movie entities.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Lists movie entities by their release date using XML format.
     *
     * @param responseType the response type to be returned.
     * @param <T> the response type.
     * @return a list of movie entities sorted by release date.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T listByReleaseDate_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("releaseDate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists movie entities by their release date using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param <T> the response type.
     * @return a list of movie entities sorted by release date.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T listByReleaseDate_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget.path("releaseDate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Lists movie entities by their categories using XML format.
     *
     * @param responseType the response type to be returned.
     * @param categoryCount the number of categories to filter by.
     * @param categories a list of categories to filter by.
     * @param <T> the response type.
     * @return a list of movie entities that match the given categories.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T listMoviesByCategories_XML(GenericType<T> responseType, String categoryCount, String categories) throws WebApplicationException {
        WebTarget resource = webTarget;
        if (categoryCount != null) {
            resource = resource.queryParam("categoryCount", categoryCount);
        }
        if (categories != null) {
            resource = resource.queryParam("categories", categories);
        }
        resource = resource.path("moviesByCategories");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists movie entities by their categories using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param categoryCount the number of categories to filter by.
     * @param categories a list of categories to filter by.
     * @param <T> the response type.
     * @return a list of movie entities that match the given categories.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T listMoviesByCategories_JSON(GenericType<T> responseType, String categoryCount, String categories) throws WebApplicationException {
        WebTarget resource = webTarget;
        if (categoryCount != null) {
            resource = resource.queryParam("categoryCount", categoryCount);
        }
        if (categories != null) {
            resource = resource.queryParam("categories", categories);
        }
        resource = resource.path("moviesByCategories");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Creates a new movie entity using XML format.
     *
     * @param requestEntity the entity to be created.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), MovieEntity.class);
    }

    /**
     * Creates a new movie entity using JSON format.
     *
     * @param requestEntity the entity to be created.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), MovieEntity.class);
    }

    /**
     * Lists movie entities by their movie hour using XML format.
     *
     * @param responseType the response type to be returned.
     * @param movieHour the movie hour to filter by.
     * @param <T> the response type.
     * @return a list of movie entities that match the given movie hour.
     * @throws ClientErrorException if a client-side error occurs.
     */
    public <T> T listByMovieHour_XML(GenericType<T> responseType, String movieHour) throws ClientErrorException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("movieHour/{0}", new Object[]{movieHour}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Lists movie entities by their movie hour using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param movieHour the movie hour to filter by.
     * @param <T> the response type.
     * @return a list of movie entities that match the given movie hour.
     * @throws ClientErrorException if a client-side error occurs.
     */
    public <T> T listByMovieHour_JSON(GenericType<T> responseType, String movieHour) throws ClientErrorException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("movieHour/{0}", new Object[]{movieHour}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Finds all movie entities using XML format.
     *
     * @param responseType the response type to be returned.
     * @param <T> the response type.
     * @return a list of all movie entities.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Finds all movie entities using JSON format.
     *
     * @param responseType the response type to be returned.
     * @param <T> the response type.
     * @return a list of all movie entities.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Removes a movie entity by its ID.
     *
     * @param id the ID of the movie entity to remove.
     * @throws WebApplicationException if a server-side error occurs.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(MovieEntity.class);
    }

    /**
     * Closes the client connection.
     */
    public void close() {
        client.close();
    }
}
