#version 330 core
precision mediump float;

attribute vec2 a_texCoord0;
attribute vec3 a_position;

uniform mat4 u_projTrans;

out vec2 v_texCoord0;
out float v_arr[25];

void main(){

	v_texCoord0 = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
	v_arr = float[](
		0.000789, 0.006581, 0.013347, 0.006581, 0.000789,
		0.006581, 0.054901, 0.111345, 0.054901, 0.006581,
		0.013347, 0.111345, 0.225821, 0.111345, 0.013347,
		0.006581, 0.054901, 0.111345, 0.054901, 0.006581,
		0.000789, 0.006581, 0.013347, 0.006581, 0.000789
	);
}