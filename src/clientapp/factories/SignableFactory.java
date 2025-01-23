/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.factories;

import clientapp.client.UserRESTClient;
import clientapp.interfaces.Signable;

/**
 *
 * @author enzo
 */
public class SignableFactory {

    private static Signable signable;

    public static Signable getSignable() {

        if (signable == null) {
            signable = new UserRESTClient();
        }

        return signable;
    }

}
