package com.mc.jdbc.common.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Errors {
	
	private Map<String, List<String>> errors = new HashMap<String, List<String>>();
	
	public void addError(String property, String message) {
		
		if(errors.containsKey(property)) {
			errors.get(property).add(message);
			return;
		}
		
		List<String> errorMessage = new ArrayList<String>();
		errorMessage.add(message);
		errors.put(property, errorMessage);
	}
	
	@Override
	public String toString() {
		String res = "";
		for (Entry<String, List<String>> entry : errors.entrySet()) {
			res += "[" + entry.getKey() + " : " + entry.getValue() + "]";
		}
		
		return res;
	}
	
	public boolean hasError() {
		return errors.size() > 0;
	}
	
	public Map<String, List<String>> getErrors() {
		return errors;
	}
	
	
	
	
	

}
