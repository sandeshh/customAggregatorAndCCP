package com.example.mydtapp;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class PortMetrics implements Serializable
{
  private static final long serialVersionUID = -3093591259431468131L;
  AtomicLong portOneCount;
  AtomicLong portTwoCount;
 
  public PortMetrics() {
    portOneCount = new AtomicLong();
    portTwoCount = new AtomicLong();
  }

  public AtomicLong getPortOneCount()
  {
    return portOneCount;
  }

  public void setPortOneCount(AtomicLong portOneCount)
  {
    this.portOneCount = portOneCount;
  }

  public AtomicLong getPortTwoCount()
  {
    return portTwoCount;
  }

  public void setPortTwoCount(AtomicLong portTwoCount)
  {
    this.portTwoCount = portTwoCount;
  }
  
}
