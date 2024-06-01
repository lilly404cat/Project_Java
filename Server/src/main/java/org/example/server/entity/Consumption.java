package org.example.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Table(name="consumption")
@Entity
public class Consumption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

    @Column(name = "quantity", nullable = true)
    Integer quantity = 0;

    @Column(name ="consumption_date", nullable = true)
    Integer consumptionDate = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", referencedColumnName = "id", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department department;

    /**
     * Constructor to set all the parameters except the id
     * @param quantity - the quantity to be set, can be null
     * @param consumptionDate - the consumption_date to be set, can be null
     * @param department - the department to be set, can be null
     * @param medicine - the medicine to be set , can be null
     */
    public Consumption(Integer quantity, Integer consumptionDate, Department department, Medicine medicine) {
        this.quantity = quantity;
        this.consumptionDate = consumptionDate;
        this.department = department;
        this.medicine = medicine;
    }
}
