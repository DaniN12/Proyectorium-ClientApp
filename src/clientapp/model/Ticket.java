/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kbilb
 */
public class Ticket {

    public class TicketEntity implements Serializable {

        private Integer id;

        private Date buyDate;

        private Float price;

        private Integer numPeople;

        private Movie movie;

        private User user;

        private byte[] movieImage;

        public TicketEntity() {

        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Date getBuyDate() {
            return buyDate;
        }

        public void setBuyDate(Date buyDate) {
            this.buyDate = buyDate;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public Integer getNumPeople() {
            return numPeople;
        }

        public void setNumPeople(Integer numPeople) {
            this.numPeople = numPeople;
        }

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public byte[] getMovieImage() {
            return movieImage;
        }

        public void setMovieImage(byte[] movieImage) {
            this.movieImage = movieImage;
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
            if (!(object instanceof TicketEntity)) {
                return false;
            }
            TicketEntity other = (TicketEntity) object;
            if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "proyectorium.crud.entities.TicketEntity[ id = " + id + " ]";
        }
    }

}
