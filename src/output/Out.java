package output;

import data.Problem;
import main.Common;

public abstract class Out {
	String output = "";
	
	public void print(String s) {
		output += s;
	}
	
	public void println(String s) {
		output += s + "\n";
	}
	
	public void emptyLine() {
		println("");
	}
	
	public String getOut() {
		return output;
	}
	
	public void printArray(double[][] t) {
		printMarkedArray(t, t, "", "-");
	}
	
	public void printPlan(String title, double[][] t) {
		printH2(title);
		printMarkedArray(t, t, "*", "0");
	}
	
	public void printPlanSrcDest(String title, double[][] plan, double[][] cost, double[] src, double[] dest) {
		printH3(title);
		printArrayExt(plan, src, dest);
		
		double f = 0;
		for (int i = 0; i < plan.length; i++)
			for (int j = 0; j < plan[0].length; j++)
				if ( !Double.isNaN( plan[i][j] ) )
					f += plan[i][j] * cost[i][j];
		
		printP("Objective function value: " + Common.clip(f) );
		emptyLine();
	}
	
	public void printCost(String title, double[][] cost) {
		printH3(title);
		printArray(cost);
	}
	
	public void printCostUV(double[][] cost, double[] u, double[] v) {
		printH3("Potential factorization");
		printArrayExt(cost, u, v);
	}
	
	public abstract String getMode();
	public abstract void printP(String s);
	public abstract void printBR(String s);
	public abstract void printH1(String s);
	public abstract void printH2(String s);
	public abstract void printH3(String s);
	public abstract void printWarning(String s1, String s2, String s3);
	
	public abstract void printProblem(Problem problem);
	public abstract void printCycle(double[][] plan, int[][] cycle);
	
	abstract void printMarkedArray(double[][] t, double[][] plan, String mark, String subst);
	abstract void printArrayExt(double[][] t, double[] u, double[] v);
}
