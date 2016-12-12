# Spark_Streaming: StateLess_Vs_StateFul_Transformations
<p>What a fun experiment indeed it was!! I hereby put together three spark streaming applications to distinguish stateless streaming transformation against stateful transformation (stateful transformations can be Windowed transformation plus UpdateStateByKey transformation).</p>

StateLess Transformation
------------------------
<p><font color="red"><b><i>socketTextStream</i></b></font> method of <b>JavaStreamingContext</b> will let open a port for input messages.<b><i>'StreamingLogInput.java'</i></b> holds application that conducts series of stateless transformations on received messages. A batch of input messages (generated every 3000 milliseconds) transforms into RDD holding only integer inputs. Further transformation produces the maximum integer value. This transformation takes place on each new batches independent of previous batches.</p>

*   **Sample Input Messages:**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/Inp_Msg_StreamingApplication.PNG" alt="input" style="width:304px;height:228px;">
</body>
</html>

*   **RDD during first 3000 milliseconds batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/StrApp1stBatch.PNG" alt="first batch" style="width:304px;height:228px;">
</body>
</html>


*   **Maximum Value of above batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/StrApp1stBatchMaxVal.PNG" alt="max val 1st batch" style="width:304px;height:228px;">
</body>
</html>


*   **RDD during second 3000 milliseconds batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/StrApp2ndBatch.PNG" alt="2nd batch" style="width:304px;height:228px;">
</body>
</html>


*   **Maximum Value of above batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/StrApp2ndBatchMaxVal.PNG" alt="Max val 2nd batch" style="width:304px;height:228px;">
</body>
</html>


*   **RDD during third 3000 milliseconds batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/StrApp3rdBatch.PNG" alt="3rd batch" style="width:304px;height:228px;">
</body>
</html>

*   **Maximum Value of above batch:**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/StrApp3rdBatchMaxVal.PNG" alt="3rd batch max val" style="width:304px;height:228px;">
</body>
</html>

<p>----------------------------------------------------------------------------------------------------------------------------------</p>



StateFul Transformation (Windowed)
----------------------------------
<p>Stateful Transformations track data across time i.e. some data from previous batches is used to generate the results for a new batch. Windowed stateful transformation compute results across a longer time period than the StreamingContext's batch interval, by combining results from multiple batches.</p>
<p>All windowe operations need two parameters, <b><i>window duration</i></b> and <b><i>sliding duration</i></b>, both of which must be a multiple of the StreamingContext's batch interval.</p>

<table style="width:100%">
  <tr>
    <td>Batch Duration</td>
  </tr>
  <tr>
    <td>Window Duration</td>
  </tr>
  <tr>
    <td>Sliding Duration</td>
  </tr>
  </table>

<p>Sometimes we need to know what happened in last n seconds every m seconds. As a simple example, lets say batch interval is 10 seconds and we need to know what happened in last 60 seconds every 30 seconds. Here 60 seconds is called window length and 30 second slide interval. Lets say first 6 batches are A,B,C,D,E,F which are part of first window. After 30 seconds second window will ve formed which will have D,E,F,G,H,I. As you can see 3 batches are common between first and second window.</p>














