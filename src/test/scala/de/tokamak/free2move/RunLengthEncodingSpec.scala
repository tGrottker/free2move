package de.tokamak.free2move

import org.specs2._

class RunLengthEncodingSpec extends Specification { def is = s2"""
  A RunLengthEncoder should
    reduce a collection of the same items to one entry                  $encodeSame
    encode a mixed number of consecutive elements accordingly           $encodeMixed
    inflate compressed collection of same elements to original size     $decodeSame
    inflate compressed collection of mixed elements to original size    $decodeMixed
  """

  private val aStream: Stream[String] = "a" #:: aStream
  private val bStream: Stream[String] = "b" #:: bStream
  private val sameElements = aStream.take(20)
  private val encodedSame = Seq(Repeat(20, "a"))
  private val mixedElements = sameElements ++ "b" #:: sameElements ++ bStream.take(10)
  private val encodedMixed = Seq(Repeat(20, "a"), Single("b"), Repeat(20, "a"), Repeat(10, "b"))

  def encodeSame = RunLengthEncoder.compress(sameElements) must_== encodedSame

  def encodeMixed = RunLengthEncoder.compress(mixedElements) must_== encodedMixed

  def decodeSame = RunLengthEncoder.decompress(encodedSame) must_== sameElements

  def decodeMixed = RunLengthEncoder.decompress(encodedMixed) must_== mixedElements

}
