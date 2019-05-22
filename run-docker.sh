echo "Creating new JAR-File..."
mvn clean install
echo "Removing current container..."
docker container rm -f repository
echo "Removing current image..."
docker image rm -f repository
echo "Building new image and starting container..."
docker-compose up
