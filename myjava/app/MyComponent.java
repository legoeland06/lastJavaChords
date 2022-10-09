package myjava.app;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author ericbruneau
 *
 */
public class MyComponent extends JComponent{
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {  
        g.setColor(Color.green);  
        g.fillRect(30, 30, 100, 100);  
      }  

}
