startapp:
	mvn clean package
	docker-compose up --build
bashdb:
	docker exec -it db bash
logsapp:
	docker logs app