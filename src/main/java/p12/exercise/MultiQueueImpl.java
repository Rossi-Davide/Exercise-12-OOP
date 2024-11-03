package p12.exercise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{

    private final Map<Q,Queue<T>> queues;

    public MultiQueueImpl (){
        this.queues = new HashMap<>();
    }

    private Set<Q> getQueues(){
        return queues.keySet();
    }

    private boolean queueExists(final Q queue){
        return queues.containsKey(queue);
    }

    private void checkAvailability(final Q queue){
        if(!queueExists(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }
    }

    private Queue<T> getQueue(final Q queue){
        checkAvailability(queue);
        return queues.get(queue);
    }

    private void closeQueue(Q queue){
        checkAvailability(queue);
        queues.remove(queue);
    }

    @Override
    public Set<Q> availableQueues() {
        return getQueues();
    }

    @Override
    public void openNewQueue(final Q queue) {
        if(queueExists(queue)){
            throw new IllegalArgumentException("Queue already open");
        }

        final Queue<T> elementsQueue = new LinkedList<>();
        queues.put(queue, elementsQueue);
    }

    @Override
    public boolean isQueueEmpty(final Q queue) {
        return getQueue(queue).isEmpty();
    }

    @Override
    public void enqueue(final T elem,final  Q queue) {
        getQueue(queue).add(elem);
    }

    @Override
    public T dequeue(final Q queue) {
        if(isQueueEmpty(queue)){
            return null;
        }

        return getQueue(queue).remove();
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        Map<Q, T> dequeuedElements = new HashMap<>();

        for(Q queue : getQueues()){
            dequeuedElements.put(queue,dequeue(queue));
        }

        return dequeuedElements;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> allElements = new HashSet<>();

        for(Q queue : getQueues()){

            allElements.addAll(getQueue(queue));
        }

        return allElements;
    }

    @Override
    public List<T> dequeueAllFromQueue(final Q queue) {
        List<T> elements = new LinkedList<>();

        while (!isQueueEmpty(queue)) {
            elements.add(dequeue(queue));
        }

        return elements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        for(Q destination : getQueues()) {
            if(!destination.equals(queue)){
                List<T> elements = dequeueAllFromQueue(queue);
                getQueue(destination).addAll(elements);
                closeQueue(queue);
                return;
            }
        }

        throw new IllegalStateException("There's no other queue available to move the elements in");

    }

}
