package org.example.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "departments")
@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Constructor which sets the name, it can't be null so i make a verification and throw an exception
     * @param name - name to be set, not null
     */
    public Department(String name) {
        if(name == null){
            throw new NullPointerException("Medicine name can't be null but it is! Do better!");
        } else {
            this.name = name;
        }
    }
}
