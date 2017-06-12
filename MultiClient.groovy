import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.Future

import java.util.Random

import groovy.transform.Field

@Field def cantHilos
@Field def tiempo = 5 //segundos

void multiClient() {
        
    def myClosure = {num -> 
            while(true) {
                def timenow = epoch()
                def speed = Math.abs(new Random().nextInt() % 80)
                println "Client DCS [${num}]: time=${timenow} speed=${speed}"
                sleep(tiempo*1000)
            }
        }
    println "Cantidad Hilos : ${cantHilos}"
    def threadPool = Executors.newFixedThreadPool(cantHilos)
     try {
      List<Future> futures = (1..cantHilos).collect{num->
        threadPool.submit({->
        myClosure num } as Callable);
      }
      // recommended to use following statement to ensure the execution of all tasks.
      futures.each{it.get()}
    }finally {
      threadPool.shutdown()
    }
}

long epoch() {
    now = Calendar.instance
    //println 'now is a ' + now.class.name
    date = now.time
    epoch1 = date.time
    return epoch1/1000
}

void updateVariables(args) {
    
    cantHilos = (args.size() > 0)?Integer.parseInt(args[0]):10
    
}

println this.args

updateVariables(args)

multiClient()