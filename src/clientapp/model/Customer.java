package clientapp.model;

import java.io.Serializable;

/**
 * Represents a customer in the system, extending the {@link UserEntity} class.
 * This class adds functionality for tracking the number of tickets associated 
 * with the customer.
 * <p>
 * The {@link Customer} class inherits from {@link UserEntity}, which represents 
 * a basic user in the system. In addition, it introduces an attribute to store 
 * the number of tickets the customer has purchased or is associated with.
 * </p>
 * 
 * @author kbilb
 */
public class Customer extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The number of tickets associated with the customer.
     */
    private Integer numTickets;

    /**
     * Default constructor for the {@link Customer} class. 
     * It invokes the constructor of {@link UserEntity}.
     */
    public Customer(){
        super();
    }

    /**
     * Gets the number of tickets associated with the customer.
     * 
     * @return the number of tickets.
     */
    public Integer getNumTickets() {
        return numTickets;
    }

    /**
     * Sets the number of tickets associated with the customer.
     * 
     * @param numTickets the number of tickets to set.
     */
    public void setNumTickets(Integer numTickets) {
        this.numTickets = numTickets;
    }

}
