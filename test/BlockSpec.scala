import org.scalatest.flatspec.AnyFlatSpec

class BlockSpec extends AnyFlatSpec {
  
    "A block's end" should "be exclusive" in {
        val block = Block(0, 100, true)
        assert(block.size == 100)
    }

    "A block" should "split" in {
        val (start, end) = (0, 250)
        val size = 50
        val target1 = Block(start, start + size, true)
        val target2 = Block(start + size, end, true)

        val result = Block.split(size).run(Block(start, end, true))
        result match
            case Failure(_) => fail("Block split failed")
            case Success(b1, b2) => 
                assert(b1.equals(target1), b1)
                assert(b2.equals(target2), b2)
    }

    "A block" should "join with another block, without regard for input order" in {
        val (s1, e1) = (0, 50)
        val (s2, e2) = (50, 100)
        Block.join(Block(s1, e1, true)).run(Block(s2, e2, true)) match
            case Failure(_) => fail("Block join failed")
            case Success(block, ()) =>
                assert(block.equals(Block(s1, e2, true)), block)
        Block.join(Block(s2, e2, true)).run(Block(s1, e1, true)) match
            case Failure(_) => fail("Block join failed")
            case Success(block, ()) =>
                assert(block.equals(Block(s1, e2, true)), block)   
    }
}
