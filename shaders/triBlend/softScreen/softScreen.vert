#version 330 core
attribute vec2 a_texCoord0;
attribute vec3 a_position;

uniform mat4 u_projTrans;

out vec2 v_texCoord0;

uniform float u_threshold;
out float v_threshold;

void main(){

	v_texCoord0 = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
    v_threshold = u_threshold * 3;

}