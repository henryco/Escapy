#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendVividLightf(base, blend) 	((blend < 0.5) ? BlendColorBurnf(base, (2.0 * blend)) : BlendColorDodgef(base, (2.0 * (blend - 0.5))))
#define BlendColorBurnf(base, blend) 	((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0))
#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))

#define BlendVividLight(base, blend) 	Blend(base, blend, BlendVividLightf)
#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)


uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;

void main() {

	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	if (targetRGBA.a != 0) 
	{
		vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
		vec3 maskedMapPre = BlendVividLight(targetRGBA, blendRGBA);
		vec3 maskedMapPrePre = BlendColorDodge(maskedMapPre, targetRGBA);

		gl_FragColor = vec4(maskedMapPrePre, blendRGBA.a);

	} else gl_FragColor = targetRGBA;
}
