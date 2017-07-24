package com.cnki.ksp.frame;

import com.cnki.ksp.controller.KSPController;
import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.Observer;
import com.cnki.ksp.core.StyleChangedException;
import com.cnki.ksp.core.observer.KspNoWinObserver;
import com.cnki.ksp.core.observer.ObserverFactory;

/**
 * the entrance of ksp in Linux.
 * 
 * @author william
 * @version 2017.7
 *
 */
public class KScorpion {
	
	private void startAutohomeScorpion() {
		try {
			Observer observer = ObserverFactory.getObserverByName(ObserverFactory.KSP_CMD_OBSERVER);
			KSPController tsp = AppContext.getBean(KSPController.class);
			for (CrawlerController cc : tsp.getCrawlers()) {
				cc.init(observer);
				cc.run();
			}
		} catch (StyleChangedException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		KScorpion ksp = new KScorpion();
		ksp.startAutohomeScorpion();
	}

}
