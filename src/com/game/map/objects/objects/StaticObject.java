package com.game.map.objects.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


import java.util.Arrays;

/**
 * @author Henry on 02/10/16.
 */
public class StaticObject extends GameObject {

	private Sprite[] obSprites;

	public StaticObject(float x, float y, int iD, String texUrl, float zoom, int type, int w, int h) {
		super(x, y, iD, texUrl, zoom, type, w, h);
	}

	@Override
	public void initializeGraphic() {

		Texture[] obTextures = new Texture[3];
		obTextures[0] = new Texture(new FileHandle(super.textureUrl[0]));
		super.defZoom = calcZoom(zoomCalculator, super.F_WIDTH, super.F_HEIGHT, obTextures[0], defZoom);

		try {
			obTextures[1] = new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "NRML.png"));
			obTextures[2] = new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "LTM.png"));
		} catch (Exception e) {
			try {
				obTextures[2] = new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "LTM.png"));
			} catch (Exception ignored) {}
		}

		Arrays.stream(obTextures).filter(texture -> texture != null).
				forEach(texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));

		this.obSprites = new Sprite[3];
		for (int i = 0; i < obSprites.length; i++) if (obTextures[i] != null) obSprites[i] = new Sprite(obTextures[i]);



		Arrays.stream(obSprites).filter(sprite -> sprite != null).forEach(
				sprite -> {
					float[] posCorrection = calcPos(positionCorrector, super.F_WIDTH, super.F_HEIGHT, sprite.getTexture(), defZoom);
					sprite.setSize(
							sprite.getTexture().getWidth() * super.defZoom,
							sprite.getTexture().getHeight() * super.defZoom);
					sprite.setPosition(super.position[0] + posCorrection[0], super.position[1] + posCorrection[1]);
					sprite.flip(false, true);

				});
	}


	@Override
	public void renderLightMap(Batch batch) {
		if (obSprites[2] != null) obSprites[2].draw(batch);
	}

	@Override
	public void renderGraphic(Batch batch) {
		if (obSprites[0] != null) obSprites[0].draw(batch);
	}

	@Override
	public void renderNormals(Batch batch) {
		if (obSprites[1] != null) obSprites[1].draw(batch);
	}

}
