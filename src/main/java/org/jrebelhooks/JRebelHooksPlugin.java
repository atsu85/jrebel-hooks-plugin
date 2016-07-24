package org.jrebelhooks;

import org.zeroturnaround.javarebel.*;

/**
 * JRebel Plugin that invokes JRebel class load/reload hooks in application class {link JRebelHooksInvoker#J_REBEL_HOOKS_CLASS}
 * that can perform application specific actions after class is loaded/reloaded
 * @author Ats Uiboupin
 */
public class JRebelHooksPlugin implements Plugin {

	public void preinit() {
		LoggerFactory.getInstance().echo("initializing " + this.getId() + "(" + this.getName() + ")");
		registerListener();
		LoggerFactory.getInstance().echo("initialized " + this.getId());
	}

	private void registerListener() {
		final Reloader reloader = ReloaderFactory.getInstance();
		reloader.addClassReloadListener(
				new ClassEventListener() {
					public void onClassEvent(int eventType, @SuppressWarnings("rawtypes") Class klass) {
						try {
							JRebelHooksInvoker.classReloaded(klass);
						} catch (final Exception e) {
							LoggerFactory.getInstance().error(e);
						}
					}

					public int priority() {
						return 0;
					}
				});

		reloader.addClassLoadListener(new ClassEventListener() {
			public void onClassEvent(int eventType, @SuppressWarnings("rawtypes") Class klass) {
				try {
					JRebelHooksInvoker.classLoaded(klass);
				} catch (final Exception e) {
					throw new RuntimeException("Failed to register class " + klass, e);
				}
			}

			public int priority() {
				return 0;
			}

		});
	}

	public boolean checkDependencies(ClassLoader classLoader, ClassResourceSource classResourceSource) {
		boolean foundClass = classResourceSource.getClassResource(JRebelHooksInvoker.J_REBEL_HOOKS_CLASS) != null;
		if (foundClass) {
			LoggerFactory.getInstance().echo(this.getId() + " is enabled for classloader " + classLoader + " - found required class " + JRebelHooksInvoker.J_REBEL_HOOKS_CLASS);
		} else {
			LoggerFactory.getInstance().echo(this.getId() + " is disabled for classloader " + classLoader + " - didn't find required class " + JRebelHooksInvoker.J_REBEL_HOOKS_CLASS);
		}
		return foundClass;
	}

	public String getId() {
		return "jrebel-hooks-plugin";
	}

	public String getName() {
		return "JRebel Hooks Plugin";
	}

	public String getDescription() {
		return "JRebel Plugin that invokes JRebel hooks in application";
	}

	public String getAuthor() {
		return "Ats Uiboupin";
	}

	public String getWebsite() {
		return "https://github.com/atsu85/jrebel-hooks-plugin";
	}

	public String getSupportedVersions() {
		return null;
	}

	public String getTestedVersions() {
		return null;
	}
}
