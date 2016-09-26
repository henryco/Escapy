#![Logo](https://raw.githubusercontent.com/henryco/Escapy/master/promo/ESCAPY.png)
<h3><i>Light container: </i></h3><br>

lights.containers: [(type[ADD_RGBA, SOFT_LIGHT, false])]
<br>
lights - <b>containers holder</b><br>
containers - <b>light containers</b><br><br>
  
  
  <b><i>Type constructor: </i></b><br><br>
  [(<b>type[</b>ADD_RGBA, SOFT_LIGHT, false<b>]</b>)] - <b>constructor</b> <br><br>
  type[0] = <b>GL blend program </b> <br>
  type[1] = <b>shader program </b> <br>
  type[2] = <b>blur </b> <br>

	type: { - alternative 
      glBlendFuncSeparate: (GL_SRC_ALPHA, GL_ONE, GL_ONE, GL_ONE_MINUS_SRC_COLOR);
      shader: OVERLAY
      shader.file("data/shaders/blend/overlay/overlay.vert", "data/shaders/blend/overlay/overlay.frag");
      shader.fileDir("data/shaders/blend/screen/", "screen");
      shader.uniforms("targetMap", "blendMap")
      blur: false;
    }	
  
  glBlendFuncSeparate - <b>manual alternative for built in GL blend programs</b><br>
  
  shader == type[1] - <b>syntax sugar</b><br>
  
  shader.file("data/shaders/blend/overlay/overlay.vert", "data/shaders/blend/overlay/overlay.frag");<br>
  <b>^ manual alternative for built in shaders</b><br>
  
  shader.fileDir("data/shaders/blend/screen/", "screen"); - <b>(path, name)</b><br>
  shader.uniforms("targetMap", "blendMap") -<b> uniform texture names (target, blend)</b><br>
  blur == type[2] - <b>syntax sugar</b><br>
  
