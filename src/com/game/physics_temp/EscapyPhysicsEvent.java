package com.game.physics_temp;

public interface EscapyPhysicsEvent extends EscapyPhysics {

	public void definePhysicalSystem(EscapyPhysicsObjectSuper physObject);

	public void physicalCalculations(EscapyPhysicsObjectSuper physObject);

	public void physicalEvent(float xpos, float ypos, float mass, float tetha, EscapyPhysicsObjectSuper physObject);

	public EscapyPhysicsObjectSuper getPhysicalBody();

}
