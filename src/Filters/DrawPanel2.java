package Filters;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;



	// Custom drawing panel written as an inner class to access the instance variables.
public class DrawPanel2 extends JPanel implements MouseListener, MouseInputListener  {
	static final long serialVersionUID=2;

	private JPanel drawnpreview;
	private DrawPad drawPanel;
	
	public DrawPanel2(JPanel parentPanel) {
		super(new BorderLayout());
		//this.setSize((int)MachineConfiguration.getSingleton().GetPaperWidth(), (int)MachineConfiguration.getSingleton().GetPaperHeight());
		//this.setLayout(new BorderLayout());
		System.out.println();
		drawPanel = new DrawPad();
		this.add(drawPanel, BorderLayout.CENTER);
		this.drawnpreview = parentPanel;
		
		JButton clearButton = new JButton("Clear");
		//creates the clear button and sets the text as "Clear"
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPanel.clear();
			}
		});
		
		JButton drawButton = new JButton ("Draw This");
		drawButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					GenerateGCODE();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CardLayout cardLayout = (CardLayout) drawnpreview.getLayout();
				cardLayout.next(drawnpreview);
				
			}
		});
		
		JPanel panel = new JPanel();
		//creates a JPanel
		panel.setPreferredSize(new Dimension(68, 32));
		panel.setMinimumSize(new Dimension(68, 32));
		panel.setMaximumSize(new Dimension(68, 32));
		panel.add(clearButton);
		panel.add(drawButton);
		this.add(panel, BorderLayout.SOUTH);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void GenerateGCODE () throws IOException {
		boolean isFirst = true;
		int increment = 2;
		DrawPoint lastP = new DrawPoint(0,0);;
		String dest = System.getProperty("user.dir")+"/temp.ngc";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dest),"UTF-8");
		out.write("G00 G90;\n");
		
		for (DrawShape s : drawPanel.getShapes()) {
			isFirst = true;
			System.out.println(s);
			for (DrawPoint p : s.getPoints()) {
				if (isFirst) {
					out.write("G00 X" + p.getX() + " Y" + p.getY() + " Z180;\n");
					isFirst = false;
				} else {
					if ((p.getX() > lastP.getX()+increment)||
							(p.getX() < lastP.getX()-increment)||
							(p.getY() > lastP.getY()+increment)||
							(p.getY() < lastP.getY()-increment)){
						out.write("G00 X" + p.getX() + " Y" + p.getY() + " Z90;\n");
					}
				}
				lastP = p;
			}
						
		}
		out.write("G00 X0 Y0 Z180;\n");
		out.close();
	}
	
	public void updateSize(boolean redrawImage) {
		//this.setSize((int)MachineConfiguration.getSingleton().GetPaperWidth(), (int)MachineConfiguration.getSingleton().GetPaperHeight());
		//this.repaint();
		this.drawPanel.updateSize(drawnpreview.getHeight(), drawnpreview.getWidth(), redrawImage);
	}

}



/**
 * This file is part of DrawbotGUI.
 *
 * DrawbotGUI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DrawbotGUI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */