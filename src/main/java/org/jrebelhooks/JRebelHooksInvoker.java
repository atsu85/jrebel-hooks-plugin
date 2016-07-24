package org.jrebelhooks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JRebelHooksInvoker {
	/** Name of the class in application, that will be invoked when JRebel detects that class is loaded or reloaded */
	static final String J_REBEL_HOOKS_CLASS = "org.jrebelhooks.JRebelHooks";

	public static void classLoaded(@SuppressWarnings("rawtypes") Class klass)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> frameworkJRIntegrationClass = getJRebelHooksClass();
		Method getInstanceMethod = frameworkJRIntegrationClass.getDeclaredMethod(
				"classLoaded",
				Class.forName("java.lang.Class"));
		getInstanceMethod.invoke(null, klass);
	}

	public static void classReloaded(@SuppressWarnings("rawtypes") Class klass)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> frameworkJRIntegrationClass = getJRebelHooksClass();
		Method getInstanceMethod = frameworkJRIntegrationClass.getDeclaredMethod(
				"classReloaded",
				Class.forName("java.lang.Class"));
		getInstanceMethod.invoke(null, klass);
	}

	private static Class<?> getJRebelHooksClass() throws ClassNotFoundException {
		return Class.forName(J_REBEL_HOOKS_CLASS);
	}

}
