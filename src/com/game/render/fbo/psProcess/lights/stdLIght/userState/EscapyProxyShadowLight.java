package com.game.render.fbo.psProcess.lights.stdLIght.userState;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.utils.translationVec.TransVec;


/**
 * Created by root on 05/09/16.
 */
public class EscapyProxyShadowLight extends EscapyShadedLight{


    public EscapyProxyShadowLight(EscapyFBO lightMap, int accuracy) {
        super(lightMap, accuracy);
    }
    public EscapyProxyShadowLight(EscapyFBO lightMap, int accuracy, EscapyLightType lightType) {
        super(lightMap, accuracy, lightType);
    }
    public EscapyProxyShadowLight(EscapyFBO lightMap) {
        super(lightMap);
    }
    public EscapyProxyShadowLight(EscapyFBO lightMap, EscapyLightType lightType) {
        super(lightMap, lightType);
    }
    public EscapyProxyShadowLight(int id, EscapyFBO lightMap, int accuracy) {
        super(id, lightMap, accuracy);
    }
    public EscapyProxyShadowLight(int id, EscapyFBO lightMap, int accuracy, EscapyLightType lightType) {
        super(id, lightMap, accuracy, lightType);
    }
    public EscapyProxyShadowLight(int id, EscapyFBO lightMap) {
        super(id, lightMap);
    }
    public EscapyProxyShadowLight(int id, EscapyFBO lightMap, EscapyLightType lightType) {
        super(id, lightMap, lightType);
    }
    public EscapyProxyShadowLight(int id, EscapyLightType lightType) {
        super(id, lightType);
    }
    public EscapyProxyShadowLight(int id) {
        super(id);
    }
    public EscapyProxyShadowLight(EscapyLightType lightType, float scale, float x, float y) {
        super(lightType, scale, x, y);
    }
    public EscapyProxyShadowLight(EscapyLightType lightType, float x, float y) {
        super(lightType, x, y);
    }
    public EscapyProxyShadowLight(EscapyLightType lightType, TransVec pos) {
        super(lightType, pos);
    }
    public EscapyProxyShadowLight(EscapyLightType lightType) {
        super(lightType);
    }
    public EscapyProxyShadowLight(TransVec pos) {
        super(pos);
    }
    public EscapyProxyShadowLight() {
    }


    @Override
    protected void initBlock(int lacc) {
        super.initBlock(lacc);
        super.threshold = 0.7f;
        this.needUpdate = true;
    }

    @Override
    public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {

        return super.preRender(escapyCamera);
    }

}
