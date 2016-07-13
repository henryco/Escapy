package com.x.game.utils.primitives.walls;

import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.x.game.utils.primitives.EscapyGeometry;


public class Walls implements EscapyGeometry {

	private static final short TYPE_SQUARE  = 1;
	private static final short TYPE_TRIANGLE = 2;
	
	protected static final int START_X = 0;
	protected static final int START_Y = 1;
	protected static final int END_X = 2;
	protected static final int END_Y = 3;
	
	private ArrayList<float[]> wallList;
	private ArrayList<Shape2D> shapeList;
	
	
	public Walls(ArrayList<int[]> wallPoints) 
	{
		this.wallList = new ArrayList<float[]>();
		this.shapeList = new ArrayList<Shape2D>();
		fillWallMap(wallPoints);
		sortWallMap();
	}

	/*
	public void renderWalls(Graphics g, int translationX, int translationY, boolean ifucan)
	{
		if (ifucan)
		{
			g.setColor(Color.red);
			for (int i = 0; i < wallList.size(); i++)
			{
				float[] tab = wallList.get(i);
				g.drawLine(tab[START_X] - translationX, tab[START_Y] - translationY, 
						tab[END_X] - translationX, tab[END_Y] - translationY);
			}
			g.setColor(Color.white);
		}
	}
	*/
	
	private void sortWallMap()
	{
		wallList.sort(new Comparator<float[]>() 
		{
			@Override
			public int compare(float[] wall1, float[] wall2) 
			{
				int val = (new Float(wall1[0])).compareTo(new Float(wall2[0]));
				if (val == 0)
					val = (new Float(wall1[1])).compareTo(new Float(wall2[1]));
				return val;
			}			
		});
	}
	
	
	private void fillWallMap(ArrayList<int[]> wallPoints)
	{
		for (int i = 0; i < wallPoints.size(); i++)
		{
			float[][] walls = null;
			
			if (wallPoints.get(i)[4] == TYPE_SQUARE)
				walls = squareWall(wallPoints.get(i)[START_X], wallPoints.get(i)[START_Y],
						wallPoints.get(i)[END_X], wallPoints.get(i)[END_Y]);
			
			else if (wallPoints.get(i)[4] == TYPE_TRIANGLE)
				walls = triangleWall(wallPoints.get(i)[START_X], wallPoints.get(i)[START_Y],
						wallPoints.get(i)[END_X], wallPoints.get(i)[END_Y]);
			
			for (int j = 0; j < walls.length; j++)
			{
				addToArrayLists(walls[j]);
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
		for (int i = 0; i < array.length; i++)
		{
			for (int k = 0; k < 2; k++)
			{
				target_xy_xy_array[numb] = array[i][k];
				numb++;
			}
		}
		shapeList.add(new Polygon(target_xy_xy_array));
	}
	
	
	private float[][] squareWall(int stX, int stY, int edX, int edY)
	{
		float[][] walls = new float[4][];
		walls[0] = arrayFiller(edX, stY, stX, stY);
		walls[1] = arrayFiller(stX, stY, stX, edY);
		walls[2] = arrayFiller(stX, edY, edX, edY);
		walls[3] = arrayFiller(edX, edY, edX, stY);
		
		return walls;
	}
	
	
	private float[][] triangleWall(int stX, int stY, int edX, int edY)
	{
		float[][] walls = new float[3][];
		walls[0] = arrayFiller(edX, stY, stX, stY);
		walls[1] = arrayFiller(stX, stY, edX, edY);
		walls[2] = arrayFiller(edX, edY, edX, stY);
		
		return walls;
	}
	

	private float[] arrayFiller(int stX, int stY, int edX, int edY)
	{
		return new float[]
		{
			stX, stY, edX, edY, 
			EscapyGeometry.lineCoeff_Ax(new float[]{stX, stY}, new float[]{edX, edY}),
			EscapyGeometry.lineCoeff_By(new float[]{stX, stY}, new float[]{edX, edY}),
			EscapyGeometry.lineCoeff_C(new float[]{stX, stY}, new float[]{edX, edY})
		};
	}

	
	
	public ArrayList<float[]> getWallMap() {
		return wallList;
	}
	

	public void debugWallMap()
	{
		System.out.println("\n Walls:");
		for (int i = 0; i < wallList.size(); i++)
		{
			float[] tab = wallList.get(i);
			System.out.println((int)tab[START_X]+" : "+(int)tab[START_Y]+" | "+(int)tab[END_X]+" : "+
					(int)tab[END_Y]+"		"+tab[4]+" : "+tab[5]+" : "+tab[6]+"");
		}
		System.out.println("\n");
	}


	public ArrayList<Shape2D> getShapeList() {
		return shapeList;
	}


	public void setShapeList(ArrayList<Shape2D> shapeList) {
		this.shapeList = shapeList;
	}
	
}
