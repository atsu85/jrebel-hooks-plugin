[![Release](https://img.shields.io/github/release/atsu85/jrebel-hooks-plugin.svg?label=latest release is)](https://jitpack.io/#atsu85/jrebel-hooks-plugin)

# jrebel-hooks-plugin
JRebel Plugin that invokes JRebel hooks in application to notify when classes are loading and reloaded by JRebel to allow application specific actions to be performed.
This plugin can be used to reinitialize some application configuration, that may be created on application startup or cached after configuration has been created.

# Using this plugin


## Download the plugin
Download the plugin [jar file](https://jitpack.io/com/github/atsu85/jrebel-hooks-plugin/v1.0.0/jrebel-hooks-plugin-v1.0.0.jar)
and save it.


## Configure JRebel to use this plugin
Add following system properties to the application launch configuration (in addition to JRebel configuration parameters):
```
-Drebel.jrebel-hooks-plugin=true
-Drebel.plugins=/path/to/jrebel-hooks-plugin-v1.0.0.jar
```

> Note: You can also enable this plugin for all applications launched with JRebel if You put them to ~/.jrebel/jrebel.properties, but then don't forget to remove the "-D" prefix

If the plugin is configured properly You should see log entry in ~/.jrebel/jrebel.log file, containing:
"initializing jrebel-hooks-plugin"


## Create class that is called after class is loaded or reloaded by JRebel

```Java
package org.jrebelhooks;

/**
 * Used by jrebel-hooks-plugin to allow application to perform custom actions after class is loaded or reloaded by JRebel
 */
public class JRebelHooks {

  /**
   * Invoked by jrebel-hooks-plugin (using reflection) after class is loaded
   * @param klass - class that was loaded
   */
  public static void classLoaded(Class<?> klass) throws Exception {
    System.out.println("classLoaded " + klass);
    // TODO do smth useful after class is loaded
  }

  /**
   * Invoked by jrebel-hooks-plugin (using reflection) after class is reloaded
   * @param klass - class that was reloaded by JRebel
   */
  public static void classReloaded(Class<?> klass) throws Exception {
    System.out.println("classReloaded " + klass);
    // TODO do smth useful after class is reloaded by JRebel
  }

}
```

> Note: names of the package and class must be exactly the same so that jrebel-hooks-plugin can find class that contains the methods to be called after class is loaded/reloaded.
Also the method signatures must match the methods shown above.

After starting application with this class and jrebel-hooks-plugin You should see log entry in ~/.jrebel/jrebel.log file, containing:
"jrebel-hooks-plugin is enabled for classloader"


## Debugging
If it looks like this plugin doesn't work, make sure You have configured it properly (see above: "Using this plugin") and created class listed above.
