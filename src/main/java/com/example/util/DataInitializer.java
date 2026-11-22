package com.example.util;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.model.Utilisateur;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class DataInitializer {
    private final EntityManager em;
    public DataInitializer(EntityManager em) { this.em = em; }

    public void initData() {
        em.getTransaction().begin();
        for (int i = 1; i <= 5; i++) {
            Utilisateur user = new Utilisateur("Utilisateur " + i,
                    "user" + i + "@example.com");
            em.persist(user);
            Salle salle = new Salle("Salle " + i, 10 + i * 5);
            salle.setDescription("Salle de formation " + i);
            em.persist(salle);
            LocalDateTime debut = LocalDateTime.now().plusDays(i);
            em.persist(new Reservation(salle, user, debut, debut.plusHours(2)));
        }
        em.getTransaction().commit();
    }
}
