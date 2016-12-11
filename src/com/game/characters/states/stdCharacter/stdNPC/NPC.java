package com.game.characters.states.stdCharacter.stdNPC;

import java.util.ArrayList;

import com.game.characters.states.stdCharacter.StdCharacter;


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
