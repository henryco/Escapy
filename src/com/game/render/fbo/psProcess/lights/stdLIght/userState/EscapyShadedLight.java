package com.game.render.fbo.psProcess.lights.stdLIght.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyGdxCamera;
import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.render.shader.lightSrc.userState.EscapyStdShadedLightSrcRenderer;
import com.game.render.shader.shadow.userState.EscapyStdShadowMapRenderer;
import com.game.render.shader.shadow.userState.EscapyStdShadowRenderer;
import com.game.utils.translationVec.TransVec;

public class EscapyShadedLight extends EscapyStdLight {

    private EscapyStdShadowMapRenderer shadowMapRenderer;
    private EscapyStdShadowRenderer shadowRenderer;

    private EscapyFBO lightMapFBO, shadowMapFBO, shadowFBO;
    private EscapyGdxCamera lightCam, shadowMapCam, shadowCam, resultCam;

    private ExtraRenderContainer lightMapContainer;

    protected TransVec transPos;

    public EscapyShadedLight(EscapyFBO lightMap, int accuracy) {
        super(lightMap);
        initBlock((int) (64 * Math.pow(2, accuracy)));
    }
    public EscapyShadedLight(EscapyFBO lightMap, int accuracy, EscapyLightType lightType) {
        super(lightMap, lightType);
        initBlock((int) (64 * Math.pow(2, accuracy)));
    }
    public EscapyShadedLight(EscapyFBO lightMap) {
        super(lightMap);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(EscapyFBO lightMap, EscapyLightType lightType) {
        super(lightMap, lightType);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(ExtraRenderContainer lmapContainer, EscapyLightType lightType) {
        super(null, lightType);
        this.initBlock((int) (64 * Math.pow(2, 1)));
        this.setLightMapContainer(lmapContainer);
    }
    public EscapyShadedLight(int id, EscapyFBO lightMap, int accuracy) {
        super(id, lightMap);
        initBlock((int) (64 * Math.pow(2, accuracy)));
    }
    public EscapyShadedLight(ExtraRenderContainer lmapContainer, int accuracy, EscapyLightType lightType) {
        super(null, lightType);
        initBlock((int) (64 * Math.pow(2, accuracy)));
        this.setLightMapContainer(lmapContainer);
    }
    public EscapyShadedLight(int id, EscapyFBO lightMap, int accuracy, EscapyLightType lightType) {
        super(id, lightMap, lightType);
        initBlock((int) (64 * Math.pow(2, accuracy)));
    }
    public EscapyShadedLight(int id, EscapyFBO lightMap) {
        super(id, lightMap);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(int id, EscapyFBO lightMap, EscapyLightType lightType) {
        super(id, lightMap, lightType);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(int id, ExtraRenderContainer lmapContainer, EscapyLightType lightType) {
        super(id, null, lightType);
        this.initBlock((int) (64 * Math.pow(2, 1)));
        this.setLightMapContainer(lmapContainer);
    }
    public EscapyShadedLight(int id, ExtraRenderContainer lmapContainer, int accuracy, EscapyLightType lightType) {
        super(id, lightType);
        initBlock((int) (64 * Math.pow(2, accuracy)));
        this.setLightMapContainer(lmapContainer);
    }
    public EscapyShadedLight(int id, EscapyLightType lightType) {
        super(id, lightType);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(int id) {
        super(id);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(EscapyLightType lightType, float scale, float x, float y) {
        super(lightType, scale, x, y);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(EscapyLightType lightType, float x, float y) {
        super(lightType, x, y);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(EscapyLightType lightType, TransVec pos) {
        super(lightType, pos);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(EscapyLightType lightType) {
        super(lightType);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight(TransVec pos) {
        super(pos);
        initBlock((int) (64 * Math.pow(2, 1)));
    }
    public EscapyShadedLight() {
        super();
        initBlock((int) (64 * Math.pow(2, 1)));
    }


    protected void initBlock(int lacc) {

        if (lacc > 2048) lacc = 2048;
        else if (lacc < 64) lacc = 64;
        System.err.println("tex_" + getID() + " : " + super.lightSource.getWidth() + " " + super.lightSource.getHeight());
        System.err.println("lac_" + getID() + " : " + lacc + " 1");

        {
            this.lightMapFBO = new StandartFBO(super.getID(), (int) super.lightSource.getWidth(),
                    (int) super.lightSource.getHeight());
            this.shadowMapFBO = new StandartFBO(super.getID(), lacc, 1);
            this.shadowFBO = new StandartFBO(super.getID(), (int) super.lightSource.getWidth(),
                    (int) super.lightSource.getHeight());
        }

        {
            this.resultCam = new EscapyGdxCamera((int) super.lightSource.getWidth(),
                (int) super.lightSource.getHeight());
            this.lightCam = new EscapyGdxCamera(lightMapFBO.getRegWidth(), lightMapFBO.getRegHeight());
            this.shadowMapCam = new EscapyGdxCamera(shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
            this.shadowCam = new EscapyGdxCamera(false, shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
        }

        {
            this.transPos = new TransVec();
            this.shadowMapRenderer = new EscapyStdShadowMapRenderer(super.getID());
            this.shadowRenderer = new EscapyStdShadowRenderer(super.getID());
        }

        super.resolution = new TransVec(super.lightSource.getWidth() * scale,
                super.lightSource.getHeight() * scale);
        super.srcRenderer = new EscapyStdShadedLightSrcRenderer(super.getID());
        super.umbra = new TransVec(0.2f, 5f);
        this.threshold = 0.8f;
    }

    /**
     * Set the shadowcast accuracy. <br>This operation will reinit light instance.
     *
     * @param acc - accuracy range: min 0, max 5 (kamikaze)
     * @return {@link AbsStdLight}
     */
    public AbsStdLight setAccuracy(int acc) {
        initBlock((int) (64 * Math.pow(2, acc)));
        return this;
    }

    @Override
    public AbsStdLight setScale(float scale) {
        super.setScale(scale);
        this.lightCam = new EscapyGdxCamera((int)(lightMapFBO.getRegWidth() * scale), (int)(lightMapFBO.getRegHeight() * scale));
        return this;
    }

    public AbsStdLight setLightMapContainer(ExtraRenderContainer lightMapContainer) {
        this.lightMapContainer = lightMapContainer;
        return this;
    }


    @Override
    public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {

        lightCam.setCameraPosition(transPos.
                vecfuncv(v -> v.sub(escapyCamera.getShiftVec())).arrfuncf(n -> n / scale));

        Sprite tempSprite = new Sprite(lightMap.getTextureRegion());
        tempSprite.setSize(lightMap.getTextureRegion().getRegionWidth() / scale,
                lightMap.getTextureRegion().getRegionHeight() / scale);

        lightMapFBO.begin().clearFBO(1f, 1f, 1f, 1f);
        stdRenderer.drawSprite(tempSprite, lightCam.getCamera());
        lightMapFBO.end();

        shadowMapFBO.begin().wipeFBO();
        shadowMapRenderer.renderShadowMap(
                lightMapFBO.getTextureRegion(), shadowMapCam.getCamera(),
                lightMapFBO.getRegWidth(), lightMapFBO.getRegHeight(), 0, 0,
                shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight(),
                threshold);
        shadowMapFBO.end();

        shadowFBO.begin().wipeFBO();
        shadowRenderer.renderShadow(
                shadowMapFBO.getTextureRegion(), shadowCam.getCamera(),
                resolution.x, resolution.y, 0, 0,
                shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight(),
                lightAngles, correct);
        shadowFBO.end();

        Sprite shadowSprite = new Sprite(shadowFBO.getTextureRegion());
        shadowSprite.setPosition(lightSource.getX(), lightSource.getY());
        lightSprite = new Sprite(shadowFBO.getTextureRegion());
        lightSprite.setPosition(lightSource.getX(), lightSource.getY());
        lightSprite.setScale(scale);
        this.fbo.begin().wipeFBO();
        {
            super.srcRenderer.renderLightSrc(lightSprite, shadowSprite,
                    escapyCamera.getCamera(), color, lightAngles,
                    resolution, coeff, correct, radius, umbra);
        }
        this.fbo.end();
        return this;
    }

    public AbsStdLight lazyRender(EscapyGdxCamera escapyCamera) {

        renderLightMap();
        renderShadowMap();
        renderShadows();
        renderLightSrc();
        return this;
    }
    public EscapyFBO renderLightMap() {

        lightCam.setCameraPosition(
                lightSource.getPosition().x + lightMapFBO.getRegWidth() / 2f,
                lightSource.getPosition().y + lightMapFBO.getRegHeight() / 2f
        );
        lightMapFBO.begin().clearFBO(1f, 1f, 1f, 1f);
        lightMapContainer.renderGraphic(lightCam);
        return lightMapFBO.end();
    }
    public EscapyFBO renderShadowMap() {

        shadowMapFBO.begin().wipeFBO();
        shadowMapRenderer.renderShadowMap(
                lightMapFBO.getTextureRegion(), shadowMapCam.getCamera(),
                lightMapFBO.getRegWidth(), lightMapFBO.getRegHeight(), 0, 0,
                shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight(),
                threshold);
        return shadowMapFBO.end();
    }
    public EscapyFBO renderShadows() {

        shadowFBO.begin().wipeFBO();
        shadowRenderer.renderShadow(
                shadowMapFBO.getTextureRegion(), shadowCam.getCamera(),
                resolution.x, resolution.y, 0, 0,
                shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight(),
                lightAngles, correct);
        return shadowFBO.end();
    }
    public EscapyFBO renderLightSrc() {


        System.out.println(shadowFBO.getRegWidth()+ " : "+fbo.getRegWidth());
        Sprite shadowSprite = new Sprite(shadowFBO.getTextureRegion());
        lightSprite = new Sprite(shadowFBO.getTextureRegion());
        this.fbo.begin().wipeFBO();

        super.srcRenderer.renderLightSrc(lightSprite, shadowSprite,
                resultCam.getCamera(), color, lightAngles,
                resolution, coeff, correct, radius, umbra
        );

        return fbo.end();
    }


    @Override
    public void stateUpdated(TransVec state) {
        super.stateUpdated(state);
        transPos.setTransVec(super.getPosition());
    }

    @Override
    public EscapyShadedLight get() {
        return this;
    }

}
