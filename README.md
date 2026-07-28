# 🎓 Training-Hub (Backend Architecture)

**Training-Hub** è il backend a microservizi sviluppato per la gestione completa di una piattaforma di formazione e percorsi didattici. Il progetto implementa un'architettura distribuita basata su **Java** e **Spring Boot**.

---

## 🏗️ Architettura dei Microservizi

Il repository contiene i seguenti microservizi indipendenti:

*   **`identity-service`**: Gestisce l'autenticazione, la registrazione degli utenti, i ruoli e la sicurezza tramite token JWT e Spring Security.
*   **`course-service`**: Si occupa della creazione, della modifica e della gestione dei corsi di formazione e delle relative lezioni.
*   **`enrollment-service`**: Gestisce le iscrizioni degli utenti ai corsi e il tracciamento delle presenze/partecipazioni.
*   **`participant-service`**: Gestisce le anagrafiche e le informazioni dettagliate dei partecipanti ai vari programmi formativi.

---

## 🛠️ Tecnologie Utilizzate

*   **Linguaggio:** Java 21
*   **Framework:** Spring Boot, Spring Security
*   **Database:** Relazionali (con configurazioni dedicate per servizio)
*   **Architettura:** Microservices Pattern

---

## 🚀 Struttura del Progetto

```text
Training-Hub/
│
├── course-service/       # Microservizio Gestione Corsi
├── enrollment-service/   # Microservizio Gestione Iscrizioni e Presenze
├── identity-service/     # Microservizio Autenticazione e Utenti
└── participant-service/  # Microservizio Gestione Partecipanti
