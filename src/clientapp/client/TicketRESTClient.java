package clientapp.client;

import clientapp.interfaces.ITicket;
import clientapp.model.TicketEntity;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:TicketEntityFacadeREST
 * [proyectorium.crud.entities.ticket]
 * USAGE:
 * <pre>
        TicketRESTClient client = new TicketRESTClient();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author kbilb
 */
public class TicketRESTClient implements ITicket {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/cinemapp/webresources";

    public TicketRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.ticket");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    @Override
    public List<TicketEntity> listByMovieASC(GenericType<List<TicketEntity>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-movie");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public List<TicketEntity> listByBuyDateASC(GenericType<List<TicketEntity>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-buy-date");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public void edit(TicketEntity ticket, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(ticket, javax.ws.rs.core.MediaType.APPLICATION_XML), TicketEntity.class);
    }

    @Override
    public TicketEntity find(String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(TicketEntity.class);
    }

    public <T> T findRange(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public void create(TicketEntity ticket) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(ticket, javax.ws.rs.core.MediaType.APPLICATION_XML), TicketEntity.class);
    }

    @Override
    public List<TicketEntity> listByPriceASC(GenericType<List<TicketEntity>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("by-price");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findAll(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(TicketEntity.class);
    }

    public void close() {
        client.close();
    }
}
