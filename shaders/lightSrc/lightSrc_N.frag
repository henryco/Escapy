#version 330 core
uniform sampler2D targetMap;
uniform sampler2D u_lightMap;

uniform vec2 u_fieldSize;
uniform vec3 u_color;
uniform float u_coeff;

in vec2 v_texCoord0;
in vec2 v_angles;
in vec2 v_radius;
in float v_angDiff;
in float v_theta;
in float v_umbraCoeff;
in float v_umbraPower;

float getMinAngle1(vec2 angles, float angle, float otherAngle) {
	return (min(abs(angles.s - angle), abs(angles.t - angle)) * otherAngle);
}
float getMinAngle2(vec2 angles, float angle, float otherAngle) {
	if (angle >= 0) return (abs(angle - angles.t) * otherAngle);
	else return (abs(angle - angles.s) * otherAngle);	
}

void main() {

	float radius = length(vec2((v_texCoord0 * 2.0) - 1.0));
	if (radius >= v_radius.s && radius <= v_radius.t) {
		
		float normedRadius = min(radius / v_radius.t, 1.0);
		vec4 color = vec4(u_color.rgb, (1.0 - pow(normedRadius, 2)));	
		vec2 norm = normalize(vec2((v_texCoord0 * 2.0) - 1.0));
		
		//float alphaColor = texture2D(u_lightMap, v_texCoord0).a;
		float angle = atan(norm.s, norm.t);

		if (color.a != 0) {
			if (v_angDiff == 1) {
				if (angle > v_angles.s || angle < v_angles.t)
					color.a = mix(color.a, 0, color.a * u_coeff);
				else if (v_umbraCoeff > 0 && v_umbraPower > 0) {
					float nrmAngle = 1.0 - getMinAngle1(v_angles, angle, v_theta);
					color.a = color.a - v_umbraCoeff * pow(nrmAngle, v_umbraPower);
				}					
			}
			else {
				if (angle > v_angles.s && angle < v_angles.t)
					color.a = mix(color.a, 0, color.a * u_coeff);
				else if (v_umbraCoeff > 0 && v_umbraPower > 0) {
					float nrmAngle = 1.0 - getMinAngle2(v_angles, angle, v_theta);
					color.a = color.a - v_umbraCoeff * pow(nrmAngle, v_umbraPower);
				}
			}
		}
		color.a *= 1.005;
		gl_FragColor = color;
	}	else gl_FragColor = vec4(0.);

}
