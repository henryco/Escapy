package com.game.physics_temp;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyPhysicsEvent.
 */
public interface EscapyPhysicsEvent extends EscapyPhysics {

	/**
	 * Define physical system.
	 *
	 * @param physObject
	 *            the phys object
	 */
	public void definePhysicalSystem(EscapyPhysicsObjectSuper physObject);

	/**
	 * Physical calculations.
	 *
	 * @param physObject
	 *            the phys object
	 */
	public void physicalCalculations(EscapyPhysicsObjectSuper physObject);

	/**
	 * Physical event.
	 *
	 * @param xpos
	 *            the xpos
	 * @param ypos
	 *            the ypos
	 * @param mass
	 *            the mass
	 * @param tetha
	 *            the tetha
	 * @param physObject
	 *            the phys object
	 */
	public void physicalEvent(float xpos, float ypos, float mass, float tetha, EscapyPhysicsObjectSuper physObject);

	/**
	 * Gets the physical body.
	 *
	 * @return the physical body
	 */
	public EscapyPhysicsObjectSuper getPhysicalBody();

}
