package com.cnki.ksp.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateHelper {

	private DateHelper() {

	}

	private static DateHelper dateHelper;

	public static synchronized DateHelper getInstance() {
		if (null == dateHelper) {
			dateHelper = new DateHelper();
		}
		return dateHelper;
	}

	public String getFormatedDate(String inputDate) {
		String reDate = useType1(inputDate);
		if (reDate.equals("")) {
			reDate = useType2(inputDate);
		}
		return reDate;
	}

	private String useType1(String inputDate) {
		Pattern pattern = Pattern.compile("[0-9]{4}[年][0-9]{1,2}[月][0-9]{1,2}[日]");
		Matcher matcher = pattern.matcher(inputDate);

		String dateStr = null;
		if (matcher.find()) {
			dateStr = matcher.group(0);
		}

		String[] arr = null;
		if (null != dateStr) {
			arr = dateStr.split("(年|月|日)");
		} else {
			return "";
		}
		String reDate = "";
		for (String item : arr) {
			reDate = reDate + item + "-";
		}

		if (!reDate.equals("")) {
			reDate = reDate.substring(0, reDate.length() - 1);
		}

		return reDate;
	}

	private String useType2(String inputDate) {
		Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
		Matcher matcher = pattern.matcher(inputDate);

		String dateStr = null;
		if (matcher.find()) {
			dateStr = matcher.group(0);
		}

		String[] arr = null;
		if (null != dateStr) {
			arr = dateStr.split("(-)");
		}
		String reDate = "";
		for (String item : arr) {
			reDate = reDate + item + "-";
		}

		if (!reDate.equals("")) {
			reDate = reDate.substring(0, reDate.length() - 1);
		}

		return reDate;
	}

}
