package com.game.executable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyExecutableUtils.
 */
public class EscapyExecutableUtils {

	/**
	 * Creates the hash map of option array.
	 *
	 * @return the hash map
	 */
	protected static HashMap<Integer, String[]> createHashMapOfOptionArray() {
		HashMap<Integer, String[]> optionMap = new HashMap<>();
		try {
			BufferedReader config = new BufferedReader(new FileReader("data\\Config\\config.ecf"));
			String line;
			while ((line = config.readLine()) != null) {
				int intKey = Integer.parseInt(line);
				ArrayList<String> optionList = new ArrayList<String>();
				config.readLine(); // next line
				while (!(line = config.readLine()).equalsIgnoreCase("<?/!>"))
					optionList.add(line);
				optionMap.put(intKey, listToStringArray(optionList));
			}
			config.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return optionMap;
	}

	/**
	 * Creates the hash map of option types.
	 *
	 * @return the hash map
	 */
	protected static HashMap<Integer, Short> createHashMapOfOptionTypes() {
		HashMap<Integer, Short> optionMap = new HashMap<>();
		try {
			BufferedReader config = new BufferedReader(new FileReader("data\\Config\\config.ecf"));
			String line;
			while ((line = config.readLine()) != null) {
				int intKey = Integer.parseInt(line);
				short type = Short.parseShort(config.readLine());
				while (!(line = config.readLine()).equalsIgnoreCase("<?/!>")) {
				}
				optionMap.put(intKey, type);
			}
			config.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return optionMap;
	}

	private static String[] listToStringArray(ArrayList<String> optionList) {
		String[] optionArray = new String[optionList.size()];
		Iterator<String> strIterator = optionList.iterator();
		for (int i = 0; i < optionList.size(); i++)
			optionArray[i] = strIterator.next();
		return optionArray;
	}

}
