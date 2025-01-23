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
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UserEntityFacadeREST
 * [proyectorium.crud.entities.userentity]<br>
 * USAGE:
 * <pre>
 *        UserRESTClient client = new UserRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class UserRESTClient implements Signable {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/cinemapp/webresources";

    public UserRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("proyectorium.crud.entities.userentity");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).
                put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), UserEntity.class);
    }

    public <T> T find(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findRange(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T signIn(Object requestEntity, GenericType<T> responseType) throws WebApplicationException {
        try {
            // Encriptar la contraseña antes de enviarla
            UserEntity userEntity = (UserEntity) requestEntity;
            String encryptedPassword = encryptPassword(userEntity.getPassword());

            // Reemplazar la contraseña con la cifrada
            userEntity.setPassword(encryptedPassword);

            // Enviar la solicitud con la contraseña cifrada
            return webTarget.path("signIn").request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                    .post(javax.ws.rs.client.Entity.entity(userEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), responseType);
        } catch (Exception e) {
            throw new WebApplicationException("Error al cifrar la contraseña", e);
        }
    }

    public void create(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).
                post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), UserEntity.class);
    }

    public <T> T findAll(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(UserEntity.class);
    }

    private String encryptPassword(String password) throws Exception {
        // Generar la clave de sesión AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);  // AES-256
        SecretKey aesKey = keyGen.generateKey();

        // Cifrar la contraseña con AES
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedPassword = aesCipher.doFinal(password.getBytes());

        // Cifrar la clave AES con RSA
        PublicKey publicKey = loadPublicKey();
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAesKey = rsaCipher.doFinal(aesKey.getEncoded());

        // Codificar la clave AES cifrada y la contraseña cifrada a Base64
        String encryptedPasswordBase64 = java.util.Base64.getEncoder().encodeToString(encryptedPassword);
        String encryptedAesKeyBase64 = java.util.Base64.getEncoder().encodeToString(encryptedAesKey);

        // Devolver ambos valores (se pueden enviar como un objeto o un JSON)
        return encryptedPasswordBase64 + ":" + encryptedAesKeyBase64;
    }

    private PublicKey loadPublicKey() throws Exception {
        InputStream keyInputStream = new FileInputStream("C://Users/2dam/Desktop/Proyectorium-ClientApp/src/Public.key");
        if (keyInputStream == null) {
            throw new FileNotFoundException("Clave pública no encontrada.");
        }
        byte[] publicKeyBytes = readAllBytes(keyInputStream);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(keySpec);
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    public void close() {
        client.close();
    }

}
