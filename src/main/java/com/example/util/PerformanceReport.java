package com.example.util;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import javax.persistence.EntityManager;

public class PerformanceReport {
    private final EntityManager em;
    public PerformanceReport(EntityManager em) { this.em = em; }

    public void printReport() {
        Statistics stats =
                em.unwrap(Session.class).getSessionFactory().getStatistics();
        System.out.println("=== RAPPORT DE PERFORMANCE ===");
        System.out.println("Requetes executees : " + stats.getQueryExecutionCount());
        System.out.println("Cache second niveau hits : " + stats.getSecondLevelCacheHitCount());
        System.out.println("Cache second niveau misses : " + stats.getSecondLevelCacheMissCount());
        System.out.println("Cache second niveau mises à jour : " + stats.getSecondLevelCachePutCount());
        System.out.println("Transactions : " + stats.getTransactionCount());
        System.out.println("Duree moyenne requete (ms) : " +
                stats.getQueryExecutionMaxTime());
    }
}
