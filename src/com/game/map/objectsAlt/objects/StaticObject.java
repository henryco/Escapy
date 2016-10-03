package com.game.map.objectsAlt.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.camera.EscapyGdxCamera;


import java.util.Arrays;

/**
 * @author Henry on 02/10/16.
 */
public class StaticObject extends GameObject {

	private Sprite[] obSprites;

	public StaticObject(float x, float y, int iD, String texUrl, float zoom, int type) {
		super(x, y, iD, texUrl, zoom, type);
	}

	@Override
	protected void initializeGraphic() {

		Texture[] obTextures = new Texture[3];
		obTextures[0] = new Texture(new FileHandle(super.textureUrl[0]));

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
					sprite.flip(false, true);
					sprite.setPosition(super.position[0], super.position[1]);
					sprite.setSize(
							sprite.getTexture().getWidth() * super.defZoom,
							sprite.getTexture().getHeight() * super.defZoom);
				});

		/*
		Texture[] obTextures = new Texture[]{
				new Texture(new FileHandle(super.textureUrl[0])),
				new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "NRML.png")),
				new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "LTM.png"))
		};

		Arrays.stream(obTextures).forEach(
				texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));

		this.obSprites = new Sprite[] {
				new Sprite(obTextures[0]),
				new Sprite(obTextures[1]),
				new Sprite(obTextures[2])
		};

		Arrays.stream(obSprites).forEach(
				sprite -> {
					sprite.flip(false, true);
					sprite.setPosition(super.position[0], super.position[1]);
					sprite.setSize(
							sprite.getTexture().getWidth() * super.defZoom,
							sprite.getTexture().getHeight() * super.defZoom);
				});
		*/
	}

	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatch.begin();
		obSprites[2].draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void renderGraphic(float[] translationVec, EscapyGdxCamera escapyCamera) {
		spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatch.begin();
		obSprites[0].draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatch.begin();
		obSprites[1].draw(spriteBatch);
		spriteBatch.end();
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
