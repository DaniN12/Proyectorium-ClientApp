/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.MovieRESTClient;
import clientapp.interfaces.IMovie;

/**
 *
 * @author enzo
 */
public class MovieFactory {

    private static IMovie imovie;

    public static IMovie getIMovie() {

        if (imovie == null) {
            imovie = new MovieRESTClient();
        }

        return imovie;
    }

}
