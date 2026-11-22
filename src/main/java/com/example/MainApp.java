package com.example;

import com.example.util.DataInitializer;
import com.example.util.MigrationExecutor;
import com.example.util.PerformanceReport;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("ReservationPU");
        EntityManager em = emf.createEntityManager();
        try {
            MigrationExecutor migration = new MigrationExecutor(
                    "jdbc:h2:mem:reservation;DB_CLOSE_DELAY=-1", "sa", "");
            migration.executeMigration("db/migration/v2_0_add_description.sql");
            new DataInitializer(em).initData();
            new PerformanceReport(em).printReport();
        } finally {
            em.close();
            emf.close();
        }
    }
}
