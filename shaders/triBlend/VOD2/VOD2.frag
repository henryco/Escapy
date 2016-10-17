#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))


#define BlendPhoenix(base, blend) 		(min(base, blend) - max(base, blend) + (1.0))
#define BlendExclusion(base, blend) 	(base + blend - 2.0 * base * blend)
#define BlendColorBurnf(base, blend) 	((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0))
#define BlendSubstractf(base, blend) 	max(base + blend - 1.0, 0.0)
#define BlendNegation(base, blend) 	((1.0) - abs((1.0) - base - blend))



#define dota(one)       dot(vec3(1), one.rgb)
#define finFunc(scr, dst, a)   max(a * (scr - dst) + dst, dst)
#define finaly(src, dst)    vec4(finFunc(src.r, dst.r, src.a), finFunc(src.g, dst.g, src.a), finFunc(src.b, dst.b, src.a), 1)
uniform sampler2D u_texture1;
uniform sampler2D u_texture2;
uniform sampler2D u_texture3;
in float v_threshold;
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

void main() {

	vec4 textureRGBA_1 = texture2D(u_texture1, v_texCoord0); //TARGET CLEAR
	if (textureRGBA_1.a != 0) {
	    vec4 textureRGBA_2 = texture2D(u_texture2, v_texCoord0); //BLEND
	    vec4 textureRGBA_3 = texture2D(u_texture3, v_texCoord0); //TARGET MASKED
        if (textureRGBA_2.a != 0) {

            vec3 maskedMapPre = BlendColorDodge(textureRGBA_1.rgb, textureRGBA_2.rgb);
		    vec3 maskedMapPrePre = BlendAverage(maskedMapPre, textureRGBA_1.rgb);

            vec4 color = vec4(maskedMapPrePre, textureRGBA_2.a);

            color = finaly(color, textureRGBA_3);
            if (dota(color) <= v_threshold) color.a = 0;
            gl_FragColor = color;
        }else gl_FragColor = textureRGBA_3;
	}
	else gl_FragColor = textureRGBA_1;
}


