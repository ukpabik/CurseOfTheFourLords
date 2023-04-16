package ai;

import java.util.ArrayList;

import game.Panel;

public class PathFinder
{
    Panel p;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> path = new ArrayList<>();
    Node start, goal, current;
    boolean atGoal = false;
    int steps = 0;
    
    public PathFinder(Panel p) {
        this.p = p;
        createNodes();
    }
    
    public void createNodes() {
    	int col=0,row=0;
        node = new Node[p.maximumWCol][p.maximumWRow];
        
        while (col < p.maximumWCol && row < p.maximumWRow) {
            //CREATING NODE FOR EVERY TILE ON THE MAP
            node[col][row] = new Node(col,row);
            col++;
            if (col == p.maximumWCol) {col = 0;row++;}
        }
        
    }
    //RESET NODES BECAUSE YOU PATHFIND A LOT
    public void resetNodes() {
        int column=0,row=0;
        
        while (column < p.maximumWCol && row < p.maximumWRow) {
            //RESET ALL OF THE NODES
            node[column][row].openSpace = false;
            node[column][row].checkedSpace = false;
            node[column][row].solidTile = false;
            
            column++;
            if (column == p.maximumWCol) {
                column = 0;
                row++;
            }
        }
        //RESET ARRAYLISTS TOO
        openList.clear();
        path.clear();
        atGoal = false;
        steps = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        
        //SET START AND GOAL NODES
        start = node[startCol][startRow];
        current = start;
        goal = node[goalCol][goalRow];
        openList.add(current);
        
        int column = 0, row = 0;
        
        while(column < p.maximumWCol && row < p.maximumWRow) {
            //KNOW WHICH NODE IS SOLID TO GET INFO FROM TILE CLASS
            int tileNum = p.tileGuide.mapTile[p.mapCurrent][column][row];
            if (p.tileGuide.tiles[tileNum].collision == true) {
                node[column][row].solidTile = true;
            }
            
            //SET COST ON THE NODE
            setCost(node[column][row]);
            
            column++;
            if (column == p.maximumWCol) {
                column = 0;
                row++;
            }
        }
    }
    public void setCost(Node node) {
        //G COST
        int xDistance = Math.abs(node.col - start.col);
        int yDistance = Math.abs(node.row - start.row);
        node.gCost = xDistance + yDistance;
        //H COST
        xDistance = Math.abs(node.col - goal.col);
        yDistance = Math.abs(node.row - goal.row);
        node.hCost = xDistance + yDistance;
        
        //F COST
        node.fCost = node.gCost + node.hCost;
    }
    public void directionalNodes(int col, int rows) {
    	int column = col;
    	int row = rows;
    	//OPEN THE UP NODE
        if (row-1 >= 0) {openNode(node[column][row-1]);}
        //OPEN THE LEFT NODE
        if (column-1 >= 0) {openNode(node[column-1][row]);}
        //OPEN THE DOWN NODE
        if (row+1 < p.maximumWRow) {openNode(node[column][row+1]);}
        //OPEN THE RIGHT NODE
        if (column+1 < p.maximumWCol) {openNode(node[column+1][row]);}
    }
    public boolean search() {
        while(atGoal == false && steps < 500) {
            int column = current.col;
            int row = current.row;
            int bestNode = 0;
            int bestfCost = 999;
            //CHECK THE CURRENT NODE
            current.checkedSpace = true;
            openList.remove(current);
            
            directionalNodes(column, row);
            
            for (int i = 0; i < openList.size(); i++)
            {
                //CHECK IF THE FCOST FOR THE NODE IS BETTER THAN THE NEXT NODE
                
                if (openList.get(i).fCost < bestfCost) {bestNode = i;bestfCost = openList.get(i).fCost;}
                
                //IF FCOST IS EQUAL, CHECK THE GCOST INSTEAD
                else if (openList.get(i).fCost == bestfCost) {
                    if(openList.get(i).gCost < openList.get(bestNode).gCost) {bestNode = i;}}
                }
            //IF THERE ARE NO NODES IN THE OPENLIST, END THE LOOP
            if (openList.size() == 0) {break;}
            
            //AFTER LOOP ENDS, openList[bestNodeIndex] is the next step to take.
            current = openList.get(bestNode);
            
            if (current == goal) {atGoal = true;trackPath();}
            
            steps++;
        }
        return atGoal;
    }
    
    public void openNode(Node node) {
        if (node.openSpace == false && node.checkedSpace == false && node.solidTile == false) {
            //SCAN FOR NODES IN THE OPEN LIST
            node.openSpace = true;
            node.parent = current;
            openList.add(node);
        }
    }
    public void trackPath() {
        Node current = goal;
        //BACKTRACKING FROM GOALNODE TO STARTNODE
        while(current != start) {
            //ADDING TO THE 0 SLOT SO THE LAST ADDED NODE IS IN THE FIRST SLOT
            path.add(0,current);
            current = current.parent;
        }
        
    }
}
