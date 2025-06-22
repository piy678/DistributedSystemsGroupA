#  *Energy Community System*

## Projektstruktur

```
├── producer/                  # Energie-Produzent Microservice
│   
├── user/                      # Energie-Verbraucher Microservice
│   
├── usage-service/            # Verarbeitung und Speicherung von Verbrauchsdaten
├── percentage-service/       # Berechnung der prozentualen Nutzung
├── gui/                      # JavaFX GUI-Anwendung
└── api/                      # Spring Boot REST API
```

---

## Voraussetzungen

* **Java JDK 17 oder höher**
* **Maven**
* **Docker & Docker Compose**
* **RabbitMQ** (wird via Docker gestartet)
* **PostgreSQL** (wird via Docker gestartet)

---

## Datenbank

Start über Docker:

```bash
docker compose up
```

Zugang über Adminer:

* [http://localhost:5432/](http://localhost:5432/)
* Datenbank-System: PostgreSQL
* Hostname: `postgres`
* Benutzer: `postgres`
* Passwort: `password`
* DB-Name: `energydb`

---

## Microservices

### 1. Producer-Service

```bash
cd producer
mvn spring-boot:run
```

* Sendet PRODUCER-Nachrichten an RabbitMQ
* Nutzt zufällige Werte + Wetterdaten (optional)

### 2. User-Service

```bash
cd user
mvn spring-boot:run
```

* Sendet USER-Nachrichten an RabbitMQ
* Nutzt Tageszeit für realistischere Nutzung

### 3. Usage-Service

```bash
cd usage-service
mvn spring-boot:run
```

* Hört auf PRODUCER/USER-Nachrichten
* Aktualisiert stündliche Verbrauchswerte
* Sendet bei Änderung eine Update-Nachricht

### 4. Percentage-Service

```bash
cd percentage-service
mvn spring-boot:run
```

* Hört auf Update-Nachrichten
* Berechnet den Anteil Community/Grid

---

## REST API

```bash
cd api
mvn spring-boot:run
```

### Endpunkte

* `GET /energy/percentage/current` → Aktuelle Prozentwerte
* `GET /energy/usage?start=...&end=...` → Historische Daten

---

## JavaFX GUI

```bash
cd gui
mvn javafx:run
```

* Holt Daten via REST API
* Zeigt aktuelle und historische Daten an
* Visualisiert Community/Grid-Verbrauch

---

## Kommunikation

Alle Komponenten kommunizieren über **RabbitMQ**.

Queue Management:
[http://localhost:5672](http://localhost:5672)
Benutzer: `admin`, Passwort: `admin`

---

## Beispiel-Ablauf

1. Producer/User senden Nachrichten an die Queue
2. Usage-Service verarbeitet die Daten & aktualisiert DB
3. Percentage-Service berechnet neue Werte
4. GUI fragt REST API nach Daten
5. REST API liefert die Daten aus der DB
6. GUI zeigt sie an

---

## Projekt

| Komponente                 |   Beschreibung                       |
| -------------------------- |    ---------------------------------- |
| REST API (Spring Boot)         | Liest echte Daten aus DB           |
| Energy Producer                | Sendet realistische Daten          |
| Energy User                    | Berücksichtigt Tageszeiten         |
| Usage Service                  | Aktualisiert korrekte Stundenwerte |
| Current Percentage Service     | Berechnet Community/Grid-Nutzung   |

