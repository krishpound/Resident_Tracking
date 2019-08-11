package resident_tracking;

//import java specific packages
import java.io.File;

// import servlet packages 
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

// import log4j packages
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
* This servlet performs the task of setting up the log4j configuration.
* <p>
* This servlet is loaded on startup by specifying the load on startup
* property in the web.xml. On load, it performs the following activities:
* <ul> Looks for the log4j configuration file.
* <ul> Configures the PropertyConfigurator using the log4j configuration
* file if it finds it, otherwise throws an Error on your
* Tomcat screen. So make sure to check the Tomcat screen once it starts up.
* However, you will still be able to access the main application, but wont get
* any log statements as you would expect.
* NOTE: This illustrates an important point about Log4J. That it is fail safe.
* The application will not stop running because Log4J could not be set up.
*
*
*/

public class SetupServlet extends HttpServlet{

	public void init(ServletConfig config) throws ServletException{

		super.init(config);

		System.out.println("Setting up log4j for Resident Tracking.");
		
		String props = config.getInitParameter("props");

		if(props == null || props.length() == 0 ||
			!(new File(props)).isFile()){

			System.err.println(
			"ERROR: Cannot read the configuration file. " +
			"Please check the path of the config init param in web.xml");
			throw new ServletException();
		}

		// look up another init parameter that tells whether to watch this
		// configuration file for changes.
		String watch = config.getInitParameter("watch");

		// use the props file to load up configuration parameters for log4j
		if(watch != null && watch.equalsIgnoreCase("true"))
			PropertyConfigurator.configureAndWatch(props);
		else
			PropertyConfigurator.configure(props);



	}

	public void destroy(){

		super.destroy();
	}
}