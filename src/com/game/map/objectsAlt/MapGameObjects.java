package com.game.map.objectsAlt;

import com.game.map.objectsAlt.layers.LayerContainer;
import net.henryco.struct.Struct;
import net.henryco.struct.container.StructContainer;
import net.henryco.struct.container.tree.StructNode;
import net.henryco.struct.container.tree.StructTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Henry on 03/10/16.
 */
public class MapGameObjects {


	public LayerContainer[] layerContainers;


	public MapGameObjects(){
	}
	public MapGameObjects(int[] dim, String location, String cfg) {
		this.initGameObjects(dim, location, cfg);
	}

	public MapGameObjects initGameObjects(int[] dim, String location, String cfg) {

		String cfgFile = getFileName(location, cfg);
		System.out.println(cfgFile);

		List<String[]>[] containerList = Struct.printDataFile(Struct.in.readStructData(cfgFile));
		StructTree containerTree = StructContainer.tree(containerList);
		System.out.println(containerTree);

		layerContainers = loadContainer(containerTree.mainNode.getStruct("map"), dim);

		return this;
	}


	private static LayerContainer[] loadContainer(StructNode mapNode, int[] dim) {

		List<LayerContainer> containers = new ArrayList<>();
		StructNode container = mapNode.getStruct("container");
		int[] iter = getIntArray(container.getStructChild());
		for (int i : iter) {
			StructNode actualCont = container.getStruct(Integer.toString(i));
			String fboName = "";
			if (actualCont.containsPrimitive("fboName")) fboName = actualCont.getPrimitive("fboName");
			containers.add(fillContainer(new LayerContainer(dim, fboName)));
		}
		return containers.toArray(new LayerContainer[containers.size()]);
	}


	private static LayerContainer fillContainer(LayerContainer container) {

		return container;
	}


	private static String getFileName(String ... name) {
		String forReturn = "";
		for (String n : name) forReturn += n;
		return forReturn;
	}

	private static int[] getIntArray(String[] arr) {
		List<Integer> intList = new ArrayList<>();
		for (String iter : arr) try {
				intList.add(Integer.parseInt(iter));
			} catch (Exception ignored) {}
		Integer[] iar = intList.toArray(new Integer[intList.size()]);
		int[] ret = new int[iar.length];
		for (int i = 0; i < ret.length; i++) ret[i] = iar[i];
		Arrays.sort(ret);
		return ret;
	}
}
