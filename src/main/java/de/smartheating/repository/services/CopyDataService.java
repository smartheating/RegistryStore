package de.smartheating.repository.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

@Service
public class CopyDataService {

	/**
	 * Diese Methode kopiert bei der aktualisierung des Users alle Werte welche mit
	 * NULL angegeben sind (Werte die nicht Pflicht sind).
	 * 
	 * @param src
	 * @param target
	 */
	public void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	/**
	 * Diese Methode gibt alle NULL-Werte welche bei der Aktualisierung des Users
	 * übergeben werden/nicht übergeben werden.
	 * 
	 * @param source
	 * @return String[]
	 */
	public String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
	
}
