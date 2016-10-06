package com.game.characters.states.stdCharacter.stdNPC;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.characters.states.stdCharacter.StdCharacter;
import com.game.physics_temp.EscapyPhysicsObjectSuper;


// TODO: Auto-generated Javadoc
/**
 * The Class NPC.
 */
public class NPC extends StdCharacter {

	
	
	public NPC(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super(urls, times, zoom);
	}
	
	public NPC(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom, int[] position) {
		super(urls, times, zoom, position);
	}

	
	
	@Override
	public void definePhysicalSystem(EscapyPhysicsObjectSuper physObject) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void physicalCalculations(EscapyPhysicsObjectSuper physObject) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void physicalEvent(float xpos, float ypos, float mass, float tetha, EscapyPhysicsObjectSuper physObject) {
		// TODO Auto-generated method stub	
	}
	
	
	
	@Override
	public void defineStandAnimation() {
		// TODO Auto-generated method stub
	}
	@Override
	public void defineMovAnimation() {
		// TODO Auto-generated method stub
	}
	@Override
	public void defineRunAnimation() {
		// TODO Auto-generated method stub
	}


}
