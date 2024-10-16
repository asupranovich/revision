package com.asupranovich.revision.sandbox;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronousQueue <T> {

    private T parcel;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition wasOffered = lock.newCondition();
    private final Condition wasTaken = lock.newCondition();

    public void offer(T newParcel) {
        try {
            lock.lock();
            while (parcel != null) {
                wasTaken.awaitUninterruptibly();
            }
            parcel = newParcel;
            wasOffered.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        try {
            lock.lock();
            while (parcel == null) {
                wasOffered.awaitUninterruptibly();
            }
            T toReturn = parcel;
            parcel = null;
            wasTaken.signalAll();
            return toReturn;
        } finally {
            lock.unlock();
        }
    }

}
