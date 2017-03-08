package de.tokamak.free2move

import scala.annotation.tailrec

object RunLengthEncoding extends Compressor {
  override def compress[A]: (Seq[A]) => Seq[Compressed[A]] = (seq) => {
    @tailrec
    def innerCompress(input: List[A], result: List[Compressed[A]] = Nil): List[Compressed[A]] = input match {
      case Nil => result
      case head :: Nil => Single(head) +: result
      case candidate :: head :: tail if !candidate.equals(head) => innerCompress(head :: tail, Single(candidate) +: result)
      case head :: tail => {
        val element = Repeat(tail.takeWhile(_ == head).length + 1, head)
        innerCompress(tail.dropWhile(_ == head), element +: result)
      }
    }
    innerCompress(seq.toList).reverse
  }

  override def decompress[A]: (Seq[Compressed[A]]) => Seq[A] = ???
}
