package clientapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a movie entity that contains details about a specific movie.
 * <p>
 * The {@link MovieEntity} class holds various attributes related to a movie such as 
 * its title, duration, synopsis, release date, associated movie hour, image, 
 * tickets, provider, and categories. This class is used to model movies in the 
 * system.
 * </p>
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

    /**
     * Default constructor that initializes default values for a {@link MovieEntity}.
     */
    public MovieEntity() {
        this.title = "";
        this.duration = 0;
        this.sinopsis = "";
        this.releaseDate = null;
        this.movieHour = MovieHour.HOUR_16;
        this.provider = null;
    }

    /**
     * Constructor to create a {@link MovieEntity} with the specified title, duration, 
     * synopsis, release date, movie hour, and provider.
     * 
     * @param title the title of the movie
     * @param duration the duration of the movie in minutes
     * @param sinopsis a brief synopsis of the movie
     * @param releaseDate the release date of the movie
     * @param movieHour the scheduled movie hour
     * @param provider the provider of the movie
     */
    public MovieEntity(String title, Integer duration, String sinopsis, Date releaseDate, MovieHour movieHour, ProviderEntity provider) {
        this.title = title;
        this.duration = duration;
        this.sinopsis = sinopsis;
        this.releaseDate = releaseDate;
        this.movieHour = movieHour;
        this.provider = provider;
    }

    /**
     * Gets the ID of the movie.
     * 
     * @return the ID of the movie.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     * 
     * @param id the ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the title of the movie.
     * 
     * @return the title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     * 
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the duration of the movie.
     * 
     * @return the duration of the movie in minutes.
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the movie.
     * 
     * @param duration the duration to set in minutes.
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets the synopsis of the movie.
     * 
     * @return the synopsis of the movie.
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Sets the synopsis of the movie.
     * 
     * @param sinopsis the synopsis to set.
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Gets the release date of the movie.
     * 
     * @return the release date of the movie.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the movie.
     * 
     * @param releaseDate the release date to set.
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the movie hour associated with the movie.
     * 
     * @return the movie hour.
     */
    public MovieHour getMovieHour() {
        return movieHour;
    }

    /**
     * Sets the movie hour for the movie.
     * 
     * @param movieHour the movie hour to set.
     */
    public void setMovieHour(MovieHour movieHour) {
        this.movieHour = movieHour;
    }

    /**
     * Gets the image associated with the movie.
     * 
     * @return the movie image.
     */
    public byte[] getMovieImage() {
        return movieImage;
    }

    /**
     * Sets the image for the movie.
     * 
     * @param movieImage the image to set.
     */
    public void setMovieImage(byte[] movieImage) {
        this.movieImage = movieImage;
    }

    /**
     * Gets the list of tickets associated with the movie.
     * 
     * @return the list of tickets.
     */
    public List<TicketEntity> getTickets() {
        return tickets;
    }

    /**
     * Sets the list of tickets associated with the movie.
     * 
     * @param tickets the list of tickets to set.
     */
    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    /**
     * Gets the provider entity associated with the movie.
     * 
     * @return the provider entity.
     */
    public ProviderEntity getProvider() {
        return provider;
    }

    /**
     * Sets the provider for the movie.
     * 
     * @param provider the provider entity to set.
     */
    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    /**
     * Gets the list of categories associated with the movie.
     * 
     * @return the list of categories.
     */
    public List<CategoryEntity> getCategories() {
        return categories;
    }

    /**
     * Sets the list of categories for the movie.
     * 
     * @param categories the list of categories to set.
     */
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
