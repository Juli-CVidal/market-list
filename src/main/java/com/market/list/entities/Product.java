package com.market.list.entities;

import com.market.list.enums.Measure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "Por favor ingrese un nombre")
    private String name;

    private String description;

    @Column(length = 1_000_000) //Using base64
    private String image;


    private String lastModification;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Measure measure;

    @Min(value = 0, message = "Por favor ingrese una cantidad positiva")
    private Double quantity;

    //This field is going to be used as a way for the user to control how many items needs
    @Min(value = 0, message = "Por favor ingrese una cantidad positiva")
    private Double preferredQuantity;

}
