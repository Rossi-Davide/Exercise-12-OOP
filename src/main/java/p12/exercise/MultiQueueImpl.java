package p12.exercise;

import java.util.HashMap;
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

    private Set<Q> getQueueIds(){
        return queues.keySet();
    }

    private Queue<T> getQueue(final Q queue){
        if(!queues.containsKey(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }

        return queues.get(queue);
    }

    @Override
    public Set<Q> availableQueues() {
        return getQueueIds();
    }

    @Override
    public void openNewQueue(final Q queue) {
        if(queues.containsKey(queue)){
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dequeueOneFromAllQueues'");
    }

    @Override
    public Set<T> allEnqueuedElements() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allEnqueuedElements'");
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dequeueAllFromQueue'");
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeQueueAndReallocate'");
    }

}
