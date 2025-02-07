/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.ProviderEntity;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Dani
 * @version 1.0
 * @see ProviderEntity
 * @see WebApplicationException
 */
public interface IProvider {

    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a finalizar, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T listByContractEnd_XML(GenericType<T> responseType) throws WebApplicationException;
    
    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a finalizar, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T listByContractEnd_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a iniciar, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T listByContractInit_XML(GenericType<T> responseType) throws WebApplicationException;
    
    /**
     * Obtiene una lista de proveedores cuyo contrato está próximo a iniciar, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T listByContractInit_JSON(GenericType<T> responseType) throws WebApplicationException;
    
    /**
     * Obtiene el número total de proveedores registrados en el sistema.
     *
     * @return Número total de proveedores en formato de texto.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public String countREST() throws WebApplicationException;

    /**
     * Edita la información de un proveedor existente, enviando los datos en formato XML.
     *
     * @param requestEntity Objeto que representa los datos del proveedor a editar.
     * @param id Identificador del proveedor a editar.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;
    
    /**
     * Edita la información de un proveedor existente, enviando los datos en formato JSON.
     *
     * @param requestEntity Objeto que representa los datos del proveedor a editar.
     * @param id Identificador del proveedor a editar.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Busca un proveedor por su identificador, devolviendo la información en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @param id Identificador del proveedor a buscar.
     * @return Información del proveedor en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T find_XML(GenericType<T> responseType, String id) throws WebApplicationException;
    
    /**
     * Busca un proveedor por su identificador, devolviendo la información en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @param id Identificador del proveedor a buscar.
     * @return Información del proveedor en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T find_JSON(GenericType<T> responseType, String id) throws WebApplicationException;
    
    /**
     * Obtiene una lista de proveedores dentro de un rango específico, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T findRange_XML(GenericType<T> responseType, String from, String to) throws WebApplicationException;
    
    /**
     * Obtiene una lista de proveedores dentro de un rango específico, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T findRange_JSON(GenericType<T> responseType, String from, String to) throws WebApplicationException;
    
    /**
     * Obtiene una lista de proveedores ordenados por precio, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T listByPrice_XML(GenericType<T> responseType) throws WebApplicationException;
    
    /**
     * Obtiene una lista de proveedores ordenados por precio, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T listByPrice_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Crea un nuevo proveedor, enviando los datos en formato XML.
     *
     * @param requestEntity Objeto que representa los datos del nuevo proveedor.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;
    
    /**
     * Crea un nuevo proveedor, enviando los datos en formato JSON.
     *
     * @param requestEntity Objeto que representa los datos del nuevo proveedor.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Obtiene una lista de todos los proveedores, en formato XML.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Obtiene una lista de todos los proveedores, en formato JSON.
     *
     * @param <T> Tipo genérico de la respuesta.
     * @param responseType Tipo de la respuesta, encapsulado en un {@link GenericType}.
     * @return Lista de proveedores en formato JSON.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Elimina un proveedor por su identificador.
     *
     * @param id Identificador del proveedor a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la comunicación con el servicio REST.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Cierra el cliente REST, liberando los recursos asociados.
     */
    public void close();
}