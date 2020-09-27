package ru.ulxanxv.sharing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JoinColumn(name = "owner_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Client owner;

    @JsonIgnore
    @JoinColumn(name = "debtor_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Client debtor;

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

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Client getDebtor() {
        return debtor;
    }

    public void setDebtor(Client debtor) {
        this.debtor = debtor;
    }
}
