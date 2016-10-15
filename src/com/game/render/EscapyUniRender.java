package com.game.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.utils.absContainer.EscapyContainerable;

/**
 * @author Henry on 03/10/16.
 */
public interface EscapyUniRender extends EscapyContainerable, EscapyUniTrans {

	void renderLightMap(Batch batch);
	void renderGraphic(Batch batch);
	void renderNormals(Batch batch);
}
