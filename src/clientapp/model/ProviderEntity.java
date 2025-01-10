/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import java.util.Date;


/**
 *
 * @author 2dam
 */
public class ProviderEntity {
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String email;

    private  String name;

    private  Integer phone;
    
    private  Date contractIni;

    private  Date contractEnd;

    private  Float price;

    
   // private List<MovieEntity> movies;

     // Constructor to initialize final fields
    public ProviderEntity(String email, String name, Integer phone, Date contractIni, Date contractEnd, Float price) {
        this.email = new String (email);
        this.name = new String (name);
        this.phone = new Integer (phone);
        this.contractIni = new Date (contractIni.getTime());
        this.contractEnd = new Date (contractEnd.getTime());
        this.price = new Float (price);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Date getContractIni() {
        return contractIni;
    }

    public void setContractIni(Date contactIni) {
        this.contractIni = contactIni;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contactEnd) {
        this.contractEnd = contactEnd;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

   /* @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryEntity)) {
            return false;
        }
        CategoryEntity other = (CategoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }*/

    @Override
    public String toString() {
        return "proyectorium.crud.entities.ProviderEntity[ id=" + id + " ]";
    }

}

