package com.market.list.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

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


    @NotBlank(message = "Por favor ingrese una contraseña")
    private String password;


    @JsonIgnore
    @ManyToMany(mappedBy = "accounts")
    private List<Group> groups;

    @JsonIgnore
    public String getPassword() {
        return password;
    }


}
