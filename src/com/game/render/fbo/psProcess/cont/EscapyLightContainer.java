package com.game.render.fbo.psProcess.cont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.program.gl.separate.GLBlendProgram;
import com.game.render.program.shader.blend.EscapyBlendRenderer;
import com.game.render.program.shader.blend.ShaderBlendProgram;
import com.game.utils.absContainer.EscapyAbsContainer;

/**
 * @author Henry on 22/09/16.
 */
public class EscapyLightContainer extends EscapyAbsContainer<AbsStdLight>  {

	public static final GLBlendProgram glProgram = new GLBlendProgram();

	private int[] blendMode;


	private Batch batch;
	private EscapyFBO colorizedFBO;
	private EscapyBlendRenderer shaderBlendProgram;

	private EscapyLightContainer() {
	}

	public EscapyLightContainer(int[] additiveBlendMode) {
		batch = new SpriteBatch();
		setColorBlendMode(additiveBlendMode);
		initFBO();
	}
	public EscapyLightContainer(EscapyBlendRenderer shaderBlendProgram) {
		batch = new SpriteBatch();
		setShaderBlendProgram(shaderBlendProgram);
		initFBO();
	}
	public EscapyLightContainer(int[] additiveBlendMode, EscapyBlendRenderer shaderBlendProgram) {
		batch = new SpriteBatch();
		setColorBlendMode(additiveBlendMode);
		setShaderBlendProgram(shaderBlendProgram);
		initFBO();
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

		colorizedFBO.begin().wipeFBO();

		batch.begin();
		batch.enableBlending();

		setProgram(blendMode);

		for (AbsStdLight tempLight : targetsList) {
			if (tempLight.isVisible()) {
				tmpSprite = new Sprite(tempLight.getFBO().getTextureRegion());
				tmpSprite.setPosition(
						tempLight.lightSource.getPosition().x,
						tempLight.lightSource.getPosition().y
				);
				tmpSprite.setScale(tempLight.getScale());
				tmpSprite.draw(batch);
			}
		}
		batch.disableBlending();
		batch.end();

		batch.begin();
		batch.setBlendFunction(srcFunc, dstFunc);
		batch.end();

		colorizedFBO.end();
	}

	public EscapyFBO renderBlendedLights(EscapyGdxCamera camera, Sprite target, EscapyFBO fbo) {

		this.renderLights(camera);
		fbo.begin();
		this.shaderBlendProgram.renderBlended(target, colorizedFBO.getSpriteRegion(), fbo.getFBOCamera().getCamera());
		return fbo.end();
	}

	public EscapyFBO renderBlendedLights(EscapyGdxCamera camera, Texture target, EscapyFBO fbo) {

		this.renderLights(camera);
		fbo.begin();
		this.shaderBlendProgram.renderBlended(target, colorizedFBO.getTextureRegion().getTexture(), 0, 0, fbo.getFBOCamera().getCamera());
		return fbo.end();
	}



	public EscapyLightContainer initFBO() {
		colorizedFBO = new StandartFBO();
		return this;
	}

	public EscapyLightContainer setColorBlendMode(int[] colorBlendMode) {
		this.blendMode = colorBlendMode.clone();
		return this;
	}

	public EscapyLightContainer setShaderBlendProgram(EscapyBlendRenderer shaderBlendProgram) {
		if (shaderBlendProgram != null) this.shaderBlendProgram = shaderBlendProgram;
		else this.shaderBlendProgram = ShaderBlendProgram.blendProgram(ShaderBlendProgram.program.SCREEN);
		return this;
	}


}
