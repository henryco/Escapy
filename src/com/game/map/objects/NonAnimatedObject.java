package com.game.map.objects;

import java.util.Arrays;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.lightMap.EscapyLightMapRenderer;
import com.game.render.extra.normalMap.EscapyNormalMapRender;

// TODO: Auto-generated Javadoc
/**
 * The Class NonAnimatedObject.
 */
public class NonAnimatedObject extends InGameObject implements EscapyNormalMapRender, 
	EscapyLightMapRenderer{

	private Texture[] ObTextures;
	private Sprite[] ObSprites;

	/**
	 * Instantiates a new non animated object.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param id
	 *            the id
	 * @param ImgUrl
	 *            the img url
	 * @param zoom
	 *            the zoom
	 * @param typo
	 *            the typo
	 */
	public NonAnimatedObject(float x, float y, int id, String ImgUrl, double zoom, int typo) {
		super(x, y, id, ImgUrl, zoom, typo);
	}


	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		this.ObTextures = new Texture[]{
				new Texture(new FileHandle(super.getImgUrl()[0])),
				new Texture(new FileHandle(super.removePNG(super.getImgUrl()[0])+"NRML.png")),
				new Texture(new FileHandle(super.removePNG(super.getImgUrl()[0])+"LTM.png"))
		}; 
		Arrays.stream(ObTextures).forEach(
				texture -> texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest));
		
		this.ObSprites = new Sprite[] {
				new Sprite(ObTextures[0]), 
				new Sprite(ObTextures[1]), 
				new Sprite(ObTextures[2])
		};
		Arrays.stream(ObSprites).forEach(
				sprite -> { 
					sprite.flip(false, true);
					sprite.setPosition(super.XPos(), super.YPos());
					sprite.setSize(
							sprite.getTexture().getWidth() * (float) zoom(),
							sprite.getTexture().getHeight() * (float) zoom());
				});
	}

	
	/* (non-Javadoc)
	 * @see com.game.render.EscapyRenderable#renderGraphic(float[], com.game.render.camera.EscapyGdxCamera)
	 */
	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatcher.begin();
			ObSprites[0].draw(spriteBatcher);
		spriteBatcher.end();

	}

	/* (non-Javadoc)
	 * @see com.game.render.extra.normalMap.EscapyNormalMapRender#renderNormals(float[], com.game.render.camera.EscapyGdxCamera)
	 */
	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatcher.begin();
			ObSprites[1].draw(spriteBatcher);
		spriteBatcher.end();
	}

	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		spriteBatcher.begin();
			ObSprites[2].draw(spriteBatcher);
		spriteBatcher.end();
	}

	

	

	

}
