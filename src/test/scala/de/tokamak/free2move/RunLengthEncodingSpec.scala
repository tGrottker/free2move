package de.tokamak.free2move

import org.specs2._

class RunLengthEncodingSpec extends Specification { def is = s2"""
  A RunLengthEncoder should
    reduce a collection of the same items to one entry            $encodeSame
    encode a mixed number of consecutive elements accordingly     $encodeMixed
  """

  private val aStream: Stream[String] = "a" #:: aStream
  private val bStream: Stream[String] = "b" #:: bStream
  private val sameElements = aStream.take(20)
  private val mixedElements = sameElements ++ "b" #:: sameElements ++ bStream.take(10)

  def encodeSame = {
    val encoded = Seq(Repeat(20, "a"))
    RunLengthEncoding.compress(sameElements) must_== encoded
  }

  def encodeMixed = {
    val encoded = Seq(Repeat(20, "a"), Single("b"), Repeat(20, "a"), Repeat(10, "b"))
    RunLengthEncoding.compress(mixedElements) must_== encoded
  }

}
