package ru.ulxanxv.sharing.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "credential")
public class Credential {

    @Id
    @TableGenerator(name = "myGen", table = "credential_sequence", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myGen")
    private Long id;

    @Column(name = "name", unique = false)
    private String name;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "credential")
    private Client client;

    public Credential() {
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
