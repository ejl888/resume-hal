package nl.my888.test.easymock;

import org.easymock.EasyMock;
import org.easymock.IAnswer;

/**
 * Created by ejl on 15/06/15.
 */
public class EchoArgumentAnswer<T> implements IAnswer<T> {

    private final int argIndex;

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
        return (T) args[argIndex];
    }


}
