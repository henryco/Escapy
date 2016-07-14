package com.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

// TODO: Auto-generated Javadoc
/**
 * The Class shaderTest.
 */
public class shaderTest implements ApplicationListener {

	/** The mask. */
	Texture tex0, tex1, mask;
	
	/** The batch. */
	SpriteBatch batch;
	
	/** The cam. */
	OrthographicCamera cam;
	
	/** The shader. */
	ShaderProgram shader;

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		final String VERTEX = Gdx.files.internal("shader/vert1.glsl").readString();
		final String FRAGMENT = Gdx.files.internal("shader/frag1.glsl").readString();

		// the texture does not matter since we will ignore it anyways
		// tex = new Texture(256, 256, Format.RGBA8888);
		tex0 = new Texture(Gdx.files.internal("data/libgdx.png"));
		tex1 = new Texture(Gdx.files.internal("data/libgdx1.png"));
		mask = new Texture(Gdx.files.internal("data/libgdx2.png"));

		// important since we aren't using some uniforms and attributes that
		// SpriteBatch expects
		ShaderProgram.pedantic = false;

		shader = new ShaderProgram(VERTEX, FRAGMENT);

		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}

		if (shader.getLog().length() != 0)
			System.out.println(shader.getLog());

		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false);

		shader.begin();
		shader.setUniformi("u_texture1", 1);
		shader.setUniformi("u_mask", 2);
		GL20 gl = Gdx.graphics.getGL20();
		// make GL_TEXTURE2 the active texture unit, then bind our mask texture
		gl.glActiveTexture(GL20.GL_TEXTURE2);
		mask.bind();

		// do the same for our other two texture units
		gl.glActiveTexture(GL20.GL_TEXTURE1);
		tex1.bind();

		gl.glActiveTexture(GL20.GL_TEXTURE0);
		tex0.bind();
		shader.end();

		batch = new SpriteBatch(1000, shader);
		batch.setShader(shader);

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
		batch.setProjectionMatrix(cam.combined);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#render()
	 */
	@Override
	public void render() {
		batch.begin();

		// notice that LibGDX coordinate system origin is lower-left
		batch.draw(tex0, 10, 10);
		// batch.draw(tex, 10, 320, 32, 32);

		batch.end();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void pause() {

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	@Override
	public void resume() {

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
		batch.dispose();
		shader.dispose();
		tex0.dispose();
		tex1.dispose();
		mask.dispose();
	}

}
