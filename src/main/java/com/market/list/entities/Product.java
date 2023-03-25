package com.market.list.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Por favor ingrese un nombre")
    private String name;

    @Min(value = 0, message = "Por favor ingrese una cantidad positiva")
    private Integer quantity;

    private String description;

    @Column(length= 1_000_000) //Using base64
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;

}
