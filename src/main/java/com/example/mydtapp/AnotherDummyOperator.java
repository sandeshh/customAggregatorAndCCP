package com.example.mydtapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.datatorrent.common.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datatorrent.api.AutoMetric;
import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.Context.OperatorContext;
import com.datatorrent.common.util.BaseOperator;

public class AnotherDummyOperator extends BaseOperator implements Serializable
{
  private static final long serialVersionUID = -4260835939026378071L;

  private static final Logger logger = LoggerFactory.getLogger(AnotherDummyOperator.class);

  private String operatorId;

  @AutoMetric
  Collection<Collection<Pair<String, Object>>> ret = new ArrayList<>();

  public final transient DefaultOutputPort<String> portOne = new DefaultOutputPort<String>();
  public final transient DefaultOutputPort<String> portTwo = new DefaultOutputPort<String>();

  public final transient DefaultInputPort<String> data = new DefaultInputPort<String>()
  {
    @Override
    public void process(String tuple)
    {
      processTuple(tuple);
    }
  };

  @Override
  public void setup(OperatorContext context)
  {
    super.setup(context);
    this.operatorId = Integer.toString(context.getId());
  }

  @Override
  public void beginWindow(long windowId)
  {
    ret.clear();

    Collection<Pair<String, Object>> val = new ArrayList<>();
    val.add(new Pair<String, Object>("hashtag", "hello " + operatorId));
    val.add(new Pair<String, Object>("count", new Random().nextInt(100)));
    ret.add(val);
  }

  private void processTuple(String tuple)
  {
    String[] arr = tuple.split("\\s");
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].length() > 5) {
        portOne.emit(arr[i].toLowerCase());
      } else {
        portTwo.emit(arr[i].toUpperCase());
      }
    }
  }

}
