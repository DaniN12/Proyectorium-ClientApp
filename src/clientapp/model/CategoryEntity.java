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
public class CategoryEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private byte[] icon;
    private String name;
    private String description;
    private Date creationDate;
    private Pegi pegi;

    public CategoryEntity(Integer id, byte[] icon, String name, String description, Date creationDate, Pegi pegi) {
        this.id= id;
        this.icon= icon;
        this.name= name;
        this.description= description;
        this.creationDate= creationDate;
        this.pegi= pegi;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }


  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
    @Override
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
    }
*/
    
    @Override
    public String toString() {
        return "proyectorium.crud.entities.CategoryEntity[ id=" + id + " ]";
    }

}
