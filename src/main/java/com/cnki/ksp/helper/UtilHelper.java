package com.cnki.ksp.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilHelper {

	/**
	 * change the time from one format to another format. if ParseException
	 * happened, return current date.
	 * 
	 * @param time,
	 *            the input time.
	 * @param timeFormat,
	 *            the input time format.
	 * @param dateFormat,
	 *            the output date format.
	 * @return
	 */
	public static final String getDateFromTimeByFormat(String time, String timeFormat, String dateFormat) {
		DateFormat tf = new SimpleDateFormat(timeFormat);
		DateFormat df = new SimpleDateFormat(dateFormat);
		Date date_time;
		try {
			date_time = tf.parse(time);
		} catch (Exception e) {
			System.out.printf("Error time format: %s, return current date.\n", time);
			return df.format(new Date());
		}

		String dateStr = df.format(date_time);
		return dateStr;

	}

}
