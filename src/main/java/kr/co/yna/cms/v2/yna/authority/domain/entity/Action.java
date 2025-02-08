package kr.co.yna.cms.v2.yna.authority.domain.entity;

public enum Action {
    CREATE, READ, UPDATE, DELETE;

    @Override
    public String toString() {
        return this.name();
    }
}
