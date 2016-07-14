package com.game.characters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;


// TODO: Auto-generated Javadoc
/**
 * The Class InitCharacters.
 */
public class InitCharacters implements EscapyRenderable {

	private ArrayList<String>[] playerAnimationUrlTab;
	private ArrayList<Integer>[] playerTimeTab;
	private Player player;
	private NPC[] npc;
	
	/**
	 * Instantiates a new inits the characters.
	 */
	public InitCharacters()
	{
		loadAnimationDataFF();
		this.player = new Player(playerAnimationUrlTab, playerTimeTab, 4f);
		this.setNpc(new NPC[0]);// TEMP
		
	}

	/**
	 * Player.
	 *
	 * @return the player
	 */
	public Player player() {
		return player;
	}

	/**
	 * Npc.
	 *
	 * @return the NP c[]
	 */
	public NPC[] npc() {
		return getNpc();
	}

	@SuppressWarnings("unchecked")
	private void loadAnimationDataFF()
	{
		String[] names = new String[]{"stand", "walk", "run", "jump", "fall", "land"};
		playerAnimationUrlTab = new ArrayList[6];
		playerTimeTab = new ArrayList[6];
		String line;
		for (int i = 0; i < names.length; i++)
		{
			playerAnimationUrlTab[i] = new ArrayList<String>();
			playerTimeTab[i] = new ArrayList<Integer>();
			try (BufferedReader doc = new BufferedReader(new FileReader("data\\characters\\player\\"
					+names[i]+"\\"+names[i]+".txt")))
			{
				while ((line = doc.readLine()) != null) 
				{
					playerAnimationUrlTab[i].add("data\\characters\\player\\"
							+ names[i]+"\\"+line.substring(0, line.indexOf("\t"))+".png");
					playerTimeTab[i].add(Integer.parseInt(line.substring(line.indexOf("\t")+1)));
				}
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	/* (non-Javadoc)
	 * @see com.game.render.EscapyRenderable#renderGraphic(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) 
	{
		for (int i = 0; i < this.getNpc().length; i++)
			this.getNpc()[i].renderGraphic(translationMatrix, escapyCamera);
		this.player.renderGraphic(translationMatrix, escapyCamera);
		
	}

	/**
	 * Gets the npc.
	 *
	 * @return the npc
	 */
	public NPC[] getNpc() {
		return npc;
	}

	/**
	 * Sets the npc.
	 *
	 * @param npc
	 *            the new npc
	 */
	public void setNpc(NPC[] npc) {
		this.npc = npc;
	}

}
