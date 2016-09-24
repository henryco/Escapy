lights.containers: [(type[ADD_RGBA, SOFT_LIGHT, false])]

lights - <b>containers holder</b><br>
containers - <b>light containers</b><br>
  [(type[ADD_RGBA, SOFT_LIGHT, false])] - <b>constructor</b> <br>

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
  
   source[0]: { <br>
		  	EscapyShadedLight: { <br>
			  	accuracy = 4 <br>
				  srcType = RND_1024 <br>
			  	maxRadius = 1.4 <br>
			  	minRadius = 0.3 <br>
			  	position = [400, 450] <br>
			  	color = (0, 0, 0) <br>
			  	angle: { <br>
		  			0: 0.125 	//angle <br>
		  			1: 0 		//shift <br>
		  			2: 0.5 		//corr <br>
		  		} <br>
		  		scale = 1.5 <br>
		  		threshold = 0.7 <br>
	    		umbra: { <br>
		  			coeff = 0.2 <br>
					recess = 5 <br>
			  	} <br>
	  			visible = false <br>
	  		} <br>
	  	} <br>
  
