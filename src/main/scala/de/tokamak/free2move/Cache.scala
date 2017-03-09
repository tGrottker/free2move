package de.tokamak.free2move

import akka.actor.Actor

class Cache extends Actor {

  var content: Seq[String] = Nil

  override def receive = {
    case newContent(seq) =>
      content = RunLengthEncoder.decompress(seq)
    case index: Int =>
      if (index >= content.size) {
        sender ! None
      } else {
        sender ! Some(content(index))
      }
    case message => println(s"Unknown message: $message")
  }

  override def toString: String = s"Cache(${content.toString()})"

}

case class newContent(seq: Seq[Compressed[String]])
