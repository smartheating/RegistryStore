package de.smartheating.repository.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.smartheating.SmartHeatingCommons.databaseInterfaces.EventRepository;
import de.smartheating.SmartHeatingCommons.exceptions.DatabaseConnectionException;
import de.smartheating.SmartHeatingCommons.persistedData.Event;
import javassist.NotFoundException;

@Service
public class EventService {
	
	Logger logger = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	EventRepository eventRepo;
	@Autowired
	CopyDataService copyService;

	public Event addEvent(Event event) throws DatabaseConnectionException {
		logger.info("Trying to save a event from the device with the id: " + event.getDeviceId());
		return eventRepo.save(event);
	}

	public void removeEvent(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find event with id: " + id);
	    Optional<Event> savedSensorEvent = eventRepo.findById(id);
	    if (!savedSensorEvent.isPresent()) {
	    	logger.info("Event with id: '" + id + "' doesn't exist");
	    	throw new NotFoundException("There is no event with id: " + id);
	    }
	   
	    eventRepo.delete(savedSensorEvent.get());
	}

	public Event getEventById(Long id) throws DatabaseConnectionException, NotFoundException {
		logger.info("Trying to find event with id: " + id);
	    Optional<Event> savedEvent = eventRepo.findById(id);
	    if (!savedEvent.isPresent()) {
	    	logger.info("Event with id: '" + id + "' doesn't exist");
	    	throw new NotFoundException("There is no event with id: " + id);
	    }
	    
	    return savedEvent.get();
	}
	
	public Iterable<Event> getEvents() throws DatabaseConnectionException {
		logger.info("Trying to read all events from database");
        return eventRepo.findAll();
	}

	public void removeAllEvents() throws DatabaseConnectionException {
		logger.info("Trying to remove all events from the database");
		eventRepo.deleteAll();
	}
}
