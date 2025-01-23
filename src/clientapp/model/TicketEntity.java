package clientapp.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlRootElement
public class TicketEntity implements Serializable {

    private Integer id;
    private String buyDate;  // Usamos String para almacenar la fecha en formato ISO sin zona horaria
    private Float price;
    private Integer numPeople;
    private MovieEntity movie;
    private UserEntity user;

    // Constructor vacío
    public TicketEntity() {
    }

    // Constructor con parámetros
    public TicketEntity(Integer id, String buyDate, Float price, Integer numPeople, MovieEntity movie, UserEntity user) {
        this.id = id;
        this.buyDate = buyDate;
        this.price = price;
        this.numPeople = numPeople;
        this.movie = movie;
        this.user = user;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    // Convertir el buyDate de String a LocalDateTime (ignorando el offset)
    public LocalDateTime getBuyDateAsLocalDateTime() {
        // Eliminar el offset (parte que sigue al '+') antes de convertir a LocalDateTime
        String dateWithoutOffset = buyDate.split("\\+")[0];
        return LocalDateTime.parse(dateWithoutOffset, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // Convertir el LocalDateTime a String
    public void setBuyDateFromLocalDateTime(LocalDateTime buyDate) {
        this.buyDate = buyDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @XmlElement
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @XmlElement
    public Integer getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }

    @XmlElement
    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    @XmlElement
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TicketEntity)) {
            return false;
        }
        TicketEntity other = (TicketEntity) object;
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "TicketEntity[id=" + id + "]";
    }

    public String getCalculatedPrice() {
        return (numPeople * price) + "€";
    }

    public String getMovieDuration() {
        return movie.getDuration() + "min";
    }
}
