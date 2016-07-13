package com.x.game.map.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.extra.normals.EscapyNormalRender;

public class NonAnimatedObject extends InGameObject implements EscapyNormalRender {

	private Texture staticObjectTexture;
	private Sprite staticObjectSprite;
	
	private Texture staticNrmlMapTexture;
	private Sprite staticNrmlMapSprite;

	public NonAnimatedObject(float x, float y, int id, String ImgUrl, double zoom, int typo) {
		super(x, y, id, ImgUrl, zoom, typo);
	}

	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		this.staticObjectTexture = new Texture(new FileHandle(super.getImgUrl()[0]));
		this.staticObjectTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		this.staticObjectSprite = new Sprite(staticObjectTexture);
		this.staticObjectSprite.flip(false, true);
		this.staticObjectSprite.setPosition(super.XPos(), super.YPos());
		this.staticObjectSprite.setSize(staticObjectSprite.getWidth() * (float) zoom(),
				staticObjectSprite.getHeight() * (float) zoom());
		
		
		//XXX NORMALMAP INIT VVV
		this.staticNrmlMapTexture 
			= new Texture(new FileHandle(super.removePNG(super.getImgUrl()[0])+"NRML.png"));
		this.staticNrmlMapTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		this.staticNrmlMapSprite = new Sprite(staticNrmlMapTexture);
		this.staticNrmlMapSprite.flip(false, true);
		this.staticNrmlMapSprite.setPosition(super.XPos(), super.YPos());
		this.staticNrmlMapSprite.setSize(staticNrmlMapSprite.getWidth() * (float) zoom(),
				staticNrmlMapSprite.getHeight() * (float) zoom());
		
	}

	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		spriteBatcher.begin();
			staticObjectSprite.draw(spriteBatcher);
		spriteBatcher.end();

	}

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		spriteBatcher.begin();
			staticNrmlMapSprite.draw(spriteBatcher);
		spriteBatcher.end();
	}

	

	

	

}
