# demo-jpa-container

This is a demo project for educational purposes covering the following topics:
- REST (JAX-RS)
- Jakarta Persistence API (JPA) with Eclipselink
- Java Server Faces (JSF)
- Testing with JUnit 5 and Mockito
- Openliberty installation
- Docker installation
- Kubernetes installation

# Openliberty installation
## Preconditions
- Maven is installed

## Start application server in DEV mode
    > mvn liberty:dev

    # without DEV mode
    > mvn liberty:start

## Call web application
Call im Browser `http://localhost:9180/demo-jpa-container`

## Stop application server
    > mvn liverty:stop

---

# Docker installation
## Preconditions
- Docker is installed

## Build images
    > cd mysqldb
    > docker build -t local/mysqldb:1.0.0 .
    > cd ..
    > docker build -t local/webapp:1.0.0 .

## Create docker network
    > docker network create -d bridge demo-network

## Start containers in background
    > docker container run --network=demo-network -d -it -p 63306:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=changeme local/mysqldb:1.0.0 --default-authentication-plugin=mysql_native_password

    > docker container run --network=demo-network -d -it -p 9180:9080 --name webapp -e DB_HOST=mysqldb -e DB_PORT=3306 -e DB_NAME=dashboard -e DB_USER=mddashboard -e DB_PASSWORD=geheim local/webapp:1.0.0

## Stop and delete containers
    > docker ps -a
    > docker rm -f <CONTAINER_ID>

---

# Docker Compose installation
## Preconditions
- Docker is installed
- Docker Compose is installed

## Start containers in background
    > docker-compose up -d

## Stop and delete containers
    > docker-compose down

---

# Kubernetes installation
## Preconditions
- Docker is installed (optional)
- Kubernetes is installed (in Docker-Desktop or minikube)
- For minikube installation see [Images can't be found in Kubernetes registry](#images-cant-be-found-in-kubernetes-registry)

The preferred variant is the Kubernetes installation directly in the Docker desktop.

## Build docker images locally
    > docker build -t local/webapp:1.0.0 .
    > cd mysqldb
    > docker build -t local/mysqldb:1.0.0 .
    > cd ..

## Install Kuberbenetes components
    > cd kubernetes
    > kubectl apply -f .\mysql-configmap.yaml
    > kubectl apply -f .\mysql-secret.yaml
    > kubectl apply -f .\mysql-deployment.yaml
    > kubectl apply -f .\webapp-deployment.yaml
    > kubectl apply -f .\webapp-ingress.yaml

## Call web application
### Via Ingress host
#### Install Ingress addon (without minikube)
    > kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.2.0/deploy/static/provider/cloud/deploy.yaml

#### Install Ingress (with minikube)
    > minikube addons enable ingress

#### Configure DNS in hosts file
    # find Ingress address
    > kubectl get ingress

    # change hosts file under - C:\Windows\System32\drivers\etc
	<INGRESS_IP> <domain> (i.e. demo-webapp.ch)

Call im Browser `http://<DOMAIN>/demo-jpa-container`

### Via Kubernetes Node-IP address
    # find Node internal IP address
    > kubectl get node -o wide

Call im Browser `http://<NODE_INTERNAL_IP>:30100/demo-jpa-container` or `http://localhost:30100/demo-jpa-container`

## Uninstall Kubernetes components
    > cd kubernetes
    > kubectl delete -f .\webapp-deployment.yaml
    > kubectl delete -f .\mysql-deployment.yaml
    > kubectl delete -f .\mysql-secret.yaml
    > kubectl delete -f .\mysql-configmap.yaml
    > kubectl delete -f .\webapp-ingress.yaml

---

## Troubleshooting
### Access to Node-IP address not possible
If minikube is installed in the Docker desktop (--driver=docker), access to the node IP address does not work and you have to configure port forwarding

    > kubectl port-forward service/webapp-service-external 30100:9080

### Access to Ingress-IP address not possible
If minikube is installed in the Docker desktop (--driver=docker), access to the ingress IP address does not work and you have to configure port forwarding

    # Find Ingress Pod
    > kubectl get pod -n ingress-ngnix

    > kubectl -n ingress-ngnix port-forward pod/<POD_NAME> --address 0.0.0.0 80:80 443:443

### Images can't be found in Kubernetes registry
When using minikube, the images have to be built in the internal minikube docker registry. For this we need to set up the Docker environment.

    # set docker env with Windows Powershell
	> minikube docker-env | Invoke-Expression

	# unset docker env with Windows Powershell
	> minikube docker-env -u | Invoke-Expression

