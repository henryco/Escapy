package com.game.map.objectsAlt.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.animator.EscapyAnimatorObject;
import com.game.render.camera.EscapyGdxCamera;

import java.util.Arrays;

/**
 * @author Henry on 02/10/16.
 */
public class AnimatedObject extends GameObject {

	private int[] animPeriod;
	private AnimatedObject animob;
	private int actualFrame;
	private boolean animationEnded = false;
	private Sprite[] obSpriteSTD, obSpriteNRML, obSpriteLTM;

	public AnimatedObject(float x, float y, int iD, String texUrl, float zoom, int type, int[] animPeriod, int w, int h) {

		super(x, y, iD, texUrl, zoom, type, w, h);
		this.animPeriod = animPeriod;
		this.animob = this;
		this.actualFrame = 0;

		EscapyAnimatorObject animator = new EscapyAnimatorObject() {

			private long time0 = System.nanoTime();
			private int currentFrame = 0;

			@Override
			public void defineAnimation() {
				long time1 = System.nanoTime() - time0;
				if ((time1 / 1000000) >= animob.animPeriod[currentFrame]) {
					time0 = System.nanoTime();
					currentFrame++;
					if (currentFrame > 9) currentFrame = 0;
				}
				animob.actualFrame = currentFrame;
			}

			@Override
			public void InterruptAnimator(EscapyAnimatorObject object) {
				if (animob.objectType == GameObject.type.INTERACTIVE && currentFrame == 9) {
					interruptObjectAnimation(object);
					currentFrame = 0;
					animationEnded = true;
				}
			}
		};
		addEscapyObjectAnimator(animator);
		initObjectAnimator(animator);
	}

	@Override
	public void initializeGraphic() {

		Texture[] obTempTexs;
		try {
			obTempTexs = new Texture[]{
					new Texture(new FileHandle(super.textureUrl[0])),
					new Texture(new FileHandle(removePNG(super.textureUrl[0])+"NRML.png")),
					new Texture(new FileHandle(removePNG(super.textureUrl[0])+"LTM.png"))
			};
			Arrays.stream(obTempTexs).forEach(
					texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));
			super.defZoom = calcZoom(zoomCalculator, super.F_WIDTH, super.F_HEIGHT, obTempTexs[0], defZoom);

			this.obSpriteSTD = makeSpriteArray(obTempTexs[0], super.position[0], super.position[1], super.defZoom);
			this.obSpriteNRML = makeSpriteArray(obTempTexs[1], super.position[0], super.position[1], super.defZoom);
			this.obSpriteLTM = makeSpriteArray(obTempTexs[2], super.position[0], super.position[1], super.defZoom);

		} catch (com.badlogic.gdx.utils.GdxRuntimeException excp) {
			if (errPrint) excp.printStackTrace();
			try {
				obTempTexs = new Texture[]{new Texture(new FileHandle(super.textureUrl[0]))};
				obTempTexs[0].setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
				super.defZoom = calcZoom(zoomCalculator, super.F_WIDTH, super.F_HEIGHT, obTempTexs[0], defZoom);
				this.obSpriteSTD = makeSpriteArray(obTempTexs[0], super.position[0], super.position[1], super.defZoom);

			} catch (com.badlogic.gdx.utils.GdxRuntimeException exc) {
				exc.printStackTrace();
			}
		}

		System.gc();
	}


	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		if (obSpriteLTM != null) {
			spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
			spriteBatch.begin();
			obSpriteLTM[animob.actualFrame].draw(spriteBatch);
			spriteBatch.end();
		}
	}

	@Override
	public void renderGraphic(float[] translationVec, EscapyGdxCamera escapyCamera) {
		if (obSpriteSTD != null) {
			spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
			spriteBatch.begin();
			obSpriteSTD[animob.actualFrame].draw(spriteBatch);
			spriteBatch.end();
		}
	}

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		if (obSpriteNRML != null) {
			spriteBatch.setProjectionMatrix(escapyCamera.getCamera().combined);
			spriteBatch.begin();
			obSpriteNRML[animob.actualFrame].draw(spriteBatch);
			spriteBatch.end();
		}
	}

	@Override
	public void renderLightMap(Batch batch) {
		if (obSpriteLTM != null) obSpriteLTM[animob.actualFrame].draw(batch);
	}

	@Override
	public void renderGraphic(Batch batch) {
		if (obSpriteSTD != null) obSpriteSTD[animob.actualFrame].draw(batch);
	}

	@Override
	public void renderNormals(Batch batch) {
		if (obSpriteNRML != null) obSpriteNRML[animob.actualFrame].draw(batch);
	}

}
