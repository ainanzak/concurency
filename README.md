# concurency
API Concurency (Input Exchange Rates)

### Docker
Dillinger is very easy to install and deploy in a Docker container.

By default, application will expose port 20030, database postgres will expose port 5432 and sonar qube will expose port 9000, so change this within the docker-compose.yaml if necessary. When ready, simply use the docker-compose to running applications.

```sh
docker-compose up -d
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
127.0.0.1:20030
```