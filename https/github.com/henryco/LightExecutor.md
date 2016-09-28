#![Logo](https://raw.githubusercontent.com/henryco/Escapy/master/promo/ESCAPY.png)
<h3><i>Light executor constructor and fields:</i></h3><br><br>
The first is that you need to know is that the <b>executor</b> constructor is declared once in the body of the 
superclass "<b>lights</b>" and is common to all light sources. Executors main task is rendering of light sources 
with optional blur and normal mapping.
<br>
```
lights.executor(false, normalMapping(true, shader()));
```
```
lights: {
...
	executor : {
		blur: false;
		normalMapping: {
			enable: true;
			shader.builtIn: DEFAULT; 
			shader.fields: {
				spriteSize = 1;	
				intensity: {
					direct: 0.2
					ambient: 0.75
					shadow: 8
					luminance: 0.15;
				}	
			}
		}		
	}
...
}
```
<br>
other form: 
```
lights.executor.blur = false;
lights.executor.normalMapping.enable: true
lgihts.executor.normalMapping.shader: 
{
    builtIn: DEFAULT
    fields: {
        spriteSize 1
        intensity (0.2, 0.75, 8)
    }
}
```
<br>
In future will be possible(via reflection) to use custom shaders for normal mapping, actualy its possible only for bland shaders:
```
{
  shader.file("*.vert", "*.frag");
  shader.uniforms (...)
  shader.fields (...)
  ...
}
```
<br><br><br>
<b><i>Light executor fields:</i></b><br>
<br><br>
<b><i>intensity.direct:</i></b> - intensity of direct light, <b>(float)</b><br>
<b><i>intensity.ambient:</i></b> - intensity of ambient light, <b>(float)</b><br>
<b><i>intensity.shadow:</i></b> - intensity of shadow, <b>(float)</b><br>
<b><i>intensity.luminance:</i></b> - luminance, <b>(float)</b><br>
<br>
<b><i>spriteSize:</i></b> - size of distance between calculated points of normal map, <b>(float)</b><br>
<br>
<b><i>shader.builtIn:</i></b> - executor's built-in shader program, necessary field! <b> (#BUILT_IN, DEFAULT)</b><br>
<br><br><br><br><br>
#
<b><i>See also:</i></b><br><i><br>
<a href="https://github.com/henryco/Escapy/blob/master/https/github.com/henryco/LightStruct.md">-> Light containers and custom shaders</a><br>
<a href="https://github.com/henryco/Escapy/blob/master/https/github.com/henryco/builtIn.md">-> Built in fields</a></i>

