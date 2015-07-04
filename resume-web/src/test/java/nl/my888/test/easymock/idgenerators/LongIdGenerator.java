package nl.my888.test.easymock.idgenerators;

import nl.my888.test.easymock.IdGenerator;

class LongIdGenerator implements IdGenerator<Long> {

    static final LongIdGenerator INSTANCE = new LongIdGenerator();

    private long lastId = 1L;

    @Override
    public Long newId() {
        return ++lastId;
    }

    @Override
    public Long lastId() {
        return lastId;
    }
}
