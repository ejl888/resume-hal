package nl.my888.test.easymock;

import org.easymock.EasyMock;
import org.easymock.IAnswer;

/**
 * Echo the argument, apply and capture the result.
 *
 * Not thread safe. Recreate before each answer.
 */
public class EchoArgumentAnswer<T> implements IAnswer<T> {

    private final int argIndex;

    private T answered;

    public EchoArgumentAnswer() {
        this(0);
    }

    public EchoArgumentAnswer(int argIndex) {
        this.argIndex = argIndex;
    }

    @Override
    public T answer() throws Throwable {
        final Object[] args = EasyMock.getCurrentArguments();
        if (args.length <= argIndex) {
            throw new IllegalArgumentException("There is no argument at " + argIndex);
        }
        answered = apply((T) args[argIndex]);
        return answered;
    }

    protected T apply(T result) {
        // hook
        return result;
    }

    public T answered() {
        return answered;
    }
}
