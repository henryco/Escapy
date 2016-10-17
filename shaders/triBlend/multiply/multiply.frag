#version 330 core
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

            vec4 color = vec4(textureRGBA_1 * textureRGBA_2);

            color = finaly(color, textureRGBA_3);
            if (dota(color) <= v_threshold) color.a = 0;
            gl_FragColor = color;
        }else gl_FragColor = textureRGBA_3;
	}
	else gl_FragColor = textureRGBA_1;
}