package de.smartheating.repository.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.smartheating.SmartHeatingCommons.databaseInterfaces.SensorEventRepository;
import de.smartheating.SmartHeatingCommons.databaseInterfaces.DeviceRepository;
import de.smartheating.SmartHeatingCommons.exceptions.DatabaseConnectionException;
import de.smartheating.SmartHeatingCommons.persistedData.SensorEvent;
import javassist.NotFoundException;

@Service
public class SensorEventService {
	
	Logger logger = LoggerFactory.getLogger(SensorEventService.class);
	
	@Autowired
	SensorEventRepository eventRepo;
	@Autowired
	DeviceRepository sensorRepo;
	@Autowired
	CopyDataService copyService;

	public SensorEvent addEvent(SensorEvent sensorEvent) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to save a sensor event from the device with the id: " + sensorEvent.getSensorId());
		return eventRepo.save(sensorEvent);
	}

	public void removeEvent(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find sensor event with id: " + id);
	    Optional<SensorEvent> savedSensorEvent = eventRepo.findById(id);
	    if (!savedSensorEvent.isPresent()) {
	    	logger.info("Sensor event with id: '" + id + "' doesn't exist");
	    	throw new NotFoundException("There is no sensor event with id: " + id);
	    }
	   
	    eventRepo.delete(savedSensorEvent.get());
	}

	public SensorEvent getEventById(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find sensor event with id: " + id);
	    Optional<SensorEvent> savedSensorEvent = eventRepo.findById(id);
	    if (!savedSensorEvent.isPresent()) {
	    	logger.info("Sensor data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no sensor with id: " + id);
	    }
	    
	    return savedSensorEvent.get();
	}
	
	public Iterable<SensorEvent> getEvents() throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to read all sensors events from database");
        return eventRepo.findAll();
	}
}
