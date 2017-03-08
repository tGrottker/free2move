package de.tokamak.free2move

import org.specs2._

class RunLengthEncodingSpec extends Specification { def is = s2"""
  A RunLengthEncoder should
    reduce a collection of the same items to one entry            $encodeSame
  """

  private val aStream: Stream[String] = "a" #:: aStream
  private val sameElements = aStream.take(20)

  def encodeSame = {
    val encoded = Seq(Repeat(20, "a"))
    RunLengthEncoding.compress(sameElements) must_== encoded
  }

}
