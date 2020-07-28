package data;

import java.util.ArrayList;

public class ProblemCollection {
	private static ArrayList<Problem> problems = new ArrayList<Problem>(); 
	
	static {
		problems.add(
				new Problem(
					"Main (a), 3 x 4, S(a) > S(b)",
					new double[] {300, 450, 800},
					new double[] {630, 160, 170, 340},
					new double[][] {
						{10, 40, 50, 20},
						{20, 60, 40, 60},
						{30, 30, 30, 40}
					}
				)
			);
		
		problems.add(
				new Problem(
					"Main (b), 3 x 4, closed",
					new double[] {300, 200, 800},
					new double[] {630, 160, 170, 340},
					new double[][] {
						{10, 40, 50, 20},
						{20, 60, 40, 60},
						{30, 30, 30, 40}
					}
				)
			);
		
		problems.add(
				new Problem(
					"Lecture, 3 x 3, closed",
					new double[] {20, 20, 30},
					new double[] {10, 15, 45},
					new double[][] {
						{1, 4, 7},
						{3, 5, 11},
						{6, 7, 9}
					}
				)
			);
		
		problems.add(
				new Problem(
					"Lecture, 3 x 3, S(a) > S(b)",
					new double[] {25, 20, 30},
					new double[] {10, 15, 45},
					new double[][] {
						{1, 4, 7},
						{3, 5, 11},
						{6, 7, 9}
					}
				)
			);
		
		problems.add(
				new Problem(
					"Lecture, 3 x 3, S(a) < S(b)",
					new double[] {20, 20, 30},
					new double[] {10, 15, 50},
					new double[][] {
						{1, 4, 7},
						{3, 5, 11},
						{6, 7, 9}
					}
				)
			);
		
		
		
		problems.add(
				new Problem(
					"Lecture, 3 x 3, degenerated",
					new double[] {10, 20, 30},
					new double[] {10, 20, 30},
					new double[][] {
						{7, 4, 4},
						{3, 6, 1},
						{2, 3, 5}
					}
				)
			);
		
		// http://matecos.ru/mat/matematika/kak-reshit-transportnuyu-zadachu-2.html
		problems.add(
				new Problem(
					"Example, 2 x 4, closed",
					new double[] {100, 200},
					new double[] {50, 100, 75, 75},
					new double[][] {
						{4, 3, 5, 6},
						{8, 2, 4, 7}
					}
				)
			);
		
		// http://matecos.ru/mm-gotresheniya/gotovye-zadachi/modeli-v-ekonomike-gotovye-zadachi/mme-24.html
		problems.add(
				new Problem(
					"Example, 2 x 3, closed",
					new double[] {15, 5},
					new double[] {6, 9, 5},
					new double[][] {
						{20, 30, 40},
						{30, 70, 70}
					}
				)
			);
		
		// http://matecos.ru/mm-gotresheniya/gotovye-zadachi/modeli-v-ekonomike-gotovye-zadachi/mme-18.html
		problems.add(
				new Problem(
					"Example, 3 x 4, closed",
					new double[] {100, 130, 170},
					new double[] {150, 120, 80, 50},
					new double[][] {
						{3, 5, 7, 11},
						{1, 4, 6, 3},
						{5, 8, 12, 7}
					}
				)
			);
		
		// http://matecos.ru/mm-gotresheniya/gotovye-zadachi/modeli-v-ekonomike-gotovye-zadachi/mme-36.html
		problems.add(
				new Problem(
					"Example, 4 x 5, closed",
					new double[] {21, 19, 15, 25},
					new double[] {15, 15, 15, 15, 20},
					new double[][] {
						{30, 24, 11, 12, 25},
						{26, 4, 29, 20, 24},
						{27, 14, 14, 10, 18},
						{6, 14, 28, 8, 2}
					}
				)
			);
	}
	
	public static Problem getProblem(int i) {
		return problems.get(i);
	}
	
	public static String[] getTitles() {
		String[] res = new String[ problems.size() ];
		for (int i = 0; i < problems.size(); i++)
			res[i] = problems.get(i).getProblemTitle();
		
		return res;
	}
	
	public static int getInitProblemNo() {
		return 0;
	}
	
	public static Problem getBlankProblem(int rows, int cols) {
		double[] srcData = new double[rows];
		double[] destData = new double[cols];
		double[][] costData = new double[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			srcData[i] = 0;
			for (int j = 0; j < cols; j++)
				costData[i][j] = 0;
		}
		
		for (int j = 0; j < cols; j++)
        	destData[j] = 0;
		
		return new Problem("Blank problem", srcData, destData, costData);
	}

}