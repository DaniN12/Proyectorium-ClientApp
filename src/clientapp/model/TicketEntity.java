package clientapp.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.collections.ObservableList;

/**
 * Clase que representa un ticket de cine, incluyendo información como el ID, 
 * la fecha de compra, el precio, el número de personas, la película asociada y el usuario.
 * Esta clase se utiliza para representar los tickets vendidos a los usuarios.
 * <p>
 * La clase incluye métodos para calcular el precio total del ticket, obtener la 
 * duración de la película y acceder a los detalles de la película y el usuario.
 * </p>
 * 
 * @author 2dam
 */
@XmlRootElement
public class TicketEntity implements Serializable {

    private Integer id;
    private Date buyDate;
    private Float price;
    private Integer numPeople;
    private MovieEntity movie;
    private UserEntity user;

    /**
     * Constructor vacío de la clase {@link TicketEntity}.
     * Se utiliza para crear un objeto vacío de {@link TicketEntity}.
     */
    public TicketEntity() {
    }

    /**
     * Constructor predeterminado que inicializa un ticket con valores predeterminados.
     * 
     * @param listMovies la lista de películas de las cuales se seleccionará la primera.
     */
    public TicketEntity(ObservableList<MovieEntity> listMovies) {
        this.buyDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.numPeople = 0;
        this.price = 7.5f;
        this.movie = listMovies.get(0);
    }

    /**
     * Constructor de la clase {@link TicketEntity} con parámetros.
     * 
     * @param id el ID del ticket.
     * @param buyDate la fecha de compra del ticket.
     * @param price el precio del ticket.
     * @param numPeople el número de personas para las cuales se compra el ticket.
     * @param movie la película asociada al ticket.
     * @param user el usuario que compró el ticket.
     */
    public TicketEntity(Integer id, Date buyDate, Float price, Integer numPeople, MovieEntity movie, UserEntity user) {
        this.id = id;
        this.buyDate = buyDate;
        this.price = price;
        this.numPeople = numPeople;
        this.movie = movie;
        this.user = user;
    }

    /**
     * Obtiene el ID del ticket.
     * 
     * @return el ID del ticket.
     */
    @XmlElement
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del ticket.
     * 
     * @param id el ID del ticket.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha de compra del ticket.
     * 
     * @return la fecha de compra del ticket.
     */
    @XmlElement
    public Date getBuyDate() {
        return buyDate;
    }

    /**
     * Establece la fecha de compra del ticket.
     * 
     * @param buyDate la fecha de compra del ticket.
     */
    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    /**
     * Obtiene el precio del ticket.
     * 
     * @return el precio del ticket.
     */
    @XmlElement
    public Float getPrice() {
        return price;
    }

    /**
     * Establece el precio del ticket.
     * 
     * @param price el precio del ticket.
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Obtiene el número de personas asociadas al ticket.
     * 
     * @return el número de personas para el ticket.
     */
    @XmlElement
    public Integer getNumPeople() {
        return numPeople;
    }

    /**
     * Establece el número de personas asociadas al ticket.
     * 
     * @param numPeople el número de personas para el ticket.
     */
    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }

    /**
     * Obtiene la película asociada al ticket.
     * 
     * @return la película asociada al ticket.
     */
    @XmlElement
    public MovieEntity getMovie() {
        return movie;
    }

    /**
     * Establece la película asociada al ticket.
     * 
     * @param movie la película asociada al ticket.
     */
    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    /**
     * Obtiene el usuario que compró el ticket.
     * 
     * @return el usuario que compró el ticket.
     */
    @XmlElement
    public UserEntity getUser() {
        return user;
    }

    /**
     * Establece el usuario que compró el ticket.
     * 
     * @param user el usuario que compró el ticket.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Calcula el precio total del ticket en función del número de personas y el precio unitario.
     * 
     * @return el precio total en formato de texto con el símbolo de euro.
     */
    public String getCalculatedPrice() {
        return (numPeople * price) + "€";
    }

    /**
     * Obtiene la duración de la película asociada al ticket.
     * 
     * @return la duración de la película en minutos.
     */
    public String getMovieDuration() {
        return movie.getDuration() + "min";
    }

    /**
     * Obtiene el título de la película asociada al ticket.
     * 
     * @return el título de la película.
     */
    public String getMovieTitle() {
        return movie.getTitle();
    }

    /**
     * Calcula el código hash del ticket, basado en su ID.
     * 
     * @return el valor de hash basado en el ID.
     */
    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    /**
     * Compara este ticket con otro objeto. Dos tickets son iguales si tienen el mismo ID.
     * 
     * @param object el objeto con el cual comparar.
     * @return {@code true} si los tickets tienen el mismo ID, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TicketEntity)) {
            return false;
        }
        TicketEntity other = (TicketEntity) object;
        return this.id != null && this.id.equals(other.id);
    }

    /**
     * Representación en cadena del ticket.
     * 
     * @return una cadena representando el ticket.
     */
    @Override
    public String toString() {
        return "TicketEntity[id=" + id + "]";
    }
}
