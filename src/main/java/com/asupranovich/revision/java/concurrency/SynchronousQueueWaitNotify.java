package com.asupranovich.revision.java.concurrency;

public class SynchronousQueueWaitNotify<T> {

    private T parcel;

    private final Object consumerLock = new Object();
    private final Object producerLock = new Object();

    public void offer(T newParcel) throws InterruptedException {
        synchronized (producerLock) {
            while (parcel != null) {
                producerLock.wait();
            }
            parcel = newParcel;
            consumerLock.notify();
        }
    }

    public T take() throws InterruptedException {
        synchronized (consumerLock) {
            while (parcel == null) {
                consumerLock.wait();
            }
            T toReturn = parcel;
            parcel = null;
            producerLock.notify();
            return toReturn;
        }
    }
}
