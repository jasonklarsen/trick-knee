package io.larsen.trickknee

import java.net.URL
import scala.util.Success
import scala.util.Try
import scala.util.parsing.json._
import scalax.io.JavaConverters._

class TravisCI(jsonGen: => Map[String, Any]) extends Twinge {
  def feltSomething = {
    jsonGen.get("last_build_status") match {
      case Some(0) => None
      case Some(s:Double) => Some(s"My Travis says that the last_build_status was '${s.toInt}' -- ain't lookin' good")
      case _              => Some("My Travis ain't workin' right")
    }
  }
}

object TravisCI {
  def parseIt(): Map[String, Any] = {
    parseIt("jasonklarsen", "trick-knee", { case ex:Throwable => ex.printStackTrace })
  }

  def parseIt(name: String, repo: String, processException: PartialFunction[Throwable, Any]): Map[String, Any] = {
    val jsonStringMaybe = Try(new URL(s"https://api.travis-ci.org/repositories/$name/$repo.json").asInput.string(scalax.io.Codec.UTF8))
    jsonStringMaybe.recover(processException)
    val jsonString = jsonStringMaybe.getOrElse("")
    val parsed = JSON.parseFull(jsonString)
    parsed match {
      case Some(p: Map[String, Any]) => p
      case _                         => Map()
    }
  }
}