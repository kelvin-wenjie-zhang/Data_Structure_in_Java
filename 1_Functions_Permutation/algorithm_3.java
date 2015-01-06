

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import javax.swing.JFrame;

public class algorithm_3 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n1 = 100000, n2 = 200000, n3 = 400000, n4 = 800000, n5 = 1600000, n6 = 3200000, n7 = 6400000;
		int[] arr1 = new int[n1];
		int[] arr2 = new int[n2];
		int[] arr3 = new int[n3];
		int[] arr4 = new int[n4];
		int[] arr5 = new int[n5];
		int[] arr6 = new int[n6];
		int[] arr7 = new int[n7];
		
		long startTime1 = System.currentTimeMillis();
		autoFill(arr1);
		swapReferences(arr1);
		long endTime1 = System.currentTimeMillis();
		long time1 = startTime1 - endTime1;
		
		long startTime2 = System.currentTimeMillis();
		autoFill(arr2);
		swapReferences(arr2);
		long endTime2 = System.currentTimeMillis();
		long time2 = startTime2 - endTime2;
		
		long startTime3 = System.currentTimeMillis();
		autoFill(arr3);
		swapReferences(arr3);
		long endTime3 = System.currentTimeMillis();
		long time3 = startTime3 - endTime3;
		
		long startTime4 = System.currentTimeMillis();
		autoFill(arr4);
		swapReferences(arr4);
		long endTime4 = System.currentTimeMillis();
		long time4 = startTime4 - endTime4;
		
		long startTime5 = System.currentTimeMillis();
		autoFill(arr5);
		swapReferences(arr5);
		long endTime5 = System.currentTimeMillis();
		long time5 = startTime5 - endTime5;
		
		long startTime6 = System.currentTimeMillis();
		autoFill(arr6);
		swapReferences(arr6);
		long endTime6 = System.currentTimeMillis();
		long time6 = startTime6 - endTime6;
		
		long startTime7 = System.currentTimeMillis();
		autoFill(arr7);
		swapReferences(arr7);
		long endTime7 = System.currentTimeMillis();
		long time7 = startTime7 - endTime7;
		
		System.out.println("N=100000 uses "+ time1 + " milliseconds.");
		System.out.println("N=200000 uses "+ time2 + " milliseconds.");
		System.out.println("N=400000 uses "+ time3 + " milliseconds.");
		System.out.println("N=800000 uses "+ time4 + " milliseconds.");
		System.out.println("N=1600000 uses "+ time5 + " milliseconds.");
		System.out.println("N=3200000 uses "+ time6 + " milliseconds.");
		System.out.println("N=6400000 uses "+ time7 + " milliseconds.");
		
		JFrame frame = new JFrame("Basic line grapher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BasicLineGrapher lineGraph = new BasicLineGrapher(new Dimension(800,
                600), 1000, 1000);
        LineGraph lg1 = new LineGraph(Color.RED);
        lg1.addPoint(new Point(0, -(int)time1));
        lg1.addPoint(new Point(-(int)time2, -(int)time2));
        lg1.addPoint(new Point(-(int)time3, -(int)time3));
        lg1.addPoint(new Point(-(int)time4, -(int)time4));
        lg1.addPoint(new Point(-(int)time5, -(int)time5));
        lg1.addPoint(new Point(-(int)time6, -(int)time6));
        lg1.addPoint(new Point(-(int)time7, -(int)time7));
        lineGraph.addLineGraph(lg1);
        
        frame.getContentPane().add(lineGraph, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
		
		
	}
	public static int randInt(int n){
		Random generator = new Random();
		return generator.nextInt(n);
	}
	
	public static void autoFill(int[] arr){
		int n = arr.length;
		for(int i = 0; i < n; i++){
			arr[i] = i + 1;
		}
	}
	
	public static void swap(int a, int b){
		int tmp = b;
		b = a;
		a = tmp;
	}
	
	public static void swapReferences(int[] arr){
		int n = arr.length;
		for(int i = 1; i < n; i++){
			swap(arr[i],arr[randInt(i)]);
		}
	}
	
}
