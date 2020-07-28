package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Common {
	public static final String TXT = "txt", HTML = "html";
	
	// Show frame on the center of screen 
	public static void showFrame(Window frame) {
		int screenWidth = 0, screenHeight = 0;
		
		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice graphicsDevice : screenDevices) {
            screenWidth = graphicsDevice.getDefaultConfiguration().getBounds().width;
            screenHeight = graphicsDevice.getDefaultConfiguration().getBounds().height;
        }
		
        Rectangle r = frame.getBounds();
		
		int frameWidth = r.width, frameHeight = r.height;
		int posX = (screenWidth - frameWidth) / 2;
		int posY = (screenHeight - frameHeight) / 2 - 40;
		
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setBounds(posX, posY, r.width, r.height);
		
		frame.setVisible(true);
	}
	
	// Create frame from panel snd show it
	public static void makeFrame(JPanel panel, String title) {
		Rectangle r = panel.getBounds();
		
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(r.x, r.y, r.width, r.height);
		frame.add(panel);
		
		showFrame(frame);
	}
	
	// String --> int
	public static int parseInt(String s) {
		int n = 0;
		try {
			n = Integer.parseInt(s);
		} catch (Exception e) {}
		return n;
	}
	
	// String --> double
	public static double parseDouble(String s) {
		double d = 0;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {}
		return d;
	}
	
	// Error message
	public static void showErrorMessage(Component cmp, String message) {
		JOptionPane.showMessageDialog(cmp, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	// Yes-No dialog
	public static int showConfirmDialog(Component cmp, String message, String title) {
		Object[] options = { "Yes", "No" };
        return JOptionPane.showOptionDialog(
        		cmp, message, title,
        		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
        		null, options, options[1]
        );
	}
	
	// Round double value
	public static double Round(double value, int digits) {
		return new BigDecimal(value).setScale(digits, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static String clip(double f) {
		if ( Double.isNaN(f) ) return "-";
		
		String sf = String.format("%8.3f", f);
		String res = sf;
		
		if ( sf.endsWith("000") )
			res = sf.substring(0,4);
		else 
			if ( sf.endsWith("00") )
				res = sf.substring(0,6);
			else
				if ( sf.endsWith("0") )
					res = sf.substring(0,7);
		
		res = res.replace(",", ".");
		res = res.replace(" ", "");
		res = res.equals("-0") ? "0" : res;
		
		return res;
	}
	
}
