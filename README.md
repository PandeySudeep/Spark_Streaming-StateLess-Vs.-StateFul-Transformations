# Spark_Streaming: StateLess_Vs._StateFul_Transformations
<p>What a fun experiment indeed it was!! I hereby put together three spark streaming applications to distinguish stateless streaming transformation against stateful transformation (stateful transformations can be Windowed transformation plus UpdateStateByKey transformation).</p>

StateLess Transformation
------------------------
<p><font color="red"><b>socketTextStream</b></font>method of <b>JavaStreamingContext</b> will let open a port for input messages.<b><i>'StreamingLogInput.java'</i></b> holds application that streams stateless transformation of received messages. A batch of input messages (generated every 3000 milliseconds) transforms into RDD holding only those input messages that are integers. Further transformation generates final RDD that generates the maximum value. This transformation takes place on each new batches independent of previous batches.</p>
