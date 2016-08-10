package topologies

import backtype.storm.topology.TopologyBuilder
import backtype.storm.tuple.Fields
import backtype.storm.{Config, LocalCluster}
import bolts.{WordCounterBolt, WordNormalizerBolt}
import spouts.WordReaderSpout
import backtype.storm.utils.Utils

/**
  * Created by enyert on 8/9/16.
  */
object TopologyMain extends App{
  override def main(args: Array[String]) {
    //println("Hello world!")
    //Topology Definition
    val builder: TopologyBuilder = new TopologyBuilder()
    builder.setSpout("word-reader", new WordReaderSpout())
    builder.setBolt("word-normalizer", new WordNormalizerBolt()).shuffleGrouping("word-reader")
    builder.setBolt("word-counter", new WordCounterBolt(), 2).fieldsGrouping("word-normalizer", new Fields("word"))


    //Configuration
    val conf = new Config()
    conf.put("wordsFile", "/Users/enyert/Documents/scala/hello-world-storm/src/main/resources/words.txt")
    conf.setDebug(false)
    conf.setMaxSpoutPending(1)

    val cluster = new LocalCluster()
    cluster.submitTopology("Getting-Started-Topology", conf, builder.createTopology())
    Utils.sleep(2000)
    cluster.shutdown()
  }

}
