package sda;
public class SortEstimate{
    public double howMany(int c, int time){
        double s = 0.0;
        double e = 9999999999.0;
        
        while((Math.abs(e-s) > 1e-9) && (Math.abs(e-s)/s > 1e-9)){
            double mid = s+(e-s)/2.0;
        	double y = c*mid*(Math.log(mid)/Math.log(2)); // f(mid)
            if(y > time){
            	e = mid;
            } else {
                s = mid;
            }
        }
        return s;
    }
    
    public static void main(String[] args){
    	SortEstimate se = new SortEstimate();
    	se.howMany(1, 2000000000);
    }
    
}