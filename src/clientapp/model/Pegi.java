package clientapp.model;

/**
 * Enum que representa las clasificaciones de edad PEGI (Pan European Game Information) 
 * asociadas a los videojuegos y películas.
 * <p>
 * El enum {@link Pegi} contiene las diferentes clasificaciones de edad que indican 
 * la idoneidad de contenido para diferentes grupos de edad. Estas clasificaciones son:
 * PEGI 3, PEGI 7, PEGI 12, PEGI 16, y PEGI 18.
 * </p>
 * 
 * @author 2dam
 */
public enum Pegi {

    /**
     * Clasificación PEGI 3: Contenido apto para todas las edades a partir de 3 años.
     */
    PEGI_3(3),

    /**
     * Clasificación PEGI 7: Contenido apto para niños a partir de 7 años.
     */
    PEGI_7(7),

    /**
     * Clasificación PEGI 12: Contenido apto para personas a partir de 12 años.
     */
    PEGI_12(12),

    /**
     * Clasificación PEGI 16: Contenido apto para personas a partir de 16 años.
     */
    PEGI_16(16),

    /**
     * Clasificación PEGI 18: Contenido apto para personas a partir de 18 años.
     */
    PEGI_18(18);

    private final int age;

    /**
     * Constructor que inicializa la clasificación de edad PEGI.
     * 
     * @param age la edad mínima recomendada para el contenido.
     */
    private Pegi(int age) {
        this.age = age;
    }

    /**
     * Obtiene la edad mínima recomendada para el contenido clasificado con el valor {@link Pegi}.
     * 
     * @return la edad mínima asociada a la clasificación PEGI.
     */
    public int getAge() {
        return age;
    }
}
