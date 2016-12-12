package stateless.stateful.streams;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.api.java.JavaStreamingContextFactory;
import org.apache.spark.streaming.Duration;

import com.google.common.base.Optional;

import scala.Tuple2;

	public class UpdateStateByKey {
		
		public static void main(String[] args) throws Exception {
	  
        final String checkpointDir= "/home/edureka/Desktop/chkptdir";
	    
		
		class JCFact implements JavaStreamingContextFactory{
			
		@Override
		public JavaStreamingContext create() {
			String master = "local[2]";
			JavaSparkContext sc = new JavaSparkContext(master, "Windowed Transform");
	        JavaStreamingContext jssc = new JavaStreamingContext(sc, new Duration(5000));
	        jssc.checkpoint(checkpointDir);
	        return jssc;
		}}
		
		JavaStreamingContext jssc = JavaStreamingContext.getOrCreate(checkpointDir,new JCFact());
			
			
		JavaDStream<String> lines = jssc.socketTextStream("localhost", 7777);
        //lines.print();
        
        JavaDStream<Integer> onlyints = lines.transform(new Function<JavaRDD<String>, JavaRDD<Integer>>(){
    	
		public JavaRDD<Integer> call(JavaRDD<String> ds){
    		return myfunc(ds);
    	}});
        onlyints.print();
        
        JavaPairDStream<String,Long> oddeven = onlyints.mapToPair(new PairFunction<Integer,String,Long>(){
        	public Tuple2<String,Long> call(Integer i){
        		if(i%2!=0){
        			return new Tuple2("odd",1L);
        		}else{
        			return new Tuple2("even",1L);
        		}
        	}
        }).updateStateByKey(new UpdateRunningSum());
        oddeven.print();
        
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
      
      static final class UpdateRunningSum implements Function2<List<Long>,Optional<Long>,Optional<Long>>{
      	
      	public Optional<Long> call (List<Long> nums, Optional<Long> current){
      	
      		long sum = current.or(0L);
      		return Optional.of(sum + nums.size());
      	}
      	
      }
	}



