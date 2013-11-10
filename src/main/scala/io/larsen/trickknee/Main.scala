package io.larsen.trickknee


object Main extends App {
  val twinges = Knee().feltSomething()
  twinges.foreach { println }
  sys.exit(twinges.size)
}

class Knee(twinges: Twinge*) {
  def feltSomething(): Seq[String] = {
    twinges.map(_.feltSomething).flatten
  }
}

object Knee {
  // Let's build this robotic trick-knee
  def apply() = new Knee(new TravisCI(TravisCI.parseIt))
}

trait Twinge {
  def feltSomething(): Option[String]
}

