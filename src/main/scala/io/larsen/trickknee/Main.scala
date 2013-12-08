package io.larsen.trickknee

import com.typesafe.config.{Config, ConfigFactory, ConfigParseOptions, ConfigResolveOptions}
import java.io.PrintStream
import scala.util.Try

object Main extends App {
  val maybeConfig = args.headOption.map(name => ConfigFactory.parseFile(new java.io.File(name)))
  val config = maybeConfig.getOrElse(ConfigFactory.empty)
  val exitCode = Knee.actUp(new Blueprint(config), System.err)
  sys.exit(exitCode)
}

trait Twinge {
  def feltSomething(): Option[String]
}

class Knee(twinges: Twinge*) {
  def feltSomething(): Seq[String] = {
    twinges.map(_.feltSomething).flatten
  }
}

object Knee {

  // Not totally proud of returning the exit code... Both side effects and a return value!?!?
  // Naughty Scala developer...  Will re-think
  def actUp(blueprint: Blueprint, printStream: PrintStream): Int = {
    val kneeAttempt = blueprint.constructKnee
    val readTheKnee = kneeAttempt.transform(workThatKnee(printStream), tellMeWhyDidMaKneeBlowUp(printStream))
    readTheKnee.get
  }

  private def workThatKnee(printStream: PrintStream)(knee: Knee) = {
    val twinges = knee.feltSomething
    twinges.foreach { printStream.println }
    Try(twinges.size)
  }

  private def tellMeWhyDidMaKneeBlowUp(printStream: PrintStream)(ex: Throwable) = { 
    printStream.println(ex.getMessage)
    ex.printStackTrace(printStream)
    Try(-1) 
  }
}