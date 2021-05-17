package com.selimsql.springboot.util;

public class Util {

	public static final String NEWLINE = System.getProperty("line.separator");

	public static int length(String st) {
		if (st==null)
			return 0;
		return st.length();
	}

	public static boolean isEmpty(String st) {
		return (length(st)==0);
	}

	public static boolean isFull(String st) {
		return (length(st) > 0);
	}

	public static long getLong(Object obj, long valueError) {
		if (obj == null)
			return valueError;
		try {
			if (obj instanceof Number)
				return ((Number) obj).longValue();

			if (obj instanceof String)
				return Long.parseLong((String) obj);

			return Long.parseLong(obj.toString());
		}
		catch (Exception err) {
			return valueError;
		}
	}

	public static long getLong(Object objSayi) {
		return getLong(objSayi, /* hataDurumunda */0);
	}

	public static int getInt(Object objSayi, int valueError) {
		return (int) getLong(objSayi, valueError);
	}

	public static int getInt(Object objSayi) {
		return getInt(objSayi, /* valueError */0);
	}
	
	public static Long getLongObj(Object objValue, Long valueException) {
		try {
			if (objValue == null || objValue instanceof Long)
				return (Long) objValue;

			if (objValue instanceof Number)
				return Long.valueOf(((Number) objValue).longValue());

			return Long.valueOf(objValue.toString());
		}
		catch (Exception err) {
			return valueException;
		}
	}

	//getLongObjNullIfError
	public static Long getLongObj(Object objValue) {
		return getLongObj(objValue, null);
	}


	public static String getStackTraceInfo(Throwable th, int level) {
		if (th == null)
			return null;

		StringBuilder sbuf = new StringBuilder();
		StackTraceElement[] trace = th.getStackTrace();
		int scanLevel = (trace == null ? 0 : trace.length);
		if (scanLevel > level)
			scanLevel = level;

		sbuf.append(NEWLINE + "ExceptionDesc:" + NEWLINE + th.toString());
		for (int i = 0; i < scanLevel; i++)
			sbuf.append(NEWLINE + " at " + trace[i]);
		// sb.append("\tat " + trace[i]);

		return sbuf.toString();
	}


	public static String getErrorMessage(Throwable err, boolean includeExceptionName, int traceLevel, int includeCauseDepth) {
		if (err == null)
			return null;

		String errorMessage = null;
		if (err instanceof NullPointerException)
			errorMessage = "NPE";

		//TODO?
		else if (err instanceof IndexOutOfBoundsException)
			errorMessage = "IOB";

		if (errorMessage != null) {
			if (traceLevel < 10)
				traceLevel = 10;
			errorMessage += ", " + getStackTraceInfo(err, traceLevel);
		}
		/*
		 * else if (err instanceof RuntimeException) { RuntimeException
		 * runtimeException = (RuntimeException)err; errorMessage =
		 * runtimeException.getMessage(); }
		 */
		else {
			errorMessage = err.getMessage();
			if (includeExceptionName)
				errorMessage = "(" + err.getClass().getSimpleName() + ") " + errorMessage;

			if (traceLevel > 0)
				errorMessage += getStackTraceInfo(err, traceLevel);

			if (includeCauseDepth > 0) {
				Throwable errCause = err.getCause();
				if (errCause != null && errCause != err) {
					errorMessage += NEWLINE;
					errorMessage += getErrorMessage(errCause, includeExceptionName, traceLevel, includeCauseDepth - 1);
				}
			}
		}

		return errorMessage;
	}//get_ErrorMessage

	public static String getErrorMessage(Throwable err) {
		final boolean includeExceptionName = false;
		final int traceLevel = 0;
		final int includeCauseDepth = 0;
		return getErrorMessage(err, includeExceptionName, traceLevel, includeCauseDepth);
	}

	public static String getErrorMessageByCauses(Throwable err, int includeCauseDepth) {
		final boolean includeExceptionName = true;
		final int traceLevel = 0;
		return getErrorMessage(err, includeExceptionName, traceLevel, includeCauseDepth);
	}

	public static String getErrorMessageByCauses(Throwable err) {
		final int includeCauseDepth = 5;
		return getErrorMessageByCauses(err, includeCauseDepth);
	}
}
