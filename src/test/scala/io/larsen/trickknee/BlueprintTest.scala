package io.larsen.trickknee

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.Failure

class BlueprintTest extends FunSpec with ShouldMatchers {

  // Being pedantic, this whole set of tests is an "integration test" as it integrates 
  // with Typesafe's Config...
  describe("A Blueprint") {
    it("will construct an Knee that always feel nothing when given an empty config") {
      val emptyConfig = ConfigFactory.empty
      val blueprint = new Blueprint(emptyConfig)

      val maybeKnee = blueprint.constructKnee
      maybeKnee should be ('success)

      val knee = maybeKnee.get
      knee.feltSomething should be (Nil)
    }

    describe("will fail sensibly if it tries to construct a Knee that is misconfigured") {

      it("by specifying an unknown Twinge type") {
        val badConfig = ConfigFactory.parseString(unknownType)
        val blueprint = new Blueprint(badConfig)

        val maybeKnee = blueprint.constructKnee
        maybeKnee should be ('failure)

        val exception = maybeKnee.failed.get
        exception.getMessage should include ("unknown type")
        exception.getMessage should include ("funnyBone")
      }

      it("by misconfiguring Travis CI") {
        val badConfig = ConfigFactory.parseString(badTravis)
        val blueprint = new Blueprint(badConfig)

        val maybeKnee = blueprint.constructKnee
        maybeKnee should be ('failure)

        val exception = maybeKnee.failed.get
        exception.getMessage should include ("Misconfigured Travis CI")
        exception.getMessage should include ("username")
      }
    }
  }

  val unknownType = """
twinges: [{
  type: "funnyBone",
  foo: "bar"
}]
"""

  val badTravis = """
twinges: [{
  type: "travis-ci",
  repository: "repo"
  not_username: "username"
}]
"""
}