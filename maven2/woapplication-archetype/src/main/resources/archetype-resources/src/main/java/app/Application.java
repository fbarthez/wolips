// Generated by the Maven Archetype Plug-in
package ${package}.app;

import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSLog;
import com.webobjects.appserver.WOApplication;
import com.webobjects.foundation._NSUtilities;
import ${package}.components.Main;

public class Application extends WOApplication {
	public static void main(String[] argv) {
		${applicationClass}.main(argv, Application.class);
	}

	public Application() {
		super();
		NSLog.out.appendln("Welcome to " + name() + " !");
		/* ** put your initialization code in here ** */
		// ensure that Main is correctly resolved at runtime
		_NSUtilities.setClassForName(Main.class, "Main");
	}

	/**
	 * Determines the WOSession class to instantiate.
	 * @see com.webobjects.appserver.WOApplication#_sessionClass()
	 */
	@Override
	protected Class< ? extends WOSession > _sessionClass() {
		return Session.class;
	}

}
