import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;	

import javax.swing.*;

import java.awt.Color;
/******************************************************************************
 * 
 * Name:		Nikhar Arora
 * Block:		B
 * Date:		10/21/14
 *  						
 * 
 *  Program #3: Game of Life
 *  Description: There are certain rules for this
 * 
 ******************************************************************************/ 
public class CopyOfGameOfLife						
{
	public static final Scanner CONSOLE = new Scanner(System.in);
	private static final int NUM_ROWS = 6;
	private static final int NUM_COLUMNS = 8;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600;
	public static final int BAR_HEIGHT = 22;
	private static boolean game[][] = new boolean[NUM_ROWS + 2][NUM_COLUMNS + 2];

	public static void main(String[] args)
	{
		
		int numNeighbors[][] = new int[NUM_ROWS + 2][NUM_COLUMNS + 2];
		
		System.out.print("Enter the number of generations: " );
		int numGeneration = CONSOLE.nextInt();
		CONSOLE.nextLine();
	
		userFillsWorld(game);
		
		for(int generation = 0; generation <= numGeneration; generation++)
		{
			System.out.println("Generation:" + generation);
			
			if(generation != 0)
			{
				countNeighbors(game, numNeighbors);
				updateWorld(game, numNeighbors);
			}
		
			printWorld(game);
		}
	}
	
	public static void userFillsWorld(boolean game[][])
	{
		System.out.print("How many alive cells do you want?: ");
		int numCellsAlive = CONSOLE.nextInt();
		CONSOLE.nextLine();
		
		for(int i = 1; i <= numCellsAlive; i++)
		{
			System.out.println("Enter the row and column of the alive cell.");
			
			int row = enterRow();
			int column = enterColumn();
	
			game[row][column] = true;
		}
		
	}
	
	public static int enterRow()
	{
		System.out.print("Enter a row between 1 and " + (NUM_ROWS - 2) + ": ");
		int row = CONSOLE.nextInt();
		CONSOLE.nextLine();
		
		while(row > NUM_ROWS - 2 || row < 1)
		{
			System.out.print("This row is out of bounds. Enter a different row: ");
			row = CONSOLE.nextInt();
			CONSOLE.nextLine();
		}
		return row;
	}
	
	public static int enterColumn()
	{
		System.out.print("Enter a column between 1 and " + (NUM_COLUMNS - 2) + ": ");
		int column = CONSOLE.nextInt();
		CONSOLE.nextLine();
		
		while(column > NUM_COLUMNS - 2 || column < 1)
		{
			System.out.print("This column is out of bounds. Enter a different column: ");
			column = CONSOLE.nextInt();
			CONSOLE.nextLine();
		}
		return column;
	}
	
	public static void countNeighbors(boolean game[][], int numNeighbors[][])
	{
		for(int row = 1; row < NUM_ROWS - 1; row++)
		{
			int countNeighbors = 0;
			
			for(int column = 1; column < NUM_COLUMNS - 1; column++)
			{
				countNeighbors = testEachSquare(game, countNeighbors, row, column);	
				numNeighbors[row][column] = countNeighbors;
				countNeighbors = 0;
			}
		}
	}
	
	public static int testEachSquare(boolean game[][], int countNeighbors, int row, int column)
	{
		if(game[row - 1][column - 1])
		{
			countNeighbors++;
		}
		if(game[row][column - 1])
		{
			countNeighbors++;
		}
		if(game[row + 1][column - 1])
		{
			countNeighbors++;
		}
		if(game[row + 1][column])
		{
			countNeighbors++;
		}
		if(game[row + 1][column + 1])
		{
			countNeighbors++;
		}
		if(game[row][column + 1])
		{
			countNeighbors++;
		}
		if(game[row - 1][column + 1])
		{
			countNeighbors++;
		}
		if(game[row - 1][column])
		{
			countNeighbors++;
		}
		
		return countNeighbors;
	}
	
	public static void updateWorld(boolean game[][], int numNeighbors[][])
	{
		for(int row = 1; row < NUM_ROWS - 1; row++)
		{
			for(int column = 1; column < NUM_COLUMNS - 1; column++)
			{
				if(numNeighbors[row][column] <= 1)
				{
					game[row][column] = false;
				}
				else if(numNeighbors[row][column] == 2)
				{
					
				}
				else if(numNeighbors[row][column] == 3)
				{
					game[row][column] = true;
				}
				else if(numNeighbors[row][column] >= 4)
				{
					game[row][column] = false;
				}
			}
		}
	}
	public static void printWorld(boolean game[][])
	{
		for(int row = 1; row < NUM_ROWS - 1; row++)
		{
			for(int column = 1; column < NUM_COLUMNS - 1; column++)
			{
				if(game[row][column])
				{
					System.out.print("*");
				}
				else
				{
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
}