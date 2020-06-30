
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
}
