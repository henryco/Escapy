package com.game.render.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.utils.arrContainer.EscapyArray;

import java.util.Date;
import java.util.function.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyShaderRender.
 */
public abstract class EscapyShaderRender {


	public final class Uniforms <T> {

		private Consumer<ShaderProgram> loader;
		private EscapyArray<T> uni;
		private EscapyArray<String> str;
		private T[] uniforms;
		private String[] names;

		private Float[] fArr;
		private Integer[] iArr;
		private Float[][] fFArr;
		private Integer[][] iIArr;

		public Uniforms(Class<T> objClass) {
			this.uni = new EscapyArray<T>(objClass){};
			this.str = new EscapyArray<String>(String.class){};
			this.uniforms = uni.container;
			this.names = str.container;
			try {
				if (indexOf(objClass, 0)){
					iArr = (Integer[]) uniforms;
					loader = program -> {for (int i = 0; i < iArr.length; i++) program.setUniformi(names[i], iArr[i]);};
				}
				else if (indexOf(objClass, 0f)){
					fArr = (Float[]) uniforms;
					loader = program -> {for (int i = 0; i < fArr.length; i++) program.setUniformf(names[i], fArr[i]);};
				}
				else if (indexOf(objClass, new Float[0])) {
					fFArr = (Float[][]) uniforms;
					loader = program -> {
						for (int k = 0; k < fFArr.length; k++) for (int i = 0; i < fFArr[k].length; i++) program.setUniformf(names[k], fFArr[k][i]);
					};
				}
				else if (indexOf(objClass, new Integer[0])) {
					iIArr = (Integer[][]) uniforms;
					loader = program -> {
						for (int k = 0; k < iIArr.length; k++) for (int i = 0; i < iIArr[k].length; i++) program.setUniformi(names[k], iIArr[k][i]);
					};
				}

			} catch (Exception ignored) {}
		}

		private boolean indexOf(Class<T> tc, Object arg1){
			try{
				return tc.isAssignableFrom(arg1.getClass());
			}catch(Exception e){
				return false;
			}
		}

		public Uniforms addUniform(String name, T val) {
			uni.addSource(val);
			str.addSource(name);
			uniforms = uni.container;
			names = str.container;

			try {
				iArr = (Integer[]) uniforms;
			} catch (Exception a) {
				try {
					fArr = (Float[]) uniforms;
				} catch (Exception b) {
					try {
						fFArr = (Float[][]) uniforms;
					} catch (Exception c) {
						try {
							iIArr = (Integer[][]) uniforms;
						} catch (Exception ignored) {}
					}
				}
			}
			return this;
		}

		public void loadUniforms(ShaderProgram program) {
			loader.accept(program);
		}

		public T get(int index) {
			return uniforms[index];
		}
		public T get(String name) {
			for (int i = 0; i < names.length; i++) if (names[i].equalsIgnoreCase(name)) return uniforms[i];
			return null;
		}
		public void set(int index, T val) {
			uni.container[index] = val;
		}
		public void set(String name, T val) {
			for (int i = 0; i < str.container.length; i++) if (str.container[i].equalsIgnoreCase(name)) uni.container[i] = val;
		}
	}


	public final Uniforms<Float> floatUniforms = new Uniforms<>(Float.class);
	public final Uniforms<Integer> intUniforms = new Uniforms<>(Integer.class);
	public final Uniforms<Float[]> floatArrUniforms = new Uniforms<>(Float[].class);
	public final Uniforms<Integer[]> intArrUniforms = new Uniforms<>(Integer[].class);

	protected Consumer<ShaderProgram> shaderLoader = program -> {};

	protected final int id;
	protected SpriteBatch batcher;

	public EscapyShaderRender() {
		this.id = generateID();
		this.batcher = new SpriteBatch();
	}
	public EscapyShaderRender(int id) {
		this.id = id;
		this.batcher = new SpriteBatch();
	}

	public abstract String toString();
	public abstract EscapyShaderRender initShaderProgram(String VERTEX, String FRAGMENT);

	public EscapyShaderRender setCustomUniforms(boolean uniforms) {
		if (uniforms) shaderLoader = program -> {
			floatUniforms.loadUniforms(program);
			intUniforms.loadUniforms(program);
			floatArrUniforms.loadUniforms(program);
			intArrUniforms.loadUniforms(program);
		};
		else shaderLoader = program -> {};
		return this;
	}


	/**
	 * Check status.
	 *
	 * @param program
	 *            the program
	 */
	protected void checkStatus(ShaderProgram program) {
		System.err.println(program.isCompiled() ? "COMPILED: "+this.toString() : "ERROR: "+this.toString()+"\n"+program.getLog()+"\n");
		if (!program.isCompiled()) {
			FileHandle file = Gdx.files.local("error_log.txt");
			file.writeString(new Date(TimeUtils.millis()).toString()+"\nERROR: "+this.toString()+"\n"+program.getLog()+"\n", true);
		}
	}

	/**
	 * Draw sprite.
	 *
	 * @param sprite
	 *            the sprite
	 * @param camera
	 *            the camera
	 */
	public void drawSprite(Sprite sprite, OrthographicCamera camera) {	
		batcher.flush();
		camera.update(); //FIXME
		this.batcher.setProjectionMatrix(camera.combined);
		this.batcher.begin();
		sprite.draw(batcher);
		this.batcher.end();
	}


	/**
	 * Draw texture.
	 *
	 * @param texture
	 *            the texture
	 * @param camera
	 *            the camera
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void drawTexture(Texture texture, OrthographicCamera camera, float x, float y) {
		batcher.flush();
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		this.batcher.draw(texture, x, y);
		this.batcher.end();
	}

	/**
	 * Draw texture region.
	 *
	 * @param region
	 *            the region
	 * @param camera
	 *            the camera
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param widht
	 *            the widht
	 * @param height
	 *            the height
	 */
	public void drawTextureRegion(TextureRegion region, OrthographicCamera camera, float x, float y, float widht,
			float height) {
		batcher.flush();
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		this.batcher.draw(region, x, y, widht, height);
		this.batcher.end();
	}

	/**
	 * Sets the sprite batch.
	 *
	 * @param batcher
	 *            the batcher
	 * @return the escapy shader render
	 */
	public EscapyShaderRender setSpriteBatch(SpriteBatch batcher) {
		this.batcher = batcher;
		return this;
	}
	
	/**
	 * Generate ID.
	 *
	 * @return the int
	 */
	private int generateID() {
		return this.hashCode();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}
	
	protected String removeFRAG(String url) {
		//.frag
		StringBuffer strb = new StringBuffer(url);
		if (strb.charAt(strb.length()-5) == '.')
			strb.delete(strb.length()-5, strb.length());
		return strb.toString();
	}
	
}
