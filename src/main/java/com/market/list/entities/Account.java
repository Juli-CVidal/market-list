package com.market.list.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.market.list.enums.Provider;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Provider provider;


    @NotBlank(message = "Por favor ingrese su nombre")
    @Column(nullable = false)
    private String name;

    //               [a-zA-Z0-9._%+-] at least one alphanumeric (or special) character before '@'
    //               [a-zA-Z0-9.-] similar as the previous,but before '@', alphanumeric or '-'
    //               \. a point '.' is needed
    //               at least two characters after the point
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe ser 'nombre@dominio.com'")
    @NotBlank(message = "Por favor ingrese un email válido")
    @Column(unique = true, nullable = false)
    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Por favor ingrese una contraseña")
    private String password;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "accounts")
    private List<Group> groups;


}
