// package heap

// import cats.implicits._

// case class Heap  (
//     size: Int,
//     blocks: List[Block],
//     data: Array[Int]
// )

// object Heap {
//     def apply(size: Int): Heap = 
//         Heap (
//             size,  
//             List(Block(0, size, true)), 
//             Array.fill(size)(0)
//         )
//     end apply


//     def alloc(size: Int)(select: BlockSelectionStrategy): Operation[Heap, Int] =
//         (heap: Heap) => heap match 
//             case _ if size > heap.size =>
//                 Failure("Alloc Out of Memory")
//             case _ if size == 0 => 
//                 Failure("Alloc Undefined Size")
//             case Heap(s, bs, d) => 
//                 bs.zipWithIndex
//                     .map { case (b, i) => Block.split(size).run(b) -> i }
//                     .find(r => r._1.isSuccess)
//                     .map { case (r, i) => r.map {
//                         case (b1, b2) => 
//                             val bl2 = if (b2.isDefined) then List(b2.get) else Nil
//                             val blks = List(b1.copy(free = false)) ::: bl2
//                             bs.patch(i, blks, 1) -> b1.start
//                     }} match
//                         case None => Failure("Alloc Failed, Couldn't find appropriate block")
//                         case Some(r) => r match 
//                             case Failure(debug) => Failure(debug)
//                             case Success((blocks, start)) => 
//                                 Success(Heap(s, blocks, d) -> start)
//     end alloc

                
// }