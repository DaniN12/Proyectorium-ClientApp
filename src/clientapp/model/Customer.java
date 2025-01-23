/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import java.io.Serializable;

/**
 *
 * @author kbilb
 */
public class Customer extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer numTickets;
    
    public Customer(){
        super();
    }

    public Integer getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(Integer numTickets) {
        this.numTickets = numTickets;
    }

}
