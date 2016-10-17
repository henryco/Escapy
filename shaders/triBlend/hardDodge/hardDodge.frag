#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))
#define BlendOverlayf(base, blend) 	(base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend)))

#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)
#define BlendHardLight(base, blend) 	BlendOverlay(blend, base)
#define BlendOverlay(base, blend) 		Blend(base, blend, BlendOverlayf)
#define BlendAverage(base, blend) 		((base + blend) / 2.0)


#define dota(one)       dot(vec3(1), one.rgb)
#define finFunc(scr, dst, a)   max(a * (scr - dst) + dst, dst)
#define finaly(src, dst)    vec4(finFunc(src.r, dst.r, src.a), finFunc(src.g, dst.g, src.a), finFunc(src.b, dst.b, src.a), 1)
uniform sampler2D u_texture1;
uniform sampler2D u_texture2;
uniform sampler2D u_texture3;
in float v_threshold;
in vec2 v_texCoord0;

void main() {

	vec4 textureRGBA_1 = texture2D(u_texture1, v_texCoord0); //TARGET CLEAR
	if (textureRGBA_1.a != 0) {
	    vec4 textureRGBA_2 = texture2D(u_texture2, v_texCoord0); //BLEND
	    vec4 textureRGBA_3 = texture2D(u_texture3, v_texCoord0); //TARGET MASKED
        if (textureRGBA_2.a != 0) {

            vec3 maskedMapPre = BlendHardLight(textureRGBA_1.rgb, textureRGBA_2.rgb);
		    vec3 maskedMapPrePre = BlendColorDodge(maskedMapPre, textureRGBA_1.rgb);
            maskedMapPrePre = BlendAverage(maskedMapPre, textureRGBA_1.rgb);
            vec4 color = vec4(maskedMapPrePre, textureRGBA_2.a);

            color = finaly(color, textureRGBA_3);
            if (dota(color) <= v_threshold) color.a = 0;
            gl_FragColor = color;
        }else gl_FragColor = textureRGBA_3;
	}
	else gl_FragColor = textureRGBA_1;
}