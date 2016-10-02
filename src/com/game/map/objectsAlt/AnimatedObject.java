package com.game.map.objectsAlt;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.animator.EscapyAnimatorObject;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.lightMap.EscapyLightMapRenderer;
import com.game.render.extra.normalMap.EscapyNormalMapRender;

import java.util.Arrays;

/**
 * @author Henry on 02/10/16.
 */
public class AnimatedObject extends GameObject
		implements EscapyNormalMapRender, EscapyLightMapRenderer {

	private int[] animPeriod;
	private AnimatedObject animob;
	private int actualFrame;
	private boolean animationEnded = false;
	private Sprite[] obSpriteSTD, obSpriteNRML, obSpriteLTM;

	public AnimatedObject(float x, float y, int iD, String texUrl, float zoom, int type, int[] animPeriod) {

		super(x, y, iD, texUrl, zoom, type);
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
	protected void initializeGraphic() {
		Texture[] obTempTexs;

		try {

			obTempTexs = new Texture[]{
					new Texture(new FileHandle(super.textureUrl[0])),
					new Texture(new FileHandle(removePNG(super.textureUrl[0])+"NRML.png")),
					new Texture(new FileHandle(removePNG(super.textureUrl[0])+"LTM.png"))
			};

			Arrays.stream(obTempTexs).forEach(
					texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));

			this.obSpriteSTD = makeSpriteArray(obTempTexs[0], super.position[0], super.position[1], super.defZoom);
			this.obSpriteNRML = makeSpriteArray(obTempTexs[1], super.position[0], super.position[1], super.defZoom);
			this.obSpriteLTM = makeSpriteArray(obTempTexs[2], super.position[0], super.position[1], super.defZoom);

		} catch (com.badlogic.gdx.utils.GdxRuntimeException excp) {
			if (errPrint) excp.printStackTrace();
			try {

				obTempTexs = new Texture[]{new Texture(new FileHandle(super.textureUrl[0]))};
				obTempTexs[0].setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
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


}
