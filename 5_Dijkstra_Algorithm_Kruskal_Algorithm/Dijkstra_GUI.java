
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Dijkstra_GUI extends JFrame{
	JLabel mylabel;
	JLabel dep;
	JLabel destin;
	JComboBox departure;
	JComboBox destination;
	JButton calculate;
	JButton exit;
	JPanel mypanel1;
	JPanel mypanel2;
	JPanel mypanel3;
	JPanel mypanel4;
	private static String[] cities;
	private static ArrayList<vertex> vertices;
	private static BinaryHeap<vertex> myheap = new BinaryHeap<vertex>();
	private static network mynetwork;
	private static graph mygraph;
	private static ArrayList<String> coordinate;
	
	public Dijkstra_GUI(){
		setTitle("Dijkstra Algorithm");
		setSize(1200,650);
		buildPanel();
		setLayout(new BorderLayout());
		add(mypanel1,BorderLayout.NORTH);
		add(mypanel2,BorderLayout.CENTER);
		add(mypanel3,BorderLayout.SOUTH);
		//pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void buildPanel() {
		mypanel1 = new JPanel();
		mypanel2 =  new JPanel(new BorderLayout());
		mypanel3 = new JPanel();
		mypanel4 = new JPanel();
		mylabel = new JLabel("Please select your departure and destination");
		calculate = new JButton("Calculate");
		calculate.addActionListener(new mylistener());
		exit =  new JButton("Exit");
		exit.addActionListener(new mylistener());
		dep = new JLabel("Departure: ");
		destin = new JLabel("Destination: ");
		departure = new JComboBox(cities);
		destination = new JComboBox(cities);
		mypanel1.add(mylabel);
		mypanel4.add(dep);
		mypanel4.add(departure);
		mypanel4.add(destin);
		mypanel4.add(destination);
		mypanel4.add(calculate);
		mypanel2.add(mypanel4,BorderLayout.NORTH);
		buildComponent();
		mypanel2.add(mygraph,BorderLayout.SOUTH);
		mypanel3.add(exit);
	}
	
	public void buildComponent(){
		mynetwork = new network(coordinate);
		mygraph = new graph(mynetwork);
	}
	
	private class mylistener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == calculate){
				String s = (String)departure.getSelectedItem();
				String t = (String)destination.getSelectedItem();
				for(int i = 0; i < vertices.size(); i++){
					if(vertices.get(i).name.equals(s)){
						dijkstra(vertices.get(i));
						break;
					}
				}
				
				for(int i = 0; i < vertices.size(); i++){
					if(vertices.get(i).name.equals(t)){
						//printPath(vertices.get(i));
						//System.out.println();
						JOptionPane.showMessageDialog(null, "Total distance is "+(int)vertices.get(i).dist + " miles.");
						mynetwork.end = vertices.get(i);
						mynetwork.start = mynetwork.end.path;
						mynetwork.flag = true;
						mygraph.repaint();
						break;
					}
				}
				
				
			}else if(e.getSource() == exit){
				System.exit(0);
			}
		}
	}
	
	public void dijkstra(vertex s){
		//initialize
		for(int i = 0; i < vertices.size(); i++){
			vertex v = vertices.get(i);
			v.dist = Double.POSITIVE_INFINITY;
			v.known = false;
		}
		s.dist = 0;
		s.known = true;
		for(int i = 0; i < vertices.size(); i++){
			vertex v = vertices.get(i);
			if(v.name.equals(s.name))
				continue;
			else{
				for(int j = 0; j < v.adj.size(); j++){
					if(v.adj.get(j).equals(s.name)){
						v.dist = v.weight.get(j);
						v.path = s;
					}
				}
				myheap.insert(v);
			}
		}
		//then we start the Dijkstra's algorithm
		while(!myheap.isEmpty()){
			vertex u = myheap.deleteMin();
			u.known = true;
			
			for(int i = 0; i < u.adj.size(); i++){
				String adjacent = u.adj.get(i);
				for(int j = 0; j < vertices.size(); j++){
					vertex v = vertices.get(j);
					if(v.name.equals(adjacent)){
						if(myheap.contains(v) && v.dist > u.dist + u.weight.get(i)){
							v.dist = u.dist + u.weight.get(i);
							v.path = u;
							myheap.update();//uses the inner build heap function
						}
					}
				}
			}
		}
	}
	
	public void printPath(vertex v){
		if(v.path != null){
			printPath(v.path);
			System.out.print(" to ");
		}
		System.out.print(v.name);
	}
	
	public String bestWay(vertex v, String way){
		if(v.path != null){
			printPath(v.path);
			way += " to ";
		}
		way += v.name;
		return way;
	}
	
	private static class vertex implements Comparable<vertex>{
		private ArrayList<String> adj = new ArrayList<String>(); //adjacency list
		private ArrayList<Double> weight = new ArrayList<Double>();; // adjacency weight
		private boolean known;
		private double dist;
		private vertex path;
		private String name;
		private int x;
		private int y;
		//Constructor
		public vertex(String name) {
			this(false,Double.POSITIVE_INFINITY,null,name);
		}
		public vertex(boolean known, double dist, vertex path, String name) {
			this.known = known;
			this.dist = dist;
			this.path = path;
			this.name =  name;
		}
		public int compareTo(vertex that) {
			double result =  this.dist - that.dist;
            return (int)result;
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
		private ArrayList<String> mylist;
		private boolean flag = false;
		private vertex start;
		private vertex end;
		public network(ArrayList<String> mylist){
			this.mylist = mylist;
		}
		public Dimension getSize(){
			return new Dimension(1200,600);
		}
		
		public void draw(Graphics g) throws NumberFormatException{
			g.setColor(Color.BLACK);
			for(int i = 0; i < mylist.size(); i++){
				StringTokenizer st = new StringTokenizer(mylist.get(i));
				ArrayList<String> tmp = new ArrayList<String>();
				while(st.hasMoreTokens()){
					tmp.add(st.nextToken(" "));
				}
				x = Integer.parseInt(tmp.get(1))/3;
				y = Integer.parseInt(tmp.get(2))/3;
				if(x == 0)
					x = 10;
				if(y == 0)
					y = 10;
				g.fillOval(x, 600-y, 10, 10);
				g.drawString(tmp.get(0), x, 600-y-5);
				
			}
			for(int i = 0; i < vertices.size(); i++){
				vertex v = vertices.get(i);
				for(int j = 0; j < v.adj.size(); j++){
					String adjname = v.adj.get(j);
					for(int k = 0; k < vertices.size(); k++){
						if(vertices.get(k).name.equals(adjname)){
							g.drawLine(v.x/3+5, 600-v.y/3+5, vertices.get(k).x/3+5, 600-vertices.get(k).y/3+5);
						}
					}
				}
			}
			g.setColor(Color.RED);
			//draw the shortest path
			if(flag != false){
				g.fillOval(start.x/3, 600-start.y/3, 10, 10);
				g.fillOval(end.x/3, 600-end.y/3, 10, 10);
				g.drawLine(start.x/3+5, 600-start.y/3+5, end.x/3+5, 600-end.y/3+5);
				while(!start.name.equals((String)departure.getSelectedItem()) ){
					start = start.path;
					end = end.path;
					g.fillOval(start.x/3, 600-start.y/3, 10, 10);
					g.fillOval(end.x/3, 600-end.y/3, 10, 10);
					g.drawLine(start.x/3+5, 600-start.y/3+5, end.x/3+5, 600-end.y/3+5);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length != 2){
			System.out.println("Please enter two files.");
			System.exit(0);
		}
		//read the coordinates
		BufferedReader brd = new BufferedReader(new FileReader(args[1]));
		coordinate = new ArrayList<String>();
		while(brd.ready())
			coordinate.add(brd.readLine());
		brd.close();
		
		BufferedReader br =  new BufferedReader(new FileReader(args[0]));
		ArrayList<String> citypairs = new ArrayList<String>();
		while(br.ready()){
			citypairs.add(br.readLine());
		}
		br.close();
		//get the unique city names
		ArrayList<String> city = new ArrayList<String>();
		for(int i = 0; i < citypairs.size(); i++){
			String[] tmp = citypairs.get(i).split(" ");
			for(int j = 0; j <= 1; j++){
				if(!city.contains(tmp[j]))
					city.add(tmp[j]);
			}
		}
		// then we store these city names to the static string array
		cities = new String[city.size()];
		for(int i = 0; i < cities.length; i++){
			cities[i] = new String(city.get(i));
		}
		
		//then we fill out the vertices array list
		vertices = new ArrayList<vertex>();
		for(int i = 0; i < cities.length; i++){
			vertices.add(new vertex(cities[i]));
		}
		//then we fill out the adjacency list and adjacency weight
		for(int i = 0; i < vertices.size(); i++){
			vertex tmp = vertices.get(i);
			for(int j = 0; j < citypairs.size(); j++){
				String[] line = citypairs.get(j).split(" ");
				if(line[0].equals(tmp.name)){
					tmp.adj.add(line[1]);
					tmp.weight.add(Double.parseDouble(line[2]));
				}else if(line[1].equals(tmp.name)){
					tmp.adj.add(line[0]);
					tmp.weight.add(Double.parseDouble(line[2]));
				}
				
			}
		}
		
		//for each vertex, we fill out the coordinate x and y
		for(int i = 0; i < coordinate.size(); i++){
			StringTokenizer st = new StringTokenizer(coordinate.get(i));
			ArrayList<String> tmp = new ArrayList<String>();
			while(st.hasMoreTokens()){
				tmp.add(st.nextToken(" "));
			}
			for(int j = 0; j < vertices.size(); j++){
				if(vertices.get(j).name.equals(tmp.get(0))){
					vertices.get(j).x = Integer.parseInt(tmp.get(1));
					vertices.get(j).y = Integer.parseInt(tmp.get(2));
				}
			}
		}
		
		new Dijkstra_GUI();

	}

}
