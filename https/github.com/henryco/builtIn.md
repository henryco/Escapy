# BUILT IN<br><br><br>

<b><i> GL blend: </i></b></br></br>
  ADD_RGB -<b>   String, (additive blend)</b></br>
  ADD_RGBA -<b>   String, (additive alpha blend)</b></br>
  </br>
  </br>


  <b><i> Fields: </i></b></br></br>
  EscapyShadedLight - <b>   struct(...)</b></br>
  EscapyStdLight - <b>   struct(...)</b></br></br>
  accuracy <b>-   int</b></br>
  srcType  <b>-   String, (RND_ + size: from 256 to 2048)</b></br>
  maxRadius <b>-   float</b></br>
	minRadius <b>-   float</b></br>
	scale <b>-   float</b></br>
	threshold <b>-   float</b></br>
	position <b>-   float[2] </b></br>
	color<b>-   float[3], (r,g,b)</b></br>
	angle <b>-   float[3], (Angle, Shift, Correction)</b></br>
	umbra: { coeef, recess } <b>-   struct(2)</b></br>
  visible <b>-   boolean</b></br>
	 </br>
  </br>
  </br>
  <b> <i>Type constructor fields:</i> </b></br>
  
  glBlendFuncSeparate: (srcRGB, dstRGB, srcA, dstA); <br>
  shader: <b>#BUILT IN SHADER PROGRAM </b> <br>
  shader.file(vertex, fragment); - <b>(path, path)</b><i> manual alternative for built in shaders</i><br>
  shader.fileDir("data/shaders/blend/screen/", "screen"); - <b>(path, name)</b> <br>
  shader.uniforms("targetMap", "blendMap") - <b> (String, String)</b> <i>uniform texture names</i> </br>
  blur - <b>boolean</b><br> </br> </br>
  
<b> <i>Shaders: </i></b></br>

<b>#</b>CEB </br>
<b>#</b>DPO </br>
<b>#</b>VHD </br>
<b>#</b>VCD </br>
<b>#</b>VOD2 </br>
<b>#</b>VSD</br>
</br>
<b>#</b>ADD</br>
<b>#</b>ADD_AVERAGE</br>
<b>#</b>ADD_DODGE</br>
<b>#</b>ADD_OVERLAY</br>
<b>#</b>ADD_OVERLAY_STRONG</br>
<b>#</b>ADD_SOFT</br>
<b>#</b>ADD_SCREEN</br></br>
<b>#</b>ADD_VIVID</br>
<b>#</b>ADD_MULTIPLY</br>
<b>#</b>ADD_MULTIPLY_STRONG</br>
</br>
<b>#</b>COLOR_DODGE </br>
<b>#</b>COLOR_DODGE_MULTI </br>
<b>#</b>COLOR_DODGE_HARD </br>
	</br>
<b>#</b>SOFT_LIGHT</br>
<b>#</b>SOFT_COLOR_DODGE_REVERSED</br>
<b>#</b>SOFT_COLOR_DODGE</br>
<b>#</b>SOFT_LIGHT_STRONG</br>
<b>#</b>SOFT_SCREEN</br>
<b>#</b>SOFT_AVERAGE</br>
<b>#</b>SOFT_OVERLAY</br>
</br>
<b>#</b>VIVID_DODGE </br>
<b>#</b>VIVID_HUE</br>
<b>#</b>VIVID_SOFT </br>
</br>
<b>#</b>SCREEN</br>
<b>#</b>SCREEN_COLOR_DODGE</br>
</br>
<b>#</b>OVERLAY</br>
<b>#</b>OVERLAY_LIGHT</br>
<b>#</b>OVERLAY_AVERAGE</br>
</br>
<b>#</b>MULTIPLY</br>
  </br>
  </br>
  </br>
	
