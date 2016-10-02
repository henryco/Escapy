package com.game.map.objectsAlt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.animator.EscapyAnimatorObject;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.lightMap.EscapyLightMapRenderer;
import com.game.render.extra.normalMap.EscapyNormalMapRender;

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

	public AnimatedObject(float x, float y, int iD, String texUrl, float zoom, int type,
						  int[] animPeriod) {
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

	}


	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {

	}

	@Override
	public void renderGraphic(float[] translationVec, EscapyGdxCamera escapyCamera) {

	}

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {

	}




}
