#version 330 core
uniform sampler2D targetMap;
uniform vec2 u_fieldSize;
uniform vec2 u_direction;

in vec2 v_texCoord0;
in float v_arr[25];

const vec4 maxvec = vec4(1.);

vec4 blur13(sampler2D image, vec2 uv, vec2 resolution, vec2 direction) {
  vec4 color = vec4(0.0);
  vec2 off1 = vec2(1.411764705882353) * direction;
  vec2 off2 = vec2(3.2941176470588234) * direction;
  vec2 off3 = vec2(5.176470588235294) * direction;
  color += texture2D(image, uv) * 0.1964825501511404;
  color += texture2D(image, uv + (off1 / resolution)) * 0.2969069646728344;
  color += texture2D(image, uv - (off1 / resolution)) * 0.2969069646728344;
  color += texture2D(image, uv + (off2 / resolution)) * 0.09447039785044732;
  color += texture2D(image, uv - (off2 / resolution)) * 0.09447039785044732;
  color += texture2D(image, uv + (off3 / resolution)) * 0.010381362401148057;
  color += texture2D(image, uv - (off3 / resolution)) * 0.010381362401148057;
  return color;
}

float blur25(sampler2D image, vec2 uv, vec2 resolution) {
 
	vec2 coords[] = vec2[](
		vec2(-2,2), vec2(-1,2), vec2(0,2), vec2(1,2), vec2(2,2),
		vec2(-2,1), vec2(-1,1), vec2(0,1), vec2(1,1), vec2(2,1),
		vec2(-2,0), vec2(-1,0), vec2(0,0), vec2(1,0), vec2(2,0),
		vec2(-2,-1), vec2(-1,-1), vec2(0,-1), vec2(1,-1), vec2(2,-1),
		vec2(-2,-2), vec2(-1,-2), vec2(0,-2), vec2(1,-2), vec2(2,-2)
	);
	float color = 0;

	for (int i = 0; i < 25; i++) {
		color += texture2D(image, uv + (coords[i].st / resolution)).a * v_arr[i];
	}	return color;
}

void main() {
	
	vec2 uv = vec2(gl_FragCoord.st / u_fieldSize.st);
	vec4 f_color = blur13(targetMap, uv, u_fieldSize, u_direction);
	f_color = vec4(texture2D(targetMap, uv).rgb, f_color.a);
//	vec4 f_color = vec4(texture2D(targetMap, uv).rgb, blur25(targetMap, uv, u_fieldSize));
	
	f_color *= 1.055;
	gl_FragColor = f_color;
}