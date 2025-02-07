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
 * @author Dani
 * @version 1.0
 * @see Serializable
 * @see XmlRootElement
 */
@XmlRootElement
public class ProviderEntity implements Serializable {

    /**
     * Identificador único para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del proveedor.
     */
    private Long id;

    /**
     * Correo electrónico del proveedor.
     */
    private String email;

    /**
     * Nombre del proveedor.
     */
    private String name;

    /**
     * Número de teléfono del proveedor.
     */
    private Integer phone;

    /**
     * Fecha de inicio del contrato del proveedor.
     */
    private Date contractIni;

    /**
     * Fecha de fin del contrato del proveedor.
     */
    private Date contractEnd;

    /**
     * Precio del servicio ofrecido por el proveedor.
     */
    private Float price;

    /**
     * Constructor para inicializar un proveedor con todos sus atributos.
     *
     * @param email Correo electrónico del proveedor.
     * @param name Nombre del proveedor.
     * @param phone Número de teléfono del proveedor.
     * @param contractIni Fecha de inicio del contrato.
     * @param contractEnd Fecha de fin del contrato.
     * @param price Precio del servicio.
     */
    public ProviderEntity(String email, String name, Integer phone, Date contractIni, Date contractEnd, Float price) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.contractIni = contractIni;
        this.contractEnd = contractEnd;
        this.price = price;
    }

    /**
     * Constructor por defecto.
     */
    public ProviderEntity() {
    }

    /**
     * Constructor para inicializar un proveedor solo con su nombre.
     *
     * @param name Nombre del proveedor.
     */
    public ProviderEntity(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del proveedor.
     *
     * @return Correo electrónico del proveedor.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del proveedor.
     *
     * @param email Correo electrónico del proveedor.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre del proveedor.
     *
     * @return Nombre del proveedor.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del proveedor.
     *
     * @param name Nombre del proveedor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el número de teléfono del proveedor.
     *
     * @return Número de teléfono del proveedor.
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     * Establece el número de teléfono del proveedor.
     *
     * @param phone Número de teléfono del proveedor.
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     * Obtiene la fecha de inicio del contrato del proveedor.
     *
     * @return Fecha de inicio del contrato.
     */
    public Date getContractIni() {
        return contractIni;
    }

    /**
     * Establece la fecha de inicio del contrato del proveedor.
     *
     * @param contractIni Fecha de inicio del contrato.
     */
    public void setContractIni(Date contractIni) {
        this.contractIni = contractIni;
    }

    /**
     * Obtiene la fecha de fin del contrato del proveedor.
     *
     * @return Fecha de fin del contrato.
     */
    public Date getContractEnd() {
        return contractEnd;
    }

    /**
     * Establece la fecha de fin del contrato del proveedor.
     *
     * @param contractEnd Fecha de fin del contrato.
     */
    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    /**
     * Obtiene el precio del servicio ofrecido por el proveedor.
     *
     * @return Precio del servicio.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Establece el precio del servicio ofrecido por el proveedor.
     *
     * @param price Precio del servicio.
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Obtiene el identificador único del proveedor.
     *
     * @return Identificador único del proveedor.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del proveedor.
     *
     * @param id Identificador único del proveedor.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Calcula el código hash para el proveedor basado en su identificador único.
     *
     * @return Código hash del proveedor.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compara este proveedor con otro objeto para determinar si son iguales.
     * Dos proveedores son iguales si tienen el mismo identificador único.
     *
     * @param object Objeto a comparar.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProviderEntity)) {
            return false;
        }
        ProviderEntity other = (ProviderEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve una representación en cadena del proveedor.
     *
     * @return Nombre del proveedor como cadena.
     */
    @Override
    public String toString() {
        return name;
    }
}
