package io.larsen.trickknee

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.parsing.json._

class TravisCITest extends FunSpec with ShouldMatchers {

  describe("A Travis") {
    it("will twinge when the last build is broken") {
      val travis = new TravisCI( Map("last_build_status" -> 1.0) )
      travis.feltSomething should be ('defined)
      travis.feltSomething.get should include ("last_build_status was '1'")
    }

    it("will not twinge when the last build is successful") {
      val travis = new TravisCI( Map("last_build_status" -> 0.0) )
      travis.feltSomething should be (None)      
    }

    // If the project were larger, I would pull these two into a "integration test suite" of some sort....
    it("will parse JSON from the Travis CI API") {
      val travisParsed = TravisCI.parseIt("jasonklarsen", "trick-knee")
      travisParsed.contains("last_build_status") should be (true)
    }
    it("will not blow up when something is wrong with Travis CI API") {
      val travisParsed = TravisCI.parseIt("nobody", "here", {case _ => })
      travisParsed.contains("last_build_status") should be (false)      
    }
  }
}