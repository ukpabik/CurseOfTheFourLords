package ai;

public class Node
{
    Node parent;
    public int col,row;
    int gCost,fCost,hCost;
    boolean solidTile,openSpace,checkedSpace;
    
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
