package vm;

import java.util.Stack;


public class CallStack {
	Stack<Frame> stack;
	
	public CallStack ()
	{
	  this.stack = new Stack<Frame> ();
	}
	
	public void push (Frame f)
	{
	  this.stack.push(f);
	  return;
	}
	
	public void pop ()
	{
	  this.stack.pop();
	}
	
	public Frame peek ()
	{
	  return this.stack.peek();
	}
}
