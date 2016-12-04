package com.game.boxPhysics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import net.henryco.struct.Struct;
import net.henryco.struct.Structurized;
import net.henryco.struct.container.tree.StructNode;

import java.util.ArrayList;

/**
 * @author Henry on 13/11/16.
 */
public class PhysShapes implements Structurized {

	private ArrayList<BodyDef> bodyDefs;
	private ArrayList<float[]> vertices;
	public ArrayList<BodyDef> getBodyDefs(){return bodyDefs;}
	public ArrayList<float[]> getVertices(){return vertices;}

	public boolean visible = false;

	public PhysShapes(){
		bodyDefs = new ArrayList<>();
		vertices = new ArrayList<>();
		Struct.log_loading = true;
	}
	public PhysShapes(StructNode structNode) {
		this();
		loadFromStruct(structNode);
	}
	@Override
	public PhysShapes loadFromStruct(StructNode structNode) {
		System.out.println(structNode);
		visible = structNode.getBool(false, "visible");
		for (StructNode container : structNode.getStructSafe("container").getStructArray()) {
			BodyDef bodyDef = new BodyDef();
			StructNode bodyNode = container.getStructSafe("bodyDef");
			bodyDef = bodyNode.loadObjectFromStruct(bodyDef, bodyDef.getClass());
			for (StructNode polygon : container.getStructSafe("polygon").getStructArray()) {
				bodyDefs.add(bodyDef);
				StructNode[] index = polygon.getStructArray();
				float[] verticies = new float[2 * index.length];
				for (int i = 0; i < index.length; i++) {
					verticies[2*i] = index[i].getFloat(999999999, "0");
					verticies[2*i + 1] = index[i].getFloat(999999999, "1");
				}
				vertices.add(verticies);
			}
		}
		return this;
	}


}
