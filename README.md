# 🎓 Training-Hub (Backend Architecture)

**Training-Hub** è il backend a microservizi sviluppato per la gestione completa di una piattaforma di formazione e percorsi didattici. Il progetto implementa un'architettura distribuita basata su **Java** e **Spring Boot**.

---

## 🏗️ Architettura dei Microservizi

Il repository contiene i seguenti microservizi indipendenti:

* **`identity-service`**: Gestisce l'autenticazione, la registrazione degli utenti, i ruoli e la sicurezza tramite token JWT e Spring Security. (Porta: `8083`)
* **`course-service`**: Si occupa della creazione, della modifica e della gestione dei corsi di formazione e delle relative lezioni. (Porta: `8082`)
* **`enrollment-service`**: Gestisce le iscrizioni degli utenti ai corsi e il tracciamento delle presenze/partecipazioni.
* **`participant-service`**: Gestisce le anagrafiche e le informazioni dettagliate dei partecipanti ai vari programmi formativi. (Porta: `8081`)

---

## 🛠️ Tecnologie Utilizzate

* **Linguaggio:** Java
* **Framework:** Spring Boot, Spring Security
* **Database:** MySQL (con configurazioni dedicate per servizio)
* **Frontend:** Angular
* **Architettura:** Microservices Pattern

---

## 🚀 Struttura del Progetto

```text
Training-Hub/
│
├── course-service/       # Microservizio Gestione Corsi
├── enrollment-service/   # Microservizio Gestione Iscrizioni e Presenze
├── identity-service/     # Microservizio Autenticazione e Utenti
└── participant-service/  # Microservizio Gestione Partecipanti


### 4. Avvio del Frontend (Angular)
1. Apri un terminale nella cartella del progetto frontend di Angular.
2. Installa le dipendenze (se non già fatto):
   ```bash
   npm install

Avvia l'applicazione in modalità di sviluppo:
ng serve
