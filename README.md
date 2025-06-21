# Energy Community Project

Dieses Projekt simuliert ein vernetztes Energiesystem mit Community- und Grid-Stromverbrauchern und -produzenten. 
Die Kommunikation erfolgt über RabbitMQ, die Datenverarbeitung über Spring Boot und die Anzeige über JavaFX.

## Projektstruktur
```
energy-community-project/
├── rest-api/                → Spring Boot REST API
├── gui/                     → JavaFX GUI
├── energy-producer/         → Producer Service
├── energy-user/             → User Service
├── usage-service/           → Verbrauchs-Service
├── percentage-service/      → Prozent-Berechnung
├── .gitignore

```

## Technologien
Java 17+
Spring Boot
JavaFX
RabbitMQ
Maven
Docker
(Optional: PostgreSQL, MySQL, H2)

## Projektablauf

### 1. Vorbereitung
- RabbitMQ mit Docker starten  
  ```bash
  docker run -d --hostname rabbit-host --name rabbitmq \
    -e RABBITMQ_DEFAULT_USER=admin \
    -e RABBITMQ_DEFAULT_PASS=admin \
    -p 5672:5672 -p 15672:15672 \
    rabbitmq:3-management
Erstellt die Queues:
energy-data
usage-update

### 2. Energy Producer starten
Sendet PRODUCER-Nachrichten mit zufälligen kWh-Werten an RabbitMQ

### 3. Energy User starten
Sendet USER-Nachrichten mit tageszeitabhängigen kWh-Werten an RabbitMQ

### 4. Usage Service starten
Liest PRODUCER & USER Nachrichten von `energy-data`
Berechnet stündliche Verteilung in `hourly_usage`
Sendet Update-Meldung an `usage-update` Queue

### 5. Current Percentage Service starten
Liest Update-Nachrichten von usage-update
Berechnet aktuelle `community_depleted` und `grid_portion`
Speichert Ergebnis in `current_percentage`

### 6. REST API starten
`/energy/current` → aktueller Prozentwert

`/energy/historical?start=...&end=...` → historische Werte

### 7. GUI starten
Fragt Daten über die REST API ab
Zeigt aktuelle und historische Werte an

Datenbankstruktur
Tabelle: `hourly_usage`
```
| hour | community_produced | community_used | grid_used |
```
Tabelle: `current_percentage`
```
| hour | community_depleted | grid_portion |
```
## To-do Liste – Energy Community Projekt

### Einrichtung
- [x] RabbitMQ mit Docker starten
- [x] Queues `energy-data` und `usage-update` erstellen
- [x] RabbitMQ  und in PostgreSQL `docker-compose.yml` integrieren
- [x] docker-compose für gesamtes System vorbereiten (RabbitMQ, PostgreSQL, Spring Boot Services)

### Komponenten
- [x] REST API mit Spring Boot erstellen  (Spring Boot, GET /energy/current, /energy/historical)
- [x] JavaFX GUI bauen  (anzeigen aktueller und historischer Daten)
- [x] Energy Producer Service programmieren (produziert zufällige kWh + Wetter-API)
- [x] Energy User Service programmieren (verbraucht zufällige kWh + Peak Times)
- [x] Usage Service programmieren  (empfängt Nachrichten, aggregiert Stundenwerte, schreibt in DB)
- [x] Current Percentage Service programmieren (berechnet community_depleted und grid_portion aus DB-Daten)

### Datenbank
- [x] Datenbankmodell anlegen (z. B. `hourly_usage`, `current_percentage`) ->JPA-Entities korrekt erstellt (@Entity, @Id, etc.)
- [x] SQL-Skripte oder JPA-Entities für Tabellen implementieren -> Felder wie communityUsed, gridUsed etc. sind vorhanden
- [ ] Daten speichern und korrekt aktualisieren

### Integration
- [ ] RabbitMQ-Verbindung in allen Services implementieren
- [ ] Producer/Consumer-Logik in Producer, User, Usage Service, Percentage Service implementieren
- [ ] Nachrichtenfluss testen: Energy Producer/User → RabbitMQ → Usage Service → PostgreSQL → Percentage Service (Producer → Queue → Usage Service → DB → Percentage Service)
- [ ] Fehlerbehandlung und Logging einbauen

### GUI/REST-Tests
- [ ] Aktuelle Werte (/energy/current) in der GUI anzeigen
- [ ] Historische Daten (/energy/historical) in der GUI anzeigen
- [ ] Zeitfilterung (Start/Ende) in der GUI testen
- [ ] Historische Datenabfrage testen
- [ ] GUI-Interaktion mit REST API testen (Buttons, Labels, Tabellen)

### Abschluss
- [ ] Code kommentieren & dokumentieren  (README.md, JavaDoc)
- [ ]  GitHub-Repo finalisieren & pushen (alle Projekte, Dockerfiles, docker-compose.yml)
- [ ] README.md vervollständigen  (Architektur, Diagramme, Projektbeschreibung)
- [ ] Projektdemo & Präsentation vorbereiten
### Zusätzliche Punkte:
- [ ] (Optional) Wetter-API für Producer-Daten einbinden
- [ ] (Optional) Logs und Monitoring (z. B. Spring Boot Actuator, Prometheus, Grafana)
- [ ] (Optional) Tests schreiben (Unit/Integrationstests für Services)
- [ ] (Optional) Fehler-Handling (z. B. Invalid Messages in RabbitMQ)
