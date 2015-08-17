import java.util.Scanner;

/**
 * @author joshfram
 *
 */
public class Exercise1 {
	String userInput;
	boolean nested = true;	//begin with assumption that the input is nested

	public Exercise1() {
		System.out.println("Please enter a string to be tested:");
		Scanner scanInput = new Scanner(System.in);
		this.userInput = scanInput.nextLine();
		scanInput.close(); 	//close scanner
	}

	public static void main(String[] args){
		Exercise1 client = new Exercise1();
		Stack stack = client.new Stack(client.userInput);	//new subclass
		client.checkPal(stack); 
		//return result to user, if the stack is empty and nested has
		//remained true, then we tell the user that the stack is nested
		if(client.nested && stack.isEmpty()) {
			System.out.println("Properly Nested");
		}
		else { 
			System.out.println("Not Properly Nested");
		}
	}

	/**
	 * This method takes the empty stack as input parameter. 
	 * It reads the characters until end of the input string. 
	 * If the character is an opening symbol, it pushes it onto the 
	 * stack. If it is a closing symbol, if the stack is empty, it reports an
	 * error. Otherwise, it pops the top character off the stack. If the symbol
	 * popped is not the corresponding opening symbol, then it reports 
	 * an error. At end of the input string, if the stack is not empty, the 
	 * method reports an error. 
	 */
	public void checkPal(Stack stack) {
		for(int i = 0; i < this.userInput.length(); i++) { 
			char currentChar = this.userInput.charAt(i);
			//check if opening symbol
			if(currentChar == '{' || 
					currentChar == '[' || currentChar == '(') {
				stack.push(currentChar);	//push letter onto stack
			}
			//check if stack is empty
			else{	
				if(stack.isEmpty()) {
					nested = false; 
				}
				try {
					char popped = stack.pop();
					//check if popped character matches the current character
					if(popped == '{' && currentChar != '}' ||
							popped == '[' && currentChar != ']' ||
							popped == '(' && currentChar != ')') {
						nested = false;
					}
				} 
				catch(ArrayIndexOutOfBoundsException ae) {
					nested = false;
				}
			}
		}
	}

	public class Stack {
		int topOfStack;
		int stackSize; 		//size of stack array determined by length of input
		char[] charStack;	//stack of characters 

		public Stack(String input) {
			this.stackSize = input.length()*2;
			this.topOfStack = 0;	//stack starts empty
			this.charStack = new char[stackSize];
		}

		public void push (char letter) {	//adds item to the top of the stack
			topOfStack++;
			charStack[topOfStack] =  letter; 
		}

		public char pop() {		//returns and removes topOfStack
			char returnLetter = charStack[topOfStack];
			topOfStack--;
			return returnLetter;
		}	

		public boolean isEmpty() { 	//checks if stack is empty
			if(topOfStack == 0) 
				return true;
			else return false; 				
		}
	}
}

