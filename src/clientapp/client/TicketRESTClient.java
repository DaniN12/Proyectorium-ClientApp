/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.client;

import clientapp.interfaces.ITicket;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:TicketEntityFacadeREST
 * [proyectorium.crud.entities.ticket]
 * <br>
 * USAGE:
 * <pre>
 *        TicketRESTClient client = new TicketRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * Este cliente interactúa con el servicio REST para la gestión de objetos TicketEntity.
 * Las operaciones disponibles permiten realizar consultas, creación, edición y eliminación de entradas en el servicio de tickets.
 *
 * @author kbilb
 */
public class TicketRESTClient implements ITicket{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/cinemapp/webresources";

    /**
     * Constructor que inicializa el cliente REST y configura la URL base del servicio.
     */
    public TicketRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.ticket");
    }

    /**
     * Obtiene el número total de tickets.
     *
     * @return El número total de tickets en forma de texto.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Obtiene una lista de tickets ordenados por película en formato XML.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de tickets en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T listByMovieASC_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-movie");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de tickets ordenados por película en formato JSON.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de tickets en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T listByMovieASC_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-movie");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Obtiene una lista de tickets ordenados por fecha de compra en formato XML.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de tickets en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T listByBuyDateASC_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-buy-date");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de tickets ordenados por fecha de compra en formato JSON.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de tickets en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T listByBuyDateASC_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-buy-date");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Edita un ticket existente en formato XML.
     *
     * @param requestEntity El objeto que contiene los datos a actualizar.
     * @param id El ID del ticket a actualizar.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    @Override
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                 .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                 .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Edita un ticket existente en formato JSON.
     *
     * @param requestEntity El objeto que contiene los datos a actualizar.
     * @param id El ID del ticket a actualizar.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    @Override
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                 .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                 .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra un ticket por su ID en formato XML.
     *
     * @param responseType Tipo de respuesta esperada.
     * @param id El ID del ticket.
     * @param <T> El tipo de respuesta.
     * @return El ticket en formato XML.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra un ticket por su ID en formato JSON.
     *
     * @param responseType Tipo de respuesta esperada.
     * @param id El ID del ticket.
     * @param <T> El tipo de respuesta.
     * @return El ticket en formato JSON.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra un rango de tickets en formato XML.
     *
     * @param responseType Tipo de respuesta esperada.
     * @param from El valor de inicio del rango.
     * @param to El valor final del rango.
     * @param <T> El tipo de respuesta.
     * @return Los tickets en el rango especificado en formato XML.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra un rango de tickets en formato JSON.
     *
     * @param responseType Tipo de respuesta esperada.
     * @param from El valor de inicio del rango.
     * @param to El valor final del rango.
     * @param <T> El tipo de respuesta.
     * @return Los tickets en el rango especificado en formato JSON.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea un nuevo ticket en formato XML.
     *
     * @param requestEntity El objeto que contiene los datos del nuevo ticket.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    @Override
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                 .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo ticket en formato JSON.
     *
     * @param requestEntity El objeto que contiene los datos del nuevo ticket.
     * @throws ClientErrorException Si ocurre un error en la petición.
     */
    @Override
    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                 .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Obtiene una lista de tickets ordenados por precio en formato XML.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de tickets ordenados por precio en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T listByPriceASC_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-price");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de tickets ordenados por precio en formato JSON.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de tickets ordenados por precio en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T listByPriceASC_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-price");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Obtiene todos los tickets en formato XML.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Todos los tickets en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene todos los tickets en formato JSON.
     *
     * @param responseType Tipo genérico de la respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Todos los tickets en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un ticket utilizando su ID.
     *
     * @param id El ID del ticket a eliminar.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    @Override
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Cierra la conexión con el cliente REST.
     */
    public void close() {
        client.close();
    }
}
