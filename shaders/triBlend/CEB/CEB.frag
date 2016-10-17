#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))
	
#define BlendExclusionf(base, blend) 	(base + blend - 2.0 * base * blend)
#define BlendColorBurnf(base, blend) 	((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0))
#define BlendOverlayf(base, blend) 	(base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend)))

#define BlendExclusion(base, blend) 	Blend(base, blend, BlendExclusionf)
#define BlendColorBurn(base, blend) 	Blend(base, blend, BlendColorBurnf)
#define BlendOverlay(base, blend) 		Blend(base, blend, BlendOverlayf)


#define dota(one)       dot(vec3(1), one.rgb)
#define finFunc(scr, dst, a)   max(a * (scr - dst) + dst, dst)
#define finaly(src, dst)    vec4(finFunc(src.r, dst.r, src.a), finFunc(src.g, dst.g, src.a), finFunc(src.b, dst.b, src.a), 1)
uniform sampler2D u_texture1;
uniform sampler2D u_texture2;
uniform sampler2D u_texture3;
in float v_threshold;
in vec2 v_texCoord0;


vec3 RGBToHSL(vec3 color)
{
	vec3 hsl; 
	
	float fmin = min(min(color.r, color.g), color.b);    
	float fmax = max(max(color.r, color.g), color.b);    
	float delta = fmax - fmin;             

	hsl.z = (fmax + fmin) / 2.0; 

	if (delta == 0.0)		
	{
		hsl.x = 0.0;	
		hsl.y = 0.0;	
	}
	else                        
	{
		if (hsl.z < 0.5)
			hsl.y = delta / (fmax + fmin);
		else
			hsl.y = delta / (2.0 - fmax - fmin); 
		
		float deltaR = (((fmax - color.r) / 6.0) + (delta / 2.0)) / delta;
		float deltaG = (((fmax - color.g) / 6.0) + (delta / 2.0)) / delta;
		float deltaB = (((fmax - color.b) / 6.0) + (delta / 2.0)) / delta;

		if (color.r == fmax )
			hsl.x = deltaB - deltaG; 
		else if (color.g == fmax)
			hsl.x = (1.0 / 3.0) + deltaR - deltaB; 
		else if (color.b == fmax)
			hsl.x = (2.0 / 3.0) + deltaG - deltaR; 

		if (hsl.x < 0.0)
			hsl.x += 1.0; 
		else if (hsl.x > 1.0)
			hsl.x -= 1.0; 
	}

	return hsl;
}

float HueToRGB(float f1, float f2, float hue)
{
	if (hue < 0.0)
		hue += 1.0;
	else if (hue > 1.0)
		hue -= 1.0;
	float res;
	if ((6.0 * hue) < 1.0)
		res = f1 + (f2 - f1) * 6.0 * hue;
	else if ((2.0 * hue) < 1.0)
		res = f2;
	else if ((3.0 * hue) < 2.0)
		res = f1 + (f2 - f1) * ((2.0 / 3.0) - hue) * 6.0;
	else
		res = f1;
	return res;
}

vec3 HSLToRGB(vec3 hsl)
{
	vec3 rgb;
	
	if (hsl.y == 0.0)
		rgb = vec3(hsl.z); // Luminance
	else
	{
		float f2;
		
		if (hsl.z < 0.5)
			f2 = hsl.z * (1.0 + hsl.y);
		else
			f2 = (hsl.z + hsl.y) - (hsl.y * hsl.z);
			
		float f1 = 2.0 * hsl.z - f2;
		
		rgb.r = HueToRGB(f1, f2, hsl.x + (1.0/3.0));
		rgb.g = HueToRGB(f1, f2, hsl.x);
		rgb.b= HueToRGB(f1, f2, hsl.x - (1.0/3.0));
	}
	
	return rgb;
}

vec3 BlendColor(in vec3 base, in vec3 blend)
{
	vec3 blendHSL = RGBToHSL(blend);
	return HSLToRGB(vec3(blendHSL.r, blendHSL.g, RGBToHSL(base).b));
}


void main() {
	
	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
	if (targetRGBA.a != 0) 
	{
		vec3 maskedMapPre = BlendColor(vec3(targetRGBA.r, targetRGBA.g, targetRGBA.b), vec3(blendRGBA.r, blendRGBA.g, blendRGBA.b));
		vec3 maskedMapPrePre = BlendExclusion(maskedMapPre, blendRGBA);
		vec3 maskedMapPrePrePre = BlendColorBurn(maskedMapPrePre, blendRGBA);

		gl_FragColor = vec4(maskedMapPrePrePre, blendRGBA.a);

	} else gl_FragColor = vec4(0.,0.,0.,0.);
}

void main() {

	vec4 textureRGBA_1 = texture2D(u_texture1, v_texCoord0); //TARGET CLEAR
	if (textureRGBA_1.a != 0) {
	    vec4 textureRGBA_2 = texture2D(u_texture2, v_texCoord0); //BLEND
	    vec4 textureRGBA_3 = texture2D(u_texture3, v_texCoord0); //TARGET MASKED
        if (textureRGBA_2.a != 0) {

            vec3 maskedMapPre = BlendColor(textureRGBA_1.rgb, textureRGBA_2.rgb);
		    vec3 maskedMapPrePre = BlendExclusion(maskedMapPre, textureRGBA_2.rgb);
		    vec3 maskedMapPrePrePre = BlendColorBurn(maskedMapPrePre, textureRGBA_2.rgb);

            vec4 color = vec4(maskedMapPrePrePre, textureRGBA_2.a);

            color = finaly(color, textureRGBA_3);
            if (dota(color) <= v_threshold) color.a = 0;
            gl_FragColor = color;
        } else gl_FragColor = textureRGBA_3;
	}
	else gl_FragColor = textureRGBA_1;
}