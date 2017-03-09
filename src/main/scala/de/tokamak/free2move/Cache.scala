package de.tokamak.free2move

import akka.actor.Actor

class Cache extends Actor {

  var content: Seq[Compressed[String]] = Nil

  override def receive = {
    case newContent(seq) =>
      content = seq
    case "content" => println(this.toString)
    case message => println(s"Unknown message: $message")
  }

  override def toString: String = s"Cache(${content.toString()})"

}

case class newContent(seq: Seq[Compressed[String]])
