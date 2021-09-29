package com.santander.cloudbr.games.challenges;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;

@QuarkusTest
public class BookResourceTest {

    private static final Integer ALL_BOOKS_SIZE = 10;

    @Test
    public void shouldPingOpenAPI() {
        given()
            .header(ACCEPT, APPLICATION_JSON)
            .when().get("/q/openapi")
            .then()
            .statusCode(OK.getStatusCode());
    }

    @Test
    public void shouldPingSwaggerUI() {
        given()
            .when().get("/q/swagger-ui")
            .then()
            .statusCode(OK.getStatusCode());
    }

    @Test
    public void shouldGetAllBooks() {
        List<Book> books = get("/books")
            .then()
            .statusCode(OK.getStatusCode())
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .extract().body().as(getBookTypeRef());
        assertEquals(ALL_BOOKS_SIZE, books.size());
    }

    @Test
    public void shouldGetBookByName() {
        given()
            .pathParam("name", "Sapiens")
            .when().get("/books/{name}")
            .then()
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .statusCode(OK.getStatusCode())
            .body("id", Is.is(1))
            .body("name", Is.is("Sapiens"))
            .body("publicationYear", Is.is(2011));
    }

    @Test
    public void shouldGetBooksByPublishYearBetween() {
        List<Book> books = given()
            .pathParam("lowerYear", "2017")
            .pathParam("higherYear", "2019")
            .when().get("/books/{lowerYear}/{higherYear}")
            .then()
            .statusCode(OK.getStatusCode())
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .extract().body().as(getBookTypeRef());
        
        assertEquals(3, books.size());
    }

    // books/2017/2019
    //     [
    //   {
    //     "id": 3,
    //     "name": "Enlightenment Now",
    //     "publicationYear": 2018
    //   },
    //   {
    //     "id": 4,
    //     "name": "Factfulness",
    //     "publicationYear": 2018
    //   },
    //   {
    //     "id": 10,
    //     "name": "The Institue",
    //     "publicationYear": 2019
    //   }
    // ]



    private TypeRef<List<Book>> getBookTypeRef() {
        return new TypeRef<List<Book>>() {
            // Kept empty on purpose
        };
    }
}