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
import de.smartheating.SmartHeatingCommons.persistedData.Event;
import de.smartheating.repository.services.EventService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(path = "/events")
public class EventController {
	
	Logger logger = LoggerFactory.getLogger(EventController.class);

	@Autowired
	EventService eventService;

	@PostMapping(produces = "application/json")
	@ApiOperation(value = "Persist events inside the database")
	public ResponseEntity<?> addEvent(@RequestBody Event event) {
		try {
			logger.info("Got request to add a new event from the device with id: " + event.getDeviceId());
			return new ResponseEntity<>(eventService.addEvent(event), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Get all events from the database")
	public ResponseEntity<?> getEvents() {
		try {
			logger.info("Got request to get all events");
			return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Get a single event inside the database")
	public ResponseEntity<?> getEventById(@PathVariable Long id) {
		try {
			logger.info("Got request to get the event with the id: " + id);
			return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Remove a event from the database")
	public ResponseEntity<?> removeEvent(@PathVariable Long id) {
		try {
			logger.info("Got request to remove the event with the id: " + id);
			eventService.removeEvent(id);
			return new ResponseEntity<>("Event deleted!", HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
