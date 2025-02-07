/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.client;

import clientapp.interfaces.IProvider;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ProviderEntityFacadeREST
 * [proyectorium.crud.entities.provider]<br>
 * USAGE:
 * <pre>
 * ProviderRESTClient client = new ProviderRESTClient();
 * Object response = client.XXX(...);
 * // do whatever with response
 * client.close();
 * </pre>
 *
 * @author 2dam
 * @version 1.0
 * @see IProvider
 */
public class ProviderRESTClient implements IProvider {

    /**
     * Objeto WebTarget utilizado para realizar las solicitudes al servicio
     * REST.
     */
    private WebTarget webTarget;

    /**
     * Cliente JAX-RS utilizado para crear las conexiones con el servicio REST.
     */
    private Client client;

    /**
     * URI base del servicio REST, obtenida desde el archivo de configuración.
     */
    private static final String BASE_URI = ResourceBundle.getBundle("resources.Config").getString("URL");

    /**
     * Constructor por defecto que inicializa el cliente y configura el
     * WebTarget con la URI base y la ruta del recurso REST.
     */
    public ProviderRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.provider");
    }

    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a finalizar,
     * en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T listByContractEnd_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByContractEnd");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a finalizar,
     * en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T listByContractEnd_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByContractEnd");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a iniciar, en
     * formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T listByContractInit_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByContractInit");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a iniciar, en
     * formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T listByContractInit_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByContractInit");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Obtiene el número total de proveedores registrados en el sistema.
     *
     * @return Número total de proveedores en formato de texto.
     * @throws ClientErrorException Si ocurre un error durante la comunicación
     * con el servicio REST.
     */
    @Override
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Edita la información de un proveedor existente, enviando los datos en
     * formato XML.
     *
     * @param requestEntity Objeto que representa los datos del proveedor a
     * editar.
     * @param id Identificador del proveedor a editar.
     * @throws ClientErrorException Si ocurre un error durante la comunicación
     * con el servicio REST.
     */
    @Override
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Edita la información de un proveedor existente, enviando los datos en
     * formato JSON.
     *
     * @param requestEntity Objeto que representa los datos del proveedor a
     * editar.
     * @param id Identificador del proveedor a editar.
     * @throws ClientErrorException Si ocurre un error durante la comunicación
     * con el servicio REST.
     */
    @Override
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Obtiene una lista de proveedores con contratos activos, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    public <T> T listActiveContracts_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByActiveContract");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores con contratos activos, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    public <T> T listActiveContracts_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByActiveContract");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca un proveedor por su identificador, devolviendo la información en
     * formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @param id Identificador del proveedor a buscar.
     * @return Información del proveedor en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un proveedor por su identificador, devolviendo la información en
     * formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @param id Identificador del proveedor a buscar.
     * @return Información del proveedor en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     *
     */
    @Override
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores dentro de un rango específico, en
     * formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores dentro de un rango específico, en
     * formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores ordenados por precio, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T listByPrice_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByPrice");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de proveedores ordenados por precio, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T listByPrice_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("listByPrice");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea un nuevo proveedor, enviando los datos en formato XML.
     *
     * @param requestEntity Objeto que representa los datos del nuevo proveedor.
     * @throws ClientErrorException Si ocurre un error durante la comunicación
     * con el servicio REST.
     */
    @Override
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo proveedor, enviando los datos en formato JSON.
     *
     * @param requestEntity Objeto que representa los datos del nuevo proveedor.
     * @throws ClientErrorException Si ocurre un error durante la comunicación
     * con el servicio REST.
     */
    @Override
    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Obtiene una lista de todos los proveedores, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene una lista de todos los proveedores, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un
     * {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la
     * comunicación con el servicio REST.
     */
    @Override
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un proveedor por su identificador.
     *
     * @param id Identificador del proveedor a eliminar.
     * @throws ClientErrorException Si ocurre un error durante la comunicación
     * con el servicio REST.
     */
    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Cierra el cliente REST, liberando los recursos asociados.
     */
    @Override
    public void close() {
        client.close();
    }

}