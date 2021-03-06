# Apache Spark Streaming: StateLess Vs StateFul Transformations
<p>What a fun experiment indeed it was!! I hereby put together three spark streaming applications to distinguish stateless streaming transformation against stateful transformation (stateful transformations can be Windowed transformation plus UpdateStateByKey transformation).</p>

StateLess Transformation
------------------------
<p><font color="red"><b><i>socketTextStream</i></b></font> method of <b>JavaStreamingContext</b> will let open a port for input messages.<b><i>'StreamingLogInput.java'</i></b> holds application that conducts series of stateless transformations on received messages. A batch of input messages (generated every 3000 milliseconds) transforms into RDD holding only integer inputs. Further transformation produces the maximum integer value. This transformation takes place on each new batches independent of previous batches.</p>

*   **Input Messages:**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/Inp_Msg_StreamingApplication.PNG" alt="input" style="width:304px;height:228px;">
</body>
</html>

*   **RDD during first 3000 milliseconds batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/StrApp1stBatch.PNG" alt="first batch" style="width:304px;height:228px;">
</body>
</html>


*   **Maximum Value of above batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/StrApp1stBatchMaxVal.PNG" alt="max val 1st batch" style="width:304px;height:228px;">
</body>
</html>


*   **RDD during second 3000 milliseconds batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/StrApp2ndBatch.PNG" alt="2nd batch" style="width:304px;height:228px;">
</body>
</html>


*   **Maximum Value of above batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/StrApp2ndBatchMaxVal.PNG" alt="Max val 2nd batch" style="width:304px;height:228px;">
</body>
</html>


*   **RDD during third 3000 milliseconds batch:**


<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/StrApp3rdBatch.PNG" alt="3rd batch" style="width:304px;height:228px;">
</body>
</html>

*   **Maximum Value of above batch:**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/StrApp3rdBatchMaxVal.PNG" alt="3rd batch max val" style="width:304px;height:228px;">
</body>
</html>

<p>----------------------------------------------------------------------------------------------------------------------------------</p>



StateFul Transformation (Windowed)
----------------------------------
<p>Stateful Transformations track data across time i.e. some data from previous batches is used to generate the results for a new batch. Windowed stateful transformation compute results across a longer time period than the StreamingContext's batch interval, by combining results from multiple batches.</p>
<p>All windowed operations need two parameters, <b><i>window duration</i></b> and <b><i>sliding duration</i></b>, both of which must be a multiple of the StreamingContext's batch interval.</p>

<table style="width:100%">
  <tr>
    <td>Batch Interval</td>
  </tr>
  <tr>
    <td>Window Duration</td>
  </tr>
  <tr>
    <td>Sliding Duration</td>
  </tr>
  </table>

<p>Sometimes we need to know what happened in last n seconds every m seconds. As a simple example, lets say batch interval is 10 seconds and we need to know what happened in last 60 seconds every 30 seconds. Here 60 seconds is called window length and 30 second slide interval. Lets say first 6 batches are A,B,C,D,E,F which are part of first window. After 30 seconds second window will ve formed which will have D,E,F,G,H,I. As you can see 3 batches are common between first and second window.</p>

<p><b><i>Windowed_Transformation.java</i></b> holds windowed transformation version of the same application contained in <i>StreamingLogInput.java</i>. The batch interval is of 3 seconds, sliding duration is of 6 seconds and maximum value is calculated on window duration of 9 seconds.</p>

*   **Input Messages:**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/WinTransInpMessages.PNG" alt="input window stateful" style="width:304px;height:228px;">
</body>
</html>

*   **RDD batch generated during Seconds (1,2 and 3):**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/WinTransFstBatchInterval.PNG" alt="window trans batch 1" style="width:304px;height:228px;">
</body>
</html>

*   **RDD batch generated during Seconds (4,5 and 6):**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/WinTrans2ndBatch.PNG" alt="window trans batch 2" style="width:304px;height:228px;">
</body>
</html>

*   **Maximum Value calculated duration first slide duration (after 6 seconds):**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/MaxVal1stSlideDuration.PNG" alt="compute 1st slide" style="width:304px;height:228px;">
</body>
</html>

*   **RDD batch generated during Seconds (7,8 and 9):**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/WinTrans3rdBatch.PNG" alt="window trans batch 3" style="width:304px;height:228px;">
</body>
</html>

*   **RDD batch generated during Seconds (10,11 and 12):**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/WinTrans4thBatch.PNG" alt="window trans batch 4" style="width:304px;height:228px;">
</body>
</html>

*   **Maximum Value computed during second sliding duration (covering RDDS of seconds 4,5,6,7,8,9,10,11 and 12):**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/MaxVal2ndSlide.PNG" alt="max val 2nd slide" style="width:304px;height:228px;">
</body>
</html>


StateFul Transformation (UpdateStateByKey)
------------------------------------------
<p><b><i>updateStateByKey()</i></b> enables maintaining state across the batches in a DStream.</p>
<p><b><i>UpdateStateByKey.java</i></b> consists of UpdateStateByKey streaming example. Input stream is the same as in preceding two experiments. The batch interval is set to be 5000 milliseconds. The event considered is the flow of 'odd' or 'even' integers. The state is represented by the total number of odd or even numbers at a particular time. Result is generated as key/value pair. Updates are across all batches continuously.</p>
<p>Down below shows the states at two particular timestamps:</p>

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/stateupdate_1.PNG" alt="update state image 1" style="width:304px;height:228px;">
</body>
</html>

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/src/main/resources/stateupdate2.PNG" alt="update state image 1" style="width:304px;height:228px;">
</body>
</html>

<p><b>Thank you all for passing by my project. This is my second Spark project. I will introduce more Spark projects covering topics like Spark SQL, Custom Partitioning, Custom Accumulators, Serialization, Machine Learning and more.</b></p>
