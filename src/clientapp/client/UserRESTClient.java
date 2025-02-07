/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.client;

import clientapp.interfaces.Signable;
import clientapp.model.UserEntity;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UserEntityFacadeREST
 * [proyectorium.crud.entities.userentity]<br>
 * This client handles operations related to the User entity in the system.
 * It allows signing in, creating new users, editing existing users, and retrieving user data.
 * <br>
 * USAGE:
 * <pre>
 *        UserRESTClient client = new UserRESTClient();
 *        Object response = client.XXX(...);
 *        // perform operations
 *        client.close();
 * </pre>
 * 
 * This client also supports password encryption using RSA before sending the request to the server.
 *
 * @author 2dam
 */
public class UserRESTClient implements Signable {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/cinemapp/webresources";

    /**
     * Constructor that initializes the Jersey client and sets the base URI for the service.
     */
    public UserRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.userentity");
    }

    /**
     * Counts the number of User entities available in the system.
     * 
     * @return The number of users as a String.
     * @throws ClientErrorException If a client-side error occurs.
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Edits the details of a specific user identified by its ID.
     * 
     * @param requestEntity The UserEntity object with updated data.
     * @param id The ID of the user to edit.
     * @throws WebApplicationException If a server-side error occurs during the update.
     */
    public void edit(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), UserEntity.class);
    }

    /**
     * Finds a User by its ID.
     * 
     * @param responseType The response type to handle the result.
     * @param id The ID of the user to retrieve.
     * @param <T> The response type.
     * @return The UserEntity found for the given ID.
     * @throws WebApplicationException If the user is not found or a server-side error occurs.
     */
    public <T> T find(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Retrieves a range of users, specified by the from and to parameters.
     * 
     * @param responseType The response type to handle the result.
     * @param from The starting index of the user range.
     * @param to The ending index of the user range.
     * @param <T> The response type.
     * @return A list of users in the specified range.
     * @throws WebApplicationException If a server-side error occurs or if the range is invalid.
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Signs in a user by encrypting their password before sending the request to the server.
     * 
     * @param requestEntity The UserEntity object with login details (including password).
     * @param responseType The response type to handle the result.
     * @param <T> The response type.
     * @return The response from the server after signing in.
     * @throws WebApplicationException If an error occurs during the sign-in process.
     */
    public <T> T signIn(Object requestEntity, GenericType<T> responseType) throws WebApplicationException {
        try {
            // Encrypt the password before sending it
            UserEntity userEntity = (UserEntity) requestEntity;
            String encryptedPassword = encryptPassword(userEntity.getPassword());

            // Replace the original password with the encrypted one
            userEntity.setPassword(encryptedPassword);

            // Send the request with the encrypted password
            return webTarget.path("signIn").request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                    .post(javax.ws.rs.client.Entity.entity(userEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), responseType);
        } catch (Exception e) {
            throw new WebApplicationException("Error encrypting password", e);
        }
    }

    /**
     * Creates a new user by encrypting their password before sending the request to the server.
     * 
     * @param requestEntity The UserEntity object with the new user details.
     * @throws WebApplicationException If an error occurs during user creation.
     */
    public void create(Object requestEntity) throws WebApplicationException {
        try {
            // Encrypt the password before sending it
            UserEntity userEntity = (UserEntity) requestEntity;
            String encryptedPassword = encryptPassword(userEntity.getPassword());

            // Replace the original password with the encrypted one
            userEntity.setPassword(encryptedPassword);

            // Send the request with the encrypted password
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                    .post(javax.ws.rs.client.Entity.entity(userEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), UserEntity.class);
        } catch (Exception e) {
            throw new WebApplicationException("Error encrypting password", e);
        }
    }

    /**
     * Retrieves all users from the system.
     * 
     * @param responseType The response type to handle the result.
     * @param <T> The response type.
     * @return A list of all users in the system.
     * @throws WebApplicationException If a server-side error occurs.
     */
    public <T> T findAll(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Removes a user from the system by its ID.
     * 
     * @param id The ID of the user to be removed.
     * @throws WebApplicationException If an error occurs during the removal process.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(UserEntity.class);
    }

    /**
     * Loads the public key from the resources to be used for encrypting the password.
     * 
     * @return The public key.
     * @throws Exception If an error occurs while loading the public key.
     */
    private PublicKey loadPublicKey() throws Exception {
        byte[] publicKeyBytes;
        try (InputStream keyInputStream = UserRESTClient.class.getResourceAsStream("Public.key");
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (keyInputStream == null) {
                throw new FileNotFoundException("No public key file found.");
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = keyInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            publicKeyBytes = baos.toByteArray();
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * Encrypts the user's password using RSA encryption.
     * 
     * @param password The password to be encrypted.
     * @return The encrypted password encoded in Base64 format.
     * @throws Exception If an error occurs during encryption.
     */
    private String encryptPassword(String password) throws Exception {
        // Load the public key
        PublicKey publicKey = loadPublicKey();

        // Encrypt the password
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedPassword = cipher.doFinal(password.getBytes());

        // Encode the encrypted password in Base64 for transmission
        return java.util.Base64.getEncoder().encodeToString(encryptedPassword);
    }

    /**
     * Closes the client and releases any resources.
     */
    public void close() {
        client.close();
    }
}
