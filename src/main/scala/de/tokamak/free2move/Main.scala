package de.tokamak.free2move

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import com.twitter.finagle.Http
import com.twitter.util.Await

import scala.concurrent.duration._
import com.typesafe.config.ConfigFactory

object Main extends App {
  implicit val timeout = Timeout(1000 millisecond)
  val conf = ConfigFactory.load()
  val interval = conf.getInt("interval")

  val system = ActorSystem("FetchAndCache")
  val cache = system.actorOf(Props[Cache], name = "cache")
  val fetcher = system.actorOf(Props(new Fetcher(cache)), name = "fetcher")

  import system.dispatcher
  system.scheduler.schedule(0 milliseconds, interval seconds, fetcher, "fetch")

  val service = new CachingService(cache, timeout)
  Await.ready(Http.serve(":8080", service))
}
