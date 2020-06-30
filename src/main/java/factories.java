import io.reactivex.Observer;
import io.reactivex.internal.operators.observable.ObservableError;

import java.util.Observable;
import java.util.concurrent.Future;

public class factories {

    public static  int a=0,b=10;

   // create factory
       Observable<String> source=Observable.create(stringEmitter -> {
         try {
             stringEmitter.onNext("first");
             stringEmitter.onNext("second");
             stringEmitter.onNext("third");
             stringEmitter.onCompleted();
         }
         catch (Exception e){
             stringEmitter.onError(e);
         }

       });

       //just factory
       Observable<String> source2=Observable.just("first","second","third");

        Observer<String> ob=new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("the printing operation is complete");

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);

            }

        };

   // cold observer of just observable
        source2.subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Operation completed"));
        source2.subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Operation completed"));



        List<String> list= Arrays.asList("hello","world");
        Observable<String> listsource= Observable.from(list);

        //converting cold to hot observer
        ConnectableObservable<String> hot=listsource.publish();

        hot.subscribe(e->System.out.println("Observer1 "+e));
        hot.subscribe(e->System.out.println("Observer2 "+e));

        hot.connect();


   // interval factory
   // main thread will die even this thread continues so we have to make main thread sleep
       //after 200ms it will emit each number beginning from zero
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
        try {
            Thread.sleep(5000);
        }
        catch (Exception e){
            e.printStackTrace();
        }

   // range factory
        Observable.range(0,10)
                .subscribe(System.out::println);

   // empty factory
   // it doesnt emit anything
  //  it was created as observable doesnt accept null objects
        Observable.empty();
        //never factory
        //it wont complete oncomplete so it will cause all observer wait forever
        Observable.never();

        //future factory
        Future<String> futurevalue=
                ...;
        Observable.from(futurevalue)
                .map(String::length)
                .subscribe(System.out::println);

    //error factories used to throw error
        Observable.error(new Exception("Crash"))
                .subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Done"));

    //although count is change output value is same so we use defer factory
        int a=0,b=10;
        Observable<Integer> dfSource=Observable.range(a,b);
        dfSource.subscribe(System.out::println);
        b=15;
        dfSource.subscribe(System.out::println);


   // defer factory
        Observable<Integer> dffsource=Observable.defer(()->Observable.range(a,b));
        dffsource.subscribe(System.out::println);
        b=15;
        dffsource.subscribe(System.out::println);

}
