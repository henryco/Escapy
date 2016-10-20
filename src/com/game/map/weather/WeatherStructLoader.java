package com.game.map.weather;

import net.henryco.struct.container.tree.StructNode;

/**
 * @author Henry on 20/10/16.
 */
public class WeatherStructLoader {

	public static EscapyWeather loadEscapyWeather(StructNode wexecNode, float scrWidth, float scrHeight) {

		EscapyWeather tempWeather = new EscapyWeather(scrWidth, scrHeight);
		tempWeather.setName(wexecNode.getString(Integer.toString(tempWeather.hashCode()), "name"));
		StructNode containerNode = wexecNode.getStructSafe("container");
		if (containerNode != null) {
			String[] indexes = containerNode.getStructChild();
			if (indexes != null && indexes.length > 0) {
				String[] names = new String[indexes.length];
				String[] paths = new String[indexes.length];
				String[] imags = new String[indexes.length];
				for (int i = 0; i < indexes.length; i++) {
					StructNode iNode = containerNode.getStructSafe(indexes[i]);
					if (iNode != null) {
						names[i] = iNode.getString("null", "name", "0");
						StructNode dirNode = iNode.getStructSafe("fileDir", "1");
						if (dirNode != null) {
							imags[i] = dirNode.getString("null", "dir", "path", "0", "url");
							paths[i] = imags[i] + dirNode.getString("null", "file", "1");
						}
					}
				}
				tempWeather.load(paths, imags, names);
			}
		}
		StructNode programNode = wexecNode.getStructSafe("program");
		if (programNode != null) {
			StructNode startNode = programNode.getStructSafe("startPoint", "0");
			if (startNode != null) tempWeather.setStartPoint(startNode.getFloat(0, "0", "x", "s"), startNode.getFloat(0, "1", "y", "t"));
			tempWeather.setWeather(programNode.getString(null, "default", "actual", "weather", "1"));
			if (programNode.getBool(false, "start", "run", "2", "on", "ON")) tempWeather.start();
		}
		return tempWeather;
	}
}
