package com.maxmind.db;

public final class CacheKey<T> {
    private final int offset;
    private final Class<T> cls;
    private final java.lang.reflect.Type type;

    public CacheKey(int offset, Class<T> cls, java.lang.reflect.Type type) {
        this.offset = offset;
        this.cls = cls;
        this.type = type;
    }

    public int getOffset() {
        return this.offset;
    }

    public Class<T> getCls() {
        return this.cls;
    }

    public java.lang.reflect.Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        CacheKey other = (CacheKey) o;

        if (this.offset != other.offset) {
            return false;
        }

        if (this.cls == null) {
            if (other.cls != null) {
                return false;
            }
        } else if (!this.cls.equals(other.cls)) {
            return false;
        }

        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = offset;
        result = 31 * result + (cls == null ? 0 : cls.hashCode());
        result = 31 * result + (type == null ? 0 : type.hashCode());
        return result;
    }
}
