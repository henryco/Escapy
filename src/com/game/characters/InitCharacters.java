package com.game.characters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.characters.states.stdCharacter.StdCharacter;
import com.game.characters.states.stdCharacter.stdNPC.NPC;
import com.game.characters.states.stdCharacter.stdPlayer.Player;
import com.game.render.EscapyUniRender;


// TODO: Auto-generated Javadoc

/**
 * The Class InitCharacters.
 */
public class InitCharacters implements EscapyUniRender {

	private ArrayList<String>[] playerAnimationUrlTab;
	private ArrayList<Integer>[] playerTimeTab;
	private StdCharacter player;
	private NPC[] npc;

	private int ID;

	/**
	 * Instantiates a new inits the characters.
	 */
	public InitCharacters() {
		loadAnimationDataFF();
		this.player = new Player(playerAnimationUrlTab, playerTimeTab, 4f);
		this.setNpc(new NPC[0]);// TEMP

	}

	private void forEach(Consumer<EscapyUniRender> cons) {
		for (NPC n : npc) cons.accept(n);
		cons.accept(player);
	}

	@Override
	public void renderLightMap(Batch batch) {
		forEach(uni -> uni.renderLightMap(batch));
	}

	@Override
	public void renderGraphic(Batch batch) {
		forEach(uni -> uni.renderGraphic(batch));
	}

	@Override
	public void renderNormals(Batch batch) {
		forEach(uni -> uni.renderNormals(batch));
	}

	@Override
	public InitCharacters shift() {
		forEach(EscapyUniRender::shift);
		return this;
	}

	@Override
	public void setID(int id) {
		this.ID = Integer.hashCode(this.hashCode() + Integer.hashCode(id));
	}

	@Override
	public int getID() {
		return ID;
	}

	@SuppressWarnings("unchecked")
	private void loadAnimationDataFF() {
		String[] names = new String[]{"stand", "walk", "run", "jump", "fall", "land"};
		playerAnimationUrlTab = new ArrayList[6];
		playerTimeTab = new ArrayList[6];
		String line;
		for (int i = 0; i < names.length; i++) {
			playerAnimationUrlTab[i] = new ArrayList<>();
			playerTimeTab[i] = new ArrayList<>();
			try (BufferedReader doc = new BufferedReader(new FileReader("data/characters/player/"
					+ names[i] + "/" + names[i] + ".txt"))) {
				while ((line = doc.readLine()) != null) {
					playerAnimationUrlTab[i].add("data/characters/player/"
							+ names[i] + "/" + line.substring(0, line.indexOf("\t")) + ".png");
					playerTimeTab[i].add(Integer.parseInt(line.substring(line.indexOf("\t") + 1)));
				}
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public Player player() {
		return (Player) this.player;
	}
	public Player getPlayer() {
		return (Player) this.player;
	}
	public InitCharacters createNewPlayer(Player newPlayer) {
		this.player = newPlayer;
		return this;
	}
	public NPC getNPC(int index) {
		return this.npc[index];
	}
	public NPC[] npc() {
		return npc;
	}
	public void setNpc(NPC[] npc) {
		this.npc = npc;
	}


}
