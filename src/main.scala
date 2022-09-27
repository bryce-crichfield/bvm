import common.Operation.{*, given}
import common.Result.{*, given}

import cats.data.StateT
import cats.implicits._


def pushList(list: List[Byte]): Operation[Stack, Unit] = 
    list.map(obj => Stack.push(obj))    
        .sequence
        .map(_ => ())
end pushList

object Main extends App
{
    println(
        pushList(List(4,3,2,1)).run(Stack())
    )
    // val heap = Heap (
    //     size = 256, 
    //     blocks = List(
    //         Block(0, 25, true)
    //     ),
    //     data = Array.fill(256)(0)
    // )
    // val size = 24
    // // val expected = List(
    // //         Block(0, 25, false), 
    // //         Block(25, 50, true),
    // //         Block(50, 100, false),
    // //         Block(100, 148, false),
    // //         Block(148, 150, true),
    // //         Block(150, 256, false)
    // //     )
    // Heap.alloc(size).run(heap) match
    //     case Failure(d) => println(d)
    //     case Success(t) =>
    //         println(t._1.blocks)
    //         // assert(t._1.blocks.equals(expected))
}