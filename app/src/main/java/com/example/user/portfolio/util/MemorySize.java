package com.example.user.portfolio.util;

public enum MemorySize {

    BYTE(1),

    KILOBYTE(1024),

    MEGABYTE(1024 * 1024);

    private int bytes;

    private MemorySize(int bytes) {
        this.bytes = bytes;
    }

    public int bytes() {
        return bytes(1);
    }

    public int bytes(int count) {
        return bytes * count;
    }
}
