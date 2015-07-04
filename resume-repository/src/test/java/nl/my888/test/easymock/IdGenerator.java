package nl.my888.test.easymock;

/**
 * Created by ejl on 04/07/15.
 */
public interface IdGenerator<T> {

    T newId();

    T lastId();
}
