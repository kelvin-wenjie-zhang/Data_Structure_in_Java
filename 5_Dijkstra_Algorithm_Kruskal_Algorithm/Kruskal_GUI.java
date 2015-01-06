
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Kruskal_GUI extends JFrame {
	public static ArrayList<vertex> vertices;
	public static ArrayList<edge> edges; //contains all the edges of the graph
	public static ArrayList<edge> mst; // contains the minimum spanning tree edges
	public static network mynetwork;
	JPanel up;
	JPanel center;
	JPanel down;
	JButton calculate;
	JButton exit;
	graph mygraph;
	
	public Kruskal_GUI(){
		setTitle("Kruskal Algorithm");
		setSize(1200,650);
		setLayout(new BorderLayout());
		buildpanel();
		add(up,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
		add(down,BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void buildpanel(){
		up = new JPanel(new FlowLayout());
		center = new JPanel(new FlowLayout());
		down = new JPanel(new FlowLayout());
		calculate = new JButton("Find Minimum Spanning Tree");
		calculate.addActionListener(new mylistener());
		up.add(calculate);
		exit = new JButton("Exit");
		exit.addActionListener(new mylistener());
		down.add(exit);
		mynetwork = new network(vertices);
		mygraph = new graph(mynetwork);
		center.add(mygraph);
	}
	
	private class mylistener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == exit){
				System.exit(0);
			}
			else {
				kruskal();
				mynetwork.flag = true;
				mygraph.repaint();
			}
		}
	}
	
	public void kruskal(){
		DisjSets ds = new DisjSets(vertices.size());
		BinaryHeap<edge> myheap = new BinaryHeap<edge>();
		//fill out the heap
		for(int i = 0; i < edges.size(); i++){
			myheap.insert(edges.get(i));
		}
		mst = new ArrayList<edge>();
		
		while(mst.size() != (vertices.size() - 1)){
			edge e = myheap.deleteMin();
			vertex u = e.start;
			vertex v = e.end;
			int index_u = vertices.indexOf(u);
			int index_v = vertices.indexOf(v);
			int uset = ds.find(index_u);
			int vset = ds.find(index_v);
			if(uset != vset){
				//Accept the edge
				mst.add(e);
				ds.union(uset, vset);
			}
		}
	}
	
	public static class edge implements Comparable<edge>{
		public vertex start;
		public vertex end;
		private double length;
		public edge(vertex start, vertex end){
			this.start = start;
			this.end = end;
			calculateLength();
		}
		private void calculateLength(){
			double x1 = start.x;
			double y1 = start.y;
			double x2 = end.x;
			double y2 = end.y;
			length = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
		}
		public double length(){
			return length;
		}
		public int compareTo(edge that) {
			double result =  this.length - that.length;
            return (int)result;
        }
	}
	
	public static class vertex {
		public String name;
		public int x;
		public int y;
		public vertex(){
			this(null,0,0);
		}
		public vertex(String name, int x, int y){
			this.name = name;
			this.x = x;
			this.y = y;
		}
	}
	
	public class graph extends JComponent {
		private network mynetwork;
		public graph(network m){
			this.mynetwork = m;
			setPreferredSize(mynetwork.getSize());
		}
		public void paintComponent(Graphics g){
			mynetwork.draw(g);
		}
	}
	
	public class network {
		private int x;
		private int y;
		private boolean flag = false;
		private String name;
		private ArrayList<vertex> mylist;
		public network(ArrayList<vertex> mylist){
			this.mylist = mylist;
		}
		public Dimension getSize(){
			return new Dimension(1200,600);
		}
		
		public void draw(Graphics g) throws NumberFormatException{
			g.setColor(Color.BLACK);
			for(int i = 0; i < mylist.size(); i++){
				vertex v = mylist.get(i);
				x = v.x/3;
				y = v.y/3;
				name = v.name;
				if(x == 0)
					x = 10;
				if(y == 0)
					y = 10;
				g.fillOval(x,500-y,10,10);
				g.drawString(name, x, 500 - y - 5);
			}
			g.setColor(Color.RED);
			if(flag != false){
				for(int i = 0; i < mst.size(); i++){
					edge e = mst.get(i);
					g.drawLine(e.start.x/3+5, 500-e.start.y/3, e.end.x/3+5, 500-e.end.y/3);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length != 1){
			System.out.println("Please enter only one file.");
			System.exit(0);
		}
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		ArrayList<String> cityxy = new ArrayList<String>();
		while(br.ready()){
			cityxy.add(br.readLine());
		}
		br.close();
		//fill out the vertices array list
		vertices = new ArrayList<vertex>();
		for(int i = 0; i < cityxy.size(); i++){
			StringTokenizer st = new StringTokenizer(cityxy.get(i));
			ArrayList<String> tmp = new ArrayList<String>();
			while(st.hasMoreTokens()){
				tmp.add(st.nextToken(" "));
			}
			vertices.add(new vertex(tmp.get(0),Integer.parseInt(tmp.get(1)),Integer.parseInt(tmp.get(2))));
		}
		//fill out the edges array list
		edges = new ArrayList<edge>();
		for(int i = 0; i <= vertices.size()-2; i++){
			for(int j = i + 1; j < vertices.size(); j++){
				edges.add(new edge(vertices.get(i),vertices.get(j)));
			}
		}
		
		new Kruskal_GUI();
	}

	
}
