/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.model;

/**
 *
 * @author 2dam
 */
public enum Pegi {
    PEGI_3(3),
    PEGI_7(7),
    PEGI_12(12),
    PEGI_16(16),
    PEGI_18(18);
    
    private final int age;

    private Pegi(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    
    
}
