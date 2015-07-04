package nl.my888.test.easymock.idgenerators;

import nl.my888.test.easymock.IdGenerator;

/**
 * Created by ejl on 04/07/15.
 */
public final class IdGenerators {

    private  IdGenerators() {
        // util
    }

    public static IdGenerator<Long> longIdGenerator() {
        return LongIdGenerator.INSTANCE;
    }
}
