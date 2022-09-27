package common

import cats.Monad

object result:
    sealed trait Result[+A]:
        def isSuccess: Boolean = this.isInstanceOf[Success[_]]
        def isFailure: Boolean = this.isInstanceOf[Failure]
        def withFilter(p: A => Boolean): Result[A] = {
            this match
                case Success(a) if p(a) => Success(a)
                case Success(a) if !p(a) => Failure("")
                case _ => this
        }
    final case class Success[A](value: A) extends Result[A]
    final case class Failure(debug: String) extends Result[Nothing]

    given ResultMonad : Monad[Result] with
        override def flatMap[A, B](fa: Result[A])(f: A => Result[B]): Result[B] =
            fa match 
                case Success(a) => f(a)
                case Failure(d) => Failure(d)
        end flatMap
        
        override def pure[A](a: A): Result[A] = Success(a)

        @annotation.tailrec
        def tailRecM[A, B](init: A)(fn: A => Result[Either[A, B]]): Result[B] =
        fn(init) match 
            case Failure(m) => Failure(m)
            case Success(Right(b)) => Success(b)
            case Success(Left(a)) => tailRecM(a)(fn)
        end tailRecM
    end ResultMonad

    extension [A] (option: Option[A])
        def toResult(debug: String): Result[A] = 
            option match
                case None => Failure(debug)
                case Some(value) => Success(value)
            
end result
