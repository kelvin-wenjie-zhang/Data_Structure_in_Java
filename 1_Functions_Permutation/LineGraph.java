
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Basic wrapper class for a line graph
 * @author stephen
 *
 */
public class LineGraph {
    private ArrayList<Point> points;
    private Color color;
    
    /**
     * Constructs a line graph
     * @param color the line graph's color
     */
    public LineGraph(Color color) {
        points = new ArrayList<Point>();
        this.color = color;
    }

    /**
     * Adds a point to the line graph
     * @param point the point to be added
     */
    public void addPoint(Point point) {
        points.add(point);
    }
    
    /**
     * Returns the color of the line graph
     * @return the color of the line graph
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Gets the i'th point in the line graph
     * @param index the index
     * @return i'th point
     */
    public Point get(int index) {
        return points.get(index);
    }
    
    /**
     * Returns the number of points in the line graph
     * @return the number of points in the line graph
     */
    public int size() {
        return points.size();
    }
}
