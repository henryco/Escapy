#version 330 core
#define dota(one, two)       dot(two.rgb, one.rgb)

uniform sampler2D targetMap;
uniform sampler2D blendMap;
uniform float threshold;

const vec3 c_one = vec3(1);

in vec2 v_texCoord0;


void main() {

	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
	if (targetRGBA.a != 0)
	{
        vec4 fin = vec4(0);
        fin = max(blendRGBA, targetRGBA);
        if (dota(c_one.rgb, fin.rgb) <= threshold)
            gl_FragColor = vec4(0);
        else
		    gl_FragColor = fin;
	} else gl_FragColor = targetRGBA;
}
