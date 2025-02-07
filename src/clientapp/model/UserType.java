package clientapp.model;

/**
 * Enum que representa los diferentes tipos de usuarios en el sistema.
 * 
 * Existen dos tipos de usuario:
 * <ul>
 *     <li><strong>ADMIN</strong>: Representa un usuario con privilegios de administrador.</li>
 *     <li><strong>CUSTOMER</strong>: Representa un usuario normal, generalmente un cliente del sistema.</li>
 * </ul>
 * 
 * 
 * @author kbilb
 */
public enum UserType {
    
    /**
     * Representa un usuario con privilegios de administrador.
     */
    ADMIN,

    /**
     * Representa un usuario normal, generalmente un cliente del sistema.
     */
    CUSTOMER
    
}
