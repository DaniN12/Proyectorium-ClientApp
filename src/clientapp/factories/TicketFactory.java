/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.TicketRESTClient;
import clientapp.interfaces.ITicket;

/**
 *
 * @author kbilb
 */
public class TicketFactory {

    // The instance is static to ensure a single instance across the application.
    private static ITicket iTicket;

    /**
     * Returns an instance of ITicket.
     * If it does not already exist, it initializes the instance with a new TicketRESTClient.
     *
     * @return an instance of ITicket
     */
    public static ITicket getITicket() {
        if (iTicket == null) {
            iTicket = new TicketRESTClient();
        }
        return iTicket;
    }
}
