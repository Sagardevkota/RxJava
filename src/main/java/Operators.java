import io.reactivex.Observable;

public class Operators {

    //filter operator
    Observable<String> stringObservable=Observable.just("alpha","omega","zeta","beta");
        stringObservable.filter(e->e.length()>4)
                .subscribe(System.out::println);

    //skip first elements
        stringObservable.skip(2)
                .subscribe(System.out::println);

    //first
        stringObservable.first("")
                .subscribe(System.out::println);

    //take while
    //it doesnt go through all emission unlike filter
        stringObservable.takeWhile(e->e.length()>4)
                .subscribe(System.out::println);

    //skip while skips the elements mathing the predicate and stops when it encounters non predicate
        stringObservable.skipWhile(e->e.length()>4)
                .subscribe(System.out::println);

    //distinct returns only each unique value(returns only alpha and other values if there are 2 alphas)
        stringObservable.distinct()
             .subscribe(System.out::println);

    //element at
        stringObservable.elementAt(2)
             .subscribe(System.out::println);

    //Transformative Operators
    //map operator
        Observable<Long> obsrc= Observable.interval(1,TimeUnit.SECONDS);
        obsrc.map(e->e+1).subscribe(System.out::println);
        Thread.sleep(5000);

    //cast operator
        stringObservable.cast(Object.class)
                .subscribe(System.out::println);

    //start with operator
        stringObservable.startWith("Emissions")
                .subscribe(System.out::println);


    //defaultifEmpty
        Observable.empty().defaultIfEmpty("Item").subscribe(System.out::println);
//
    //switchifEmpty switch to another observable if empty
        Observable.empty().switchIfEmpty(stringObservable)
                .subscribe(System.out::println);


    //delay is used when certain observables fire emission faster than observer can handle
    Observable<Integer> sorce=Observable.just(1,2,3,4,5,6,7,8,343,32);
        sorce.delay(5,TimeUnit.SECONDS)
                .subscribe(System.out::println);
        Thread.sleep(5000);

    //sorted operator
        sorce.sorted(Comparator.reverseOrder())
                .subscribe(System.out::println);

    //repeat number of times the observables
        sorce.repeat(2)
                .subscribe(System.out::println);

    //scan it aggregates all the elements
        sorce.scan((total,next)->total+next)
                .subscribe(System.out::println);

    //reduce operator
        Observable.just("a","","c","d")
                .reduce((a,b)->a+(b.equals("")?"":","+b))
                .subscribe(System.out::println);

    //"all" reducing operator returns true if all verifies predicate if not then false
        Observable.just("a","c","d")
              .all(e->e.length()==1)
               .subscribe(System.out::println);

    //any
        Observable.just("a","c","d")
                .any(e->e.length()==2)
                .subscribe(System.out::println);

    //count
        Observable.just("a","c","d")
               .count()
               .subscribe(System.out::println);

    //contains returns true if observable contains the object
        Observable.just("a","c","d")
              .contains("a")
               .subscribe(System.out::println);


    //to list and to sorted list
        Observable.just("a","c","d","b")
             .toSortedList()
               .subscribe(System.out::println);

    //map or multimap key and value pair
        Observable.just("a","c","d","b")
                .toMap(String::length)
                .subscribe(System.out::println);

    //multimap
        Observable.just("apple","cat","dog","ball")
                .toMultimap(e->e.charAt(0),String::length,HashMap::new)
                .subscribe(System.out::println);


    //collect
        Observable.just("apple","cat","dog","ball")
                .collect(HashSet::new,HashSet::add)
                .subscribe(System.out::println);


    //error operator onerrorItemReturn and onErrorResumeNext for error ocurrence
    //in following case 0 causes error so after that 5,6,7 will be used as observable

        Observable.just(1,2,3,4,5,0,8,2)
                .map(e->5/e).onErrorResumeNext(Observable.just(5,6,7))
                .subscribe(System.out::println);

    //retry operator
    //it will throw error as divided by zero is not handled like above
        Observable.just(1,2,3,4,5,0,8,2)
                .map(e->5/e)
                .retry(2)
               .subscribe(System.out::println);

    //action operators(doOnNext,doOnComplete,doOnError it is used before on next,completer or error occurs
        Observable.just(1,2,3,4,5,0,8,2)
                .doOnNext(integer -> System.out.println("element coming "+integer))
                .doOnComplete(()->System.out.println("You are almost there"))
                .retry(2)
                .subscribe(System.out::println);
}
