package com.example.service;

import com.example.model.Reservation;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class ReservationService {
    private static final Logger LOGGER =
            Logger.getLogger(ReservationService.class.getName());
    private final EntityManager em;

    public ReservationService(EntityManager em) { this.em = em; }

    public Reservation create(Reservation reservation) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(reservation);
            tx.commit();
            LOGGER.info("Reservation creee: " + reservation.getId());
            return reservation;
        } catch (OptimisticLockException | RollbackException e) {
            if (tx.isActive()) tx.rollback();
            LOGGER.severe("Conflit ou erreur de transaction");
            throw e instanceof OptimisticLockException
                    ? (OptimisticLockException) e
                    : new OptimisticLockException(e);
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            LOGGER.severe("Erreur lors de la creation: " + e.getMessage());
            throw e;
        }
    }

    public List<Reservation> searchByRoomWithJPQL(String roomName) {
        return em.createQuery(
                "SELECT r FROM Reservation r WHERE r.salle.nom = :nomSalle " +
                        "AND r.dateDebut >= :date",
                Reservation.class)
                .setParameter("nomSalle", roomName)
                .setParameter("date", LocalDateTime.now())
                .getResultList();
    }

    public List<Reservation> searchByRoomWithCriteria(String roomName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        Predicate predicate = cb.and(
                cb.equal(root.get("salle").get("nom"), roomName),
                cb.greaterThan(root.get("dateDebut"), LocalDateTime.now()));
        cq.select(root).where(predicate);
        return em.createQuery(cq).getResultList();
    }
}
