package inc.evil.stock;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MicrometerDemo {
    public static void main(String[] args) {
        MeterRegistry registry = new SimpleMeterRegistry();

        Counter counter = Counter.builder("http.server.requests")
                .tag("outcome", "success")
                .register(registry);
        counter.increment();
        counter.increment(1.5);
        counter.increment(-1.5);
        System.out.println("Counter: " + counter.count());

        Timer timer = Timer.builder("http.server.request.time")
                .register(registry);
        timer.record(() -> {
            try {
                Thread.sleep(1400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Timer: " + timer.totalTime(TimeUnit.MILLISECONDS));
        System.out.println("Timer: " + timer.count());

        List<String> list = new ArrayList<>();

        Gauge gauge = Gauge.builder("list.size", list, List::size).register(registry);

        list.add("1");
        list.add("1");
        list.add("1");

        System.out.println("Gauge: " + gauge.value());
    }
}
