
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.JFrame;

public class algorithm_2 {

	public static void main(String[] args) throws IOException{
		
		int n1 = 25000, n2 = 50000, n3 = 100000, n4 = 200000, n5 = 400000, n6 = 800000;
		int[] arr1 = new int[n1];
		int[] arr2 = new int[n2];
		int[] arr3 = new int[n3];
		int[] arr4 = new int[n4];
		int[] arr5 = new int[n5];
		int[] arr6 = new int[n6];
		
		long startTime1 = System.currentTimeMillis();
		algorithm2(arr1);
		long endTime1 = System.currentTimeMillis();
		long time1 = endTime1 - startTime1;
		
		long startTime2 = System.currentTimeMillis();
		algorithm2(arr2);
		long endTime2 = System.currentTimeMillis();
		long time2 = endTime2 - startTime2;
		
		long startTime3 = System.currentTimeMillis();
		algorithm2(arr3);
		long endTime3 = System.currentTimeMillis();
		long time3 = endTime3 - startTime3;
		
		long startTime4 = System.currentTimeMillis();
		algorithm2(arr4);
		long endTime4 = System.currentTimeMillis();
		long time4 = endTime4 - startTime4;
		
		long startTime5 = System.currentTimeMillis();
		algorithm2(arr5);
		long endTime5 = System.currentTimeMillis();
		long time5 = endTime5 - startTime5;
		
		long startTime6 = System.currentTimeMillis();
		algorithm2(arr6);
		long endTime6 = System.currentTimeMillis();
		long time6 = endTime6 - startTime6;
        
		System.out.println("N=25000 uses "+ time1 + " milliseconds.");
		System.out.println("N=50000 uses "+ time2 + " milliseconds.");
		System.out.println("N=100000 uses "+ time3 + " milliseconds.");
		System.out.println("N=200000 uses "+ time4 + " milliseconds.");
		System.out.println("N=400000 uses "+ time5 + " milliseconds.");
		System.out.println("N=800000 uses "+ time6 + " milliseconds.");
		
		JFrame frame = new JFrame("Basic line grapher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BasicLineGrapher lineGraph = new BasicLineGrapher(new Dimension(800,
                600), 100, 100);
        LineGraph lg1 = new LineGraph(Color.RED);
        lg1.addPoint(new Point(0, (int)time1));
        lg1.addPoint(new Point((int)time2, (int)time2));
        lg1.addPoint(new Point((int)time3, (int)time3));
        lg1.addPoint(new Point((int)time4, (int)time4));
        lg1.addPoint(new Point((int)time5, (int)time5));
        lg1.addPoint(new Point((int)time6, (int)time6));
        lineGraph.addLineGraph(lg1);
        
        frame.getContentPane().add(lineGraph, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
		
	}
	
	public static void algorithm2(int[] arr){
		int n = arr.length;
		boolean[] used = new boolean[n];
		for(int k = 0; k < n; k++){
			used[k] = false;
		}
		for(int i = 0; i < n; i++){
			int ran = randInt(n);
			if(used[ran]=false){
				used[ran]=true;
				arr[i] = ran;
			}
			while(used[ran]==true){
				ran = randInt(n);
			}
		}
	}
	
	public static int randInt(int n){
		Random generator = new Random();
		return generator.nextInt(n);
	}
	
}
