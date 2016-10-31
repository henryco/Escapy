#version 330 core
attribute vec4 a_color;
attribute vec2 a_texCoord0;
attribute vec3 a_position;

uniform mat4 u_projTrans;
uniform vec2 u_angles;
uniform float u_angCorrect;

out vec4 v_color;
out vec2 v_texCoord0;
out vec2 v_angles;
out float v_angDiff;
out float v_gaussian[9];
out float v_blurVal[9];

void main(){
	v_angles = u_angles - u_angCorrect; 
//	(v_angles.s > v_angles.t) ? v_angDiff = 1 : v_angDiff = 0;
	v_angDiff = (v_angles.s > v_angles.t) ? 1 : 0;
	
	v_color = a_color;
	v_texCoord0 = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
	v_gaussian = float[](0.05, 0.09, 0.12, 0.15, 0.16,
		0.15, 0.12, 0.09, 0.05);
	v_blurVal = float[](-4.0, -3.0, -2.0, -1.0,
		0.0, 1.0, 2.0, 3.0, 4.0);
}