package de.smartheating.repository.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.smartheating.SmartHeatingCommons.databaseInterfaces.SensorRepository;
import de.smartheating.SmartHeatingCommons.exceptions.DatabaseConnectionException;
import de.smartheating.SmartHeatingCommons.persistedData.Sensor;
import javassist.NotFoundException;

@Service
public class SensorService {
	
	Logger logger = LoggerFactory.getLogger(SensorService.class);
	
	@Autowired
	SensorRepository sensorRepo;
	@Autowired
	CopyDataService copyService;

	public Sensor addSensor(Sensor sensor) throws DatabaseConnectionException {
		logger.info("Got sensor with name: " + sensor.getSensorName());
		Sensor savedSensor = sensorRepo.save(sensor);
		logger.info("Saved sensor data with id: " + savedSensor.getId());
		return savedSensor;
	}

	public Sensor updateSensor(Sensor newSensorData, Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find sensor data with id: " + id);
	    Optional<Sensor> savedSensor = sensorRepo.findById(id);
	    if (!savedSensor.isPresent()) {
	    	logger.info("Sensor data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no sensor with id: " + id);
	    }
	    
	    // Update saved instance with incoming properties that are not null
	    copyService.copyNonNullProperties(newSensorData, savedSensor.get());
	    // Update saved instance inside the database
	    return sensorRepo.save(savedSensor.get());
	}

	public void removeSensor(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find sensor data with id: " + id);
	    Optional<Sensor> savedSensor = sensorRepo.findById(id);
	    if (!savedSensor.isPresent()) {
	    	logger.info("Sensor data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no sensor with id: " + id);
	    }
	   
	    sensorRepo.delete(savedSensor.get());
	}

	public Sensor getSensorById(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find sensor data with id: " + id);
	    Optional<Sensor> savedSensor = sensorRepo.findById(id);
	    if (!savedSensor.isPresent()) {
	    	logger.info("Sensor data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no sensor with id: " + id);
	    }
	    
	    return savedSensor.get();
	}
	
	public Iterable<Sensor> getSensors() throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to read sensors from database");
        return sensorRepo.findAll();
	}

}
