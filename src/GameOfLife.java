import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;	

import javax.swing.*;

import java.awt.Color;
/******************************************************************************
 * 
 * Name:		Nikhar Arora
 * Block:		B
 * Date:		10/27/14						
 * 
 *  Program #5: The Game of Life
 *  Description: In this program, there is a 2D array of alive and dead cells.
 *  There is a number of generations, entered by the user, and at each new generation,
 *  the cells all change their status based on this set of rules:
 *  
 *  # of surrounding alive cells <= 1 --> dead
 *  # of surrounding alive cells = 2 --> unchanged
 *  # of surrounding alive cells = 3 --> alive
 *  # of surrounding alive cells >= 4 --> dead
 *  
 *  EXTRAS: This program has graphics. In order to go to the next
 *  		time click, you have to press the space bar. After the last generation is
 *  		finished, the screen will print "no more generations"
 ******************************************************************************/ 
public class GameOfLife extends JFrame
						implements KeyListener
{
	public static final Scanner CONSOLE = new Scanner(System.in);
	private static final int NUM_ROWS = 15;
	private static final int NUM_COLUMNS = 17;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600;
	public static final int BAR_HEIGHT = 22;
	private static boolean game[][] = new boolean[NUM_ROWS + 2][NUM_COLUMNS + 2];
	private static int numNeighbors[][] = new int[NUM_ROWS + 2][NUM_COLUMNS + 2];
	private static int generation = 0;
	private static int numGeneration;

	public static void main(String[] args)
	{
		//Find out number of generations wanted
		System.out.print("Enter the number of generations: " );
		numGeneration = CONSOLE.nextInt();
		CONSOLE.nextLine();
	
		//Fill the world for the start of the game
		userFillsWorld(game);
		if(generation == 0)
		{
			System.out.println("Generation: " + generation);
			printWorld(game);
			generation++;
		}
		
		//Make the window for graphics
		GameOfLife gp = new GameOfLife();
		gp.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		gp.setVisible(true);
		gp.addKeyListener(gp);
	}
	
	/**
	 * This method asks the user how many cells they want alive. It then
	 * asks for the row and column of the cell they want alive and makes
	 * it alive
	 * @param game	This is the 2D array of alive and dead cells
	 */
	public static void userFillsWorld(boolean game[][])
	{
		System.out.print("How many alive cells do you want?: ");
		int numCellsAlive = CONSOLE.nextInt();
		CONSOLE.nextLine();
		
		for(int i = 1; i <= numCellsAlive; i++)
		{
			System.out.println("Enter the row and column of the alive cell.");
			
			//Get the row of the alive cell
			int row = enterRow();
			//Get the column of the alive cell
			int column = enterColumn();
	
			//Make the spot the user wanted alive
			game[row][column] = true;	
		}
		
	}
	
	/**
	 * This method finds the row the user wants their alive cell to be in
	 * @return	Returns the row the user inputs.
	 */
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
	
	/**
	 * This method finds the column the user wants their alive cell in
	 * @return Returns the column the user inputs.
	 */
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
	
	/**
	 * This method counts the number of neighbors each cell in the game
	 * array has.
	 * @param game	The array of alive and dead cells
	 * @param numNeighbors	The number of neighbors each cell has
	 */
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
	
	/**
	 * This method checks the number of alive cells around an individual cell
	 * @param game	The game array
	 * @param countNeighbors	The number of neighbors for an individual cell
	 * @param row	The row of the cell
	 * @param column	The column of the cell
	 * @return	Returns the number of alive neighbors for the cell
	 */
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
	
	/**
	 * This method changes the status of each individual cell based on its 
	 * number of neighbors
	 * @param game	The array of alive and dead cells
	 * @param numNeighbors	The array of cells with number of neighbors
	 */
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
					//Do nothing
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
	
	/**
	 * This method prints the game at each generation in the console.
	 * @param game
	 */
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
	
	/**
	 * Called when a key is pressed
	 * Required for any KeyListener 
	 */
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_SPACE)
		{
			System.out.println("Generation:" + generation);
			if(generation <= numGeneration)
			{
				if(generation != 0)
				{
					countNeighbors(game, numNeighbors);
					updateWorld(game, numNeighbors);
				}
				
				printWorld(game);
				repaint();
				generation++;
			}
			else
			{
				System.out.println("No more generations.");
				repaint();
			}
		}
	}
		
	/**
	 * 
	 * @param e		Contains info about the key typed
	 */
	public void keyTyped(KeyEvent e)
	{
	}
		
	/**
	 * Called when a key is released
	 * Required for any KeyListener
	 * 
	 * @param e		Contains info about the key released
	 */
	public void keyReleased(KeyEvent e)
	{
	}
	
	/**
	 * The paint makes the rows and columns on the window. It also
	 * makes the alive cells appear orange and the rest black.
	 */
	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		// Clear the window.
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		final int START_X = 0;
		final int START_Y = BAR_HEIGHT;
		final int WIDTH = WINDOW_WIDTH/(NUM_COLUMNS - 2);
		final int HEIGHT = (WINDOW_HEIGHT - BAR_HEIGHT)/(NUM_ROWS - 2);
		int xCorner;
		int yCorner;
		
		for(int row = 1; row < NUM_ROWS - 1; row++)
		{
			yCorner = START_Y + (row -1) * HEIGHT;
			for(int column = 1; column < NUM_COLUMNS - 1; column++)
			{
				xCorner = START_X + (column - 1) * WIDTH;
				
				//Make alive cells orange
				if(game[row][column] == true)
				{
					g.setColor(Color.orange);
					g.fillRect(xCorner, yCorner, WIDTH, HEIGHT);
				}
			
				g.setColor(Color.white);
				g.drawRect(xCorner, yCorner, WIDTH, HEIGHT);
			}
		}
		
		if(generation > numGeneration)
		{
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.setColor(Color.cyan);
			g.drawString("No More Generations", (WINDOW_WIDTH / 6), (WINDOW_HEIGHT / 2));
		}
	}

}
