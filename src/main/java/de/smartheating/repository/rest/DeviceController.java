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
import de.smartheating.SmartHeatingCommons.persistedData.Device;
import de.smartheating.repository.services.DeviceService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(path = "/devices")
public class DeviceController {
	
	Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	DeviceService deviceService;

	@PostMapping(produces = "application/json")
	@ApiOperation(value = "Persist device data inside the database")
	public ResponseEntity<?> addDevice(@RequestBody Device device) {
		try {
			logger.info("Got request to add a new device with the name: " + device.getDeviceName());
			return new ResponseEntity<>(deviceService.addDevice(device), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Get all devices from the database")
	public ResponseEntity<?> getDevices() {
		try {
			logger.info("Got request to get all devices");
			return new ResponseEntity<>(deviceService.getDevices(), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Get a single device inside the database")
	public ResponseEntity<?> getDeviceById(@PathVariable Long id) {
		try {
			logger.info("Got request to get the device with the id: " + id);
			return new ResponseEntity<>(deviceService.getDeviceById(id), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Update a device that is already inside the database")
	public ResponseEntity<?> updateDevice(@PathVariable Long id, @RequestBody Device device) {
		try {
			logger.info("Got request to update a device with the id: " + id);
			return new ResponseEntity<>(deviceService.updateDevice(device, id), HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	

	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiOperation(value = "Remove a device from the database")
	public ResponseEntity<?> removeDevice(@PathVariable Long id) {
		try {
			logger.info("Got request to remove the device with the id: " + id);
			deviceService.removeDevice(id);
			return new ResponseEntity<>("Device deleted!", HttpStatus.OK);
		} catch (DatabaseConnectionException d) {
			logger.error("Could not connect with database: " + d.getMessage());
			return new ResponseEntity<>(d.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}