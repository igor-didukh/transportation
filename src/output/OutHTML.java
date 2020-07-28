package output;

import data.Problem;
import main.Common;

public class OutHTML extends Out {
	public OutHTML() {
		println("<html>");
	}
	
	@Override
	public void printH1(String s) {
		println("<h1>" + s + "</h1>");
	}

	@Override
	public void printH2(String s) {
		println("<h2>" + s + "</h2>");
		
	}

	@Override
	public void printH3(String s) {
		println("<h3><font color = 'red'>" + s + "</font></h3>");
		
	}
	
	@Override
	public void printP(String s) {
		println("<p>" + s + "</p>");
		
	}

	@Override
	public void printBR(String s) {
		println(s.replace("<", "&lt;") + "<br />");
	}
	
	@Override
	public void printWarning(String s1, String s2, String s3) {
		printP(s1 + "<br />\n" + s2);
		printH3(s3);
	}
	
	@Override
	public String getMode() {
		return Common.HTML;
	}
	
	@Override
	public void printProblem(Problem problem) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void printCycle(double[][] plan, int[][] cycle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void printMarkedArray(double[][] t, double[][] plan, String mark, String subst) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void printArrayExt(double[][] t, double[] u, double[] v) {
		// TODO Auto-generated method stub
		
	}

}