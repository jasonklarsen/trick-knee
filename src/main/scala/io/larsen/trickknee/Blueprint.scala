package io.larsen.trickknee

import com.typesafe.config.{Config, ConfigException, ConfigFactory}
import scala.collection.JavaConversions._
import scala.util.Try

class Blueprint(config: Config) {

  def constructKnee(): Try[Knee] = {
    Try(new Knee(constructTwinges:_*))
  }

  private def constructTwinges: Array[Twinge] = 
    twingeConfigs.map(tc => {
      tc.getString("type") match {
        case "travis-ci" => travisTwinge(tc)
        case unknownType => 
          throw new java.lang.IllegalArgumentException("Twinge misconfigured: Specified an unknown type of :" + unknownType)
      }
    }).toArray

  private def travisTwinge(tc: Config) =
    Try(TravisCI(tc.getString("repository"), tc.getString("username"))).recover({ 
      case ex => throw new ConfigException.Generic("Misconfigured Travis CI: " + ex.getMessage, ex)
    }).get

  private def twingeConfigs: Iterable[Config] =
    if (config.hasPath("twinges"))
      iterableAsScalaIterable(config.getConfigList("twinges"))
    else
      List()
}
