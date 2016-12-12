# Spark_Streaming: StateLess_Vs_StateFul_Transformations
<p>What a fun experiment indeed it was!! I hereby put together three spark streaming applications to distinguish stateless streaming transformation against stateful transformation (stateful transformations can be Windowed transformation plus UpdateStateByKey transformation).</p>

StateLess Transformation
------------------------
<p><font color="red"><b><i>socketTextStream</i></b></font> method of <b>JavaStreamingContext</b> will let open a port for input messages.<b><i>'StreamingLogInput.java'</i></b> holds application that conducts series of stateless transformations on received messages. A batch of input messages (generated every 3000 milliseconds) transforms into RDD holding only integer inputs. Further transformation produces the maximum integer value. This transformation takes place on each new batches independent of previous batches.</p>

*   **Sample Input Messages:**

<html>
<body>
<img src="https://github.com/PandeySudeep/Spark_Streaming-StateLess-Vs.-StateFul-Transformations/blob/master/Inp_Msg_StreamingApplication.PNG" alt="call signs" style="width:304px;height:228px;">
</body>
</html>
