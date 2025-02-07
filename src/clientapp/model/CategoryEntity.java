package clientapp.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a category entity in the system, containing information about
 * a specific category, including its icon, name, description, creation date, 
 * and PEGI rating.
 * <p>
 * This class is annotated with {@link XmlRootElement} to allow it to be 
 * serialized and deserialized from XML format. It implements {@link Serializable} 
 * to allow object persistence and transfer across network or file systems.
 * </p>
 * 
 * @author 2dam
 */
@XmlRootElement
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier of the category.
     */
    private Integer id;

    /**
     * The icon representing the category in the form of a byte array.
     */
    private byte[] icon;

    /**
     * The name of the category.
     */
    private String name;

    /**
     * A description of the category.
     */
    private String description;

    /**
     * The creation date of the category.
     */
    private Date creationDate;

    /**
     * The PEGI (Pan European Game Information) rating associated with the category.
     */
    private Pegi pegi;

    /**
     * Default constructor for the CategoryEntity class.
     */
    public CategoryEntity() {
    }

    /**
     * Constructor for creating a CategoryEntity with the specified values.
     * 
     * @param icon the icon representing the category.
     * @param name the name of the category.
     * @param description a description of the category.
     * @param creationDate the creation date of the category.
     * @param pegi the PEGI rating associated with the category.
     */
    public CategoryEntity(byte[] icon, String name, String description, Date creationDate, Pegi pegi) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.pegi = pegi;
    }

    /**
     * Gets the unique identifier of the category.
     * 
     * @return the ID of the category.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the category.
     * 
     * @param id the ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the icon representing the category.
     * 
     * @return the byte array representing the category's icon.
     */
    public byte[] getIcon() {
        return icon;
    }

    /**
     * Sets the icon representing the category.
     * 
     * @param icon the byte array representing the category's icon.
     */
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    /**
     * Gets the name of the category.
     * 
     * @return the name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     * 
     * @param name the name to set for the category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the category.
     * 
     * @return the description of the category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category.
     * 
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creation date of the category.
     * 
     * @return the creation date of the category.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the category.
     * 
     * @param creationDate the creation date to set.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the PEGI rating associated with the category.
     * 
     * @return the PEGI rating of the category.
     */
    public Pegi getPegi() {
        return pegi;
    }

    /**
     * Sets the PEGI rating associated with the category.
     * 
     * @param pegi the PEGI rating to set.
     */
    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    /**
     * Returns a hash code value for this category entity. The hash code is based 
     * on the category's unique ID.
     * 
     * @return the hash code for this category entity.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Checks if this category entity is equal to another object.
     * The equality is based on the category's unique ID.
     * 
     * @param object the object to compare with.
     * @return true if this category is equal to the specified object, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CategoryEntity)) {
            return false;
        }
        CategoryEntity other = (CategoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the category entity, typically the name 
     * of the category.
     * 
     * @return a string representation of the category entity.
     */
    @Override
    public String toString() {
        return name;
    }
}
