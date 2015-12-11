package sda;

public class BubSort {
	public static int[] sort(int[] original2){
		int[] original = new int[original2.length];
		for(int i=0; i<original2.length; i++)
			original[i]=original2[i];
		
		boolean switched = true;
		
		while(switched){
			switched = false;
			for(int i=0; i<original.length-1; i++){
				if(original[i] > original[i+1]){
					original[i] ^= original[i+1];
					original[i+1] ^= original[i];
					original[i] ^= original[i+1];
					switched = true;
				}
			}
		}
		
		return original;
		
	}
	
	
	public static void main(String[] args){
		int[] a = new int[]{5,3,4,1,2,7};
		a = sort(a);
		for(int x : a){
			System.out.println(x);
		}
		
	}
}
