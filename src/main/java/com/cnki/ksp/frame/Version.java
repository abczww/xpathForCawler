package com.cnki.ksp.frame;

import java.util.HashMap;
import java.util.Map;

public class Version {

	public static final String LATEST_VERSION = "V1.0.2";

	private static Map<String, String> versions = new HashMap<String, String>();
	{
		versions.put("V1.0.1", "import hibernate, get rid of mybatis");
		versions.put("V1.0.2", "develop check style class to check/find the changed style");

	}

}
