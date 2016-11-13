package com.game.utils.primitives.walls;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.phys.PhysPolygon;
import com.game.phys.shape.EscapyPolygon;
import com.game.render.camera.EscapyGdxCamera;
import com.game.utils.primitives.EscapyGeometry;


// TODO: Auto-generated Javadoc
/**
 * The Class EscapyWalls.
 */
public class EscapyWalls implements EscapyGeometry {

	private static final short TYPE_SQUARE  = 1;
	private static final short TYPE_TRIANGLE = 2;

	private static final int START_X = 0;
	private static final int START_Y = 1;
	private static final int END_X = 2;
	private static final int END_Y = 3;
	
	private ArrayList<float[]> wallList;
	private ArrayList<EscapyPolygon> shapeList;
	private ArrayList<PhysPolygon> polygons;

	private ShapeRenderer renderer;

	/**
	 * Instantiates a new physShapes.
	 *
	 * @param wallPoints
	 *            the wall points
	 */
	public EscapyWalls(ArrayList<int[]> wallPoints)
	{
		this();
		fillWallMap(wallPoints);
		sortWallMap();
		renderer = new ShapeRenderer();
	}
	public EscapyWalls(){
		this.wallList = new ArrayList<>();
		this.shapeList = new ArrayList<>();
		this.polygons = new ArrayList<>();
	}

	private void sortWallMap()
	{
		wallList.sort((wall1, wall2) -> {
			int val = (new Float(wall1[0])).compareTo(wall2[0]);
			if (val == 0) val = (new Float(wall1[1])).compareTo(wall2[1]);
			return val;
		});
	}

	public void draw(EscapyGdxCamera camera) {

		renderer.setProjectionMatrix(camera.combined());
		renderer.begin(ShapeRenderer.ShapeType.Line);
		shapeList.forEach(s -> renderer.polygon(s.getVertices()));
		renderer.end();
	}
	
	private void fillWallMap(ArrayList<int[]> wallPoints)
	{
		for (int[] wallPoint : wallPoints) {
			float[][] walls = null;

			if (wallPoint[4] == TYPE_SQUARE)
				walls = squareWall(wallPoint[START_X], wallPoint[START_Y],
						wallPoint[END_X], wallPoint[END_Y]);

			else if (wallPoint[4] == TYPE_TRIANGLE)
				walls = triangleWall(wallPoint[START_X], wallPoint[START_Y],
						wallPoint[END_X], wallPoint[END_Y]);

			assert walls != null;
			for (float[] wall : walls) {
				addToArrayLists(wall);
			}
			addFlattenArray(walls);
		}
	}
	
	
	private void addToArrayLists(float[] array)
	{
		wallList.add(array);
	}
	
	private void addFlattenArray(float[][] array)
	{
		float[] target_xy_xy_array = new float[array.length*2];
		int numb = 0;
		for (float[] anArray : array) {
			for (int k = 0; k < 2; k++) {
				target_xy_xy_array[numb] = anArray[k];
				numb++;
			}
		}
		shapeList.add(new EscapyPolygon(target_xy_xy_array));
		polygons.add(new PhysPolygon(shapeList.get(shapeList.size() - 1), true));
	}
	
	
	private static float[][] squareWall(int stX, int stY, int edX, int edY)
	{
		float[][] walls = new float[4][];
		walls[0] = arrayFiller(edX, stY, stX, stY);
		walls[1] = arrayFiller(stX, stY, stX, edY);
		walls[2] = arrayFiller(stX, edY, edX, edY);
		walls[3] = arrayFiller(edX, edY, edX, stY);
		
		return walls;
	}
	
	
	private static float[][] triangleWall(int stX, int stY, int edX, int edY)
	{
		float[][] walls = new float[3][];
		walls[0] = arrayFiller(edX, stY, stX, stY);
		walls[1] = arrayFiller(stX, stY, edX, edY);
		walls[2] = arrayFiller(edX, edY, edX, stY);
		
		return walls;
	}
	

	private static float[] arrayFiller(int stX, int stY, int edX, int edY)
	{
		return new float[]{stX, stY, edX, edY};
	}

	
	
	/**
	 * Gets the wall map.
	 *
	 * @return the wall map
	 */
	public ArrayList<float[]> getWallMap() {
		return wallList;
	}
	

	/**
	 * Debug wall map.
	 */
	public void debugWallMap()
	{
		System.out.println("\n EscapyWalls:");
		for (float[] tab : wallList) {
			System.out.println((int) tab[START_X] + " : " + (int) tab[START_Y] + " | " + (int) tab[END_X] + " : " +
					(int) tab[END_Y] + "		" + tab[4] + " : " + tab[5] + " : " + tab[6] + "");
		}
		System.out.println("\n");
	}


	public ArrayList<EscapyPolygon> getShapeList() {
		return shapeList;
	}
	public ArrayList<PhysPolygon> getPolygons(){return polygons;}
	public void setShapeList(ArrayList<EscapyPolygon> shapeList) {
		this.shapeList = shapeList;
	}



}
