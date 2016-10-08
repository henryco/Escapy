#version 330 core
uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;

void main() {

	vec4 targtetRGBA = texture2D(targetMap, v_texCoord0);
	vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
	
	vec4 maskedMap = vec4(targtetRGBA * blendRGBA);  
	gl_FragColor = maskedMap;
}