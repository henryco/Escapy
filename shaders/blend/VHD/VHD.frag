#version 330 core
#define Blend(base, blend, funcf) 		vec3(funcf(base.r, blend.r), funcf(base.g, blend.g), funcf(base.b, blend.b))

#define BlendHardMixf(base, blend) 	((BlendVividLightf(base, blend) < 0.5) ? 0.0 : 1.0)
#define BlendVividLightf(base, blend) 	((blend < 0.5) ? BlendColorBurnf(base, (2.0 * blend)) : BlendColorDodgef(base, (2.0 * (blend - 0.5))))
#define BlendColorBurnf(base, blend) 	((blend == 0.0) ? blend : max((1.0 - ((1.0 - base) / blend)), 0.0))
#define BlendColorDodgef(base, blend) 	((blend == 1.0) ? blend : min(base / (1.0 - blend), 1.0))
#define BlendDifferencef(base, blend) 	abs(base - blend)

#define BlendColorDodge(base, blend) 	Blend(base, blend, BlendColorDodgef)
#define BlendHardMix(base, blend) 		Blend(base, blend, BlendHardMixf)
#define BlendVividLight(base, blend) 	Blend(base, blend, BlendVividLightf)
#define BlendDifference(base, blend) 	Blend(base, blend, BlendDifferencef)

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
vec3 BlendHue(in vec3 base, in vec3 blend)
{
	vec3 baseHSL = RGBToHSL(base);
	return HSLToRGB(vec3(RGBToHSL(blend).r, baseHSL.g, baseHSL.b));
}
vec3 BlendSaturation(vec3 base, vec3 blend)
{
	vec3 baseHSL = RGBToHSL(base);
	return HSLToRGB(vec3(baseHSL.r, RGBToHSL(blend).g, baseHSL.b));
}
void main() {
	
	vec4 targetRGBA = texture2D(targetMap, v_texCoord0);
	vec4 blendRGBA = texture2D(blendMap, v_texCoord0);
	if (targetRGBA.a != 0) 
	{
		vec3 maskedMapPre = BlendVividLight(vec3(targetRGBA.r, targetRGBA.g, targetRGBA.b), vec3(blendRGBA.r,blendRGBA.g, blendRGBA.b));
		//vec3 maskedMapPrePre = BlendHue(maskedMapPre, vec3(blendRGBA.r,blendRGBA.g, blendRGBA.b));
		//vec3 maskedMapPrePrePre = BlendColorDodge(maskedMapPrePre, vec3(blendRGBA.r,blendRGBA.g, blendRGBA.b));

		gl_FragColor = vec4(maskedMapPre, blendRGBA.a);

	} else gl_FragColor = vec4(0.,0.,0.,0.);
}



