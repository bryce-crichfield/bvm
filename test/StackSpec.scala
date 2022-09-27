import org.scalatest.flatspec.AnyFlatSpec

class StackSpec extends AnyFlatSpec {
  
    "A Stack" should "fail if popping from the empty stack" in {
        val result = Stack.pop().run(Stack())
        assert(result.isFailure)
    }

    "A Stack" should "pop values in LIFO order" in {
        val stack = Stack(data = List(3, 2, 1), capacity = 3)
        Stack.pop().run(stack) match
            case Failure(debug) => fail("Stack pop failed")
            case Success(stk, num) =>
                assert(num == 3)
                assert(stk.equals(Stack(data = List(2, 1), 
                    capacity = 2)))
    }

    "A Stack" should "fail if pushing onto the full stack" in {
        val stack = Stack(capacity = 256)
        val result = Stack.push(1).run(stack)
        assert(result.isFailure)
    }

    "A Stack" should "push values in LIFO order" in {
        val stack = Stack(data = List(3, 2, 1))
        Stack.push(4).run(stack) match
            case Failure(_) => fail("Stack push failed")
            case Success(stk, _) =>
                assert(stk.data.equals(List(4,3,2,1)))
    }
}
