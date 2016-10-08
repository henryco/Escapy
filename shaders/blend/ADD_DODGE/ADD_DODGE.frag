#version 330 core
#define Blend(base, blend, funcf)		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))
#define BlendColorMix(base, blend) 	Blend(base, blend, BlendAddf)
#define BlendAddf(base, blend)			min(base + blend, 1.0)
#define BlendAverage(base, blend) 		((base + blend) / 2.0)
#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))
#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)

#define BlendOverlayf(base, blend) 	(base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend)))
#define BlendOverlay(base, blend) 		Blend(base, blend, BlendOverlayf)

#define BlendSoftLightf(base, blend) 	((blend < 0.5) ? (2.0 * base * blend + base * base * (1.0 - 2.0 * blend)) : (sqrt(base) * (2.0 * blend - 1.0) + 2.0 * base * (1.0 - blend)))
#define BlendSoftLight(base, blend) 	Blend(base, blend, BlendSoftLightf)


uniform sampler2D targetMap;
uniform sampler2D blendMap;


in vec2 v_texCoord0;


void main() {
	
	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	if (targetRGBA.a != 0) {
        vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
        vec3 color = BlendColorMix(targetRGBA, blendRGBA);
        color = BlendColorDodge(color, targetRGBA.rgb);

        color = BlendAverage(color, targetRGBA.rgb);
		gl_FragColor = vec4(color.rgb, blendRGBA.a);
	}
	else gl_FragColor = targetRGBA;
}


