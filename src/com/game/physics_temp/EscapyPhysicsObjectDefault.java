package com.game.physics_temp;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyPhysicsObjectDefault.
 */
public class EscapyPhysicsObjectDefault extends EscapyPhysicsObjectSuper {

	/**
	 * Instantiates a new escapy physics object default.
	 *
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 * @param m
	 *            the m
	 * @param xp
	 *            the xp
	 * @param yp
	 *            the yp
	 * @param obj
	 *            the obj
	 */
	public EscapyPhysicsObjectDefault(float w, float h, float m, float xp, float yp, EscapyPhysicsEvent obj) {
		super(w, h, m, xp, yp);
		setObject(obj, this);

	}

	/**
	 * Instantiates a new escapy physics object default.
	 */
	public EscapyPhysicsObjectDefault() {

	}

}
