package clientapp.factories;

import clientapp.client.UserRESTClient;
import clientapp.interfaces.Signable;

/**
 * Factory class for creating and providing an instance of the Signable interface.
 * <p>
 * The SignableFactory class ensures that a single instance of the Signable interface 
 * is used throughout the application. If the instance has not been created yet, 
 * it will instantiate a new UserRESTClient to fulfill the Signable interface contract.
 * </p>
 */
public class SignableFactory {

    /**
     * A static instance of the Signable interface.
     */
    private static Signable signable;

    /**
     * Returns the instance of the Signable interface.
     * <p>
     * If the instance of Signable has not been created yet, a new instance of
     * UserRESTClient will be instantiated and returned.
     * </p>
     * 
     * @return the instance of Signable.
     */
    public static Signable getSignable() {

        if (signable == null) {
            signable = new UserRESTClient();
        }

        return signable;
    }

}
