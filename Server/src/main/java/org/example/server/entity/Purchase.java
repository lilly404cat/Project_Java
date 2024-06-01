package org.example.server.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
@Table(name="purchases")
@Entity
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

    @Column(name = "quantity", nullable = true)
    Integer quantity;

    @Column(name = "price", nullable = true)
    Double price;

    @Column(name = "purchase_date", nullable = true)
    Date purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", referencedColumnName = "id", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id ", referencedColumnName = "id", nullable = false)
    private Supplier supplier;

    /**
     * Constructor to set all the parameters except the id
     * @param quantity - the quantity to be set, can be null
     * @param price - the price to be set, can be null
     * @param purchaseDate - the purchase_date to be set, can be null
     * @param medicine - the medicine to be set, can be null
     */
    public Purchase(Integer quantity, Double price, Date purchaseDate, Medicine medicine) {
        this.quantity = quantity;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.medicine = medicine;
    }
}
