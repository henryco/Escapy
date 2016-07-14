package com.game.map.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.animator.EscapyAnimatorObject;
import com.game.executable.EscapyExecutableObjects;
import com.game.render.EscapyGdxCamera;
import com.game.render.extra.normals.EscapyNormalRender;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedObject.
 */
public class AnimatedObject extends InGameObject implements EscapyExecutableObjects, EscapyNormalRender {

	private int[] animPeriod;
	private AnimatedObject animob;
	private EscapyAnimatorObject animator;
	private int actualFrame;
	private boolean animationEnded = false;

	private Texture objectTexture;
	private TextureRegion objectTextureRegion;

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
				if (currentFrame > 9)
					currentFrame = 0;
				if ((time1 / 1000000) >= getAnimPeriod()[currentFrame]) {
					time0 = System.nanoTime();
					currentFrame++;
				}
				animob.setActualFrame(currentFrame); // FIXME

			}

			@Override
			public void InterruptAnimator(EscapyAnimatorObject object) {
				if (animob.getType() == objectType.Interactive && currentFrame == 9) {
					interruptObjectAnimation(object);
					currentFrame = 0;
					animationEnded = true;
					System.gc();
				}
			}
		};

		addEscapyObjectAnimator(animator);
		initObjectAnimator(animator);
		EscapyExecutableObjects.initExecutableObject((short) getID(), this);

	}

	/* (non-Javadoc)
	 * @see com.game.map.objects.InGameObject#initializeGraphic()
	 */
	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		this.objectTexture = new Texture(new FileHandle(getImgUrl()[0]));
		this.objectTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		this.objectTextureRegion = new TextureRegion(objectTexture, 0, 0,
				(int) ((float) objectTexture.getWidth() / 10.), objectTexture.getHeight());
	}

	/* (non-Javadoc)
	 * @see com.game.render.EscapyRenderable#renderGraphic(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		objectTextureRegion.setRegion((int) ((objectTexture.getWidth() / 10.) * (animob.getActualFrame())), 0,
				(int) ((float) objectTexture.getWidth() / 10.), objectTexture.getHeight());

		spriteBatcher.begin();
		makeSpriteFromTexture(objectTextureRegion, XPos(), YPos(), (float) zoom()).draw(spriteBatcher);
		spriteBatcher.end();
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

	/* (non-Javadoc)
	 * @see com.game.executable.EscapyExecutableObjects#actionAnimation()
	 */
	@Override
	public void actionAnimation() {
		startObjectAnimator(animator);
	}

	/* (non-Javadoc)
	 * @see com.game.executable.EscapyExecutableObjects#actionAnimationFinish()
	 */
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

	/* (non-Javadoc)
	 * @see com.game.render.extra.normals.EscapyNormalRender#renderNormals(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		// TODO Auto-generated method stub
		
	}

}
