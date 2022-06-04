package com.issue;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class AddComment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=10;
		RestAssured.baseURI = "http://localhost:8080";
		SessionFilter filter=new SessionFilter();
		String response = given().log().all().header("Content-Type", "application/json")
				.body("{ \"username\": \"naveenkurella9700\", \"password\": \"9700919120\" }").log().all().filter(filter).when()
				.log().all()
				.post("/rest/auth/1/session").then().log().all().extract().asString();

		// Add Comment
		given().pathParam("issueIdOrKey", "10100").log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"Second Comment from API.\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(filter)
				.when().log().all().post("/rest/api/2/issue/{issueIdOrKey}/comment")
				.then().assertThat().statusCode(201);
		
		//Add Attachment
		given().pathParam("issueIdOrKey", "10100").header("X-Atlassian-Token","no-check")
		.filter(filter)
		.header("Content-Type", "multipart/form-data")
		.multiPart("file",new File("jira.txt"))
		.when().log().all().post("/rest/api/2/issue/{issueIdOrKey}/attachments")
		.then().assertThat().statusCode(200);
		
	}

}
