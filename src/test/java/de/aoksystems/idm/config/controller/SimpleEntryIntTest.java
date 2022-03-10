package de.aoksystems.idm.config.controller;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;
import de.aoksystems.idm.techbase.InsuranceInstitution;
import de.aoksystems.idm.techbase.testing.IntegrationTestRunner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class SimpleEntryIntTest extends IntegrationTestRunner {
	final SimpleEntryDto versionEntry = createDefault("TestVersion", "0.1.0-SNAPSHOT");
	final List<SimpleEntryDto> testEntries = Arrays.asList( versionEntry );

	@Test
	@Order(1)
    public void should_saveVersionEntry_when_allFieldsAreSetAndValid() {
		RestAssured.
			given().
				body(versionEntry).
				auth().oauth2("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJFNlptV1RRSHkwV2V6TFRUTml0RF9RMEVMTUZWNEJpYkY3OWg2SjhRNHJvIn0.eyJleHAiOjE2Mjg3MTUzNDksImlhdCI6MTYyODcxNTA0OSwianRpIjoiOGQ0NjRiMTMtNTE3NS00ZTEzLWJlOTEtNTRmY2E1YmQ5MjIyIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5pZG0tbmlnaHRseS5ydWJ5aGVhbHRoLmI1MS5tc2dydWJ5LmNsb3VkL2F1dGgvcmVhbG1zL2lkbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmMWE1NzE1ZS01NTU0LTQ1NzQtYmQ5Yi1lYTUzOWNkNDVjM2IiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJpZG0tYXBwIiwic2Vzc2lvbl9zdGF0ZSI6ImYwNWJhYWIyLTA5YTctNDM4Yy05MzYzLWU3ZjM4ODI5OTU2NCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImdlbmRlciI6Ik1hbiIsIm5hbWUiOiJBbmRyZWFzIFJlZGVsIiwicGhvbmVzIjoiKzQ5LTExODgwIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYW5kcmVhcy5yZWRlbEBtc2cuZ3JvdXAiLCJlbWFpbEFjdGl2YXRpb24iOiJmYWxzZSIsImdpdmVuX25hbWUiOiJBbmRyZWFzIiwiZmFtaWx5X25hbWUiOiJSZWRlbCIsImVtYWlsIjoiYW5kcmVhcy5yZWRlbEBtc2cuZ3JvdXAifQ.RGzQX36DWDdyYH6THT_MLRbsLuXx2Myg5tloqJz2-KHj_NkNL37ye-quOyhsNiUlirOVBDo2KF2yFv_aRme_thLFPFVReXjuTNI8miZlT5gg0KPD_frB2cnPlxSGs43CT5tmk-pbdbZCRL33wmD0RbuEMlICasAimafjzbzYeTctUyO9drGWNFtQgPfECvQjHP7SbgPjx3U0arygCx8ovDMABymDFLOT572UwxheiWawBNoCIN64GV1OitPh6nVbM_hNXyxQUVeWIL9d8UaTOukJxl4y0Im3Sm5ullm-6BR4R0Gw5RL1p8HBbdYHS3KOkOBOtA9tcBHwuceq4G8CRw").
			when().
				contentType(ContentType.JSON).
				post("/simple").
			then().
				statusCode(200).
				contentType(ContentType.JSON).
			assertThat().
				body("id",      Matchers.not(Matchers.empty())).
				body("version", Matchers.not(Matchers.empty())).
				body("key",     Matchers.is(versionEntry.getKey())).
				body("value",   Matchers.is(versionEntry.getValue()));
	}
	
	@Test
	@Order(2)
	public void should_findVersionEntry_when_searchCriteriaIsValid() {
		RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				queryParam("client", versionEntry.getClient()).
				queryParam("key", versionEntry.getKey()).
				get("/simple").
			then().
				statusCode(200).
				contentType(ContentType.JSON).
			assertThat().
				body("isEmpty()", Matchers.is(false)).
				body("key",       Matchers.is(versionEntry.getKey())).
				body("value",     Matchers.is(versionEntry.getValue())).
				body("client",    Matchers.is(versionEntry.getClient()));
	}
	
	@Test
	@Order(3)
	public void should_findNothing_when_searchCriteriaIsValidButNoEntryExisting() {
		final String response = RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				queryParam("client", InsuranceInstitution.RHEINLAND_HAMBURG.getName()).
				queryParam("key", "Random123.Random321.Random231").
				get("/simple").
			then().
				statusCode(200).
			extract()
				.asString();
		
		Assertions.assertThat(response).
			isEmpty();
	}
	

	@Test
	@Order(4)
	public void should_abortSearch_when_searchCriteriaIsInvalid() {
		RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				queryParam("client", "NotExistingClient").
				get("/simples").
			then().
				statusCode(400).
	    	assertThat().
    			body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(5)
    public void should_notSaveEntry_when_mandatoryFieldsAreNotSet() {
		final SimpleEntryDto invalidEntry = createDefault(null, null);
		
		RestAssured.
			given().
				body(invalidEntry).
			when().
				contentType(ContentType.JSON).
				post("/simple").
			then().
				statusCode(400).
			assertThat().
				body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(6)
    public void should_notSaveEntry_when_mandatoryFieldsAreSetButInvalid() {
		final SimpleEntryDto invalidEntry = createDefault("RemoteDebug", "true");
		invalidEntry.setClient("Invalid Client");
		invalidEntry.setCharacter("Invalid character");
		
		RestAssured.
			given().
				body(invalidEntry).
			when().
				contentType(ContentType.JSON).
				post("/simple").
			then().
				statusCode(400).
			assertThat().
				body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(7)
    public void should_notSaveVersionEntry_when_versionEntryAlreadyExisting() {
		RestAssured.
			given().
				body(versionEntry).
			when().
				contentType(ContentType.JSON).
				post("/simple").
			then().
				statusCode(400).
			assertThat().
				body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(98)
    public void should_findEntries_when_restrictedToTestEntries() {
		final SimpleEntryDto[] entries = findAll();
		
		Assertions.assertThat(entries).
			isNotEmpty().
			hasSizeGreaterThanOrEqualTo(testEntries.size());
	}
	
	@Test
	@Order(99)
    public void should_deleteWithoutAnyFailures_when_testEntriesExisting() {
		final SimpleEntryDto[] entries = findAll();
		
		for (int index = 0; index < entries.length; index++) {
			RestAssured.
				given().
					contentType(ContentType.JSON).
				when().
					delete("/simple/{entryId}", entries[index].getId()).
				then().
					statusCode(204);
		}
	}
	
	private SimpleEntryDto[] findAll() {
		return RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				get("/simples").
			then().
				statusCode(200).
				contentType(ContentType.JSON).
			extract().
				as(SimpleEntryDto[].class);
	}
    
    
	private SimpleEntryDto createDefault(String key, String value) {
		SimpleEntryDto entry = new SimpleEntryDto();
		entry.setClient(InsuranceInstitution.BADEN_WUERTTEMBERG.getName());
		entry.setCharacter(CharacterDto.FIXED);
		entry.setKey(key);
		entry.setValue(value);
		entry.setValid(true);
		
		return entry;
	}
}
