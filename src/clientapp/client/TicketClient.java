package clientapp.client;

import clientapp.interfaces.ITicket;
import clientapp.model.Ticket;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:TicketEntityFacadeREST
 * [proyectorium.crud.entities.ticket]
 * USAGE:
 * <pre>
 *        TicketClient client = new TicketClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author kbilb
 */
public class TicketClient implements ITicket {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/cinemapp/webresources";

    public TicketClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.ticket");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    @Override
    public List<Ticket> listByMovieASC() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("by-movie");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(List.class);
    }

    @Override
    public List<Ticket> listByBuyDateASC() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("by-buy-date");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(List.class);
    }

    @Override
    public void edit(Ticket ticket, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(ticket, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public Ticket find(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Ticket.class);
    }

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public void create(Ticket ticket) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(ticket, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public List<Ticket> listByPriceASC() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("by-price");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(List.class);
    }

    @Override
    public List<Ticket> findAll() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(List.class);
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete();
    }

    public void close() {
        client.close();
    }
}
