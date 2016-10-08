#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))
#define BlendScreenf(base, blend) 		(1.0 - ((1.0 - base) * (1.0 - blend)))

#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)
#define BlendScreen(base, blend) 		Blend(base, blend, BlendScreenf)
#define BlendAverage(base, blend) 		((base + blend) / 2.0)


uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;

void main() {

	vec4 targtetRGBA = texture2D(targetMap, v_texCoord0);
	if (targtetRGBA.a != 0) 
	{
		vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
		vec3 maskedMapPre = BlendColorDodge(targtetRGBA, blendRGBA);
		vec3 maskedMapPrePre = BlendScreen(maskedMapPre, targtetRGBA);
       // maskedMapPrePre = BlendAverage(maskedMapPrePre, targtetRGBA.rgb);

		//gl_FragColor = vec4(maskedMapPrePre, blendRGBA.a);
		gl_FragColor = vec4(max(maskedMapPrePre, targtetRGBA.rgb), blendRGBA.a);

	} else gl_FragColor = targtetRGBA;
}