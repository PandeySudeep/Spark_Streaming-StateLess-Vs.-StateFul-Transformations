package stateless.stateful.streams;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.Duration;

public class StreamingLogInput {
  
  public static void main(String[] args) throws Exception {
	  
/*
 * The code below doesn't contain checkpointing mechanism. Sample implementation is available in Windowed_Transformation.java.
*/
		String master = "local[2]";
		JavaSparkContext sc = new JavaSparkContext(master, "StreamingLogInput");
        JavaStreamingContext jssc = new JavaStreamingContext(sc, new Duration(3000));
        JavaDStream<String> lines = jssc.socketTextStream("localhost", 7777);
     
        JavaDStream<Integer> onlyints = lines.transform(new Function<JavaRDD<String>, JavaRDD<Integer>>(){
    	
    	public JavaRDD<Integer> call(JavaRDD<String> ds){
    		return myfunc(ds);
    	}});
        onlyints.print();
    
        JavaDStream<Integer> maxval = onlyints.reduce(new Function2<Integer,Integer,Integer>(){
    	
        public Integer call(Integer i1, Integer i2){
    		return Math.max(i1,i2);
    	}
    });
    
        
        maxval.print();
    
        // start our streaming context and wait for it to "finish"
        jssc.start();
        // Wait for 10 seconds then exit. To run forever call without a timeout
        jssc.awaitTermination();
    
	}
  
  static JavaRDD<Integer> myfunc(JavaRDD<String> input_rdd) throws NumberFormatException{
	  
	  JavaRDD<Integer> output_rdd = input_rdd.map(new Function<String,Integer>(){
		
					public Integer call(String s){
		  				Integer i=0;
		  				try{
		  					i= Integer.parseInt(s);
		  				}catch (NumberFormatException nfe){
		  					i=0;
		  				}
		  				return i;
		  }});
	  return output_rdd;
  }
}

