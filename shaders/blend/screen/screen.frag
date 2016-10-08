#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendScreenf(base, blend) 		(1.0 - ((1.0 - base) * (1.0 - blend)))
#define BlendSoftLightf(base, blend) 	((blend < 0.5) ? (2.0 * base * blend + base * base * (1.0 - 2.0 * blend)) : (sqrt(base) * (2.0 * blend - 1.0) + 2.0 * base * (1.0 - blend)))

#define BlendSoftLight(base, blend) 	Blend(base, blend, BlendSoftLightf)
#define BlendScreen(base, blend) 		Blend(base, blend, BlendScreenf)


uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;

void main() {

	vec4 targtetRGBA = texture2D(targetMap, v_texCoord0);
	if (targtetRGBA.a != 0)
	{
		vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
		vec3 maskedMapPre = BlendScreen(targtetRGBA, blendRGBA);

		gl_FragColor = vec4(maskedMapPre, blendRGBA.a));

	} else gl_FragColor = targtetRGBA;
}