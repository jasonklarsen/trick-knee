package io.larsen.trickknee

import com.typesafe.config.ConfigFactory
import scala.util.Try

object Main extends App {
  val maybeConfig = args.headOption.map(ConfigFactory.load)
  val config = maybeConfig.getOrElse(ConfigFactory.empty)
  val blueprint = new Blueprint(config)
  val maybeKnee = blueprint.constructKnee
  val readTheKnee = maybeKnee.transform(workThatKnee, tellMeWhyDidMaKneeBlewUp)
  sys.exit(readTheKnee.get)

  def workThatKnee(knee: Knee) = {
    val twinges = knee.feltSomething
    twinges.foreach { println }
    Try(twinges.size)
  }

  def tellMeWhyDidMaKneeBlewUp(ex: Throwable) = { 
    println(ex.getMessage)
    ex.printStackTrace
    Try(-1) 
  }
}

class Knee(twinges: Twinge*) {
  def feltSomething(): Seq[String] = {
    twinges.map(_.feltSomething).flatten
  }
}

trait Twinge {
  def feltSomething(): Option[String]
}

