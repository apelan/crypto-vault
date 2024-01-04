# Crypto Vault API
<hr/>

### Technology used
Crypto Vault API is created using Java and web framework Spring Boot. <br>
Secured using Bearer Authentication with [Json Web Tokens](https://jwt.io/). <br>
All data is persisted in PostgreSQL database, integration tests are performed with H2 in-memory database. <br>
Database migrations are performed with Flyway. <br>
OpenAPI(Swagger) is used for API documentation.


### Prerequisites to run on local machine

- Installed PostgreSQL on machine (check How To's section for instructions)
- PostgreSQL database named _crypto_vault_db_, schema will be created with migrations
- Java - recommended version 17 (minimum version 16)


### Run on local machine
In order to run on local machine go to project root folder, open command prompt and run the following command to build a project. <br>
**Note**: this step will also run all unit and integrations tests
```shell
gradlew build
```

When project is build, we can run it via following command.
```shell
gradlew bootRun
```
Application by default is running on port 8080, it will be available at:
http://localhost:8080/api


<hr/>

### How to's

- <b>Postgres installation</b> <br>
Download from [official website](https://www.postgresql.org/download/). Run installation, you might be prompted to provide password for user. Default used in project is "postgres", if you choose different one you will need to change [application properties](https://github.com/apelan/crypto-vault/blob/main/src/main/resources/application.yml#L8).



- <b>Test API</b> <br>
We can test API with Swagger (when running on local) or using any rest client. I've provided [Postman Collection](https://github.com/apelan/crypto-vault/blob/main/Crypto%20Vault%20Collection.postman_collection.json) in root folder of project.<br> Postman Collection is configured for local usage, to test against deployed application on Railway you need to change url. <br>

- <b>Use API</b> <br>
In order to use API, we must hit /login endpoint first (_default username=user and password=user_) which will return us JWT. <br>
We can use that JWT token as Bearer Authentication header for other endpoints, keep in mind that it expires in 60mins. <br>
If you use provided Postman Collection there is pre-script which calls /login endpoint before each request, thus making API testing easier.
