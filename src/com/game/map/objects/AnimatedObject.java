package com.game.map.objects;

import java.util.Arrays;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.animator.EscapyAnimatorObject;
import com.game.executable.EscapyExecutableObjects;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.lightMap.EscapyLightMapRenderer;
import com.game.render.extra.normalMap.EscapyNormalMapRender;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedObject.
 */
public class AnimatedObject extends InGameObject implements EscapyExecutableObjects, 
	EscapyNormalMapRender, EscapyLightMapRenderer {

	private int[] animPeriod;
	private AnimatedObject animob;
	private EscapyAnimatorObject animator;
	private int actualFrame;
	private boolean animationEnded = false;

	private Sprite[] obSpriteSTD, obSpriteNRML, obSpriteLTM;

	/**
	 * Instantiates a new animated object.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param id
	 *            the id
	 * @param ImgUrl
	 *            the img url
	 * @param AnimPeriod
	 *            the anim period
	 * @param zoom
	 *            the zoom
	 * @param typo
	 *            the typo
	 */
	public AnimatedObject(float x, float y, int id, String ImgUrl, int[] AnimPeriod, double zoom, int typo) {
		super(x, y, id, ImgUrl, zoom, typo);
		animPeriod = AnimPeriod;
		animob = this;
		actualFrame = 0;

		animator = new EscapyAnimatorObject() {
			private long time0 = System.nanoTime();
			private int currentFrame = 0;

			@Override
			public void defineAnimation() {
				long time1 = System.nanoTime() - time0;
			
				if ((time1 / 1000000) >= getAnimPeriod()[currentFrame]) {
					time0 = System.nanoTime();
					currentFrame++;
					if (currentFrame > 9)
						currentFrame = 0;
				}
				animob.setActualFrame(currentFrame); // FIXME

			}

			@Override
			public void InterruptAnimator(EscapyAnimatorObject object) {
				if (animob.getType() == objectType.Interactive && currentFrame == 9) {
					interruptObjectAnimation(object);
					currentFrame = 0;
					animationEnded = true;
				}
			}
		};

		addEscapyObjectAnimator(animator);
		initObjectAnimator(animator);
		EscapyExecutableObjects.initExecutableObject((short) getID(), this);

	}


	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();
		Texture[] obTempTexs;
		
		try {
			
			obTempTexs = new Texture[]{
					new Texture(new FileHandle(getImgUrl()[0])),
					new Texture(new FileHandle(super.removePNG(super.getImgUrl()[0])+"NRML.png")),
					new Texture(new FileHandle(super.removePNG(super.getImgUrl()[0])+"LTM.png"))
			};
			
			Arrays.stream(obTempTexs).forEach(
					texture -> texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest));
			
			this.obSpriteSTD = makeSpriteArray(obTempTexs[0], XPos(), YPos(), (float) zoom());
			this.obSpriteNRML = makeSpriteArray(obTempTexs[1], XPos(), YPos(), (float) zoom());
			this.obSpriteLTM = makeSpriteArray(obTempTexs[2], XPos(), YPos(), (float) zoom());
			
		} catch (com.badlogic.gdx.utils.GdxRuntimeException excp) {
				
			//excp.printStackTrace();
			try {
				
				obTempTexs = new Texture[]{new Texture(new FileHandle(getImgUrl()[0]))};
				obTempTexs[0].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
				this.obSpriteSTD = makeSpriteArray(obTempTexs[0], XPos(), YPos(), (float) zoom());
				
			} catch (com.badlogic.gdx.utils.GdxRuntimeException exc) {
				exc.printStackTrace();
			}
		}

		System.gc();
	}


	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		if (obSpriteSTD != null) {
			spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
			spriteBatcher.begin();
			obSpriteSTD[animob.getActualFrame()].draw(spriteBatcher);
			spriteBatcher.end();
		}
	}
	

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		if (obSpriteNRML != null) {
			spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
			spriteBatcher.begin();
			obSpriteNRML[animob.getActualFrame()].draw(spriteBatcher);
			spriteBatcher.end();
		}
	}

	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		if (obSpriteLTM != null) {
			spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
			spriteBatcher.begin();
			obSpriteLTM[animob.getActualFrame()].draw(spriteBatcher);
			spriteBatcher.end();
		}
	}
	

	/**
	 * Make sprite from texture.
	 *
	 * @param texture
	 *            the texture
	 * @param xpos
	 *            the xpos
	 * @param ypos
	 *            the ypos
	 * @param zoom
	 *            the zoom
	 * @return the sprite
	 */
	protected Sprite makeSpriteFromTexture(TextureRegion texture, float xpos, float ypos, float zoom) {
		
		Sprite sprite = new Sprite(texture);
		sprite.flip(false, true);
		sprite.setPosition(xpos, ypos);
		sprite.setSize(sprite.getWidth() * zoom, sprite.getHeight() * zoom);
		
		return sprite;
	}	

	
	private Sprite[] makeSpriteArray(Texture texture, float xpos, float ypos, float zoom) {
		
		Sprite[] arr = new Sprite[10];
		TextureRegion texReg = new TextureRegion(texture, 0, 0,
				(int) ((float) texture.getWidth() / 10.), texture.getHeight());
		
		for (int i = 0; i < 10; i++) {
			texReg.setRegion((int) ((texture.getWidth() / 10.) * i), 0,
					(int) ((float) texture.getWidth() / 10.), texture.getHeight());
			arr[i] = makeSpriteFromTexture(texReg, xpos, ypos, zoom);
		}	return arr;
	}
	

	@Override
	public void actionAnimation() {
		startObjectAnimator(animator);
	}


	@Override
	public boolean actionAnimationFinish() {
		if (animationEnded) {
			animationEnded = false;
			return true;
		}
		return false;
	}

	private int[] getAnimPeriod() {
		return animPeriod;
	}

	private int getActualFrame() {
		return actualFrame;
	}

	private void setActualFrame(int actualFrame) {
		this.actualFrame = actualFrame;
	}


}
