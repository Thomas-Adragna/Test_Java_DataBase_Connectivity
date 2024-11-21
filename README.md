# Java JDBC Project

Ce projet est une application Java utilisant JDBC pour interagir avec une base de données H2. Elle démontre les fonctionnalités CRUD (Create, Read, Update, Delete) sur une table `Students`.

## Fonctionnalités
- Connexion à une base de données H2 en mémoire.
- Création d'une table `Students` avec les colonnes `id`, `name`, et `age`.
- Implémentation des opérations CRUD via le pattern DAO (Data Access Object).
- Tests d'intégration avec JUnit 5.
- Exemple d'utilisation via un menu interactif en console.

## Prérequis
- **Java 19** (ou version ultérieure)
- **Gradle 8.3** (ou version compatible avec Java 19)
- **H2 Database** (inclus via Gradle)

## Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/java-jdbc-project.git
   cd java-jdbc-project

2. Compilez et exécutez les tests :
    ```bash
   ./gradlew build
   
3. Lancez l'application :
    ```bash
   ./gradlew run
