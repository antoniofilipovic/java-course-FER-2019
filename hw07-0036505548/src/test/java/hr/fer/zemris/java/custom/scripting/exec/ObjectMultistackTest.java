package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	private ObjectMultistack stack;

    @BeforeEach
    public void setUp(){
        this.stack = new ObjectMultistack();

    }

    @Test
    public void pushTest() {
        stack.push("Ante",new ValueWrapper(4));
        stack.push("Ivo",new ValueWrapper(5));
        stack.push("Ante",new ValueWrapper(7));

        assertEquals(7, stack.peek("Ante").getValue());
        assertEquals(5, stack.peek("Ivo").getValue());
    }

    @Test
    public void popTest() {
    	stack.push("Ante",new ValueWrapper(4));
        stack.push("Ivo",new ValueWrapper(5));
        stack.push("Ante",new ValueWrapper(7));

        assertEquals(7, stack.pop("Ante").getValue());
        assertEquals(5, stack.pop("Ivo").getValue());
    }

    @Test
    public void peekTest() {
    	 stack.push("Ante",new ValueWrapper(4));
         stack.push("Ivo",new ValueWrapper(5));
         stack.push("Ante",new ValueWrapper(7));

         assertEquals(7, stack.peek("Ante").getValue());
         assertEquals(5, stack.peek("Ivo").getValue());
         assertEquals(7, stack.peek("Ante").getValue());
         stack.pop("Ante");
         assertEquals(4, stack.peek("Ante").getValue());
         
    }
    @Test
	void peekThrowsTest() {
		assertThrows(EmptyStackException.class,()-> stack.peek("Ante"));
		stack.push("Ante", new ValueWrapper(2));
		stack.pop("Ante");
		assertThrows(EmptyStackException.class,()-> stack.peek("Ante"));
	}
    @Test
    public void isEmptyTest() {
        assertTrue(stack.isEmpty("Ante"));
        assertTrue(stack.isEmpty("Ivo"));
        stack.push("Ante",new ValueWrapper(4));
        stack.push("Ivo",new ValueWrapper(5));
        stack.push("Ante",new ValueWrapper(7));
        assertFalse(stack.isEmpty("Ante"));
        assertFalse(stack.isEmpty("Ivo"));
        assertTrue(stack.isEmpty("Pero"));
        stack.pop("Ante");
        assertFalse(stack.isEmpty("Ante"));
        stack.pop("Ante");
        assertTrue(stack.isEmpty("Ante"));
    }

}
