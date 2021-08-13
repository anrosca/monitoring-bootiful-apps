# Stock Profit Tracker

### Setup 

- Go to the `https://iexcloud.io/` and create an account
- Create an environment variable `IEX_API_KEY=<<your-api-token>>`
- Go to the `src/main/resources/docker` folder and edit the `.env` file in the following way:
```text
IEX_API_KEY=<<your-api-token>>
```
- Build the project. Go to the project's root directory and execute:
```shell
$ mvn clean package -DskipTests
```
- From the `src/main/resources/docker` directory execute the following command:
```shell
$ docker-compose up --build
```

- Prometheus is available at [http://localhost:9090](http://localhost:9090)
- Grafana is available at [http://localhost:3000](http://localhost:3000)
- Spring boot app is available at [http://localhost:8080](http://localhost:8080)

