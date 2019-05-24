package de.smartheating.repository.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.smartheating.SmartHeatingCommons.exceptions.DatabaseConnectionException;
import de.smartheating.SmartHeatingCommons.persistedData.SensorEvent;
import de.smartheating.repository.services.SensorEventService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(path = "/sensorevents")
public class SensorEventController {
	
	Logger logger = LoggerFactory.getLogger(SensorEventController.class);

	@Autowired
	SensorEventService sensorEventService;

	@PostMapping(produces = "application/json")
	@ApiOperation(value = "Persist sensor events inside the database")
	public ResponseEntity<?> addEvent(@RequestBody SensorEvent sensorEvent) {
		try {
			logger.info("Got request to add a new sensor event from the sensor with id: " + sensorEvent.getSensorId());
			return new ResponseEntity<>(sensorEventService.addEvent(sensorEvent), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Get all sensor events from the database")
	public ResponseEntity<?> getEvents() {
		try {
			logger.info("Got request to get all sensor events");
			return new ResponseEntity<>(sensorEventService.getEvents(), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Get a single sensor event inside the database")
	public ResponseEntity<?> getEventById(@PathVariable Long id) {
		try {
			logger.info("Got request to get the sensor event with the id: " + id);
			return new ResponseEntity<>(sensorEventService.getEventById(id), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Remove a sensor event from the database")
	public ResponseEntity<?> removeEvent(@PathVariable Long id) {
		try {
			logger.info("Got request to remove the sensor event with the id: " + id);
			sensorEventService.removeEvent(id);
			return new ResponseEntity<>("Sensor deleted!", HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
