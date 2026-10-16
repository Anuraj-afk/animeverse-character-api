# AnimeVerse Character API

A RESTful API for the anime characters featured in 
AnimeVerse website.

Built with Java 25, Spring Boot, Spring Data JPA, and PostgreSQL.
Data is stored in Neon, and the application is deployed to Render
using Docker.

## Deployed API

https://animeverse-character-api.onrender.com/api/characters

The free Render service sleeps when idle. The first request after
inactivity may take approximately one minute while the service starts.

## Entity

Entity class: AnimeCharacter  
Database table: characters

| Attribute | Type | Rules |
|---|---|---|
| characterId | long | Unique, automatically generated primary key |
| name | String | Required, not blank, maximum 100 characters |
| description | String | Required, not blank, maximum 2000 characters |
| universe | String | Required, not blank, maximum 100 characters |
| species | String | Required, not blank, maximum 100 characters |

Clients should omit characterId from POST and PUT bodies.
The path ID identifies the record being updated.

## Architecture

- CharacterController handles HTTP requests and input validation.
- CharacterService handles CRUD logic and missing-record checks.
- CharacterRepository handles persistence using Spring Data JPA.
- AnimeCharacter defines the entity and table mapping.

The controller calls the service, and the service calls the repository.

## Endpoints

| Method | Path | Description | Success status |
|---|---|---|---|
| GET | /api/characters | Get all characters | 200 |
| GET | /api/characters/{id} | Get one character by ID | 200 |
| POST | /api/characters | Create a character | 201 |
| PUT | /api/characters/{id} | Replace a character's editable fields | 200 |
| DELETE | /api/characters/{id} | Delete a character | 204 |
| GET | /api/characters?universe=Dragon%20Ball | Filter by universe | 200 |
| GET | /api/characters?name=go | Search by partial name | 200 |

Universe filtering matches the full universe value, ignoring case.
Name searching is partial and case-insensitive.

Both parameters may be combined:

/api/characters?name=go&universe=Dragon%20Ball

Blank query parameters are ignored.
A search with no matches returns an empty array with status 200.

## POST Example

POST /api/characters

Content-Type: application/json

```json
{
  "name": "Goku",
  "description": "A Saiyan martial artist who protects Earth.",
  "universe": "Dragon Ball",
  "species": "Saiyan"
}
```

Returns 201 Created with the saved character and generated characterId.

## PUT Example

PUT /api/characters/1

Replace 1 with an existing character ID.

Content-Type: application/json

```json
{
  "name": "Goku",
  "description": "A Saiyan warrior who trains to become stronger and protects Earth.",
  "universe": "Dragon Ball",
  "species": "Saiyan"
}
```

Returns 200 OK with the updated character.
All four editable fields are required for PUT.

## Error Responses

- 400 Bad Request: invalid request body, including missing or blank
  name or description.
- 404 Not Found: GET, PUT, or DELETE targets an ID that does not exist.
- 204 No Content: successful deletion; the response body is empty.

## Run Locally

Requirements: JDK 25 and access to a PostgreSQL database.

Set SPRING_DATASOURCE_URL in the terminal environment to your own
database's JDBC connection value. Credentials are not stored in this
repository.

In Git Bash, start the application from that same terminal:

```bash
./mvnw spring-boot:run
```

Local endpoint:

http://localhost:8080/api/characters

The application uses PORT when provided, or 8080 otherwise.

## Deployment

The multi-stage Dockerfile builds the JAR using the Maven wrapper
and JDK 25, then runs it using JRE 25.

Configure SPRING_DATASOURCE_URL in Render's environment settings.
PostgreSQL data persists in Neon.