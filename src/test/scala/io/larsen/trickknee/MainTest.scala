package io.larsen.trickknee

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.Failure
import scala.util.Try

class MainTest extends FunSpec with ShouldMatchers {

  describe("A Knee") {

    it("feels Nil when all of the Twinges don't feel anything") {
      val trickKnee = new Knee(happyTwinge, noTwinge)
      trickKnee.feltSomething should be (Nil)
    }

    it("feels a list of the twinge messages when all of the Twinges feel something") {
      val trickKnee = new Knee(strangeTwinge, peculiarTwinge)
      trickKnee.feltSomething should be (Seq(strangeMessage, peculiarMessage))
    }

    it("feels Only the twinges that felt something when you have a mix of twinges that felt and didn't feel anything") {
      val trickKnee = new Knee(noTwinge, peculiarTwinge, happyTwinge, strangeTwinge)
      trickKnee.feltSomething should be (Seq(peculiarMessage, strangeMessage))
    }

    it("acts up but says nothing when the Knee feels nothing") {
      val blueprint = dirtyMockBlueprint
      blueprint.mockToReturnUnfeelingKnee

      Knee.actUp(blueprint, new java.io.PrintStream(testStream))

      testStream.toString should be ('empty)
    }

    it("acts up and says something when the Knee feels broken (i.e. an exception)") {
      val blueprint = dirtyMockBlueprint
      blueprint.mockToReturnBrokenKnee

      Knee.actUp(blueprint, new java.io.PrintStream(testStream))

      testStream.toString should include (exceptionMessage)
      testStream.toString should include (thisTestNameAsPartOfStackTraceOutput)
    }

    it("acts up and says something when the Knee feels something") {
      val blueprint = dirtyMockBlueprint
      blueprint.mockToReturnFeelingKnee

      Knee.actUp(blueprint, new java.io.PrintStream(testStream))

      testStream.toString should include (strangeMessage)
      testStream.toString should include (peculiarMessage)
    }    
  }

  val happyTwinge = new Twinge { def feltSomething = None }
  val noTwinge = new Twinge { def feltSomething = None }
  val strangeMessage = "I felt something strange"
  val strangeTwinge = new Twinge { def feltSomething = Some(strangeMessage) }
  val peculiarMessage = "I felt something peculiar"
  val peculiarTwinge = new Twinge { def feltSomething = Some(peculiarMessage) }

  val testStream = new java.io.ByteArrayOutputStream
  val exceptionMessage = "OH NO!"
  val thisTestNameAsPartOfStackTraceOutput = "MainTest"

  // If this style of mocking happens once or twice more, will import a proper mocking library...
  // No internet connection makes me desperate, so not pulling in a lib yet.
  def dirtyMockBlueprint() = {
    new Blueprint(null) {
      var knee: Try[Knee] = null
      override def constructKnee(): Try[Knee] = knee

      def mockToReturnUnfeelingKnee() {
        knee = Try(new Knee(happyTwinge, noTwinge))
      }
      def mockToReturnBrokenKnee() {
        knee = Try(throw new java.lang.IllegalArgumentException(exceptionMessage))
      }
      def mockToReturnFeelingKnee() {
        knee = Try(new Knee(strangeTwinge, peculiarTwinge))
      }
    }
  }
}