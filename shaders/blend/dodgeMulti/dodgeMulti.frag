#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))
#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)

#define BlendMultiplyf(base, blend)		(base * blend)
#define BlendMultiply(base, blend) 		Blend(base, blend, BlendMultiplyf)


uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;

void main() {
	
	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	if (targetRGBA.a != 0) 
	{
		vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
		vec3 maskedMapPre = BlendMultiply(targetRGBA, blendRGBA);
		vec3 maskedMapPrePre = BlendColorDodge(maskedMapPre, targetRGBA);

		gl_FragColor = vec4(maskedMapPrePre, blendRGBA.a);

	} else gl_FragColor = targetRGBA;
}