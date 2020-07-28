package data;

import javax.swing.table.TableModel;

public class Problem {
	private String problemTitle;
	
	private String[] srcTitles;
	private double[] srcData;
	
	private String[] destTitles;
	private double[] destData;

	private double[][] costData;
	
	// Consturctor from arrays with titles by default
	public Problem(String problemTitle, double[] srcData, double[] destData, double[][] costData) {
		this.problemTitle = problemTitle;
		
		this.srcData = srcData;
		
		srcTitles = new String[srcData.length];
		for (int i = 0; i < srcData.length; i++)
			srcTitles[i] = "Src" + (i+1);
		
		this.destData = destData;
		
		destTitles = new String[destData.length];
		for (int j = 0; j < destData.length; j++)
        	destTitles[j] = "Dest" + (j+1);
		
		this.costData = costData;
	}
	
	// Consturctor from arrays 
	public Problem(String problemTitle, String[] srcTitles, double[] srcData, String[] destTitles, double[] destData, double[][] costData) {
		this.problemTitle = problemTitle;
		
		this.srcTitles = srcTitles;
		this.srcData = srcData;
		
		this.destTitles = destTitles;
		this.destData = destData;
		
		this.costData = costData;
	}
	
	// Consturctor from table models
	public Problem(String problemTitle, TableModel srcTitlesTM, TableModel srcDataTM, TableModel destTitlesTM, TableModel destDataTM, TableModel costDataTM) {
		this.problemTitle = problemTitle;
		
		srcTitles = tableDataString(srcTitlesTM);
		srcData = tableDataDouble(srcDataTM);
		
		destTitles = tableDataString(destTitlesTM);
		destData = tableDataDouble(destDataTM);

		int m = costDataTM.getRowCount();
    	int n = costDataTM.getColumnCount();
		
    	costData = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				costData[i][j] = (double) costDataTM.getValueAt(i, j);
	}
	
	// From table model - to array of strings
	private String[] tableDataString(TableModel tm) {
    	int m = tm.getRowCount();
    	int n = tm.getColumnCount();
    	
    	String[] res; 
    	if (n == 1) {
    		res = new String[m];
    		for (int i = 0; i < m; i++)
				res[i] = (String) tm.getValueAt(i, 0);
    	} 
    	else {
    		res = new String[n];
    		for (int j = 0; j < n; j++)
				res[j] = (String) tm.getValueAt(0, j);
    	}
    	
    	return res;
    }
    
	// From table model - to array of doubles
	private double[] tableDataDouble(TableModel tm) {
    	int m = tm.getRowCount();
    	int n = tm.getColumnCount();
    	
    	double[] res; 
    	if (n == 1) {
    		res = new double[m];
    		for (int i = 0; i < m; i++)
				res[i] = (double) tm.getValueAt(i, 0);
    	} 
    	else {
    		res = new double[n];
    		for (int j = 0; j < n; j++)
				res[j] = (double) tm.getValueAt(0, j);
    	}
    	
    	return res;
    }
	
	// Getters
	public String getProblemTitle() {
		return problemTitle;
	}
	
	public String[] getSrcTitles() {
		return srcTitles;
	}
	
	public double[] getSrcData() {
		return srcData;
	}
	
	public String[] getDestTitles() {
		return destTitles;
	}
	
	public double[] getDestData() {
		return destData;
	}
	
	public double[][] getCostData() {
		return costData;
	}
	
	// "Packed" getters
	public String[][] getSrcTitlesObj() {
		String[][] res = new String[srcTitles.length][1];
		for (int i = 0; i < srcTitles.length; i++)
			res[i][0] = srcTitles[i];
		return res;
	}
	
	public String[][] getDestTitlesObj() {
		String[][] res = new String[1][destTitles.length];
		for (int i = 0; i < destTitles.length; i++)
			res[0][i] = destTitles[i];
		return res;
	}
	
	public Double[][] getSrcDataObj() {
		Double[][] res = new Double[srcData.length][1];
		for (int i = 0; i < srcData.length; i++)
			res[i][0] = srcData[i];
		return res;
	}
	
	public Double[][] getDestDataObj() {
		Double[][] res = new Double[1][destData.length];
		for (int i = 0; i < destData.length; i++)
			res[0][i] = destData[i];
		return res;
	}
	
	public Double[][] getCostDataObj() {
		Double[][] res = new Double[srcData.length][destData.length];
		
		for (int i = 0; i < srcData.length; i++)
			for (int j = 0; j < destData.length; j++)
				res[i][j] = costData[i][j];
				
		return res;
	}
	
	public int rows() {
		return srcData.length;
	}
	
	public int cols() {
		return destData.length;
	}
	
	// Get sources supplies summ 
	public double srcSumm() {
		double res = 0;
		for (int i = 0; i < srcData.length; i++)
			res += srcData[i];
		
		return res;
	}
	
	// Get destinations requirements summ
	public double destSumm() {
		double res = 0;
		for (int j = 0; j < destData.length; j++)
			res += destData[j];
		
		return res;
	}
	
	// problem is closed?
	public boolean isClosed() {
		return srcSumm() == destSumm();
	}
	
}