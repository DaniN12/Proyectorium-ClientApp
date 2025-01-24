/*
 * This class represents all the movies that will be created
 */
package clientapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author enzo
 */
@XmlRootElement
public class MovieEntity implements Serializable {

    private Integer id;

    private String title;

    private Integer duration;

    private String sinopsis;

    private Date releaseDate;

    private MovieHour movieHour;

    private byte[] movieImage;

    private List<TicketEntity> tickets;
    private ProviderEntity provider;
    private List<CategoryEntity> categories;

    public MovieEntity() {

        this.title = "";
        this.duration = 0;
        this.sinopsis = "";
        this.releaseDate = null;
        this.movieHour = MovieHour.HOUR_16;
        this.provider = new ProviderEntity();
    }

    public MovieEntity(String title, Integer duration, String sinopsis, Date releaseDate, MovieHour movieHour, ProviderEntity provider) {
        this.title = title;
        this.duration = duration;
        this.sinopsis = sinopsis;
        this.releaseDate = releaseDate;
        this.movieHour = movieHour;
        this.provider = provider;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieHour getMovieHour() {
        return movieHour;
    }

    public void setMovieHour(MovieHour movieHour) {
        this.movieHour = movieHour;
    }

    public byte[] getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(byte[] movieImage) {
        this.movieImage = movieImage;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieEntity)) {
            return false;
        }
        MovieEntity other = (MovieEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }

}
