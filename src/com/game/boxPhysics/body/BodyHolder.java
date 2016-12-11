package com.game.boxPhysics.body;

import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * @author Henry on 11/12/16.
 */
public interface BodyHolder {

	void updateHolder(IBoxBody boxBody);
	IBoxBody getIBoxBody();
	BodyDef getBodyDef();
}
