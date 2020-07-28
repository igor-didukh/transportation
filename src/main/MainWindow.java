package main;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import data.Problem;
import data.ProblemCollection;

import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String DIMENSIONS = "DIMENSIONS", SOLVE = "SOLVE", EXIT = "EXIT";
	private static final Integer[] COMBO_VALUES = new Integer[] {2, 3, 4, 5};
	
	private JPanel contentPane;
	private JComboBox<Integer> comboSrc, comboDest;
	private JComboBox<String> comboProblem;
	private JTable tableSrcTitles, tableSrcData, tableDestTitles, tableDestData, tableCostData;
	private JLabel lblSumm;

	public MainWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				closeFrame(evt);
			}
		});
		
		setTitle("The Transportation Problem");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 749, 337);
		
		contentPane = new JPanel();
		contentPane.setBackground( new Color(238, 238, 238) );
		contentPane.setBorder( new BevelBorder(BevelBorder.LOWERED, null, null, null, null) );
		contentPane.setLayout( new BorderLayout(0, 0) );
		setContentPane(contentPane);
		
		JPanel panelTitel = new JPanel();
		panelTitel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panelTitel, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("The transportation problem solution");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setForeground(new Color(0, 0, 128));
		panelTitel.add(lblTitle);
		
		JPanel panelData = new JPanel();
		panelData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panelData, BorderLayout.CENTER);
		GridBagLayout gbl_panelData = new GridBagLayout();
		gbl_panelData.columnWidths = new int[] {100, 285, 100};
		gbl_panelData.rowHeights = new int[] {40, 0, 40};
		gbl_panelData.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panelData.rowWeights = new double[]{0.0, 0.0, 0.0};
		panelData.setLayout(gbl_panelData);
		
		JLabel lblTitleL = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To&nbsp;<hr>&nbsp;From");
		lblTitleL.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblTitleL.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTitleL = new GridBagConstraints();
		gbc_lblTitleL.fill = GridBagConstraints.BOTH;
		gbc_lblTitleL.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitleL.gridx = 0;
		gbc_lblTitleL.gridy = 0;
		panelData.add(lblTitleL, gbc_lblTitleL);
		
		tableDestTitles = new JTable();
		tableDestTitles.setForeground(new Color(128, 0, 0));
		tableDestTitles.setFont(new Font("Dialog", Font.BOLD, 12));
		tableDestTitles.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tableDestTitles.setBackground(SystemColor.controlHighlight);
		GridBagConstraints gbc_tableDestTitles = new GridBagConstraints();
		gbc_tableDestTitles.fill = GridBagConstraints.BOTH;
		gbc_tableDestTitles.insets = new Insets(0, 0, 5, 5);
		gbc_tableDestTitles.gridx = 1;
		gbc_tableDestTitles.gridy = 0;
		panelData.add(tableDestTitles, gbc_tableDestTitles);
		
		JLabel lblTitleR = new JLabel("<html>Source<br>supply");
		lblTitleR.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblTitleR.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTitleR = new GridBagConstraints();
		gbc_lblTitleR.fill = GridBagConstraints.BOTH;
		gbc_lblTitleR.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitleR.gridx = 2;
		gbc_lblTitleR.gridy = 0;
		panelData.add(lblTitleR, gbc_lblTitleR);
		
		tableSrcTitles = new JTable();
		tableSrcTitles.setForeground(new Color(128, 0, 0));
		tableSrcTitles.setFont(new Font("Dialog", Font.BOLD, 12));
		tableSrcTitles.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tableSrcTitles.setBackground(SystemColor.controlHighlight);
		GridBagConstraints gbc_tableSrcTitles = new GridBagConstraints();
		gbc_tableSrcTitles.fill = GridBagConstraints.BOTH;
		gbc_tableSrcTitles.insets = new Insets(0, 0, 5, 5);
		gbc_tableSrcTitles.gridx = 0;
		gbc_tableSrcTitles.gridy = 1;
		panelData.add(tableSrcTitles, gbc_tableSrcTitles);
		
		tableCostData = new JTable();
		tableCostData.setForeground(new Color(128, 0, 0));
		tableCostData.setBackground(SystemColor.controlHighlight);
		GridBagConstraints gbc_tableCostData = new GridBagConstraints();
		gbc_tableCostData.fill = GridBagConstraints.BOTH;
		gbc_tableCostData.insets = new Insets(0, 0, 5, 5);
		gbc_tableCostData.gridx = 1;
		gbc_tableCostData.gridy = 1;
		panelData.add(tableCostData, gbc_tableCostData);
		tableCostData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		tableSrcData = new JTable();
		tableSrcData.setForeground(new Color(128, 0, 0));
		tableSrcData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tableSrcData.setBackground(SystemColor.controlHighlight);
		GridBagConstraints gbc_tableSrcData = new GridBagConstraints();
		gbc_tableSrcData.fill = GridBagConstraints.BOTH;
		gbc_tableSrcData.insets = new Insets(0, 0, 5, 0);
		gbc_tableSrcData.gridx = 2;
		gbc_tableSrcData.gridy = 1;
		panelData.add(tableSrcData, gbc_tableSrcData);
		
		JLabel lblFoofterL = new JLabel("<html>Destinations<br>requirements");
		lblFoofterL.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblFoofterL.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblFoofterL = new GridBagConstraints();
		gbc_lblFoofterL.fill = GridBagConstraints.BOTH;
		gbc_lblFoofterL.insets = new Insets(0, 0, 0, 5);
		gbc_lblFoofterL.gridx = 0;
		gbc_lblFoofterL.gridy = 2;
		panelData.add(lblFoofterL, gbc_lblFoofterL);
		
		tableDestData = new JTable();
		tableDestData.setForeground(new Color(128, 0, 0));
		tableDestData.setBackground(SystemColor.controlHighlight);
		tableDestData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_tableDestData = new GridBagConstraints();
		gbc_tableDestData.fill = GridBagConstraints.BOTH;
		gbc_tableDestData.insets = new Insets(0, 0, 0, 5);
		gbc_tableDestData.gridx = 1;
		gbc_tableDestData.gridy = 2;
		panelData.add(tableDestData, gbc_tableDestData);
		
		lblSumm = new JLabel();
		lblSumm.setForeground(new Color(128, 0, 0));
		lblSumm.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSumm.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblSumm.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblSumm = new GridBagConstraints();
		gbc_lblSumm.fill = GridBagConstraints.BOTH;
		gbc_lblSumm.gridx = 2;
		gbc_lblSumm.gridy = 2;
		panelData.add(lblSumm, gbc_lblSumm);
		
		JPanel panelSettings = new JPanel();
		panelSettings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panelSettings, BorderLayout.WEST);
		panelSettings.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblProblem = new JLabel("Predefined problem:");
		lblProblem.setHorizontalAlignment(SwingConstants.CENTER);
		panelSettings.add(lblProblem);
		
		JPanel panelProblem = new JPanel();
		panelSettings.add(panelProblem);
		
		comboProblem = new JComboBox<String>();
		comboProblem.setModel(new DefaultComboBoxModel<String>( ProblemCollection.getTitles() ));
		comboProblem.setSelectedIndex( ProblemCollection.getInitProblemNo() );
		comboProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getProblem();
			}
		});
		panelProblem.add(comboProblem);
		
		JLabel lbl1 = new JLabel("");
		panelSettings.add(lbl1);
		
		JPanel panelSources = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelSources.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelSettings.add(panelSources);
		
		JLabel lblSources = new JLabel("Sources:     ");
		lblSources.setFont(new Font("Courier New", Font.PLAIN, 12));
		panelSources.add(lblSources);
		
		comboSrc = new JComboBox<Integer>();
		comboSrc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboSrc.setModel(new DefaultComboBoxModel<Integer>(COMBO_VALUES));
		comboSrc.setSelectedIndex(1);
		panelSources.add(comboSrc);
		
		JPanel panelDestinations = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelDestinations.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelSettings.add(panelDestinations);
		
		JLabel lblDestinations = new JLabel("Destinations:");
		lblDestinations.setFont(new Font("Courier New", Font.PLAIN, 12));
		panelDestinations.add(lblDestinations);
		
		comboDest = new JComboBox<Integer>();
		comboDest.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboDest.setModel(new DefaultComboBoxModel<Integer>(COMBO_VALUES));
		comboDest.setSelectedIndex(2);
		panelDestinations.add(comboDest);
		
		JLabel lbl2 = new JLabel("");
		panelSettings.add(lbl2);
		
		JPanel panelBtn = new JPanel();
		panelSettings.add(panelBtn);
		
		JButton btnDimsChange = new JButton("Clear");
		btnDimsChange.setFont(new Font("Courier New", Font.BOLD, 12));
		btnDimsChange.setActionCommand(DIMENSIONS);
		btnDimsChange.addActionListener(this);
		panelBtn.add(btnDimsChange);
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		JButton btnSolve = new JButton("Solve...");
		btnSolve.setForeground(new Color(0, 0, 128));
		btnSolve.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSolve.setActionCommand(SOLVE);
		btnSolve.addActionListener(this);
		panelBottom.add(btnSolve);
		
		getProblem();
		
	}
	
	// Get problem data from problem collection
	private void getProblem() {
		int comboIndex = comboProblem.getSelectedIndex();
		if (comboIndex == -1) return;
		
		Problem problem = ProblemCollection.getProblem(comboIndex);
		comboSrc.setSelectedIndex(problem.rows() - 2);
    	comboDest.setSelectedIndex(problem.cols() - 2);
		redefineTables(problem);
	}
	
	// Fill form tables with problem data
    private void redefineTables(Problem problem) {
        tableSrcTitles.setModel( new StringTableModel( problem.getSrcTitlesObj() ) );
        center(tableSrcTitles);
        
        tableDestTitles.setModel( new StringTableModel( problem.getDestTitlesObj() ) );
        center(tableDestTitles);
        
        tableCostData.setModel( new DoubleTableModel( problem.getCostDataObj() ) );
        
        DoubleTableModel tmSrcData = new DoubleTableModel( problem.getSrcDataObj() );
        tmSrcData.addTableModelListener( makeTableModelListener() );
        tableSrcData.setModel(tmSrcData);
        
        DoubleTableModel tmDestData = new DoubleTableModel( problem.getDestDataObj() );
        tmDestData.addTableModelListener( makeTableModelListener() );
        tableDestData.setModel(tmDestData);
        
        calcSums();
    }
    
    // Center text of table cells
    private void center(JTable table) {
    	DefaultTableCellRenderer r = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
        r.setHorizontalAlignment(JLabel.CENTER);
    }
    
    // Calc sums for source and destination tables
    private void calcSums() {
		double sSrc = 0;
		for (int i = 0; i < comboSrc.getSelectedIndex()+2; i++)
			sSrc += (double) tableSrcData.getValueAt(i,0);
		
		double sDest = 0;
		for (int j = 0; j < comboDest.getSelectedIndex()+2; j++)
			sDest += (double) tableDestData.getValueAt(0,j);
		
       lblSumm.setText( "<html><p align=\"right\">" + Common.clip(sSrc) + "<br>" + Common.clip(sDest) + "</p>" );
	}
    
    @Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
			
		case DIMENSIONS:
			redefineTables( ProblemCollection.getBlankProblem( comboSrc.getSelectedIndex() + 2, comboDest.getSelectedIndex() + 2 ) );
			comboProblem.setSelectedIndex(-1);
			break;
			
		case SOLVE:
			new Solution(
				new Problem(
					"Work problem", 
					tableSrcTitles.getModel(), 
					tableSrcData.getModel(), 
					tableDestTitles.getModel(), 
					tableDestData.getModel(), 
					tableCostData.getModel()
				)
			);
			break;
        
        case EXIT:
        	closeFrame(e);
        	break;
		}
	}
    
	// Action on close main window
	private void closeFrame(java.awt.AWTEvent evt) {
		if ( Common.showConfirmDialog(this, "You really want to exit?", "Exit") == JOptionPane.YES_OPTION )
			System.exit(0);
    }
	
	// Table model listener for source and destination tables
	private TableModelListener makeTableModelListener() {
		return new TableModelListener() {
	    	@Override
	    	public void tableChanged(TableModelEvent e) {
	    		calcSums();
	        }
		};
	}
	
	// Data model for titles
	private class StringTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		
		StringTableModel(Object[][] data) {
			super(data, new String[data[0].length]);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
        public Class getColumnClass(int columnIndex) {
			return java.lang.String.class;
        }
	}
    
	// Data model for values
	private class DoubleTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		
		DoubleTableModel(Object[][] data) {
			super(data, new String[data[0].length]);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
        public Class getColumnClass(int columnIndex) {
			return java.lang.Double.class;
        }
	}
	
}