package com.game.boxPhysics;

import com.badlogic.gdx.physics.box2d.*;
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

	public static final String exampleStructFile =
			"//EXAMPLE STRUCT FILE\n" +
			"//use to init physics\n" +
			"//Static*, Dynamic*, Kinematic*\n" +
			"//@author: henryCo\n" +
			"#import struct\n" +
			"exampleName: {\n" +
			"\tvisible true;\n" +
			"\tcontainer[0]: {\n" +
			"\t\tbodyDef {\n" +
			"\t\t\tangularDamping = 0\n" +
			"\t\t\tposition {\n" +
			"\t\t\t\tx = 0;\n" +
			"\t\t\t\ty = 0;\n" +
			"\t\t\t}\n" +
			"\t\t\ttype.java.field(\"com\",\"badlogic\",\"gdx\",\"physics\",\"box2d\",\"BodyDef\",\"$BodyType\", \"StaticBody\")\n" +
			"\n" +
			"\t\t}\n" +
			"\t\tpolygon[0]: {\n" +
			"\t\t\t0: [0.0, 0.0]\n" +
			"\t\t\t1: [1.0, 0.0]\n" +
			"\t\t\t2: [1.0, 1.0]\n" +
			"\t\t\t3: [0.0, 1.0]\n" +
			"\t\t}\n" +
			"\t\tpolygon[1]: ([0.0, 0.0], [1.0, 0.0], [1.0, 1.0], [0.0, 1.0])\n" +
			"\t}\n" +
			"}";
}
