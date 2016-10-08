#version 330 core
uniform sampler2D targetMap;
uniform vec2 u_fieldSize;
uniform vec2 u_direction;

in vec2 v_texCoord0;

vec4 blur5(sampler2D image, vec2 uv, vec2 resolution, vec2 direction) {
  vec4 color = vec4(0.0);
  vec2 off1 = vec2(1.3333333333333333) * direction;
  color += texture2D(image, uv) * 0.29411764705882354;
  color += texture2D(image, uv + (off1 / resolution)) * 0.35294117647058826;
  color += texture2D(image, uv - (off1 / resolution)) * 0.35294117647058826;
  return color; 
}

void main() {
	
	vec2 uv = vec2(gl_FragCoord.st / u_fieldSize.st);
	vec4 f_color = blur5(targetMap, uv, u_fieldSize, u_direction);
	gl_FragColor = f_color;
}