package io.larsen.trickknee

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.Failure

class MainTest extends FunSpec with ShouldMatchers {

  describe("A Knee") {

    it("feels Nil when all of the Twinges don't feel anything") {
      val trickKnee = new Knee(happyTwinge, noTwinge)
      trickKnee.feltSomething should be (Nil)
    }

    it("feels a list of the twinge messages when all of the Twinges feel something") {
      val trickKnee = new Knee(strangeTwinge, peculiarTwinge)
      trickKnee.feltSomething should be (Seq("I felt something strange", "I felt something peculiar"))
    }

    it("feels Only the twinges that felt something when you have a mix of twinges that felt and didn't feel anything") {
      val trickKnee = new Knee(noTwinge, peculiarTwinge, happyTwinge, strangeTwinge)
      trickKnee.feltSomething should be (Seq("I felt something peculiar", "I felt something strange"))
    }
  }

  val happyTwinge = new Twinge { def feltSomething = None }
  val noTwinge = new Twinge { def feltSomething = None }
  val strangeTwinge = new Twinge { def feltSomething = Some("I felt something strange") }
  val peculiarTwinge = new Twinge { def feltSomething = Some("I felt something peculiar") }

}