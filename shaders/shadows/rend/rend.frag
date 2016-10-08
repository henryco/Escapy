#version 330 core
#define PI 3.141

uniform sampler2D u_texture;
uniform vec2 resolution;


in vec2 v_texCoord0;
in vec4 v_color;
in vec2 v_angles;
in float v_angDiff;
in float v_gaussian[9];
in float v_blurVal[9];


float sample(vec2 coord, float r) {
    return step(r, texture2D(u_texture, coord).r);
}

float caclTheta(float xcoord) {
	return (PI * ((2*xcoord) - 1.0));
}


void main() {
   
    vec2 norm = v_texCoord0.st * 2.0 - 1.0;
	float brightness = 3;
    
	float theta = atan(norm.y, norm.x);
	float r = length(norm); 
    float coord = (theta + PI) / (2.0*PI);
//	float blur = (1./resolution.x) * smoothstep(0., 1., r);
	float blur = (1./resolution.x) * (1.0 - pow(r, 3));
	float sum = 0.0;
	vec2 tc = vec2(coord, 0.0);

	for (int i = 0; i < 9; i++) {
		sum += sample(vec2(tc.x + v_blurVal[i] * blur, tc.y), r) * v_gaussian[i];
	}	
	sum *= brightness;

//	vec4 rgbColor = v_color * vec4(vec3(1.0), sum * smoothstep(1.0, 0.0, r));
	vec4 rgbColor = v_color * vec4(vec3(1.0), sum * (1.0 - pow(r, 3)));
	gl_FragColor = rgbColor;

}



