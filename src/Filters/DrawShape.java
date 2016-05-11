package Filters;
import java.util.ArrayList;

public class DrawShape {
	private ArrayList<DrawPoint> points;
	
	DrawShape (ArrayList<DrawPoint> ps) {
		points = new ArrayList<DrawPoint>();
		for (DrawPoint p : ps) {
			this.points.add(p);
		}
	}
	
	public ArrayList<DrawPoint> getPoints () {
		return this.points;
	}

}
