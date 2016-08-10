package spouts

import java.util

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.{IRichSpout, OutputFieldsDeclarer}
import backtype.storm.tuple.{Fields, Values}
import backtype.storm.utils.Utils

import scala.io.Source

/**
  * Created by enyert on 8/8/16.
  */
class WordReaderSpout extends IRichSpout{

  var collector: SpoutOutputCollector = _

  var fileName: String = ""

  //var isComplete: Boolean = false

  //def callOnOpen: (TopologyContext) => Unit = { (TopologyContext) => Unit }

  var context: TopologyContext = _

  var completed = false

  /** IRichSpout Methods **/

  override def nextTuple(): Unit = {
    if(completed) {
      Utils.sleep(1000)
      return
    }

    for(line <- Source.fromFile(fileName).getLines()) {
      collector.emit(new Values(line), line)
    }
    completed = true
  }

  override def activate(): Unit = {}

  override def deactivate(): Unit = {}

  override def close(): Unit = {}

  override def fail(msgId: scala.Any): Unit =
    System.err.print("Failed" + msgId)

  override def open(conf: util.Map[_, _], context: TopologyContext, collector: SpoutOutputCollector): Unit = {
    this.context = context
    this.collector = collector
    fileName = conf.get("wordsFile").toString()
  }


  override def ack(msgId: scala.Any): Unit = println("OK: " + msgId)

  override def getComponentConfiguration: util.Map[String, AnyRef] = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit =
    declarer.declare(new Fields("line"))
}
