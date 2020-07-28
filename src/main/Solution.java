package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import data.Problem;
import output.InfoWindow;
import output.Out;
import output.OutTXT;

public class Solution {
	private Out out = new OutTXT();
	private int m, n;
	private double[] srcData, destData, u, v; 
	private double[][] initCost, cost, plan;
	private Node initNode;
	
	public Solution (Problem problem) {
		getProblemData(problem);
		
		out.printH1("Problem data");
		out.printProblem(problem);
		out.printCost("Base cost table", cost);
		
		plan = northWestCorner();
		out.printPlanSrcDest("Init plan", plan, initCost, srcData, destData);
		
		int step = 1;
		while (true) {
			out.printH1("Step " + step);
			
			findUV();
			out.printCostUV(cost, u, v);
			
			recalcCost();
			out.printCost("Recalc using potentials", cost);
			
			initNode = findInitNode();
			out.printP( 
				String.format(
					"Minimal value [ cell(%d,%d) ]: %s\n", 
					initNode.row + 1, 
					initNode.col + 1, 
					Common.clip( cost[ initNode.row ][ initNode.col ] ) 
				) 
			);
			
			if ( plan[initNode.row][initNode.col] >= 0 ) {
				out.printH1("Optimal plan found");
				out.printPlanSrcDest("Optimal plan", plan, initCost, srcData, destData);
				break;
			}
			
			int[][] cycle = createCycle( findResNode() );
			plan[ initNode.row ][ initNode.col ] = 0;
			out.printCycle(plan, cycle);
			
			recalcPlan(cycle);
			out.printPlanSrcDest("New plan", plan, initCost, srcData, destData);
			
			step++;
		}
		
		Common.showFrame( new InfoWindow(out.getOut(), out.getMode()) );
	}
	
	// Fill work arrays with problem data
	private void getProblemData(Problem problem) {
		int rows = problem.rows();
		int cols = problem.cols();
		
		double sa = problem.srcSumm();
		double sb = problem.destSumm();
		
		m = sa < sb ? rows + 1: rows;
		n = sa > sb ? cols + 1: cols;
		
		u = new double[m];
		v = new double[n];
		
		// get source data
		double[] a = problem.getSrcData(); 
		srcData = new double[m];
		
		for (int i = 0; i < rows; i++)
			srcData[i] = a[i];

		if (rows < m)	// deficit
			srcData[m-1] = sb - sa;
		
		// get dest data
		double[] b = problem.getDestData(); 
		destData = new double[n];
		
		for (int j = 0; j < cols; j++)
			destData[j] = b[j];
		
		if (cols < n)	// excess
			destData[n-1] = sa - sb;
		
		// get cost data
		double[][] c = problem.getCostData();
		cost = new double[m][n];
		initCost = new double[m][n];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				cost[i][j] = c[i][j];
				initCost[i][j] = c[i][j];
			}
			
