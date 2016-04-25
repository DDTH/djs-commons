package com.github.ddth.djs.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Cron-like format for DJS.
 * 
 * <p>
 * Format: {@code <Second (0-59)> <Minute (0-59)> 
 * <Hour (0-23)> <DoM (1-31)> <MoY (1-12)> <DoW (1:Monday-7:Sunday)>}
 * </p>
 * <p>
 * In {@code Month of Year} and {@code Day of Week} fields, full names (
 * {@code "January,February,...,December"} or
 * {@code "Monday,Tuesday,...,Sunday"}) or abbreviations (
 * {@code "Jan,Feb,...,Dec"} or {@code "Mon,Tue,...,Sun"}) can be used instead
 * of numeric values.
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class CronFormat {

	/**
	 * Parses a cron format from a plain text string.
	 * 
	 * @param input
	 *            either short format {@code <Second> <Minute> <Hour>} or full
	 *            format
	 *            {@code <Second> <Minute> <Hour> <Day_Of_Month> <Month> <Day_Of_Week>}
	 * @return
	 */
	public static CronFormat parse(String input) {
		String[] tokens = input.trim().split("[\\s\\t]+");
		if (tokens == null || (tokens.length != 3 && tokens.length != 6)) {
			throw new IllegalArgumentException("Invalid input [" + input + "]!");
		}
		if (tokens.length == 3) {
			return new CronFormat(tokens[0], tokens[1], tokens[2]);
		}
		if (tokens.length == 6) {
			return new CronFormat(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
		}
		return null;
	}

	private final static Pattern PATTERN_TICK = Pattern.compile("^\\*\\/(\\d+)$");
	private final static Pattern PATTERN_RANGE = Pattern.compile("^(\\d+)-(\\d+)$");
	private final static Pattern PATTERN_RANGE_NAME = Pattern.compile("^([A-Z]+)-([A-Z]+)$", Pattern.CASE_INSENSITIVE);
	private final static Pattern PATTERN_EXACT = Pattern.compile("^(\\d+)$");
	private final static Pattern PATTERN_EXACT_NAME = Pattern.compile("^([A-Z]+)$", Pattern.CASE_INSENSITIVE);

	/**
	 * Matches against a cron part.
	 * 
	 * @param value
	 *            value to match
	 * @param pattern
	 *            cron format, either {@code *}, {@code *}{@code /3} or
	 *            {@code 1,3,5,7-10}
	 * @return
	 */
	public static boolean matches(int value, String pattern) {
		String[] tokens = pattern.trim().split("[,;]+");
		for (String token : tokens) {
			if (StringUtils.equals(token, "*")) {
				return true;
			}

			Matcher mTick = PATTERN_TICK.matcher(token);
			if (mTick.matches()) {
				int div = Integer.parseInt(mTick.group(1));
				if ((div > 0) && (value % div == 0)) {
					return true;
				}
				continue;
			}

			Matcher mExact = PATTERN_EXACT.matcher(token);
			if (mExact.matches()) {
				if (value == Integer.parseInt(token)) {
					return true;
				}
				continue;
			}

			Matcher mRange = PATTERN_RANGE.matcher(token);
			if (mRange.matches()) {
				int min = Integer.parseInt(mRange.group(1));
				int max = Integer.parseInt(mRange.group(2));
				if (min <= value && value <= max) {
					return true;
				}
				continue;
			}
		}
		return false;
	}

	private static boolean matchesName(int value, String[] nameList, String pattern) {
		String name = nameList[value - 1];
		String[] tokens = pattern.trim().split("[,;]+");
		for (String token : tokens) {
			if (StringUtils.equals(token, "*")) {
				return true;
			}

			Matcher mTick = PATTERN_TICK.matcher(token);
			if (mTick.matches()) {
				int div = Integer.parseInt(mTick.group(1));
				if ((div > 0) && (value % div == 0)) {
					return true;
				}
				continue;
			}

			Matcher mExact = PATTERN_EXACT.matcher(token);
			if (mExact.matches()) {
				if (value == Integer.parseInt(token)) {
					return true;
				}
				continue;
			}
			Matcher mExactName = PATTERN_EXACT_NAME.matcher(token);
			if (mExactName.matches()) {
				String g = mExactName.group(1);
				if (g.length() >= 3 && StringUtils.startsWithIgnoreCase(name, g)) {
					return true;
				}
				continue;
			}

			Matcher mRange = PATTERN_RANGE.matcher(token);
			if (mRange.matches()) {
				int min = Integer.parseInt(mRange.group(1));
				int max = Integer.parseInt(mRange.group(2));
				if (min <= value && value <= max) {
					return true;
				}
				continue;
			}
			Matcher mRangeName = PATTERN_RANGE_NAME.matcher(token);
			if (mRangeName.matches()) {
				String minName = mRangeName.group(1);
				String maxName = mRangeName.group(2);
				int min = 0, max = 0;
				for (int i = 0; i < nameList.length; i++) {
					if (minName.length() >= 3 && StringUtils.startsWithIgnoreCase(nameList[i], minName)) {
						min = i + 1;
					}
					if (maxName.length() >= 3 && StringUtils.startsWithIgnoreCase(nameList[i], maxName)) {
						max = i + 1;
					}
				}
				if (min > 0 && max > 0 && min <= value && value <= max) {
					return true;
				}
				continue;
			}
		}
		return false;
	}

	/**
	 * Matches against the DoW cron part.
	 * 
	 * @param value
	 *            {@code 1:Monday} to {@code 7:Sunday}.
	 * @param pattern
	 *            either {@code *}, {@code *}{@code /3}, {@code 1,2,3-5} or
	 *            {@code Mon,Tue-Sat}
	 * @return
	 */
	public static boolean matchesDow(int value, String pattern) {
		if (value < MONDAY || value > SUNDAY) {
			return false;
		}
		return matchesName(value, DOW_LIST, pattern);
	}

	/**
	 * Matches against the Month cron part.
	 * 
	 * @param value
	 *            {@code 1:January} to {@code 12:Decembers}.
	 * @param pattern
	 *            either {@code *}, {@code *}{@code /3}, {@code 1,2,3-5} or
	 *            {@code Jan,Mar,June-December}
	 * @return
	 */
	public static boolean matchesMonth(int value, String pattern) {
		if (value < JANUARY || value > DECEMBER) {
			return false;
		}
		return matchesName(value, MONTH_LIST, pattern);
	}

	public final static int MONDAY = 1, TUESDAY = 2, WEDNESDAY = 3, THURSDAY = 4, FRIDAY = 5, SATURDAY = 6, SUNDAY = 7;
	public final static int JANUARY = 1, FEBRUARY = 2, MARCH = 3, APRIL = 4, MAY = 5, JUNE = 6, JULY = 7, AUGUST = 8,
			SEPTEMBER = 9, OCTOBER = 10, NOVEMBER = 11, DECEMBER = 12;
	private final static String[] DOW_LIST = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
			"SUNDAY" };
	private final static String[] MONTH_LIST = { "JANUARY", "FEBRUARY ", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
			"AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };

	private String second = "*", minute = "*", hour = "*", dayOfMonth = "*", month = "*", dayOfWeek = "*";

	public CronFormat() {
	}

	public CronFormat(String second, String minute, String hour) {
		setSecond(second);
		setMinute(minute);
		setHour(hour);
	}

	public CronFormat(String second, String minute, String hour, String dayOfMonth, String month, String dayOfWeek) {
		setSecond(second);
		setMinute(minute);
		setHour(hour);
		setDayOfMonth(dayOfMonth);
		setMonth(month);
		setDayOfWeek(dayOfWeek);
	}

	/**
	 * Matches this cron format against a timestamp.
	 * 
	 * @param timestampMillis
	 * @return
	 */
	public boolean matches(long timestampMillis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestampMillis);
		return matches(cal);
	}

	/**
	 * Matches this cron format against a timestamp.
	 * 
	 * @param timestamp
	 * @return
	 */
	public boolean matches(Date timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		return matches(cal);
	}

	/**
	 * Matches this cron format against a timestamp.
	 * 
	 * @param timestamp
	 * @return
	 */
	public boolean matches(Calendar timestamp) {
		int valSecond = timestamp.get(Calendar.SECOND);
		if (!matches(valSecond, this.second)) {
			return false;
		}

		int valMinute = timestamp.get(Calendar.MINUTE);
		if (!matches(valMinute, this.minute)) {
			return false;
		}

		int valHour = timestamp.get(Calendar.HOUR_OF_DAY);
		if (!matches(valHour, this.hour)) {
			return false;
		}

		int valDay = timestamp.get(Calendar.DAY_OF_MONTH);
		if (!matches(valDay, this.dayOfMonth)) {
			return false;
		}

		int valMonth = timestamp.get(Calendar.MONTH) + 1;
		if (!matchesMonth(valMonth, this.month)) {
			return false;
		}

		int _valDow = timestamp.get(Calendar.DAY_OF_WEEK);
		int valDow = 0;
		switch (_valDow) {
		case Calendar.MONDAY:
			valDow = MONDAY;
			break;
		case Calendar.TUESDAY:
			valDow = TUESDAY;
			break;
		case Calendar.WEDNESDAY:
			valDow = WEDNESDAY;
			break;
		case Calendar.THURSDAY:
			valDow = THURSDAY;
			break;
		case Calendar.FRIDAY:
			valDow = FRIDAY;
			break;
		case Calendar.SATURDAY:
			valDow = SATURDAY;
			break;
		case Calendar.SUNDAY:
			valDow = SUNDAY;
			break;
		}
		if (!matchesDow(valDow, this.dayOfWeek)) {
			return false;
		}

		return true;
	}

	/*----------------------------------------------------------------------*/

	public String getSecond() {
		return second;
	}

	public CronFormat setSecond(String second) {
		this.second = second;
		return this;
	}

	public String getMinute() {
		return minute;
	}

	public CronFormat setMinute(String minute) {
		this.minute = minute;
		return this;
	}

	public String getHour() {
		return hour;
	}

	public CronFormat setHour(String hour) {
		this.hour = hour;
		return this;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public CronFormat setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
		return this;
	}

	public String getMonth() {
		return month;
	}

	public CronFormat setMonth(String month) {
		this.month = month;
		return this;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public CronFormat setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		return this;
	}

	public static void main(String[] args) {
		{
			int value = 1;
			String pattern = "*";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t\t " + matches(value, pattern));

			value = 1;
			pattern = "*/2";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t " + matches(value, pattern));

			value = 2;
			pattern = "*/2";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t " + matches(value, pattern));

			value = 3;
			pattern = "1,3,5";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t " + matches(value, pattern));

			value = 16;
			pattern = "1,3,5-17";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t " + matches(value, pattern));

			value = 2;
			pattern = "1,3,5-7";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t " + matches(value, pattern));

			value = 8;
			pattern = "1,3,5-7";
			System.out.println("[" + value + "] matches again [" + pattern + "]:\t " + matches(value, pattern));
		}
		System.out.println("--------------------------------------------------");
		{
			int value = MONDAY;
			String pattern = "*";
			System.out.println("[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t\t "
					+ matchesDow(value, pattern));

			value = TUESDAY;
			pattern = "*/2";
			System.out.println("[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t\t "
					+ matchesDow(value, pattern));

			value = WEDNESDAY;
			pattern = "*/2";
			System.out.println(
					"[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t " + matchesDow(value, pattern));

			value = TUESDAY;
			pattern = "mon,TUE,Wednesday";
			System.out.println(
					"[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t " + matchesDow(value, pattern));

			value = FRIDAY;
			pattern = "mon,TUE,Wed-Saturday";
			System.out.println(
					"[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t " + matchesDow(value, pattern));

			value = TUESDAY;
			pattern = "mon,Wed-Saturday";
			System.out.println(
					"[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t " + matchesDow(value, pattern));

			value = SUNDAY;
			pattern = "mon,Wed-Saturday";
			System.out.println(
					"[" + DOW_LIST[value - 1] + "] matches again [" + pattern + "]:\t " + matchesDow(value, pattern));
		}
		System.out.println("--------------------------------------------------");
		{
			int value = JANUARY;
			String pattern = "*";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t\t\t "
					+ matchesMonth(value, pattern));

			value = FEBRUARY;
			pattern = "*/2";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t\t "
					+ matchesMonth(value, pattern));

			value = MARCH;
			pattern = "*/2";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t\t\t "
					+ matchesMonth(value, pattern));

			value = FEBRUARY;
			pattern = "jan,February,APRil";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t\t "
					+ matchesMonth(value, pattern));

			value = MAY;
			pattern = "jan,February,APR-NOVember";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t "
					+ matchesMonth(value, pattern));

			value = MARCH;
			pattern = "jan,February,APR-NOVember";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t "
					+ matchesMonth(value, pattern));

			value = DECEMBER;
			pattern = "jan,February,APR-NOVember";
			System.out.println("[" + MONTH_LIST[value - 1] + "] matches again [" + pattern + "]:\t "
					+ matchesMonth(value, pattern));
		}
	}
}
