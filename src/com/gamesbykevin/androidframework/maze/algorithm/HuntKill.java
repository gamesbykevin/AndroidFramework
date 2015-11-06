package com.gamesbykevin.androidframework.maze.algorithm;

import com.gamesbykevin.androidframework.maze.Maze;
import com.gamesbykevin.androidframework.maze.MazeHelper;
import com.gamesbykevin.androidframework.maze.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Hunt Kill maze generation algorithm
 * @author GOD
 */
public class HuntKill extends Maze
{
    //current location
    private int currentCol, currentRow;
    
    //temporary list of optional rooms
    private List<Room> options;
    
    //have we hit a dead end
    private boolean deadend = false;
    
    public HuntKill(final int cols, final int rows) throws Exception
    {
        super(cols, rows);
        
        //fill all walls
        super.populateRooms();
        
        //create a new list
        this.options = new ArrayList<Room>();
    }        
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        options.clear();
        options = null;
    }
    
    /**
     * Create our maze
     * @param random Object used to make random decisions
     */
    @Override
    public void update(final Random random) throws Exception
    {
        //if generated no need to continue
        if (isGenerated())
            return;
        
        //clear list of objects
        options.clear();
                        
        //we are just starting to create the maze
        if (!MazeHelper.hasVisited(this))
        {
            //set the random location
            currentCol = random.nextInt(getCols());
            currentRow = random.nextInt(getRows());
            
            //mark this room as visited
            getRoom(currentCol, currentRow).setVisited(true);
        }
        else if (deadend)
        {
            //start hunting for the next available room in this particular order
            for (int row = 0; row < getRows(); row++)
            {
                for (int col = 0; col < getCols(); col++)
                {
                    //get the current room
                    final Room room = getRoom(col, row);
                    
                    //we are only interested in rooms that haven't been visited
                    if (room.hasVisited())
                        continue;
                    
                    //clear the list of objects
                    options.clear();
                    
                    //check for visited cells
                    final Room east = getRoom(col + 1, row);
                    final Room west = getRoom(col - 1, row);
                    final Room north = getRoom(col, row - 1);
                    final Room south = getRoom(col, row + 1);

                    //make sure room exists and is already visited
                    if (east != null && east.hasVisited())
                        options.add(east);
                    if (west != null && west.hasVisited())
                        options.add(west);
                    if (north != null && north.hasVisited())
                        options.add(north);
                    if (south != null && south.hasVisited())
                        options.add(south);

                    //we only want to join a unvisited room to a visited room
                    if (!options.isEmpty())
                    {
                        //assign new location
                        this.currentCol = col;
                        this.currentRow = row;
                        
                        //join the rooms
                        joinRooms(random);
                        
                        //now re-assign new location
                        this.currentCol = col;
                        this.currentRow = row;

                        //no longer in a dead end
                        deadend = false;

                        //exit loop
                        return;
                    }
                }
            }
        }
        
        //the rooms in each direction
        final Room east = getRoom(currentCol + 1, currentRow);
        final Room west = getRoom(currentCol - 1, currentRow);
        final Room north = getRoom(currentCol, currentRow - 1);
        final Room south = getRoom(currentCol, currentRow + 1);
        
        //if the rooms exist and have not visited, add it to the list
        if (east != null && !east.hasVisited())
            options.add(east);
        if (west != null && !west.hasVisited())
            options.add(west);
        if (north != null && !north.hasVisited())
            options.add(north);
        if (south != null && !south.hasVisited())
            options.add(south);
        
        //if we have options to choose from
        if (!options.isEmpty())
        {
            joinRooms(random);
        }
        else
        {
            //we have a dead end, select another random room that has not been visited
            deadend = true;
        }
    }
    
    /**
     * Join the current room with one in our optional list.<br>
     * In addition we will mark the rooms as visited and the new room we merge will become the current location
     * @param random Object used to make random decisions
     * @throws Exception 
     */
    private void joinRooms(final Random random) throws Exception
    {
        //get the current room
        Room room = getRoom(currentCol, currentRow);

        //get a random neighbor room
        Room neighbor = options.get(random.nextInt(options.size()));

        //mark both as visited
        room.setVisited(true);
        neighbor.setVisited(true);

        //join the rooms to create the path
        MazeHelper.joinRooms(room, neighbor);

        //now set the new position
        currentCol = neighbor.getCol();
        currentRow = neighbor.getRow();
        
        //update progress
        updateProgress();
    }
}