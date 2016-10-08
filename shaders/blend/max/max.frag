#version 330 core
uniform sampler2D targetMap;
uniform sampler2D blendMap;

in vec2 v_texCoord0;


void main() {

	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
	if (targetRGBA.a != 0)
	{
        vec4 fin = vec4(0);
        if (blendRGBA.a > 0) {
            fin = max(blendRGBA, targetRGBA);
            if (fin.rgb == targetRGBA.rgb) fin.a = 0;
            //    fin = mix(fin, targetRGBA, blendRGBA);
           // }
        }
		gl_FragColor = fin;

	} else gl_FragColor = targetRGBA;
}
