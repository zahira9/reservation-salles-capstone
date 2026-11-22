package com.example.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salles")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private int capacite;
    private String description;
    @Version
    private int version;
    @OneToMany(mappedBy = "salle")
    private List<Reservation> reservations = new ArrayList<>();

    public Salle() {}
    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getVersion() { return version; }
    public List<Reservation> getReservations() { return reservations; }
}
