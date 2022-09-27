package common

import Result.*
import cats.data.StateT

object Operation:
  type Operation[I, O] = StateT[Result, I, O]

  given OperationFromFunction [I, O] : Conversion[I => Result[(I, O)], Operation[I, O]] with
        def apply(fn: I => Result[(I, O)]): Operation[I, O] =
          StateT[Result, I, O] { i => fn(i) }
  end OperationFromFunction
end Operation