import org.scalatest.flatspec.AnyFlatSpec

class HeapSpec extends AnyFlatSpec {
  
    "A heap" should "should alloc" in {
        val heap = Heap(256)
        val size = 20
        val expected = List(
            Block(0, size, false), Block(size, 256, true)
        )
        Heap.alloc(size).run(heap) match
            case Failure(_) => fail("Heap alloc Failed")
            case Success((heap, address)) =>
                assert(address == 0)
                assert(heap.blocks.equals(expected), heap)
        
    }
}
