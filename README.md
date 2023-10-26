# interview_movie

Interview movie implements the following features:

- Get movie by id
- Get list of movie
- Create new movie
- Update existing movie
- Delete existing movie

### Running the app locally by container

#### 1. Start the application
    docker compose up

### Swagger UI and Swagger docs
- http://localhost:8081/interview/swagger-ui/#/
- http://localhost:8081/interview/v2/api-docs

### Running the tests
#### Run unit test

    ./gradlew test

#### Run integration test

    docker build -t movie-image . 
    docker run -d -p 63306:3306 --name movie -e MYSQL_ROOT_PASSWORD=movie123 movie-image
    ./gradlew integrationTest

