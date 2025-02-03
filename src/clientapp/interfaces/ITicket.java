/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.TicketEntity;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author 2dam
 */
public interface ITicket {
    
    public List<TicketEntity> listByMovieASC(GenericType<List<TicketEntity>> responseType) throws WebApplicationException;
    
    public List<TicketEntity> listByBuyDateASC(GenericType<List<TicketEntity>> responseType) throws WebApplicationException;

    public void edit(TicketEntity ticket, String id) throws WebApplicationException;

    public TicketEntity find(GenericType<TicketEntity> responseType, String id) throws ClientErrorException;

    public void create(TicketEntity ticket) throws WebApplicationException;

    public List<TicketEntity> listByPriceASC(GenericType<List<TicketEntity>> responseType) throws WebApplicationException;
    
    public List<TicketEntity> findAll(GenericType<List<TicketEntity>> responseType) throws WebApplicationException;

    public void remove(String id) throws WebApplicationException;
    
}
