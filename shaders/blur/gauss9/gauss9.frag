#version 330 core
uniform sampler2D targetMap;
uniform vec2 u_fieldSize;
uniform vec2 u_direction;

in vec2 v_texCoord0;

vec4 blur9(in sampler2D image, in vec2 uv, in vec2 resolution, in vec2 direction) {
	vec4 color = vec4(0.0);
	vec2 off1 = vec2(1.3846153846) * direction;
	vec2 off2 = vec2(3.2307692308) * direction;
	color += texture2D(image, uv) * 0.2270270270;
	color += texture2D(image, uv + (off1 / resolution)) * 0.3162162162;
	color += texture2D(image, uv - (off1 / resolution)) * 0.3162162162;
	color += texture2D(image, uv + (off2 / resolution)) * 0.0702702703;
	color += texture2D(image, uv - (off2 / resolution)) * 0.0702702703;
  	return color;
}

void main() {
	
	vec2 uv = vec2(gl_FragCoord.st / u_fieldSize.st);
	vec4 f_color = blur9(targetMap, uv, u_fieldSize, u_direction);
	gl_FragColor = f_color;
}