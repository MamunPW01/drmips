/*
    DrMIPS - Educational MIPS simulator
    Copyright (C) 2013-2015 Bruno Nova <brunomb.nova@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.feup.brunonova.drmips.gui;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.UIManager;
import org.feup.brunonova.drmips.simulator.mips.Data;

/**
 * Utility functions.
 * 
 * @author Bruno Nova
 */
public class Util {
	/** The index of the binary format in combo boxes. */
	public static final int BINARY_FORMAT_INDEX = 0;
	/** The index of the decimal format in combo boxes. */
	public static final int DECIMAL_FORMAT_INDEX = 1;
	/** The index of the hexadecimal format in combo boxes. */
	public static final int HEXADECIMAL_FORMAT_INDEX = 2;
	/** The index of the instruction performance mode type in combo boxes. */
	public static final int INSTRUCTION_PERFORMANCE_TYPE_INDEX = 0;
	/** The index of the CPU performance mode type in combo boxes. */
	public static final int CPU_PERFORMANCE_TYPE_INDEX = 1;
	
	/** The constant that represents the left side of the split pane. */
	public static final int LEFT = 0;
	/** The constant that represents the right side of the split pane. */
	public static final int RIGHT = 1;
	
	/** Class logger. */
	private static final Logger LOG = Logger.getLogger(Util.class.getName());

	/** Color of a wire in the datapath. */
	public static Color wireColor = Color.BLACK;
	/** Color of the control path in the datapath. */
	public static Color controlPathColor = new Color(0, 130, 200);
	/** Color of the critical path in the datapath. */
	public static final Color criticalPathColor = Color.RED;
	/** Color of a "irrelevant" wire in the datapath. */
	public static final Color irrelevantColor = Color.GRAY;
	/** Color of a register/address being read. */
	public static Color readColor = new Color(0, 160, 0);
	/** Color of a register/address being written. */
	public static final Color writeColor = Color.RED;
	/** Color of a register/address being read and written at the same time. */
	public static final Color rwColor = new Color(255, 128, 0);
	/** Color of the IF pipeline stage. */
	public static final Color ifColor = new Color(0, 170, 230);
	/** Color of the ID pipeline stage. */
	public static final Color idColor = Color.GREEN;
	/** Color of the EX pipeline stage. */
	public static final Color exColor = Color.MAGENTA;
	/** Color of the MEM pipeline stage. */
	public static final Color memColor = new Color(255, 128, 0);
	/** Color of the WB pipeline stage. */
	public static final Color wbColor = Color.RED;

	/**
	 * Converts the specified color to an "rgb(R,G,B)" string (for use in HTML).
	 * @param color The color.
	 * @return String in the format "rgb(R,G,B)".
	 */
	public static String colorToRGBString(Color color) {
		return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
	}

	/**
	 * Centers the given window on the screen.
	 * @param window Window to center.
	 */
	public static void centerWindow(Window window) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((int)screen.getWidth() / 2 - window.getWidth() / 2, (int)screen.getHeight() / 2 - window.getHeight() / 2);
	}
	
	/**
	 * Configures the given window to be closed when the Escape button is pressed.
	 * @param <W> A window (JFrame, JDialog, etc.).
	 * @param window Window to configure.
	 */
	public static <W extends Window & RootPaneContainer> void enableCloseWindowWithEscape(final W window) {
		Action closeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			}
		};
		
		window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		window.getRootPane().getActionMap().put("close", closeAction);
	}
	
	/**
	 * Returns a string the the given data formated in bin/dec/hex according to the selected format in the given combo box.
	 * @param data Original data.
	 * @param format The data format (<tt>Util.BINARYL_FORMAT_INDEX/Util.DECIMAL_FORMAT_INDEX/Util.HEXADECIMAL_FORMAT_INDEX</tt>).
	 * @return Data formated to the selected format, as a string.
	 */
	public static String formatDataAccordingToFormat(Data data, int format) {
		switch(format) {
			case BINARY_FORMAT_INDEX: return data.toBinary();
			case HEXADECIMAL_FORMAT_INDEX: return data.toHexadecimal();
			default: return "" + data.getValue();
		}
	}
	
	/**
	 * Returns the path of the specified file.
	 * <p>The function tries to return the canonical path of the file (unique path,
	 * follows symlinks, etc.) but, if not possible, returns the absolute path (the
	 * same file can have many absolute paths).<br />
	 * This function was created to avoid writing try...catch every time, since
	 * <tt>File.getAbsoluteFile()</tt> can throw an exception.</p>
	 * @param file The desired file.
	 * @return The complete path to the file.
	 */
	public static String getFilePath(File file) {
		try {
			return file.getCanonicalPath();
		}
		catch(Exception e) {
			LOG.log(Level.WARNING, "error getting canonical path for file " + file, e);
			return file.getAbsolutePath();
		}
	}
	
	/**
	 * Sets the program's light look and feel.
	 */
	public static void setLightLookAndFeel() {
		try {
			Properties props = new Properties();
			props.put("logoString", "");
			props.put("windowDecoration", "off");
			MintLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");

			// Set appropriate colors for theme
			wireColor = Color.BLACK;
			controlPathColor = new Color(0, 130, 200);
			readColor = new Color(0, 160, 0);
		} catch (Exception ex) {
			LOG.log(Level.WARNING, "error setting light LookAndFeel", ex);
		}
	}
	
	/**
	 * Sets the program's dark look and feel.
	 */
	public static void setDarkLookAndFeel() {
		try {
			Properties props = new Properties();
			props.put("logoString", "");
			props.put("windowDecoration", "off");
			HiFiLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");

			// Set appropriate colors for theme
			wireColor = Color.WHITE;
			controlPathColor = new Color(0, 170, 230);
			readColor = Color.GREEN;
		} catch (Exception ex) {
			LOG.log(Level.WARNING, "error setting dark LookAndFeel", ex);
		}
	}
}
