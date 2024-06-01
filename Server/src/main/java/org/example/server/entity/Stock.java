package org.example.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= " stocks")
@Entity
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

//    @Column(name = "medicine_id", nullable = true)
//    Integer medicineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", referencedColumnName = "id", nullable = false)
    private Medicine medicine;

    @Column(name = "quantity", nullable = true)
    Integer quantity;

    @Column(name = "last_updated", nullable = true)
    Timestamp lastUpdated;

    /**
     * Constructor to set all the parameters except the id
     * @param medicine - the medicine to be set, can be null
     * @param quantity - the quantity to be set, can be null
     * @param lastUpdated - the timestamp last_update to be set, can be null
     */
    public Stock(Medicine medicine, Integer quantity, Timestamp lastUpdated) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }
}
