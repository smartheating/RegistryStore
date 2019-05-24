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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.smartheating.SmartHeatingCommons.exceptions.DatabaseConnectionException;
import de.smartheating.SmartHeatingCommons.persistedData.Sensor;
import de.smartheating.repository.services.SensorService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(path = "/sensors")
public class SensorController {
	
	Logger logger = LoggerFactory.getLogger(SensorController.class);

	@Autowired
	SensorService sensorService;

	@PostMapping(produces = "application/json")
	@ApiOperation(value = "Persist sensor data inside the database")
	public ResponseEntity<?> addSensor(@RequestBody Sensor sensor) {
		try {
			logger.info("Got request to add a new sensor with the name: " + sensor.getSensorName());
			return new ResponseEntity<>(sensorService.addSensor(sensor), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Get all sensors from the database")
	public ResponseEntity<?> getSensors() {
		try {
			logger.info("Got request to get all sensors");
			return new ResponseEntity<>(sensorService.getSensors(), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Get a single sensor inside the database")
	public ResponseEntity<?> getSensorById(@PathVariable Long id) {
		try {
			logger.info("Got request to get the sensor with the id: " + id);
			return new ResponseEntity<>(sensorService.getSensorById(id), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Update a sensor that is already inside the database")
	public ResponseEntity<?> updateSensor(@PathVariable Long id, @RequestBody Sensor sensor) {
		try {
			logger.info("Got request to update a sensor with the id: " + id);
			return new ResponseEntity<>(sensorService.updateSensor(sensor, id), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	

	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Remove a sensor from the database")
	public ResponseEntity<?> removeSensor(@PathVariable Long id) {
		try {
			logger.info("Got request to remove the sensor with the id: " + id);
			sensorService.removeSensor(id);
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