# interview_movie

## Features
Interview movie implements the following features:

- Get movie by id
- Get list of movie
- Create new movie
- Update existing movie
- Delete existing movie

## Repository structure
| Folder   | Description                                 | 
|----------|:--------------------------------------------| 
| Movie    | Springboot app                              | 
| mysql_ds | Mysql container                             |
| postman  | File to import postman                      |
| .env     | Environment variable for docker-compose.yml |

## Local RUN
### Running the app locally by container

#### 1. Start the application
    cd Movie && ./gradlew clean build && cd .. && docker compose up && cd ..

### 2. Swagger UI and Swagger docs
- http://localhost:8081/interview/swagger-ui/#/
- http://localhost:8081/interview/v2/api-docs

### Running the tests
#### Run unit test

    cd Movie && ./gradlew test && cd ..

#### Run integration test

    cd Movie && docker run -d -p 63306:3306 --name movie -e MYSQL_ROOT_PASSWORD=movie123 movie-image && sleep 4 && ./gradlew integrationTest && cd ..
