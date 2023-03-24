package com.market.list.entities;


import com.market.list.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<ProductListing> productListings;

    @ManyToMany
    private List<Account> accounts;
}
