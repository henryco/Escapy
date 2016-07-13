package com.x.game.map.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x.game.render.EscapyGdxCamera;

public class BackGround extends InGameObject{

	private static final int DefinitelyNotID = Integer.MIN_VALUE;
	private static final int BACKGROUND = 6;
	private static final int X = 0, Y = 0;
	private Texture background;
	private Sprite backgroundSprite;
	
	
	public BackGround(String ImgUrl, int frameW, int frameH, double scaleRatio) {
		super(X, Y, DefinitelyNotID, ImgUrl, calcBgrZoom(frameW, frameH, scaleRatio), BACKGROUND);
	}

	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		/** Static position on (0, 0) **/
		
		float p0x = (escapyCamera.getCamera().position.x - (escapyCamera.getCamera().viewportWidth / 2.f));
		float p0y = (escapyCamera.getCamera().position.y - (escapyCamera.getCamera().viewportHeight / 2.f));
		
		spriteBatcher.begin();
		backgroundSprite.setPosition(p0x, p0y);
		backgroundSprite.draw(spriteBatcher);
		spriteBatcher.end();
		
	}

	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		this.background = new Texture(new FileHandle(getImgUrl()[0]));
		this.background.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		this.backgroundSprite = new Sprite(background);
		this.backgroundSprite.flip(false, true);
		this.backgroundSprite.setSize(backgroundSprite.getWidth() * (float) zoom(),
				backgroundSprite.getHeight() * (float) zoom());
	}

	public void reInitializeGraphic(int frameW, int frameH, double scaleRatio) {
		setDefZoom(calcBgrZoom(frameW, frameH, scaleRatio));
		initializeGraphic();
	}

	private static double calcBgrZoom(int frameW, int frameH, double scaleRatio) {
		double xScale = frameW / (640. * scaleRatio);
		double yScale = frameH / (510. * scaleRatio);
		return Math.max(xScale, yScale);
	}

	
}
