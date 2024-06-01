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
@Table(name="medicines")
@Entity
public class Medicine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "price_per_unit", nullable = false)
    private Double pricePerUnit;

    /**
     * Constructor which works only with the name because it is the only column that can't be false
     * @param name -the name to be set
     */
    public Medicine(String name){
        if(name == null){
            throw new NullPointerException("Medicine name can't be null but it is! Do better!");
        } else {
            this.name = name;
        }
    }

    /**
     * Constructor to set all the parameters except the id
     * @param name - name to be set, not null
     * @param description - description to be set, can be null
     * @param unit- unit to be set , can be null
     * @param pricePerUnit - price_per_unit to be set, can be nulll
     */
    public Medicine(String name, String description, String unit, Double pricePerUnit){
        if(name == null){
            throw new NullPointerException("Medicine name can't be null but it is! Do better!");
        } else {
            this.name = name;
            this.description = description;
            this.unit = unit;
            this.pricePerUnit = pricePerUnit;
        }
    }
}
