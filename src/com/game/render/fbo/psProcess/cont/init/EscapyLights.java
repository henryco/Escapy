package com.game.render.fbo.psProcess.cont.init;

import com.game.render.fbo.psProcess.cont.EscapyLightContainer;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Henry on 22/09/16.
 */
public class EscapyLights {

	public EscapyLightContainer[] lightContainers = new EscapyLightContainer[0];
	private int size;
	public EscapyLights() {

	}

	public EscapyLights addLightContainer(EscapyLightContainer container) {

		EscapyLightContainer[] tmp = new EscapyLightContainer[lightContainers.length + 1];
		System.arraycopy(lightContainers, 0, tmp, 0, lightContainers.length);
		tmp[tmp.length - 1] = container;
		lightContainers = tmp;
		size += 1;
		return this;
	}

	public void forEachFunc(Function<EscapyLightContainer, Object> fucnt) {
		for (EscapyLightContainer container : lightContainers) fucnt.apply(container);
	}

	public void forEach(Consumer<EscapyLightContainer> consumer) {
		for (EscapyLightContainer container : lightContainers) consumer.accept(container);
	}

	public int size(){
		return size;
	}
}
