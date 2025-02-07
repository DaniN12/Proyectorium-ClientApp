package clientapp.factories;

import clientapp.client.TicketRESTClient;
import clientapp.interfaces.ITicket;

/**
 * Factory class for providing instances of {@link ITicket}.
 * <p>
 * This class ensures that only one instance of {@link ITicket} is used across the application
 * by following the Singleton design pattern. The instance is lazily initialized when 
 * {@link #getITicket()} is called for the first time.
 * </p>
 * The {@link TicketRESTClient} is used to fulfill the {@link ITicket} interface.
 */
public class TicketFactory {

    /**
     * The instance of {@link ITicket} that is shared across the application.
     */
    private static ITicket iTicket;

    /**
     * Returns the single instance of {@link ITicket}.
     * <p>
     * If the instance does not already exist, it initializes the instance 
     * using the {@link TicketRESTClient}.
     * </p>
     * 
     * @return an instance of {@link ITicket}.
     */
    public static ITicket getITicket() {
        if (iTicket == null) {
            iTicket = new TicketRESTClient();
        }
        return iTicket;
    }
}
