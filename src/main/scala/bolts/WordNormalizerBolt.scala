package bolts

import java.util

import backtype.storm.task.{OutputCollector, TopologyContext}
import backtype.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import backtype.storm.tuple.{Fields, Tuple, Values}

/**
  * Created by enyert on 8/9/16.
  */
class WordNormalizerBolt extends IRichBolt{

  var collector: OutputCollector = _

  override def getComponentConfiguration: util.Map[String, AnyRef] = null

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit =
    declarer.declare(new Fields("word"))

  override def cleanup(): Unit = {}

  override def execute(input: Tuple): Unit = {
    //println("Execute from WordNormalizerBolt")
    val sentence: String = input.getString(0)
    for(word <- sentence.split(" ")) {
      val nsWord = word.trim()
      if(!nsWord.isEmpty) {
        val notEmptyWord: String = nsWord.toLowerCase
        //var a: List[String] = List(notEmptyWord)
        //println(notEmptyWord)
        collector.emit(input, new Values(notEmptyWord))
        //collector.ack(input)
      }
    }
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.collector = collector
  }
}
