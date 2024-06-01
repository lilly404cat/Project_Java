package org.example.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
@Table(name = "suppliers")
@Entity
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_info", nullable = true)
    private String contactInfo;

    /**
     * Constructor to set all the parameters except the id
     * @param name - the name to be set , it can't be null
     * @param contactInfo - the contact_info which can be null
     */
    public Supplier(String name, String contactInfo) {
        if(name == null){
            throw new NullPointerException("Medicine name can't be null but it is! Do better!");
        } else {
            this.name = name;
            this.contactInfo = contactInfo;
        }
    }

    /**
     * Constructor which sets the name, it can't be null so i make a verification and throw an exception
     * @param name - name to be set, not null
     */
    public Supplier(String name) {
        if(name == null){
            throw new NullPointerException("Medicine name can't be null but it is! Do better!");
        } else {
            this.name = name;
        }
    }
}
