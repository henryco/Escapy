package com.game.render.fbo.psProcess.lights.stdLIght.userState;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.utils.translationVec.TransVec;

import java.util.function.Function;

/**
 * Created by root on 05/09/16.
 */
public class EscapyProxyShadowLight extends EscapyShadedLight{


    private boolean needUpdate;


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

        if (needUpdate) {
            needUpdate = false;
            return super.preRender(escapyCamera);
        }
        return this;
    }


    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public EscapyProxyShadowLight setUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
        return this;
    }



    @Override
    public AbsStdLight setLightSource(EscapyLightType light) {
        this.needUpdate = true;
        return super.setLightSource(light);
    }
    @Override
    public AbsStdLight setScale(float scale) {
        this.needUpdate = true;
        return super.setScale(scale);
    }
    @Override
    public AbsStdLight setPosition(float x, float y) {
        this.needUpdate = true;
        return super.setPosition(x, y);
    }
    @Override
    public AbsStdLight setPosition(float[] xy) {
        this.needUpdate = true;
        return super.setPosition(xy);
    }
    @Override
    public AbsStdLight setPosition(Vector2 vec) {
        this.needUpdate = true;
        return super.setPosition(vec);
    }
    @Override
    public AbsStdLight setPosition(TransVec vec) {
        this.needUpdate = true;
        return super.setPosition(vec);
    }
    @Override
    public AbsStdLight setCoeff(float cf) {
        this.needUpdate = true;
        return super.setCoeff(cf);
    }
    @Override
    public AbsStdLight setAngle(float srcAngle, float shiftAngle, float corr) {
        this.needUpdate = true;
        return super.setAngle(srcAngle, shiftAngle, corr);
    }
    @Override
    public AbsStdLight setAngle(float angle) {
        this.needUpdate = true;
        return super.setAngle(angle);
    }
    @Override
    public AbsStdLight setAngle(float[] angles) {
        this.needUpdate = true;
        return super.setAngle(angles);
    }
    @Override
    public AbsStdLight setAngle(TransVec angles) {
        this.needUpdate = true;
        return super.setAngle(angles);
    }
    @Override
    public AbsStdLight rotAngle(float shiftAngle) {
        this.needUpdate = true;
        return super.rotAngle(shiftAngle);
    }
    @Override
    public AbsStdLight addAngle(float shiftAngle) {
        this.needUpdate = true;
        return super.addAngle(shiftAngle);
    }
    @Override
    public AbsStdLight setAngleCorrection(float corr) {
        this.needUpdate = true;
        return super.setAngleCorrection(corr);
    }
    @Override
    public AbsStdLight setMinRadius(float minRadius) {
        this.needUpdate = true;
        return super.setMinRadius(minRadius);
    }
    @Override
    public AbsStdLight setMinRadius(Function<Float, Float> funct) {
        this.needUpdate = true;
        return super.setMinRadius(funct);
    }
    @Override
    public void stateUpdated(TransVec state) {
        this.needUpdate = true;
        super.stateUpdated(state);
    }
    @Override
    public AbsStdLight setMaxRadius(float maxRadius) {
        this.needUpdate = true;
        return super.setMaxRadius(maxRadius);
    }
    @Override
    public AbsStdLight setMaxRadius(Function<Float, Float> funct) {
        this.needUpdate = true;
        return super.setMaxRadius(funct);
    }
    @Override
    public AbsStdLight setUmbraCoeff(float umbraCoeff) {
        this.needUpdate = true;
        return super.setUmbraCoeff(umbraCoeff);
    }
    @Override
    public AbsStdLight setUmbraCoeff(Function<Float, Float> funct) {
        this.needUpdate = true;
        return super.setUmbraCoeff(funct);
    }
    @Override
    public AbsStdLight setUmbraRecess(float umbraRecess) {
        this.needUpdate = true;
        return super.setUmbraRecess(umbraRecess);
    }
    @Override
    public AbsStdLight setUmbraRecess(Function<Float, Float> funct) {
        this.needUpdate = true;
        return super.setUmbraRecess(funct);
    }
    @Override
    public AbsStdLight setColor(Color color) {
        this.needUpdate = true;
        return super.setColor(color);
    }
    @Override
    public AbsStdLight setColor(float r, float g, float b) {
        this.needUpdate = true;
        return super.setColor(r, g, b);
    }
    @Override
    public AbsStdLight setColor(int r255, int g255, int b255) {
        this.needUpdate = true;
        return super.setColor(r255, g255, b255);
    }
}
