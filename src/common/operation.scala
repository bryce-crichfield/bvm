package common

import common.result.*
import common.result.given

import cats.data.StateT

object operation:
  type Operation[I, O] = StateT[Result, I, O]

  given OperationFromFunction [I, O] : Conversion[I => Result[(I, O)], Operation[I, O]] with
        def apply(fn: I => Result[(I, O)]): Operation[I, O] =
          StateT[Result, I, O] { i => fn(i) }
  end OperationFromFunction
end operation