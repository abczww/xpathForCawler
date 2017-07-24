package com.cnki.ksp.core.observer;

import java.util.HashMap;
import java.util.Map;

import com.cnki.ksp.core.Observer;

/**
 * it could generate different Observers.
 * 
 * @author windhut
 * @version 2017.7
 *
 */
public class ObserverFactory {

	private static Map<String, Observer> kspObserverMap = null;

	public static Observer getObserverByName(String className) {
		if (kspObserverMap == null) {
			kspObserverMap = new HashMap<>();
		}
		if (kspObserverMap.get(className) == null) {
			if (className.equals(KspObserver.class.getName())) {
				KspObserver kspObserver = KspObserver.getIntance();
				kspObserverMap.put(className, kspObserver);
			} else if (className.equals(KspNoWinObserver.class.getName())) {
				Observer observer = KspNoWinObserver.getIntance();
				kspObserverMap.put(className, observer);
			}
		}
		return kspObserverMap.get(className);
	}

}
