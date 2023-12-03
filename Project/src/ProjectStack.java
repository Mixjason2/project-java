public class ProjectStack implements StatementStack, IterationStack , IntegerStack , OperationStack , InfixToPostfixStack{
    private int total;
    private Object stack[];
    int top;
    public ProjectStack() {
        this.MyStack();
    }
    public void MyStack() {
        top = -1;
        total = 100;
        stack = new Object[total];
    }
    public void push(Object e) throws Exception {
        if (full()) throw new Exception("Stack is full.");
        stack[++top] = e;
    }
    public Object pop() throws Exception {
        if (empty()) throw new Exception("Stack is empty.");
        Object result = this.top();
        stack[top--] = null;
        return result;
    }
    public Object top() throws Exception {
        if (empty()) throw new Exception("Stack is empty.");
        return stack[top];
    }
    public boolean empty() {
        return top == -1;
    }
    public boolean full() {
        return top + 1 == total;
    }
    public void clear() {
        top = -1;
    }
}