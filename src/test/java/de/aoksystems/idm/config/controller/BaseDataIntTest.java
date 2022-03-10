package de.aoksystems.idm.config.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import de.aoksystems.idm.techbase.testing.IntegrationTestRunner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class BaseDataIntTest extends IntegrationTestRunner {
	
	@Test
    public void should_listAllExistingClients_when_calledWithoutFilterCriteria() {
		RestAssured.
    		given().
    		when().
    			contentType(ContentType.JSON).
    			get("/clients").
    		then().
    			statusCode(200).
    			contentType(ContentType.JSON).
    		assertThat().
    			body("size()", Matchers.is(15)).
    			body("id",     Matchers.not(Matchers.empty())).
    			body("name",   Matchers.not(Matchers.empty()));
	}

	@Test
    public void should_listAllExistingCharacters_when_calledWithoutFilterCriteria() {
		RestAssured.
    		given().
    		when().
    			contentType(ContentType.JSON).
    			get("/characters").
    		then().
    			statusCode(200).
    			contentType(ContentType.JSON).
    		assertThat().
    			body("size()", Matchers.is(3)).
    			body("id",     Matchers.not(Matchers.empty())).
    			body("type",   Matchers.not(Matchers.empty()));
	}
}
