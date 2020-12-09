# Deploying sample application to Google Cloud
It's easier than you might think!

## 1. Create your service and push to GitHub repository
We will use this service as an example  
Run it and check
```
curl localhost:8080/status
```

## 2. Make Dockerfile for your application
```
FROM adoptopenjdk/openjdk14
EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cloud.jar
ENTRYPOINT ["java","-jar","/cloud.jar"]
```

## 3. Build Docker image and push to project’s image repository
```
git clone https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage.git
cd cloud
```

Build project with gradle
```
./gradlew clean assemble
```

Build image. Note that gcr.io/made2020/ inside tag indicates that image will be pushed to project’s image repository  
```
docker build . --tag gcr.io/made2020/cloud-sample:1.0
```

Push image to local repository
```
docker push gcr.io/made2020/cloud-sample:1.0
```

## 4. Deploy workload with given repository
https://console.cloud.google.com/kubernetes/workload?cloudshell=true&project=made2020  
workloads


## 5. Expose 8080 port on static ip using LoadBalancer service in Kubernetes
https://console.cloud.google.com/kubernetes/workload?cloudshell=true&project=made2020  
services and Ingress

## 6. Check you service on public API