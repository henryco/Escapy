#version 330 core
attribute vec2 a_texCoord0;
attribute vec3 a_position;

uniform mat4 u_projTrans;
uniform vec2 u_angles;
uniform vec2 u_radius;
uniform float u_angCorrect;
uniform vec2 u_umbra;

const float c_pi = radians(180);

out vec2 v_texCoord0;
out vec2 v_angles;
out vec2 v_radius;
out float v_angDiff;
out float v_theta;
out float v_umbraCoeff;
out float v_umbraPower;

void main(){
	v_texCoord0 = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
	v_radius = vec2(u_radius.s * 0.5, u_radius.t);
	v_angles = radians((u_angles - u_angCorrect) * 360);
	v_angles.s > v_angles.t ? v_angDiff = 1 : v_angDiff = 0;
	v_umbraCoeff = u_umbra.s;
	v_umbraPower = u_umbra.t;
	if (v_angDiff == 1) v_theta = (2.0 / abs(v_angles.s - v_angles.t));
	else v_theta = (2.0 / (2*c_pi - (abs(v_angles.s) + abs(v_angles.t))));

}