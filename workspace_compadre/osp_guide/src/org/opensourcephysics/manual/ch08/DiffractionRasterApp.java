/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.manual.ch08;
import org.opensourcephysics.frames.RasterFrame;

/**
 * DiffractionRasterApp demonstrates how to use a RasterFrame by computing a rectangular slit diffraction pattern.
 *
 * @author Wolfgang Christian
 * @version 1.0
 */
public class DiffractionRasterApp {
  static final int SIZE = 512;

  public static void main(String[] args) {
    RasterFrame frame = new RasterFrame("x", "y", "Raster Frame");
    frame.setBWPalette();
    int[][] data = new int[SIZE][SIZE];
    frame.setAll(data, -10, 10, -10, 10);
    for(int i = 0;i<SIZE;i++) {
      double x = frame.indexToX(i);
      double xAmp = (x==0) ? 1 : Math.sin(x)/x;
      for(int j = 0;j<SIZE;j++) {
        double y = frame.indexToY(j);
        double yAmp = (y==0) ? 1 : Math.sin(y)/y;
        double amp = xAmp*yAmp;
        // sqrt of intensity gives better visibility
        data[i][j] = (int) (255*Math.sqrt(amp*amp));
      }
    }
    frame.setAll(data);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
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
