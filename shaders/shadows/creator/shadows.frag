#version 330 core
#define PI 3.14

uniform sampler2D u_texture;
uniform vec2 resolution;
uniform float u_THRESHOLD;

const float av = 0.333333;

in vec2 v_texCoord0;

void main(void) {

	float distancez = 1.0;
  	float alphaCol = 1.;

	for (float y=0.0; y<resolution.y; y+=1.0) {
 
		vec2 norm = vec2(v_texCoord0.s, y/resolution.y) * 2.0 - 1.0;
		float theta = PI*1.5 + norm.x * PI; 
		float r = (1.0 + norm.y) * 0.5;

		vec2 coord = vec2(-r * sin(theta), -r * cos(theta))/2.0 + 0.5;
		vec4 data = texture2D(u_texture, coord);
		float dst = y/resolution.y;
		float caster = dot(data.rgb, vec3(av));// * av;

		if (caster <= u_THRESHOLD) {
			distancez = min(distancez, dst);
			alphaCol = caster;
			break;
        }
  	} 
	gl_FragColor = vec4(vec3(distancez, alphaCol, alphaCol), 1); // G and B chanell FREE
}