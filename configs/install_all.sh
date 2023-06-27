npm install n8n -g
n8n start --tunnel

curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube_latest_amd64.deb
sudo dpkg -i minikube_latest_amd64.deb
minikube start

kubectl create namespace monitoring
helm install kube-prom-stack prometheus-community/kube-prometheus-stack -f prometheus-values.yaml -n monitoring
helm install kminion kminion/kminion -f kminion-values.yaml -n monitoring 

kubectl create namespace kafka
helm install kafka oci://registry-1.docker.io/bitnamicharts/kafka -f kafka-values.yaml -n kafka 

kubectl create namespace processing
kubectl create -f producer-deployment.yaml -n processing
kubectl create -f consumer-deployment.yaml -n processing