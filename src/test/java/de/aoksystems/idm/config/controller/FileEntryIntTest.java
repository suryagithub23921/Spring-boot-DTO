package de.aoksystems.idm.config.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.FileContentDto;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.techbase.InsuranceInstitution;
import de.aoksystems.idm.techbase.testing.IntegrationTestRunner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class FileEntryIntTest extends IntegrationTestRunner {
	final FileEntryDto logEntry = createDefault("TestLogConfig", "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPGNvbmZpZ3VyYXRpb24+CjwvY29uZmlndXJhdGlvbj4=");
	final List<FileEntryDto> testEntries = Arrays.asList( logEntry );

	@Test
	@Order(1)
    public void should_saveLogEntry_when_allFieldsAreSetAndValid() {
		RestAssured.
			given().
				body(logEntry).
			when().
				contentType(ContentType.JSON).
				post("/file").
			then().
				statusCode(200).
				contentType(ContentType.JSON).
			assertThat().
				body("id",      Matchers.not(Matchers.empty())).
				body("version", Matchers.not(Matchers.empty())).
				body("key",     Matchers.is(logEntry.getKey()));;
	}
	

	@Test
	@Order(2)
	public void should_findLogEntry_when_searchCriteriaIsValid() {
		RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				queryParam("client", logEntry.getClient()).
				queryParam("key", logEntry.getKey()).
				get("/file").
			then().
				statusCode(200).
				contentType(ContentType.JSON).
			assertThat().
				body("isEmpty()", Matchers.is(false)).
				body("key",       Matchers.is(logEntry.getKey())).
				body("client",    Matchers.is(logEntry.getClient()));
	}
	
	@Test
	@Order(3)
	public void should_findNothing_when_searchCriteriaIsValidButNoEntryExisting() {
		final String response = RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				queryParam("client", InsuranceInstitution.SACHSEN_ANHALT.getName()).
				queryParam("key", "Random123.Random321.Random231").
				get("/file").
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
				get("/files").
			then().
				statusCode(400).
	    	assertThat().
    			body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(5)
    public void should_notSaveEntry_when_mandatoryFieldsAreNotSet() {
		final FileEntryDto invalidEntry = createDefault(null, null);
		
		RestAssured.
			given().
				body(invalidEntry).
			when().
				contentType(ContentType.JSON).
				post("/file").
			then().
				statusCode(400).
			assertThat().
				body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(6)
    public void should_notSaveEntry_when_mandatoryFieldsAreSetButInvalid() {
		final FileEntryDto invalidEntry = createDefault("RemoteDebug", "true");
		invalidEntry.setClient("Invalid Client");
		invalidEntry.setCharacter("Invalid character");
		
		RestAssured.
			given().
				body(invalidEntry).
			when().
				contentType(ContentType.JSON).
				post("/file").
			then().
				statusCode(400).
			assertThat().
				body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(7)
    public void should_notSaveLogEntry_when_versionEntryAlreadyExisting() {
		RestAssured.
			given().
				body(logEntry).
			when().
				contentType(ContentType.JSON).
				post("/file").
			then().
				statusCode(400).
			assertThat().
				body("data", Matchers.nullValue());
	}
	
	@Test
	@Order(98)
    public void should_findEntries_when_restrictedToTestEntries() {
		final FileEntryDto[] entries = findAll();
		
		Assertions.assertThat(entries).
			isNotEmpty().
			hasSizeGreaterThanOrEqualTo(testEntries.size());
	}
	
	@Test
	@Order(99)
    public void should_deleteWithoutAnyFailures_when_testEntriesExisting() {
		final FileEntryDto[] entries = findAll();
		
		for (int index = 0; index < entries.length; index++) {
			RestAssured.
				given().
					contentType(ContentType.JSON).
				when().
					delete("/file/{entryId}", entries[index].getId()).
				then().
					statusCode(204);
		}
	}
	
	private FileEntryDto[] findAll() {
		return RestAssured.
			given().
			when().
				contentType(ContentType.JSON).
				get("/files").
			then().
				statusCode(200).
				contentType(ContentType.JSON).
			extract().
				as(FileEntryDto[].class);
	}
    
    
	private FileEntryDto createDefault(String key, String value) {
		FileContentDto content = null;
		
		if (value != null) {
			content = new FileContentDto();
			content.setBinary(StandardCharsets.UTF_8.encode(value).array());
		}
		
		FileEntryDto entry = new FileEntryDto();
		entry.setClient(InsuranceInstitution.HESSEN.getName());
		entry.setCharacter(CharacterDto.FIXED);
		entry.setKey(key);
		entry.setValid(true);
		entry.setExtension("xml");
		entry.setLocation("/tmp");
		entry.setContent(content);
		
		return entry;
	}
}
