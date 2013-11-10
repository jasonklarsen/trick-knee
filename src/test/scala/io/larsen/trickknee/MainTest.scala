package io.larsen.trickknee

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class MainTest extends FunSpec with ShouldMatchers {

  val happyTwinge = new Twinge { def feltSomething = None }
  val noTwinge = new Twinge { def feltSomething = None }
  val strangeTwinge = new Twinge { def feltSomething = Some("I felt something strange") }
  val peculiarTwinge = new Twinge { def feltSomething = Some("I felt something peculiar") }

  describe("A Knee") {

    it("feels Nil when all of the Twinges don't feel anything") {
      val trickKnee = new Knee(happyTwinge, noTwinge)
      trickKnee.feltSomething should be (Nil)
    }

    it("feels a list of the twinges when all of the Twinges feel something") {
      val trickKnee = new Knee(strangeTwinge, peculiarTwinge)
      trickKnee.feltSomething should be (Seq("I felt something strange", "I felt something peculiar"))
    }

    it("feels Only the twinges that feel something when you have a mix of twinges that feel and don't feel anything") {
      val trickKnee = new Knee(noTwinge, peculiarTwinge, happyTwinge, strangeTwinge)
      trickKnee.feltSomething should be (Seq("I felt something peculiar", "I felt something strange"))
    }
  }
}