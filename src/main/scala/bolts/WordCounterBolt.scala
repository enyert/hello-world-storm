package bolts

import java.util

import backtype.storm.task.{OutputCollector, TopologyContext}
import backtype.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import backtype.storm.tuple.Tuple

/**
  * Created by enyert on 8/9/16.
  */
class WordCounterBolt extends IRichBolt{

  var id: Int = 0

  var name: String = ""

  var counters: Map[String, Int] = _

  var collector: OutputCollector = _


  override def getComponentConfiguration: util.Map[String, AnyRef] = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {}

  override def cleanup(): Unit = {
    println("-- Word Counter [" + name + "-" + id + "] --")
    println("Counters number: " + counters.size)
    for(entry <- counters) {
      println(entry._1 + ": " + entry._2)
    }
  }

  override def execute(input: Tuple): Unit = {
    //println("Entered Here")
    val str = input.getString(0)
    //println("Value is: " + str)
    if(counters.contains(str) == false) {
      //counters = counters - "abc"
      counters = counters ++ Map(str -> 1)
    } else {
      println("Value is: " + str)
      val c = counters(str) + 1
      counters = counters ++ Map(str -> c)
    }
    collector.ack(input)
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    counters = Map[String, Int]()
    this.collector = collector
    name = context.getThisComponentId()
    id = context.getThisTaskId()
  }
}
