package output;

import data.Problem;
import main.Common;

public class OutTXT extends Out {
	
	public OutTXT() {}

	@Override
	public void printH1(String s) {
		s += (s.length() % 2 == 1) ? " " : "";
		
		String ss = "";
		for (int i = 0; i < (50 - s.length()) / 2; i++)
			ss += "=";
		
		println(ss + " " + s + " " + ss);
		emptyLine();
	}

	@Override
	public void printH2(String s) {
		println("== " + s + " ==");
		emptyLine();
	}

	@Override
	public void printH3(String s) {
		println(s + ": ");
	}
	
	@Override
	public void printP(String s) {
		println(s);
	}

	@Override
	public void printBR(String s) {
		println(s);
	}
	
	@Override
	public void printWarning(String s1, String s2, String s3) {
		emptyLine();
		println(s1);
		println(s2);
		
		emptyLine();
		printH3(s3);
	}

	@Override
	public String getMode() {
		return Common.TXT;
	}
	
	private boolean isNaN(Double d) {
		return d.isNaN();
	}
	
	
	private String left(String s, int k, String smb, boolean alignLeft) {
		String res;
		
		if ( k < s.length() ) {
			res = s.substring(0, k);
		} else {
			res = "";
			int ext = k - s.length(); 
			int p = alignLeft ? 0 : (ext / 2);
			for (int i = 0; i < p ; i++)
				res += smb;
			res += s;
			for (int i = 0; i < ext - p ; i++)
				res += smb;
		}

		return res;
	}
	
	@Override
	public void printProblem(Problem problem) {
		final String tab = "-------", spaces = "       ";
		
		int m = problem.rows();
		int n = problem.cols();
		
		String[] srcTitles = problem.getSrcTitles();
		String[] destTitles = problem.getDestTitles();
		
		double[] srcData = problem.getSrcData();
		double[] destData = problem.getDestData();
		
		double[][] cost = problem.getCostData();
		
		print(tab + "|");
		for (int j = 0; j < n; j++)
			print( left(destTitles[j], 7, "-", false) + "|");
		println(tab);
		
		for (int i = 0; i < m; i++) {
			print( left(srcTitles[i], 7, " ", true) + "|" );
			for (int j = 0; j < n; j++)
				print( String.format("%7s", Common.clip( cost[i][j] ) ) + "|" );
			println( String.format("%7s", Common.clip( srcData[i] ) ) );
		}
		
		print(tab + "|");
		for (int j = 0; j < n; j++)
			print(tab + "|");
		println(tab);
		
		print(spaces + "|");
		for (int j = 0; j < n; j++)
			print( String.format("%7s", Common.clip( destData[j] ) )  + "|" );
		emptyLine();
		
		emptyLine();
		double sa = problem.srcSumm();
		double sb = problem.destSumm();
		
		printP( "Summ(src)  = " + Common.clip(sa) );
		printP( "Summ(dest) = " + Common.clip(sb) );
		emptyLine();
		
		if (sa < sb) {
			printH2("The problem is open!");
			printP("Fictitious source added!");
			emptyLine();
		} else
			if (sa > sb) {
				printH2("The problem is open!");
				printP("Fictitious destination added!");
				emptyLine();
			} else
				printH2("The problem is closed");
	}
	
	@Override
	public void printCycle(double[][] plan, int[][] cycle) {
		printH3("Cycle for recalc the plan");
		int n = plan[0].length;
		String[] tails = new String[] {"-", "", "+"};
		
		for (int i = 0; i < plan.length; i++) {
			for (int j = 0; j < n; j++) {
				String s = Common.clip( plan[i][j] );
				String tail = tails[ cycle[i][j]+1 ]; 
				print( String.format("%5s", s + tail) );
			}
			emptyLine();
		}
		
		emptyLine();
	}

	@Override
	void printMarkedArray(double[][] t, double[][] plan, String mark, String subst) {
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[0].length; j++) {
				
				String s = Common.clip( t[i][j] );
				String tail = "";
				
				if ( !isNaN(plan[i][j]) ) {
					if (mark.length() != 0 )
						tail = mark;
				} else
					if (subst.length() != 0 )
						s = subst;	
					
				print( String.format("%5s", s + tail) );
			}
				
			emptyLine();
		}
		emptyLine();
	}
	
	@Override
	void printArrayExt(double[][] t, double[] u, double[] v) {
		int n = t[0].length;
		
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < n; j++)
				print( String.format("%5s", Common.clip( t[i][j] ) ) );
			
			print( "|" + String.format("%5s", Common.clip( u[i] ) ) );
			emptyLine();
		}
		
		for (int j = 0; j < n; j++)
			print( "-----" );
		print( "|" );
		emptyLine();
		
		for (int j = 0; j < n; j++)
			print( String.format("%5s", Common.clip( v[j] ) ) );
		
		emptyLine();
		emptyLine();
	}

}