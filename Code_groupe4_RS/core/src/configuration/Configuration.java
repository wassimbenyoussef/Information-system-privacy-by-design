package configuration;

import java.io.FileInputStream;
import java.util.Properties;


 /**
  * Configuration This class is responsible for calling the config files 
 * @author Majdi Ben Fredj
 *
 */
public class Configuration {
	

	private static Properties configuration = null;
	
	public static Properties getConfiguration() {
		if (configuration == null) {
			new Configuration();
		}
		
		return configuration;
	}

	public Configuration() {
		String configurationFilePath = System.getProperty("configurationFilePath");
		configuration = new Properties();
		try {
			configuration.load(new FileInputStream(configurationFilePath));
		} catch (Exception e) {
			System.out.println("problem when loading configuration file [" + configurationFilePath + "]");
			e.printStackTrace();
			return;
		}
	
	}
	
}
