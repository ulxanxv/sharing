package ru.ulxanxv.sharing.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Table(name = "disk")
public class Disk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @JoinColumn(name = "first_owner_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Client firstOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getFirstOwner() {
        return firstOwner;
    }

    public void setFirstOwner(Client firstOwner) {
        this.firstOwner = firstOwner;
    }
}
