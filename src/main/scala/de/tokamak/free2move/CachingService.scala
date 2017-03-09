package de.tokamak.free2move

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.twitter.finagle.Service
import com.twitter.finagle.http.Method.Get
import com.twitter.finagle.http.path.{/, Path, Root}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.Future

import scala.concurrent.Await
import scala.util.control.NonFatal

class CachingService(private val cache: ActorRef, implicit val timeout: Timeout) extends Service[Request, Response] {

  override def apply(request: Request): Future[Response] = request.method -> Path(request.path) match {
    case (Get, Root / index) =>
      val position = try {
        index.toInt
      } catch {
        case NonFatal(_) =>
          -1
      }
      if (position < 0) {
        val response = Response(Status.InternalServerError)
        response.contentString = s"Was not able to access element at position $index.\n" +
          s"Make sure to use an index greater or equal 0."
        Future.value(response)
      } else {
        val result = Await.result(cache ? position, timeout.duration)
        val res = result.asInstanceOf[Option[String]] match {
          case None =>
            val response = Response(Status.InternalServerError)
            response.contentString = s"Was not able to find an element at position $position. The index was out of bounds."
            response
          case Some(s: String) =>
            val response = Response(Status.Ok)
            response.contentString = s    // one might append a newline character for better output with curl
            response
        }
        Future.value(res)
      }
  }

}
