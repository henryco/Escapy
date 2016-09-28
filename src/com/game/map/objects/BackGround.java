package com.game.map.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.camera.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Class BackGround.
 */
public class BackGround extends InGameObject{

	private static final int DefinitelyNotID = Integer.MIN_VALUE;
	private static final int BACKGROUND = 6;
	private static final int X = 0, Y = 0;
	private Sprite backgroundSprite;
	
	private int F_WIDTH;
	private int F_HEIGHT;
	private float corr = 0;

	/**
	 * Instantiates a new back ground.
	 *
	 * @param ImgUrl
	 *            the img url
	 * @param frameW
	 *            the frame W
	 * @param frameH
	 *            the frame H
	 * @param scaleRatio
	 *            the scale ratio
	 */
	public BackGround(String ImgUrl, int frameW, int frameH, double scaleRatio) {
		super(X, Y, DefinitelyNotID, ImgUrl, scaleRatio, BACKGROUND);
		F_WIDTH = frameW;
		F_HEIGHT = frameH;
		initializeGraphic();
	}


	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		
		float p0x = (escapyCamera.getCamera().position.x - (escapyCamera.getCamera().viewportWidth / 2.f));
		float p0y = (escapyCamera.getCamera().position.y - (escapyCamera.getCamera().viewportHeight / 2.f));
		backgroundSprite.setPosition(p0x, p0y + corr);
		spriteBatcher.begin();
		backgroundSprite.draw(spriteBatcher);
		spriteBatcher.end();
		
	}


	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		Texture background = new Texture(new FileHandle(getImgUrl()[0]));
		background.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		double zm = calcBgrZoom(F_WIDTH, F_HEIGHT, background.getWidth(), background.getHeight(), zoom());

		this.backgroundSprite = new Sprite(background);
		this.backgroundSprite.flip(false, true);
		this.backgroundSprite.setSize(backgroundSprite.getWidth() * (float) zm,
				backgroundSprite.getHeight() * (float) zm);
		corr = 0 - (backgroundSprite.getHeight() - F_HEIGHT);
	}


	private static double calcBgrZoom(int frameW, int frameH, int w, int h, double scaleRatio) {
		double xScale = frameW / w;
		double yScale = frameH / h;
		return Math.max(xScale, yScale);
	}

	
}
