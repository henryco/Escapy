#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))
#define BlendOverlayf(base, blend) 	(base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend)))

#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)
#define BlendHardLight(base, blend) 	BlendOverlay(blend, base)
#define BlendOverlay(base, blend) 		Blend(base, blend, BlendOverlayf)
#define BlendAverage(base, blend) 		((base + blend) / 2.0)


uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;

void main() {

	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	if (targetRGBA.a != 0) 
	{
		vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
		vec3 maskedMapPre = BlendHardLight(targetRGBA, blendRGBA);
		vec3 maskedMapPrePre = BlendColorDodge(maskedMapPre, targetRGBA);
        maskedMapPrePre = BlendAverage(maskedMapPre, targetRGBA.rgb);
		gl_FragColor = vec4(maskedMapPrePre, blendRGBA.a);

	} else gl_FragColor = targetRGBA;
}