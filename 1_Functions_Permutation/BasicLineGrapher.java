
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Basic GUI line grapher
 * @author stephen
 *
 */
public class BasicLineGrapher extends JComponent {
    private static final int AXES_OFFSET = 50;

    private ArrayList<LineGraph> lines;
    private Dimension size;
    private int xlim;
    private int ylim;

    /**
     * Constructs a basic line grapher JComponent
     * @param size the preferred dimensions of the component
     * @param xlim the x-axis limit
     * @param ylim the y-axis limit
     */
    public BasicLineGrapher(Dimension size, int xlim, int ylim) {
        lines = new ArrayList<LineGraph>();
        this.size = size;
        this.xlim = xlim;
        this.ylim = ylim;
    }

    /**
     * Adds a line graph to the line grapher
     * @param lineGraph the line graph object
     */
    public void addLineGraph(LineGraph lineGraph) {
        lines.add(lineGraph);
    }

    /**
     * {@inheritDoc}
     */
    public Dimension getPreferredSize() {
        return size;
    }
    
    /**
     * {@inheritDoc}
     */
    public Dimension getMinimumSize() {
        return new Dimension(2*AXES_OFFSET, 2*AXES_OFFSET);
    }

    /**
     * {@inheritDoc}
     * 
     * Paints the line graphs
     */
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        // Set background to white
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Making X and Y axes
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(AXES_OFFSET, getHeight() - AXES_OFFSET, getWidth()
                - AXES_OFFSET, getHeight() - AXES_OFFSET);
        g2.drawLine(AXES_OFFSET, AXES_OFFSET, AXES_OFFSET, getHeight()
                - AXES_OFFSET);
        
        // Draws XY max labels
        g2.drawString("" + ylim, AXES_OFFSET, AXES_OFFSET);
        g2.drawString("" + xlim, getWidth() - AXES_OFFSET, getHeight() - AXES_OFFSET);
        
        // Drawing the lines
        for (LineGraph l : lines) {
            if (l.size() > 0) {
                g2.setColor(l.getColor());
                for (int i = 1; i < l.size(); i++) {
                    Point p1 = getPointCoords(l.get(i-1).x,
                                              l.get(i-1).y);
                    Point p2 = getPointCoords(l.get(i).x,
                                              l.get(i).y);
                    System.out.println(p2);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }
    
    /**
     * Converts the JComponent grid to the grid of the graph
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the corrected coordinates
     */
    private Point getPointCoords(int x, int y) {
        int graphWidth = getWidth() - 2 * AXES_OFFSET;
        int graphHeight = getHeight() - 2 * AXES_OFFSET;
        
        int graphX = (int) (graphWidth * (x / (double) xlim) + AXES_OFFSET);
        int graphY = getHeight() - graphHeight * y / ylim - AXES_OFFSET;
        return new Point(graphX, graphY);
    }
}
