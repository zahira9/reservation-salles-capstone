# Reservation Salles - TP Capstone

Application JEE pure avec JPA/Hibernate pour gérer les utilisateurs, salles et réservations.

## Technologies

- Java 11
- Maven
- JPA 2.2
- Hibernate 5.6
- Ehcache
- H2
- JUnit 5

## Exécuter

```bash
mvn test
mvn exec:java -Dexec.mainClass=com.example.MainApp
```

## Contenu

- Modèle `Utilisateur`, `Salle`, `Reservation`
- `@Version` pour le verrouillage optimiste
- Cache de second niveau
- Jeu de données réaliste
- Migration JDBC H2
- Rapport Hibernate
- JPQL et Criteria API
- Transactions, logs et gestion des erreurs
