
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.JFrame;

public class algorithm_1 {

	public static void main(String[] args) throws IOException{
		
		int n1 = 250, n2 = 500, n3 = 1000, n4 = 2000;
		int[] arr1 = new int[n1];
		int[] arr2 = new int[n2];
		int[] arr3 = new int[n3];
		int[] arr4 = new int[n4];
		
		long startTime1 = System.currentTimeMillis();
		algorithm1(arr1);
		long endTime1 = System.currentTimeMillis();
		long time1 = endTime1 - startTime1;
		
		long startTime2 = System.currentTimeMillis();
		algorithm1(arr2);
		long endTime2 = System.currentTimeMillis();
		long time2 = endTime2 - startTime2;
		
		long startTime3 = System.currentTimeMillis();
		algorithm1(arr3);
		long endTime3 = System.currentTimeMillis();
		long time3 = endTime3 - startTime3;
		
		long startTime4 = System.currentTimeMillis();
		algorithm1(arr4);
		long endTime4 = System.currentTimeMillis();
		long time4 = endTime4 - startTime4;
        
		System.out.println("N=250 uses "+ time1 + " milliseconds.");
		System.out.println("N=500 uses "+ time2 + " milliseconds.");
		System.out.println("N=1000 uses "+ time3 + " milliseconds.");
		System.out.println("N=2000 uses "+ time4 + " milliseconds.");
		
		JFrame frame = new JFrame("Basic line grapher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BasicLineGrapher lineGraph = new BasicLineGrapher(new Dimension(800,
                600), 20, 20);
        LineGraph lg1 = new LineGraph(Color.RED);
        lg1.addPoint(new Point(0, (int)time1));
        lg1.addPoint(new Point((int)time2, (int)time2));
        lg1.addPoint(new Point((int)time3, (int)time3));
        lg1.addPoint(new Point((int)time4, (int)time4));
        lineGraph.addLineGraph(lg1);
        
        frame.getContentPane().add(lineGraph, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
		
	}
	
	public static void algorithm1(int[] arr){
		int n = arr.length;
		boolean flag = true;
		for(int i = 0; i < n; i++){
			int last = 0;
			do{
				int tmp = randInt(n);
				for(int j = 0; j < i; j++){
					if(arr[j] == tmp){
						flag = false;
						break;
					}
					else{
						flag = true;
						last = tmp;
					}
				}
			}while(flag == false);
			arr[i] = last;
		}
	}
	
	public static int randInt(int n){
		Random generator = new Random();
		return generator.nextInt(n);
	}
	
}
