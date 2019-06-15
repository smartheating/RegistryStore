package de.smartheating.repository.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.smartheating.SmartHeatingCommons.databaseInterfaces.DeviceRepository;
import de.smartheating.SmartHeatingCommons.exceptions.DatabaseConnectionException;
import de.smartheating.SmartHeatingCommons.persistedData.Device;
import javassist.NotFoundException;

@Service
public class DeviceService {
	
	Logger logger = LoggerFactory.getLogger(DeviceService.class);
	
	@Autowired
	DeviceRepository deviceRepo;
	@Autowired
	CopyDataService copyService;

	public Device addDevice(Device device) throws DatabaseConnectionException {
		logger.info("Got device with name: " + device.getDeviceName());
		Device savedDevice = deviceRepo.save(device);
		logger.info("Saved device data with id: " + savedDevice.getId());
		return savedDevice;
	}

	public Device updateDevice(Device newDeviceData, Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find device data with id: " + id);
	    Optional<Device> savedDevice = deviceRepo.findById(id);
	    if (!savedDevice.isPresent()) {
	    	logger.info("Sensor data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no sensor with id: " + id);
	    }
	    
	    // Update saved instance with incoming properties that are not null
	    copyService.copyNonNullProperties(newDeviceData, savedDevice.get());
	    // Update saved instance inside the database
	    return deviceRepo.save(savedDevice.get());
	}

	public void removeDevice(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find device data with id: " + id);
	    Optional<Device> savedDevice = deviceRepo.findById(id);
	    if (!savedDevice.isPresent()) {
	    	logger.info("Device data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no device with id: " + id);
	    }
	   
	    deviceRepo.delete(savedDevice.get());
	}

	public Device getDeviceById(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find device data with id: " + id);
	    Optional<Device> savedDevice = deviceRepo.findById(id);
	    if (!savedDevice.isPresent()) {
	    	logger.info("Device data with id: '" + id + "' don't exist");
	    	throw new NotFoundException("There is no device with id: " + id);
	    }
	    
	    return savedDevice.get();
	}
	
	public Iterable<Device> getDevices() throws DatabaseConnectionException {
		logger.info("Trying to read devices from database");
        return deviceRepo.findAll();
	}

}
