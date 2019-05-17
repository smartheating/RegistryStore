package de.smartheating.repository.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.smartheating.SmartHeatingCommons.TestModel;
import io.swagger.annotations.ApiOperation;

@RestController
public class RepositoryController {
	/**
	 * This method returns a greeting.
	 * 
	 * @return greeting
	 */
	@GetMapping(value = "/repository", produces = "application/json")
	@ApiOperation(value = "Get Greeting")
	public ResponseEntity<?> greeting() {
		TestModel test = new TestModel();
		test.setTest("Repository is up!");
		return new ResponseEntity<>(test, HttpStatus.OK);
	}
}
