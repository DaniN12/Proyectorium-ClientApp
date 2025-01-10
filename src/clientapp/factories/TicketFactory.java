/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.TicketClient;
import clientapp.interfaces.ITicket;

/**
 *
 * @author kbilb
 */
public class TicketFactory {
    
    public static ITicket getTicketFactory() {
        
        return new TicketClient();
        
    }
    
}
