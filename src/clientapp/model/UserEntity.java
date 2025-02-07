package clientapp.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase que representa a un usuario del sistema. Contiene información como su ID, 
 * correo electrónico, nombre completo, contraseña, dirección, ciudad, código postal, 
 * ID de la empresa a la que pertenece, tipo de usuario y los tickets asociados al usuario.
 * <p>
 * La clase es utilizada para gestionar los datos relacionados con los usuarios del sistema.
 * </p>
 * 
 * @author kbilb
 */
@XmlRootElement
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String fullName;

    private String password;

    private String street;

    private String city;

    private Integer zip;

    private Integer companyId;

    private UserType userType;

    private List<TicketEntity> tickets;

    /**
     * Obtiene el ID del usuario.
     * 
     * @return el ID del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     * 
     * @param id el ID del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return el correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param email el correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre completo del usuario.
     * 
     * @return el nombre completo del usuario.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Establece el nombre completo del usuario.
     * 
     * @param fullName el nombre completo del usuario.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param password la contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la calle en la que vive el usuario.
     * 
     * @return la calle en la que vive el usuario.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Establece la calle en la que vive el usuario.
     * 
     * @param street la calle en la que vive el usuario.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Obtiene la ciudad en la que vive el usuario.
     * 
     * @return la ciudad en la que vive el usuario.
     */
    public String getCity() {
        return city;
    }

    /**
     * Establece la ciudad en la que vive el usuario.
     * 
     * @param city la ciudad en la que vive el usuario.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtiene el código postal del usuario.
     * 
     * @return el código postal del usuario.
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Establece el código postal del usuario.
     * 
     * @param zip el código postal del usuario.
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Obtiene el ID de la empresa a la que pertenece el usuario.
     * 
     * @return el ID de la empresa a la que pertenece el usuario.
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * Establece el ID de la empresa a la que pertenece el usuario.
     * 
     * @param companyId el ID de la empresa a la que pertenece el usuario.
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * Obtiene el tipo de usuario.
     * 
     * @return el tipo de usuario.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Establece el tipo de usuario.
     * 
     * @param userType el tipo de usuario.
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Obtiene la lista de tickets asociados al usuario.
     * 
     * @return la lista de tickets asociados al usuario.
     */
    @XmlTransient
    public List<TicketEntity> getTickets() {
        return tickets;
    }

    /**
     * Establece la lista de tickets asociados al usuario.
     * 
     * @param tickets la lista de tickets asociados al usuario.
     */
    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    /**
     * Calcula el código hash del usuario, basado en su ID.
     * 
     * @return el valor de hash basado en el ID.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compara este usuario con otro objeto. Dos usuarios son iguales si tienen el mismo ID.
     * 
     * @param object el objeto con el cual comparar.
     * @return {@code true} si los usuarios tienen el mismo ID, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Representación en cadena del usuario. En este caso, el correo electrónico del usuario.
     * 
     * @return una cadena representando al usuario, en este caso el correo electrónico.
     */
    @Override
    public String toString() {
        return email;
    }

}
