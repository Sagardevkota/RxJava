
public class concurrencyAndSchedulers{
//blocking it blocks all the thread unless onComplete is called
        //but it can be dangerous
        Observable.interval(1,TimeUnit.SECONDS).take(10)
                .blockingSubscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Onserver 1 done"));

        Observable.interval(1200,TimeUnit.MICROSECONDS).take(5)
                .blockingSubscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Onserver 2 done"));


        //schedulers are interface that creates new thread where observers work on
        //it have many threads:computation,IO,new Thread,single
        //io is used for web request and db it queues all the threads
        //single where all observers work in queue in single thread
        //subscribe on is used to define type of schedulers


        //computation thread
        Observable<String> source=Observable.just("apple","white","blue")
                .subscribeOn(Schedulers.computation());
        source.subscribe(s->System.out.println("Observer 1" + s+" on:"+ LocalTime.now()));
        source.subscribe(s->System.out.println("Observer 2" + s+" on:"+ LocalTime.now()));
       source.observeOn(Schedulers.io());
       //we can use custom executor instead of schedulers 
       ExecutorService service= Executors.newFixedThreadPool(10);
        Scheduler scheduler=Schedulers.from(service);
        

        Thread.sleep(3000);
        
        //flowables can handle backpressure(sometimes the observable emits the value that is excessive for observer)
          Flowable.range(0,50000000)
                .doOnNext(s->System.out.println("emission number "+s))
                .subscribeOn(Schedulers.computation())
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
        Thread.sleep(10000);
        
        
        //backpressure strategies
        
          //in create factory since we implement on next on our own...it doesnt have internal backpressure so we have to define strategy ourselves
       //strategies are
        // buffer-it caches the emission that cant be handled right away,
        // drop-drops emission,
        // error-when subscriber cant keeup up with flowable it throws error,
        // latest-it takes last emission and caches unless the subscriber is ready
        //missing-it doesnt implement any backpressure it means we will use backpressure operator ourselves
        Flowable<String> source1=Flowable.create(source->{
            source.onNext("black");
            source.onNext("white");
        }
        ,BackpressureStrategy.ERROR

        );
        
        //back pressure operators
        Flowable<Long> source1=Flowable.interval(1,TimeUnit.SECONDS);
        source1.onBackpressureBuffer(10,()->System.out.println("Overflow"),BackpressureOverflowStrategy.DROP_LATEST);
        source1.onBackpressureLatest();
     
        
}
