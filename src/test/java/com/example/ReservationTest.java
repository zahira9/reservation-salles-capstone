package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.model.Utilisateur;
import com.example.service.ReservationService;
import com.example.util.DataInitializer;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    static void initFactory() {
        emf = Persistence.createEntityManagerFactory("ReservationPU");
    }

    @BeforeEach
    void initManager() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Reservation").executeUpdate();
        em.createQuery("DELETE FROM Utilisateur").executeUpdate();
        em.createQuery("DELETE FROM Salle").executeUpdate();
        em.getTransaction().commit();
        new DataInitializer(em).initData();
    }

    @AfterEach
    void closeManager() {
        em.close();
    }

    @AfterAll
    static void closeFactory() {
        emf.close();
    }

    @Test
    void createsAReservation() {
        Utilisateur user = new Utilisateur("Test", "test@example.com");
        Salle salle = new Salle("Salle Test", 30);
        Reservation reservation = new Reservation(
                salle, user, LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(1));
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        em.persist(salle);
        em.persist(reservation);
        tx.commit();
        assertNotNull(reservation.getId());
    }

    @Test
    void jpqlAndCriteriaReturnMatchingReservations() {
        ReservationService service = new ReservationService(em);
        List<Reservation> jpql = service.searchByRoomWithJPQL("Salle 1");
        List<Reservation> criteria = service.searchByRoomWithCriteria("Salle 1");
        assertEquals(1, jpql.size());
        assertEquals(jpql.size(), criteria.size());
    }
}
