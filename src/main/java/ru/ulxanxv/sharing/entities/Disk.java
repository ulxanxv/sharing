package ru.ulxanxv.sharing.entities;

import javax.persistence.*;

@Entity
@Table(name = "disk")
public class Disk {

    @Id
    @TableGenerator(name = "myGen", table = "disk_sequence", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myGen")
    private Long id;

    @Column(name = "name")
    private String name;

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
