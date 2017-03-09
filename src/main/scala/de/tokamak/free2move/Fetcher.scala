package de.tokamak.free2move

import akka.actor.{Actor, ActorRef}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}

class Fetcher(private val cache: ActorRef) extends Actor {
    val client: Service[http.Request, http.Response] = Http.newService("challenge.carjump.net:80")
    val request = http.Request(http.Method.Get, "/A")
    request.host = "challenge.carjump.net"
  override def receive = {
    case "fetch" => {
      val response: Future[http.Response] = client(request)
        Await.result(response.onSuccess { rep: http.Response =>
          cache ! newContent(RunLengthEncoder.compress(rep.getContentString().split("\n")))
        })
    }
    case message => println(s"Unknown message: ${message.toString}")
  }
}
