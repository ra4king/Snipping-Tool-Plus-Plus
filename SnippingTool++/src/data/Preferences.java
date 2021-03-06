package data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * @author Shane
 * 
 *         This class file simply creates and checks directories. performs first
 *         time setups and sets defaults.
 * 
 */

public class Preferences
{
	private static String os = System.getProperty("os.name");
	public static final String VERSION = "5.3.1";
	/*
	 * 5.2.2
	 *
	 *Updates
	 *=======
	 *
	 *Bugs
	 *====
	 *No longer submits multiple times when using the hotkey in the editor
	 *Typed text does not disappear when you change the tool
	 *Changing the color sets the transparency to your pre-defined correctly now.
	 * 
	 * TODO ==== Get multi-snippet capture working.
	 */

	public static final String DATA_FOLDER_PATH = System
			.getProperty("user.home") + "/.snippingtool++/data/version5/";
	public static final String DATA_FOLDER_PATH_MAC = System
			.getProperty("user.home")
			+ "/Library/Application Support/snippingtool++/data/version5/"; // added
																			// the
																			// correct
																			// directory
																			// for
																			// mac
																			// to
																			// store
																			// this
																			// kind
																			// of
																			// stuff
	public static long TOTAL_SAVED_UPLOADS = 0;
	public static String DEFAULT_CAPTURE_DIR = System.getProperty("user.home")
			+ "/pictures/SnippingTool++/";
	// private final String user_capture_dir = System.getProperty("user.home") +
	// "/pictures/SnippingTool++/";

	public static boolean EDITING_ENABLED = true;
	public static boolean AUTO_SAVE_UPLOADS = true;
	public static long DEFAULT_UPLOAD_PROVIDER = 0; // 0 = imgur, 1 = minus
	public static boolean FORCE_MULTI_CAPTURE = false;
	public static long DEFAULT_EDITING_TOOL = 0; // pencil by default

	private File DATA_FOLDER;
	private File PICTURES_FOLDER;

	private static JSONObject pref; // outputs
	private static JSONObject prefIn;

	public Preferences()
	{
		if (os.indexOf("Mac") >= 0) // added check for osx file system
		{
			DATA_FOLDER = new File(DATA_FOLDER_PATH_MAC);
		} else
		{ // Windows
			DATA_FOLDER = new File(DATA_FOLDER_PATH);
		}
		PICTURES_FOLDER = new File(DEFAULT_CAPTURE_DIR); // added for osx should
															// make no
															// difference in
															// windows
															
		pref = new JSONObject();
		checkDirectories();
	}
	
	public static void loadPreferences()
	{
		/*
		 * Load the preferences from the json file and set the Preferences Class
		 * constants
		 * 
		 * preferences are not saving and loading correctly NOTE: possibly fixed
		 * now
		 */
		System.out.println("Loading preferences...");
		JSONParser prefParser = new JSONParser();
		try
		{
			Object obj;
			if (os.indexOf("Mac") >= 0) // added check for osx file system
			{
				obj = prefParser.parse(new FileReader(
						DATA_FOLDER_PATH_MAC + "/prefs.json"));
			} else
			{ // Windows
				obj = prefParser.parse(new FileReader(
						DATA_FOLDER_PATH + "/prefs.json"));
			}
			prefIn = (JSONObject) obj;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		DEFAULT_CAPTURE_DIR = (String) prefIn
				.get("user_capture_dir");
		DEFAULT_EDITING_TOOL = (Long) prefIn
				.get("default.editing.tool");
		DEFAULT_UPLOAD_PROVIDER = (Long) prefIn
				.get("default.upload.provider");
		EDITING_ENABLED = (Boolean) prefIn.get("editing.enabled");
		FORCE_MULTI_CAPTURE = (Boolean) prefIn
				.get("force.multi.capture");
		AUTO_SAVE_UPLOADS = (Boolean) prefIn
				.get("auto.save.uploads");
	}

	private void checkDirectories()
	{
		if (!DATA_FOLDER.exists()) // if the data folder does not exist, create
									// it (first time setup)
		{
			System.out.println("Version mismatch... \nPerforming first time setup...");
			setupDirectories();
		} else
		{
			loadPreferences();
			
			if (!VERSION.equals(prefIn.get("version"))) // if different version
				setupDirectories();
		}
		
		if (!PICTURES_FOLDER.exists() && (os.indexOf("Mac") >= 0)) // so it will
																	// make sure
																	// that the
																	// pictures
																	// folder is
																	// there in
																	// osx
		{
			System.out
					.println("No pictures folder existes... \nCreating folder");
			PICTURES_FOLDER.mkdirs();
			new File(DEFAULT_CAPTURE_DIR + "/Captures/").mkdir();
			new File(DEFAULT_CAPTURE_DIR + "/Uploads/").mkdir();
		}
	}

	private void setupDirectories()
	{
		/*
		 * create the data directory, default snipping tool capture directory
		 */
		DATA_FOLDER.mkdirs(); // changed to mkdirs
		createPreferencesFile();
	}

	@SuppressWarnings("unchecked")
	private void createPreferencesFile()
	{
		pref.put("version", VERSION);
		pref.put("user_capture_dir", DEFAULT_CAPTURE_DIR);
		pref.put("editing.enabled", EDITING_ENABLED);
		pref.put("default.upload.provider", DEFAULT_UPLOAD_PROVIDER);
		pref.put("auto.save.uploads", AUTO_SAVE_UPLOADS);
		pref.put("force.multi.capture", FORCE_MULTI_CAPTURE);
		pref.put("default.editing.tool", DEFAULT_EDITING_TOOL);
		try
		{
			FileWriter file;
			if (os.indexOf("Mac") >= 0) // added check for osx file system
			{
				file = new FileWriter(DATA_FOLDER_PATH_MAC + "/prefs.json");
			} else
			{ // Windows
				file = new FileWriter(DATA_FOLDER_PATH + "/prefs.json");
			}
			file.write(pref.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("=====PREFERENCES======\n" + pref);
	}

	@SuppressWarnings("unchecked")
	public static void updatePreferences(PreferencesUI ui)
	{
		pref.put("version", VERSION);
		pref.put("user_capture_dir", ui.directoryField.getText());
		pref.put("editing.enabled", ui.chckbxEnableEditor.isSelected());
		pref.put("default.upload.provider", 0);
		pref.put("auto.save.uploads", ui.chckbxAutosaveUploads.isSelected());
		pref.put("force.multi.capture",
				ui.chckbxForceMultisnippetCapture.isSelected());
		pref.put("default.editing.tool", ui.toolBox.getSelectedIndex());
		try
		{
			FileWriter file;
			if (os.indexOf("Mac") >= 0) // added check for osx file system
			{
				file = new FileWriter(DATA_FOLDER_PATH_MAC + "/prefs.json");
			} else
			{ // Windows
				file = new FileWriter(DATA_FOLDER_PATH + "/prefs.json");
			}
			file.write(pref.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("=====PREFERENCES======\n" + pref);
	}

}
