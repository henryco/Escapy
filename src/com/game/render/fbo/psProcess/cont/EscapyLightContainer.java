package com.game.render.fbo.psProcess.cont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.utils.absContainer.EscapyAbsContainer;

/**
 * @author Henry on 22/09/16.
 */
public class EscapyLightContainer extends EscapyAbsContainer<AbsStdLight>  {


	public static final int[] ADD_RGB = new int[]{GL30.GL_SRC_ALPHA, GL30.GL_ONE, GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_COLOR};
	public static final int[] ADD_RGBA = new int[]{GL30.GL_SRC_ALPHA, GL30.GL_ONE, GL30.GL_ONE, GL30.GL_ONE_MINUS_SRC_COLOR};

	private int[] blendMode;

	private Batch batch;

	public EscapyLightContainer() {
		batch = new SpriteBatch();
		setColorBlendMode(ADD_RGBA);
	}
	public EscapyLightContainer(int[] blendMode) {
		batch = new SpriteBatch();
		setColorBlendMode(blendMode);
	}


	public EscapyLightContainer setColorBlendMode(int[] colorBlendMode) {
		this.blendMode = colorBlendMode.clone();
		return this;
	}

	private void setProgram(int p1, int p2, int p3, int p4) {
		batch.setBlendFunction(-1, -1);
		Gdx.gl30.glBlendFuncSeparate(p1,p2, p3, p4);
		Gdx.gl30.glBlendEquationSeparate(GL30.GL_FUNC_ADD, GL30.GL_FUNC_ADD);
	}
	private void setProgram(int[] program) {
		this.setProgram(program[0], program[1], program[2], program[3]);
	}


	public EscapyLightContainer makeLights() {
		super.targetsList.forEach(light -> {
			if (light.isVisible() && light.isNeedUpdate()) light.lazyRender(null);
		});
		return this;
	}


	public void renderLights(EscapyGdxCamera camera) {

		Sprite tmpSprite;
		camera.update();
		batch.setProjectionMatrix(camera.combined());

		int srcFunc = batch.getBlendSrcFunc();
		int dstFunc = batch.getBlendDstFunc();

		batch.begin();
		batch.enableBlending();

		setProgram(blendMode);

		for (AbsStdLight tempLight : targetsList) {
			tmpSprite = new Sprite(tempLight.getFBO().getTextureRegion());
			tmpSprite.setPosition(
					tempLight.lightSource.getPosition().x,
					tempLight.lightSource.getPosition().y - 12
			);
			tmpSprite.setScale(tempLight.getScale());

			tmpSprite.draw(batch);
		}
		batch.disableBlending();
		batch.end();

		batch.begin();
		batch.setBlendFunction(srcFunc, dstFunc);
		batch.end();

	}
	public void renderLights(EscapyGdxCamera camera, Sprite target) {
		Sprite tmpSprite;

		int srcFunc = batch.getBlendSrcFunc();
		int dstFunc = batch.getBlendDstFunc();

		float x = camera.getCamera().position.x;
		float y = camera.getCamera().position.y;

		camera.setCameraPosition(target.getX() + (target.getWidth() / 2.f),
				target.getY() + (target.getHeight() / 2f));
		batch.setProjectionMatrix(camera.combined());
		batch.begin();
		batch.enableBlending();
		setProgram(blendMode);
		target.draw(batch);
		batch.end();

		camera.setCameraPosition(x, y);
		batch.setProjectionMatrix(camera.combined());
		batch.begin();

		for (AbsStdLight tempLight : targetsList) {
			tmpSprite = new Sprite(tempLight.getFBO().getTextureRegion());
			tmpSprite.setPosition(
					tempLight.lightSource.getPosition().x,
					tempLight.lightSource.getPosition().y - 12
			);
			tmpSprite.setScale(tempLight.getScale());

			tmpSprite.draw(batch);
		}
		batch.disableBlending();
		batch.end();

		batch.begin();
		batch.setBlendFunction(srcFunc, dstFunc);
		batch.end();

	}

	public EscapyFBO renderLightsBuffered(EscapyGdxCamera camera, EscapyFBO fbo) {
		fbo.begin();
		renderLights(camera);
		return fbo.end();
	}
	public EscapyFBO renderLightsBuffered(EscapyGdxCamera camera, EscapyFBO fbo, Sprite target) {
		fbo.begin();
		renderLights(camera, target);
		return fbo.end();
	}

}
