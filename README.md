# demo-jpa-container

Das ist ein Demo Projekt.

# Docker Installation
TODO

# Kubernetes Intallation

## Vorrausetzungen
- Docker ist installiert
- Kubernetes ist installiert (direkt im Docker-Desktop oder minikube)
- Für minikube Installation siehe [Images sind nicht für Kubernetes sichtbar](#images-sind-nicht-für-kubernetes-sichtbar)

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
### Kubernetes Node-IP ausfindig machen
    $ kubectl get node -o wide
Im Browser `http://<NODE-INTERNAL-IP>:30100/demo-jpa-container` aufrufen

## Troubleshooting
### Zugriff auf Node-IP Adresse nicht möglich
Wenn minikube im Docker-Desktop (--driver=docker) installiert wird, funktioniert der Zugriff auf die Node-IP Adresse nicht und man muss eine Port-Forwarding konfigurieren

    > kubectl port-forward service/webapp-service-external 30100:9080

### Images sind nicht für Kubernetes sichtbar
Wenn man minikube verwendet, müssen die Images im internen minikube Docker-Registry buildet werden. Dafür müssen wir die Docker Umgebung einrichten.

    # set docker env with Windows Powershell
	> minikube docker-env | Invoke-Expression

	# unset docker env with Windows Powershell
	> minikube docker-env -u | Invoke-Expression

