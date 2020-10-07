package ru.ulxanxv.sharing.models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "taken_item")
public class TakenItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "disk_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Disk disk;

    @JoinColumn(name = "owner_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Client owner;

    @JoinColumn(name = "debtor_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Client debtor;

    @Column(name = "is_free")
    private boolean isFree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(@NotNull Disk disk) {
        this.disk = disk;
        this.isFree = (debtor == null);
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
        this.isFree = (debtor == null);
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
