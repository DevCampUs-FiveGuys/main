docker stop devcampus
docker rm devcampus
docker rmi devcampus
docker build -t devcampus .
docker run -d -p 8060:8080 --name devcampus devcampus