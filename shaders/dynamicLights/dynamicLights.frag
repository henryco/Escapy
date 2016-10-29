#version 330 core

#define dota(one, two)       dot(two, one.rgb)

in vec4 v_texCoord0;

uniform sampler2D normalMap;
uniform sampler2D colorMap;
uniform sampler2D maskMap;
uniform float ambientIntensity;
uniform float directIntensity;
uniform float shadowIntensity;
uniform float spriteSize;
uniform float height;
uniform float threshold;
uniform vec2 fieldSize;

const vec3 av = vec3(0.33333);
const vec3 c_one = vec3(1);
const int c_dir[3] = int[](-1, 0, 1);

vec4 floorLight(in vec4 light, in float comp) {
	return vec4(min(light.r, comp), min(light.g, comp), 
		min(light.b, comp), min(light.a, comp));
}

vec4 cutLight(in vec4 light, in float comp) {
	float m = max(comp, light.r);
	m = max(m, light.g);
	m = max(m, light.b);
	vec4 returnable = light + max(0.0, m-1.); 
	return returnable;
}


vec2 getLightDirection(sampler2D image, vec2 uv, vec2 resolution, float size) {

    vec2 dir = vec2(0);
    for (int x = 0; x < 3; x++){
        for (int y = 0; y < 3; y++){
            vec2 uv_dir = vec2(uv.x + (size * c_dir[x]) / resolution.s, uv.y + (size * c_dir[y]) / resolution.t);
            float brightness = dot(texture2D(image, uv_dir).rgb, av);
            vec2 direction = vec2(c_dir[x], c_dir[y]) * brightness;
            dir += direction;
        }
    }
    return normalize(dir);
}

void main() {

	vec4 col = texture2D(colorMap, v_texCoord0.st);	

	if (col.a != 0) {

        vec4 maskRGBA = texture2D(maskMap, v_texCoord0.st);
        col = max(maskRGBA, col);
        if (dota(c_one, col.rgb) <= threshold) gl_FragColor = vec4(0);
        else {
            vec2 uv = vec2(gl_FragCoord.st / fieldSize.st);
            vec3 normal = normalize(2.0 * texture2D(normalMap, uv).rgb - 1.0);
            float z = min(1, dot((col.rgb * directIntensity) - shadowIntensity, av));
            vec3 direction = vec3(getLightDirection(colorMap, uv, fieldSize, spriteSize), z);
            normal.r *= -1;
            float a = (dot(direction, normal) * ambientIntensity);
            a = (min(1, max(height * col.a, a + height)));
            vec4 finVec = vec4(col.rgb, min(a, col.a));

        	gl_FragColor = finVec;
        }
	}	else gl_FragColor = col;
}



