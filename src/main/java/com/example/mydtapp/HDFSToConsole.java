/**
 * Put your copyright and license info here.
 */
package com.example.mydtapp;

import com.datatorrent.common.partitioner.StatelessPartitioner;
import org.apache.apex.malhar.lib.fs.LineByLineFileInputOperator;
import org.apache.hadoop.conf.Configuration;

import com.datatorrent.api.Context;
import com.datatorrent.api.DAG;
import com.datatorrent.api.DAG.Locality;
import com.datatorrent.api.StreamingApplication;
import com.datatorrent.api.Context.PortContext;
import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.lib.io.ConsoleOutputOperator;

@ApplicationAnnotation(name = "HDFSToConsole")
public class HDFSToConsole implements StreamingApplication
{

  @Override
  public void populateDAG(DAG dag, Configuration conf)
  {
    LineByLineFileInputOperator fileInput = dag.addOperator("input", new LineByLineFileInputOperator());
    
    AnotherDummyOperator dummy = dag.addOperator("dummy", new AnotherDummyOperator());
    dag.setAttribute(dummy, Context.OperatorContext.METRICS_AGGREGATOR, new MyMetricsAggregator());
    //dag.setInputPortAttribute(dummy.data, PortContext.PARTITION_PARALLEL, true);

    dag.setAttribute(dummy, Context.OperatorContext.PARTITIONER, new StatelessPartitioner<AnotherDummyOperator>(4));


    ConsoleOutputOperator consOne = dag.addOperator("consoleOne", new ConsoleOutputOperator());
    ConsoleOutputOperator consTwo = dag.addOperator("consoleTwo", new ConsoleOutputOperator());
    
    dag.addStream("randomData", fileInput.output, dummy.data).setLocality(Locality.CONTAINER_LOCAL);
    dag.addStream("randomDataOne", dummy.portOne, consOne.input);
    dag.addStream("randomDataTwo", dummy.portTwo, consTwo.input);
  }
}
