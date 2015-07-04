package nl.my888.test.easymock;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

/**
 * Created by ejl on 04/07/15.
 */
public class PersistedEchoArgumentAnswer<T, I> extends EchoArgumentAnswer<T> {

    private final IdGenerator<I> idGenerator;

    private final Field field;

    public PersistedEchoArgumentAnswer(Class<T> targetClazz, IdGenerator<I> idGenerator) {
        this("id", targetClazz, idGenerator);
    }

    public PersistedEchoArgumentAnswer(String fieldName, Class<T> targetClazz, IdGenerator<I> idGenerator) {
        this.idGenerator = idGenerator;
        this.field = ReflectionUtils.findField(targetClazz, fieldName);
        ReflectionUtils.makeAccessible(field);
    }

    @Override
    protected T apply(T result) {
        ReflectionUtils.setField(field, result, idGenerator.newId());
        return result;
    }
}
