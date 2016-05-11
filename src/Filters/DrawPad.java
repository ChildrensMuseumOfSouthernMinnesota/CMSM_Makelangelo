package Filters;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import Makelangelo.MachineConfiguration;

class DrawPad extends JComponent{
	static final long serialVersionUID=2;
	Image image;
	//this is gonna be your image that you draw on
	Graphics2D graphics2D;
	//this is what we'll be using to draw on
	int currentX, currentY, oldX, oldY;
	//these are gonna hold our mouse coordinates

	private ArrayList<DrawShape> shapes = new ArrayList<DrawShape>();
	private ArrayList<DrawPoint> points = new ArrayList<DrawPoint>();
	double x_coord=0;
	double y_coord=0;
	double scale = 15;
	int height;
	int width;

	//Now for the constructors
	public DrawPad() {
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				MachineConfiguration mc=MachineConfiguration.getSingleton();
				oldX = e.getX()-(width-((int)((mc.paper_right-mc.paper_left)*scale)))/2;
				oldY = e.getY();
			}
		});
		addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e) {
				shapes.add(new DrawShape(points));
				points = new ArrayList<DrawPoint>();
				System.out.println(shapes);
			}
		});
		addMouseMotionListener(new MouseAdapter(){
			
			public void mouseDragged(MouseEvent e) {
				MachineConfiguration mc=MachineConfiguration.getSingleton();
				currentX = e.getX()-(width-((int)((mc.paper_right-mc.paper_left)*scale)))/2;
				currentY = e.getY();
				if(graphics2D != null)
					graphics2D.drawLine(oldX, oldY, currentX, currentY);
				repaint();
				points.add(new DrawPoint((e.getX()-(width-((int)((mc.paper_right-mc.paper_left)*scale)))/2-x_coord)/scale*10, (y_coord-e.getY())/scale*10));
				oldX = currentX;
				oldY = currentY;
			}
	    });
		this.width = getWidth();
	}

	public void paintComponent(Graphics g){
		MachineConfiguration mc=MachineConfiguration.getSingleton();
		if(image == null){
			//MachineConfiguration mc=MachineConfiguration.getSingleton();
			image = createImage((int)((mc.paper_right-mc.paper_left)*scale), (int)((mc.paper_top-mc.paper_bottom)*scale));
			graphics2D = (Graphics2D)image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
			x_coord = ((mc.paper_right-mc.paper_left)*scale)/2;
			y_coord = ((mc.paper_top-mc.paper_bottom)*scale)/2;
		}
		//graphics2D.translate((this.width-((int)((mc.paper_right-mc.paper_left)*scale)))/2, 0);
		g.drawImage(image, (this.width-((int)((mc.paper_right-mc.paper_left)*scale)))/2, 0, null);
	}
	
	
	//this is the painting bit
	//if it has nothing on it then
	//it creates an image the size of the window
	//sets the value of Graphics as the image
	//sets the rendering
	//runs the clear() method
	//then it draws the image

	public void updateSize(int height, int width, boolean redrawImage) {
		MachineConfiguration mc=MachineConfiguration.getSingleton();
		this.width = width;
		scale = height/(mc.paper_top-mc.paper_bottom);
		image = null;
		repaint();
	}
	
	public void clear(){
		MachineConfiguration mc=MachineConfiguration.getSingleton();
		graphics2D.setPaint(Color.white);
		System.out.println((this.width-((int)((mc.paper_right-mc.paper_left)*scale)))/2);
		//graphics2D.translate((this.width-((int)((mc.paper_right-mc.paper_left)*scale)))/2, 0);
		graphics2D.fillRect(0, 0, ((int)((mc.paper_right-mc.paper_left)*scale)), getSize().height);

		graphics2D.setPaint(Color.black);
		repaint();
		shapes = new ArrayList<DrawShape>();
		points = new ArrayList<DrawPoint>();
	}
	
	public ArrayList<DrawShape> getShapes() {
		return this.shapes;
	}
}