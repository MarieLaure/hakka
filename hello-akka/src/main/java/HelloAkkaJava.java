import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Inbox;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class HelloAkkaJava {
    // public static class Greet implements Serializable {}
    // public static class WhoToGreet implements Serializable {
    //     public final String who;
    //     public WhoToGreet(String who) {
    //         this.who = who;
    //     }
    // }
    // public static class Greeting implements Serializable {
    //     public final String message;
    //     public Greeting(String message) {
    //         this.message = message;
    //     }
    // }
    final static ActorSystem system = ActorSystem.create("helloakka");
    final static ActorRef human = system.actorOf(Props.create(Human.class), "human");
    final static ActorRef presenceDetector = system.actorOf(Props.create(PresenceDetector.class), "detector");
    final static ActorRef lamp = system.actorOf(Props.create(Lamp.class), "lamp");
    
    /* Actions (=messages) to tell to the human to enter in the room */
    public static class GoInTheRoom implements Serializable {}
    public static class ExitTheRoom implements Serializable {}
    
    /* Actions (=messages) for a "human" actor */
    public static class EnterTheRoom implements Serializable {}
    public static class LeaveTheRoom implements Serializable {}
    
    /* Actions (=messsages) for a "lamp" actor */ 
    public static class SwitchOn implements Serializable {}
    public static class SwitchOff implements Serializable {}
    

    // // public static class Greeter extends UntypedActor {
    // //     String greeting = "";

    // //     public void onReceive(Object message) {
    //     if (message instanceof WhoToGreet)
    //         greeting = "hello, " + ((WhoToGreet) message).who;

    //     else if (message instanceof Greet)
    //         getSender().tell(new Greeting(greeting), getSelf());

    //     else unhandled(message);
    // }
    // // }
    
    /*---- Create a "human" actor ----*/
    public static class Human extends UntypedActor {
        String name = "Human";
        
        public void onReceive(Object message) {
            if (message instanceof GoInTheRoom){
                System.out.println(name + ": I enter in the room");
                presenceDetector.tell(new EnterTheRoom(), getSelf());
            }
            else if (message instanceof ExitTheRoom){
                System.out.println(name + ": I leave the room");
                presenceDetector.tell(new LeaveTheRoom(), getSelf());
            }
            else unhandled(message);
        }
        
    }
    
    /*---- Create a "presenceDetector" actor ----*/
    public static class PresenceDetector extends UntypedActor {
        String name = "Detector";
        
       public void onReceive(Object message) {
            if (message instanceof EnterTheRoom){
                System.out.println(name + ": Presence detected");
                lamp.tell(new SwitchOn(), getSelf());
            }
                
            else if (message instanceof LeaveTheRoom){
                System.out.println(name + ": Unpresence detected");
                lamp.tell(new SwitchOff(), getSelf());
            }
               
            else unhandled(message);
        }
       
    }
    
    /*---- Create a "lamp" actor ----*/
    public static class Lamp extends UntypedActor {
        String name = "Lamp";
        
        public void onReceive(Object message) {
            if (message instanceof SwitchOn){
                System.out.println(name + ": I am switched on!");
            }
            else if (message instanceof SwitchOff){
                System.out.println(name + ": I am switched off!");
            }
            else unhandled(message);
        }
        
    }

    public static void main(String[] args) {
        // // Create the 'helloakka' actor system
        // final ActorSystem system = ActorSystem.create("helloakka");

        // // // Create the 'greeter' actor
        // // final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");
        
        // //Create the 'human' actor
        // final ActorRef human = system.actorOf(Props.create(Human.class), "human");
        
        // //Create the 'presenceDetector' actor
        // final ActorRef presenceDetector = system.actorOf(Props.create(PresenceDetector.class), "detector");
        
        // //Create the 'lamp' actor
        // final ActorRef lamp = system.actorOf(Props.create(Lamp.class), "lamp");
        
        
        /*--- Scénario 2 ---*/
        
        //The human enters in the room after 10 seconds
        system.scheduler().scheduleOnce(Duration.create(10, TimeUnit.SECONDS), human, new GoInTheRoom(), system.dispatcher(), null);
        
        //The human leaves the room after 15 seconds
        system.scheduler().scheduleOnce(Duration.create(35, TimeUnit.SECONDS), human, new ExitTheRoom(), system.dispatcher(), null);

        // // Create the "actor-in-a-box"
        // final Inbox inbox = Inbox.create(system);

        // // Tell the 'greeter' to change its 'greeting' message
        // greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());

        // // Ask the 'greeter for the latest 'greeting'
        // // Reply should go to the "actor-in-a-box"
        // inbox.send(greeter, new Greet());

        // // Wait 5 seconds for the reply with the 'greeting' message
        // Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        // System.out.println("Greeting: " + greeting1.message);

        // // Change the greeting and ask for it again
        // greeter.tell(new WhoToGreet("typesafe"), ActorRef.noSender());
        // inbox.send(greeter, new Greet());
        // Greeting greeting2 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        // System.out.println("Greeting: " + greeting2.message);

        // // after zero seconds, send a Greet message every second to the greeter with a sender of the GreetPrinter
        // ActorRef greetPrinter = system.actorOf(Props.create(GreetPrinter.class));
        // system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), greeter, new Greet(), system.dispatcher(), greetPrinter);
    }

    // public static class GreetPrinter extends UntypedActor {
    //     public void onReceive(Object message) {
    //         if (message instanceof Greeting)
    //             System.out.println(((Greeting) message).message);
    //             System.out.println("bla");
    //     }
    // }
}
