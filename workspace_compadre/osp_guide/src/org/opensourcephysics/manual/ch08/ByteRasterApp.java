/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.manual.ch08;
import org.opensourcephysics.display.*;
import org.opensourcephysics.display2d.ByteRaster;
import javax.swing.JFrame;

/**
 * ByteRasterApp tests the DrawingPanel class by creating a byte raster.
 *
 * @author Wolfgang Christian
 * @version 1.0
 */
public class ByteRasterApp {
  static final int SIZE = 256;

  /**
   * A ByteRaster test program.
   *
   * The main method starts the Java application.
   *
   * @param args[]  the input parameters
   */
  public static void main(String[] args) {
    // PlottingPanel panel = new PlottingPanel("x","y","Byte Raster");
    PlottingPanel panel = PlottingPanelFactory.createType2("x", "y", "Byte Raster");
    DrawingFrame frame = new DrawingFrame(panel);
    frame.setSize(400, 400);
    ByteRaster byteRaster = new ByteRaster(SIZE, SIZE);
    byteRaster.setMinMax(-1, 1, 1, -1);
    // panel.setPreferredMinMax(0,256,0,256);
    byte[][] data = new byte[SIZE][SIZE];
    for(int r = 0;r<SIZE;r++) {
      for(int c = 0;c<SIZE;c++) {
        data[r][c] = (byte) ((256*Math.random()));
      }
    }
    byteRaster.setBlock(0, 0, data);
    byteRaster.setBlock(32, 0, new byte[32][8]);
    panel.addDrawable(byteRaster);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    // byteRaster.showLegend();
    // GUIUtils.timingTest(byteRaster);
  }
}

/*
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.

 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must must also be be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * For additional information and documentation on Open Source Physics,
 * please see <http://www.opensourcephysics.org/>.
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
