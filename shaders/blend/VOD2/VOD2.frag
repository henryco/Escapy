#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

uniform sampler2D targetMap;
uniform sampler2D blendMap;

#define BlendPhoenix(base, blend) 		(min(base, blend) - max(base, blend) + (1.0))
#define BlendExclusion(base, blend) 	(base + blend - 2.0 * base * blend)
#define BlendColorBurnf(base, blend) 	((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0))
#define BlendSubstractf(base, blend) 	max(base + blend - 1.0, 0.0)
#define BlendNegation(base, blend) 	((1.0) - abs((1.0) - base - blend))


in vec2 v_texCoord0;



void main() {
	
	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
	if (targetRGBA.a != 0) {

		//vec3 fkcinBigPre = BlendReflect(targetRGBA, blendRGBA);

        float rr = BlendExclusion(targetRGBA.r, blendRGBA.r);
         float gg = BlendExclusion(targetRGBA.g, blendRGBA.g);
          float bb = BlendExclusion(targetRGBA.b, blendRGBA.b);

   //     float rr = abs(targetRGBA.r - blendRGBA.r);
   //     float gg = abs(targetRGBA.g - blendRGBA.g);
   //     float bb = abs(targetRGBA.b - blendRGBA.b);

		gl_FragColor = vec4(vec3(rr, gg, bb), blendRGBA.a);

	} else gl_FragColor = targetRGBA;
}



