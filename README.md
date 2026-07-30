Esattamente, basta aggiungere una sezione dedicata all'avvio nel file README.md.

Ecco la versione aggiornata e completa del testo, pronta da copiare e incollare nel tuo README.md, con tutte le istruzioni sui database e l'ordine di avvio:

🎓 Training-Hub (Backend Architecture)
Training-Hub è il backend a microservizi sviluppato per la gestione completa di una piattaforma di formazione e percorsi didattici. Il progetto implementa un'architettura distribuita basata su Java e Spring Boot.

🏗️ Architettura dei Microservizi
Il repository contiene i seguenti microservizi indipendenti:

identity-service: Gestisce l'autenticazione, la registrazione degli utenti, i ruoli e la sicurezza tramite token JWT e Spring Security. (Porta: 8083)

course-service: Si occupa della creazione, della modifica e della gestione dei corsi di formazione e delle relative lezioni. (Porta: 8082)

enrollment-service: Gestisce le iscrizioni degli utenti ai corsi e il tracciamento delle presenze/partecipazioni.

participant-service: Gestisce le anagrafiche e le informazioni dettagliate dei partecipanti ai vari programmi formativi. (Porta: 8081)

🛠️ Tecnologie Utilizzate
Linguaggio: Java

Framework: Spring Boot, Spring Security

Database: MySQL (con configurazioni dedicate per servizio)

Frontend: Angular

Architettura: Microservices Pattern

🚀 Struttura del Progetto
Plaintext
Training-Hub/
│
├── course-service/       # Microservizio Gestione Corsi
├── enrollment-service/   # Microservizio Gestione Iscrizioni e Presenze
├── identity-service/     # Microservizio Autenticazione e Utenti
└── participant-service/  # Microservizio Gestione Partecipanti
⚙️ Guida all'Avvio in Locale
Essendo un'architettura a microservizi, non è richiesto alcun deploy online: puoi eseguire l'intera piattaforma direttamente sulla tua macchina in locale seguendo questi passaggi.

1. Prerequisiti
Assicurati di avere installato sulla tua macchina:

Java (JDK)

Node.js e Angular CLI (per il frontend)

MySQL Server attivo

2. Configurazione dei Database
Crea i database necessari su MySQL (le applicazioni sono configurate per crearli o aggiornarli automaticamente tramite Hibernate, ma è consigliabile averli predisposti):

training_hub_courses (per il course-service)

Database relativi agli altri microservizi secondo le rispettive configurazioni nei file application.properties

3. Avvio dei Microservizi Backend
Apri i singoli progetti dei microservizi nella tua IDE (es. IntelliJ IDEA) e avvia le classi principali Spring Boot nell'ordine logico consigliato:

Identity Service (porta 8083)

Course Service (porta 8082)

Participant Service (porta 8081)

Enrollment Service

4. Avvio del Frontend (Angular)
Apri un terminale nella cartella del progetto frontend di Angular.

Installa le dipendenze (se non già fatto):

Bash
npm install
Avvia l'applicazione in modalità di sviluppo:

Bash
ng serve
Apri il browser all'indirizzo
