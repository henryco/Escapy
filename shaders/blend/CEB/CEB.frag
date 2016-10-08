#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))
	
#define BlendExclusionf(base, blend) 	(base + blend - 2.0 * base * blend)
#define BlendColorBurnf(base, blend) 	((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0))
#define BlendOverlayf(base, blend) 	(base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend)))

#define BlendExclusion(base, blend) 	Blend(base, blend, BlendExclusionf)
#define BlendColorBurn(base, blend) 	Blend(base, blend, BlendColorBurnf)
#define BlendOverlay(base, blend) 		Blend(base, blend, BlendOverlayf)


uniform sampler2D targetMap;
uniform sampler2D blendMap;

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
		//BlendColor BlendOverlay
		vec3 maskedMapPre = BlendColor(vec3(targetRGBA.r, targetRGBA.g, targetRGBA.b), vec3(blendRGBA.r, blendRGBA.g, blendRGBA.b));
		vec3 maskedMapPrePre = BlendExclusion(maskedMapPre, blendRGBA);
		vec3 maskedMapPrePrePre = BlendColorBurn(maskedMapPrePre, blendRGBA);

		gl_FragColor = vec4(maskedMapPrePrePre, blendRGBA.a);

	} else gl_FragColor = vec4(0.,0.,0.,0.);
}