			if (cols < n) {	// excess
				cost[i][n-1] = 0;
				initCost[i][n-1] = 0;
			}
		}
		
		if (rows < m) // deficit
			for (int j = 0; j < cols; j++) {
				cost[m-1][j] = 0;
				initCost[m-1][j] = 0;
			}
	}
	
	// Check if every cell of array = 0
	private boolean isEmpty(double[] tbl) {
		boolean res = true;
		for (int i = 0; i < tbl.length; i++)
			if (tbl[i] != 0) {
				res = false;
				break;
			}
		return res;
	}
	
	// Initial allocation search (the Northwest-Corner method)
	private double[][] northWestCorner() {
		int row = 0, col = 0;
		
		double[] a = srcData.clone();
        double[] b = destData.clone();
        
        double[][] res = new double[m][n];
        for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				res[i][j] = Double.NaN;
        
        while ( !(isEmpty(a) && isEmpty(b) ) ) {
            double diff = Math.min( a[row], b[col] );
            
            res[row][col] = diff;
            a[row] -= diff; 
            b[col] -= diff;
            
            // degeneration
            if ( (a[row] == 0) && (b[col] == 0) && (col + 1 < n) ) 
            	res[row][col+1] = 0;
            
            if (a[row] == 0) 
            	row++;
            
            if (b[col] == 0) 
            	col++;
        }
        
        return res;
    }
	
	private int address(int i, int j) {
		return i * n + j;
	}
	
	private boolean inPlan(int i, int j) {
		Double p = plan[i][j];
		return !p.isNaN();
	}
	
	// calc u,v factorization for cost table
	private void findUV() {
		int row = 0, col = 0;
		LinkedList<Integer> cells = new LinkedList<Integer>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		for (int i = 0; i < m; i++)
			u[i] = Double.NaN;
		
		for (int j = 0; j < n; j++)
			v[j] = Double.NaN;
		
		// set initial (north-west) position
		for (int i = m-1; i >= 0; i--)
			for (int j = n-1; j >= 0; j--) 
				if ( inPlan(i,j) ) {
					row = i;
					col = j;
				}
		
		u[row] = 0;
		v[col] = -cost[row][col];
		cells.add( address(row,col) );
		
		// search and calc u, v
		while ( !cells.isEmpty() ) {
			int cell = cells.poll();
			int r = cell / n;
			int c = cell % n;
			
			visited.add(cell);
			
			for (int i = 0; i < m; i++) {
				int addr = address(i,c);
				if ( inPlan(i,c) && (i != r) && !visited.contains(addr) ) {
					u[i] = -cost[i][c] - v[c];
					cells.add(addr);
					break;
				}
			}
			
			for (int j = 0; j < n; j++) {
				int addr = address(r,j);
				if ( inPlan(r,j) && (j != c) && !visited.contains(addr) ) {
					v[j] = -cost[r][j] - u[r];
					cells.add(addr);
					break;
				}
			}
		}
	}
	
	// calc new cost table via u,v factorization
	private void recalcCost() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				cost[i][j] += u[i] + v[j];
	}
	
	// find init node for cycle
	private Node findInitNode() {
		double minVal = Double.MAX_VALUE;
		int initRow = -1;
		int initCol = -1;
		
		// first min cost
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				if (cost[i][j] < minVal) {
					minVal = cost[i][j];
					initRow = i;
					initCol = j;
				}
			}
		
		return new Node(initRow, initCol, null);
	}
	
	// find last node for cycle
	private Node findResNode() {
		Node res = null;
		int step = 0;
		LinkedList<Node> nodes = new LinkedList<Node>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		for ( Node child : initNode.getChildren() )
			nodes.push(child);
		
		while ( !nodes.isEmpty() ) {
			Node curNode = nodes.pop();
			if ( visited.contains(curNode.address) ) continue;
			
			if ( (curNode.address == initNode.address) && (step > 0) ) {
				res = curNode.parent;
				break;
			}
			
			visited.add(curNode.address);
			
			for (Node child : curNode.getChildren() ) {
				if ( !visited.contains(child.address) )
					nodes.push(child);
			}
			
			step++;
		}
		
		return res;
	}
	
	// create cycle from init node to last node
	private int[][] createCycle(Node resNode) {
		int depth = 0;
		for ( Node p = resNode; p != null; p = p.parent )
			depth++;
		
		Node[] tmpCycle = new Node[depth + 1];
		Node p = resNode; 
		for (int i = depth - 1; i >= 0; i--) {
			tmpCycle[i] = p;
			p = p.parent;
		}
		tmpCycle[depth] = initNode;
		
		int[][] res = new int[m][n];
		res[ tmpCycle[0].row ][ tmpCycle[0].col ] = 1;
		
		Node tmpNode = null;
		int val = -1;
		for (int i = 1; i < depth; i++) {
			tmpNode = tmpCycle[i];
			if ( (tmpCycle[i-1].row == tmpNode.row) && (tmpNode.row == tmpCycle[i+1].row) )
				continue;
			if ( (tmpCycle[i-1].col == tmpNode.col) && (tmpNode.col == tmpCycle[i+1].col) )
				continue;
			res[ tmpNode.row ][ tmpNode.col ] = val;
			val *= -1;
		}
		
		return res;
	}
	
	// Calc new plan using cycle
	private void recalcPlan(int[][] cycle) {
		double min = Double.MAX_VALUE;
		int r = -1;
		int c = -1;
		
		// first min cycle value
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				if ( (cycle[i][j] == -1) && (plan[i][j] < min) ) {
					min = plan[i][j];
					r = i;
					c = j;
				}
			}
		
		// create new plan
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				if (cycle[i][j] == -1)
					plan[i][j] -= min;
				else
					if (cycle[i][j] == 1)
						plan[i][j] += min;
					
			}
		
		plan[initNode.row][initNode.col] = min;
		plan[r][c] = Double.NaN;
	}
	
	// Graph node class for DFS method (cycle creation)
	private class Node {
		private int address, row, col;
		private double distance;
		private Node parent;
		private ArrayList<Node> res;
		
		Node(int row, int col, Node parent) {
			this.row = row;
			this.col = col;
			this.address = address(row, col);
			this.parent = parent;
			this.distance = (parent == null) ? 0 : Math.sqrt( Math.pow(row - initNode.row, 2) + Math.pow(col - initNode.col, 2) );
		}
		
		private boolean childFound(int ci, int cj) {
			Node tmp = new Node(ci, cj, this);
			int initAddress = initNode.address;
			
			if (tmp.address == initAddress) {
				if (this.parent.address != initAddress)
					res.add(tmp);
				return true;
			}
			
			if ( inPlan(ci, cj) ) {
				res.add(tmp);
				return true;
			}; 
			
			return false;
		}
		
		protected ArrayList<Node> getChildren() {
			res = new ArrayList<Node>(3);
			
			for (int i = row - 1; i >= 0; i--)
				if ( childFound(i, col) ) break;
			
			for (int i = row + 1; i < m; i++)
				if ( childFound(i, col) ) break;
			
			for (int j = col - 1; j >= 0; j--)
				if ( childFound(row, j) ) break;
			
			for (int j = col + 1; j < n; j++)
				if ( childFound(row, j) ) break;
			
			res.sort( new NodeComparator() );
			
			return res;
		}
		
		public String toString() {
			return "(" + row + "; " + col + "), addr = " + address + ", dist = " + Common.clip(distance) + ";\n\tparent = " + parent;
			//return "(" + row + "; " + col + ")";
		}
	}
	
	// Node distance comparator (for child array sorting)
	class NodeComparator implements Comparator<Node> {
		public int compare(Node a, Node b) {
			int res = 0;
			
			if (a.distance < b.distance) 
				res = 1;
			else
				if (a.distance > b.distance) 
					res = -1;
			
			return res;
		}
	}
	
}