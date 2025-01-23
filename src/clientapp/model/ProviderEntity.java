/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@XmlRootElement
public class ProviderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String name;

    private Integer phone;

    private Date contractIni;

    private Date contractEnd;

    private Float price;

    // Constructor to initialize final fields
    public ProviderEntity(String email, String name, Integer phone, Date contractIni, Date contractEnd, Float price) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.contractIni = contractIni;
        this.contractEnd = contractEnd;
        this.price = price;
    }

    public ProviderEntity() {

    }
    
    public ProviderEntity(String name){
        this.name = name;
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

    public void setContractIni(Date contractIni) {
        this.contractIni = contractIni;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return name;
    }

}
