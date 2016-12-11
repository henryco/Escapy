package com.game.characters;

import com.badlogic.gdx.physics.box2d.World;
import com.game.boxPhysics.body.BodyHolder;
import com.game.boxPhysics.body.IBoxBody;
import com.game.characters.states.stdCharacter.StdCharacter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Henry on 10/12/16.
 */
public class SharedCharacters {

	public interface defaultLoader {
		List<IBoxBody> load(World world, float meter_scale, List<BodyHolder> holderList);
	}
	public defaultLoader loader = (world, meter_scale, holderList)
			-> holderList.stream().map(character -> IBoxBody.createStdCharacter(meter_scale, world, character)).collect(Collectors.toList());

	public List<StdCharacter> characterList;
	public List<IBoxBody> iBoxBodyList;


	public SharedCharacters() {
		characterList = new ArrayList<>();
	}
	public SharedCharacters(StdCharacter player, StdCharacter ... npc) {
		characterList = new ArrayList<>();
		characterList.add(player);
		Collections.addAll(characterList, npc);
	}

	public List<IBoxBody> loadBodyList(World world, float meter_scale) {
		return loader.load(world, meter_scale, new ArrayList<>(characterList));
	}

}
