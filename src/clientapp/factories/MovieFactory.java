package clientapp.factories;

import clientapp.client.MovieRESTClient;
import clientapp.interfaces.IMovie;

/**
 * Factory class for creating and providing an instance of the IMovie interface.
 * <p>
 * The MovieFactory class ensures that a single instance of the IMovie interface 
 * is used throughout the application. If the instance has not been created yet, 
 * it will instantiate a new MovieRESTClient to fulfill the IMovie interface contract.
 * </p>
 */
public class MovieFactory {

    /**
     * A static instance of the IMovie interface.
     */
    private static IMovie imovie;

    /**
     * Returns the instance of the IMovie interface.
     * <p>
     * If the instance of IMovie has not been created yet, a new instance of
     * MovieRESTClient will be instantiated and returned.
     * </p>
     * 
     * @return the instance of IMovie.
     */
    public static IMovie getIMovie() {

        if (imovie == null) {
            imovie = new MovieRESTClient();
        }

        return imovie;
    }

}
