#version 330 core
#define Blend(base, blend, funcf)		vec4(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b), (blend.a))
#define BlendColorMix(base, blend) 	Blend(base, blend, BlendAddf)
#define BlendAddf(base, blend)			min(base + blend, 1.0)
#define BlendAverage(base, blend) 		((base + blend) / 2.0)


uniform sampler2D targetMap;
uniform sampler2D blendMap;


in vec2 v_texCoord0;


void main() {
	
	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	if (targetRGBA.a != 0) {
        vec4 blendRGBA = texture2D(blendMap, v_texCoord0);

		gl_FragColor = BlendColorMix(targetRGBA, blendRGBA);
	}
	else gl_FragColor = targetRGBA;
}


