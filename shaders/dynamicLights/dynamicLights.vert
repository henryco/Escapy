#version 330 core
attribute vec2 a_texCoord0;
attribute vec3 a_position;
 
uniform mat4 u_projTrans;
uniform vec2 fieldSize;

out vec4 v_texCoord0;

void main() {
	
	v_texCoord0 = vec4(a_texCoord0, 1.0, 1.0);
	gl_Position = u_projTrans * vec4(a_position, 1.0);
}