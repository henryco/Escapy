#version 330 core
uniform sampler2D normalMap;
uniform sampler2D color_map;
uniform vec2 light;
uniform vec2 screen;
uniform float dist;
uniform vec4 io_lightColor;

in vec2 v_texCoord0;

void main() {
	
	vec4 normalRGBA = texture2D(normalMap, v_texCoord0);
	vec4 texColorRGBA = texture2D(color_map, v_texCoord0);
	if (normalRGBA.a != 0) 
	{
		vec3 normal = normalRGBA.rgb;
		normal = 2.0*normal - 1.0;
   		vec3 n = normalize(normal);
		vec3 l = normalize(vec3((gl_FragCoord.xy-light.xy)/screen, dist));
		float a = dot(n, l);

		vec4 aTexColorRGBA = a * texColorRGBA;
		vec4 texColor = aTexColorRGBA;

		gl_FragColor = texColor;

	} else 
		gl_FragColor = texColorRGBA;
}