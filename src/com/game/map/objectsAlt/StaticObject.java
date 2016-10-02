package com.game.map.objectsAlt;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.lightMap.EscapyLightMapRenderer;
import com.game.render.extra.normalMap.EscapyNormalMapRender;

import java.util.Arrays;

/**
 * @author Henry on 02/10/16.
 */
public class StaticObject extends GameObject implements EscapyNormalMapRender,
		EscapyLightMapRenderer {

	private Sprite[] ObSprites;

	public StaticObject(float x, float y, int iD, String texUrl, float zoom, int type) {
		super(x, y, iD, texUrl, zoom, type);
	}

	@Override
	protected void initializeGraphic() {
		Texture[] obTextures = new Texture[]{
				new Texture(new FileHandle(super.textureUrl[0])),
				new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "NRML.png")),
				new Texture(new FileHandle(removePNG(super.textureUrl[0]) + "LTM.png"))
		};
		Arrays.stream(obTextures).forEach(
				texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));

		this.ObSprites = new Sprite[] {
				new Sprite(obTextures[0]),
				new Sprite(obTextures[1]),
				new Sprite(obTextures[2])
		};
		Arrays.stream(ObSprites).forEach(
				sprite -> {
					sprite.flip(false, true);
					sprite.setPosition(super.position[0], super.position[1]);
					sprite.setSize(
							sprite.getTexture().getWidth() * super.defZoom,
							sprite.getTexture().getHeight() * super.defZoom);
				});
	}

	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatch.begin();
		ObSprites[2].draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void renderGraphic(float[] translationVec, EscapyGdxCamera escapyCamera) {
		spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatch.begin();
		ObSprites[0].draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatch.begin();
		ObSprites[1].draw(spriteBatch);
		spriteBatch.end();
	}


}
