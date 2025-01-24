package clientapp.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

@XmlRootElement
public class TicketEntity implements Serializable {

    private Integer id;
    private Date buyDate;  // Usamos Date para almacenar la fecha y hora
    private Float price;
    private Integer numPeople;
    private MovieEntity movie;
    private UserEntity user;

    // Constructor vacío
    public TicketEntity() {
    }

    // Constructor con parámetros
    public TicketEntity(Integer id, Date buyDate, Float price, Integer numPeople, MovieEntity movie, UserEntity user) {
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
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
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
