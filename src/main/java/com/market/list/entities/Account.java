package com.market.list.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Por favor ingrese su nombre")
    @Column(unique = true, nullable = false)
    private String name;

    //               [a-zA-Z0-9._%+-] at least one alphanumeric (or special) character before '@'
    //               [a-zA-Z0-9.-] similar as the previous,but before '@', alphanumeric or '-'
    //               \. a point '.' is needed
    //               at least two characters after the point
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe ser 'nombre@dominio.com'")
    @NotBlank(message = "Por favor ingrese un email válido")
    @Column(unique = true, nullable = false)
    private String email;

    //                ^(?=.* [A-Z]) checks if the string contains at least one uppercase letter
    //                (?=.*\d) checks if the string contains at least one digit (0-9)
    //                [A-Za-z\d] permits any uppercase/lowercase/digits
    //                {8,} checks if the string contains at least eight characters
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener un mínimo de ocho caracteres, una letra mayúscula y un número")
    @NotBlank(message = "Por favor ingrese una contraseña")
    private String password;

    @ManyToMany(mappedBy = "accounts")
    private List<Group> groups;
}
