package utils;

import java.util.HashMap;

public class ScenarioContext {
	
	public static HashMap<String, Object> mpp = new HashMap<>();
	
	public static void set(String key, Object value) {
		mpp.put(key, value);
	}
	
	public static Object get(String key) {
		return mpp.get(key);
	}

}
