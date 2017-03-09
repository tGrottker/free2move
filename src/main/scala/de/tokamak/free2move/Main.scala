package de.tokamak.free2move

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import scala.concurrent.duration._
import com.typesafe.config.ConfigFactory

object Main extends App {
  implicit val timeout = Timeout(500 millisecond)
  val conf = ConfigFactory.load()
  val interval = conf.getInt("interval")

  val system = ActorSystem("FetchAndCache")
  val cache = system.actorOf(Props[Cache], name = "cache")
  val fetcher = system.actorOf(Props(new Fetcher(cache)), name = "fetcher")

  import system.dispatcher
  system.scheduler.schedule(0 milliseconds, interval seconds, fetcher, "fetch")
  system.scheduler.schedule(3 seconds, interval seconds, cache, "content")
}
