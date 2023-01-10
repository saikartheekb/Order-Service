package com.assignment.orderservice.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "offer_name", nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false)
    private double discountPercentage;

    // Eligibility based on number of items
    @Column(name = "minimum_orders", nullable = false)
    private int minimumOrders;

    @Column(name = "maximum_orders")
    private int maximumOrders;

    @Column(name = "start_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "is_enabled", columnDefinition = "bit(1) default 0", nullable = false)
    private boolean enabled;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "offer_order",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orders;
}
