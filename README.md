# demo-jpa-container

Das ist ein Demo Projekt für Schulungszweck, welches folgende Themen abdeckt:
- REST (JAX-RS)
- Jakarta Persistence API (JPA) mit Eclipselink
- Java Server Faces (JSF)
- Testing mit JUnit 5 und Mockito
- Openliberty Installation
- Docker Installation
- Kubernetes Installation

# Openliberty Installation
## Voraussetzung
TODO

---

# Docker Installation
## Voraussetzungen
- Docker ist installiert

## Images builden
    > cd mysqldb
    > docker build -t local/mysqldb:1.0.0 .
    > cd ..
    > docker build -t local/webapp:1.0.0 .

## Docker Netzwerk erstellen
    > docker network create -d bridge demo-network

## Containers im Hintergrund starten
    > docker container run --network=demo-network -d -it -p 63306:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=changeme local/mysqldb:1.0.0 --default-authentication-plugin=mysql_native_password

    > docker container run --network=demo-network -d -it -p 9180:9080 --name webapp -e DB_HOST=mysqldb -e DB_PORT=3306 -e DB_NAME=dashboard -e DB_USER=mddashboard -e DB_PASSWORD=geheim local/webapp:1.0.0

## Containers stoppen und löschen
    > docker ps -a
    > docker rm -f <CONTAINER_ID>

# Docker Compose Installation
## Voraussetzungen
- Docker ist installiert
- Docker Compose ist installiert

## Containers im Hintergrund starten
    > docker-compose up -d

## Containers stoppen und löschen
    > docker-compose down

---

# Kubernetes Installation
## Voraussetzungen
- Docker ist installiert (optional)
- Kubernetes ist installiert (direkt im Docker-Desktop oder minikube)
- Für minikube Installation siehe [Images sind nicht für Kubernetes sichtbar](#images-sind-nicht-für-kubernetes-sichtbar)

Die bevorzugte Variante ist die Kubernetes Installation direkt im Docker-Desktop.

## Docker Images lokal builden
    > docker build -t local/webapp:1.0.0 .
    > cd mysqldb
    > docker build -t local/mysqldb:1.0.0 .
    > cd ..

## Kuberbenetes Komponenten installieren
    > kubectl apply -f .\mysql-configmap.yaml
    > kubectl apply -f .\mysql-secret.yaml
    > kubectl apply -f .\mysql-deployment.yaml
    > kubectl apply -f .\webapp-deployment.yaml
    > kubectl apply -f .\webapp-ingress.yaml

## Web Applikation aufrufen
### Via Kubernetes Node-IP Adresse
    > kubectl get node -o wide

Im Browser `http://<NODE_INTERNAL_IP>:30100/demo-jpa-container` aufrufen
oder `http://localhost:30100/demo-jpa-container`

### Via DNS mit Ingress (minikube Variante)
    > minikube addons enable ingress

    # Ingress Adresse finden
    > kubectl get ingress

    # hosts Datei unter - C:\Windows\System32\drivers\etc - anpassen
	<INGRESS_IP> <domain> (Bsp.: demo-webapp.ch)

Im Browser `http://<DOMAIN>/demo-jpa-container` aufrufen

## Troubleshooting
### Zugriff auf Node-IP Adresse nicht möglich
Wenn minikube im Docker-Desktop (--driver=docker) installiert wird, funktioniert der Zugriff auf die Node-IP Adresse nicht und man muss eine Port-Forwarding konfigurieren

    > kubectl port-forward service/webapp-service-external 30100:9080

### Zugriff auf Ingress-IP Adresse nicht möglich
Wenn minikube im Docker-Desktop (--driver=docker) installiert wird, funktioniert der Zugriff auf die Ingress-IP Adresse nicht und man muss eine Port-Forwarding konfigurieren

    # Ingress Pod finden
    > kubectl get pod -n ingress-ngnix

    > kubectl -n ingress-ngnix port-forward pod/<POD_NAME> --address 0.0.0.0 80:80 443:443

### Images sind nicht für Kubernetes sichtbar
Wenn man minikube verwendet, müssen die Images im internen minikube Docker-Registry buildet werden. Dafür müssen wir die Docker Umgebung einrichten.

    # set docker env with Windows Powershell
	> minikube docker-env | Invoke-Expression

	# unset docker env with Windows Powershell
	> minikube docker-env -u | Invoke-Expression

