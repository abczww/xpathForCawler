package com.cnki.ksp.core.observer;

import java.util.HashMap;
import java.util.Map;

import com.cnki.ksp.core.Observer;

/**
 * it could generate different Observers.
 * 
 * @author william
 * @version 2017.7
 *
 */
public class ObserverFactory {

	private static Map<String, Observer> kspObserverMap = null;
	
	public static final String KSP_FRAME_OBSERVER = "frameObserver";
	public static final String KSP_CMD_OBSERVER = "cmdObserver";

	public static Observer getObserverByName(String typeName) {
		if (kspObserverMap == null) {
			kspObserverMap = new HashMap<>();
		}
		if (kspObserverMap.get(typeName) == null) {
			if (typeName.equals(KSP_FRAME_OBSERVER)) {
				KspObserver kspObserver = KspObserver.getIntance();
				kspObserverMap.put(typeName, kspObserver);
			} else if (typeName.equals(KSP_CMD_OBSERVER)) {
				Observer observer = KspNoWinObserver.getIntance();
				kspObserverMap.put(typeName, observer);
			}
		}
		return kspObserverMap.get(typeName);
	}

}
