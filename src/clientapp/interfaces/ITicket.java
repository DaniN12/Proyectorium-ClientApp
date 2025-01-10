/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.interfaces;

import clientapp.model.Ticket;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface ITicket {
    
    public List<Ticket> listByMovieASC(GenericType<List<Ticket>> responseType) throws ClientErrorException;
    
    public List<Ticket> listByBuyDateASC(GenericType<List<Ticket>> responseType) throws ClientErrorException;

    public void edit(Ticket ticket, String id) throws ClientErrorException;

    public Ticket find(String id) throws ClientErrorException;

    public void create(Ticket ticket) throws ClientErrorException;

    public List<Ticket> listByPriceASC(GenericType<List<Ticket>> responseType) throws ClientErrorException;

    public List<Ticket> findAll(GenericType<List<Ticket>> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;
    
}
