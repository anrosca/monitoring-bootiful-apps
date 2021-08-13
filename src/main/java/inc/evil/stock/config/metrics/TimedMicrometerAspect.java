package inc.evil.stock.config.metrics;

import io.micrometer.core.instrument.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@Aspect
public class TimedMicrometerAspect {
    public static final String DEFAULT_METRIC_NAME = "method.timed";
    public static final String DEFAULT_EXCEPTION_TAG_VALUE = "none";
    public static final String EXCEPTION_TAG = "exception";

    private final MeterRegistry registry;
    private final Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint;

    public TimedMicrometerAspect() {
        this(Metrics.globalRegistry);
    }

    public TimedMicrometerAspect(MeterRegistry registry) {
        this(registry, pjp ->
                Tags.of("class", pjp.getStaticPart().getSignature().getDeclaringTypeName(),
                        "method", pjp.getStaticPart().getSignature().getName())
        );
    }

    public TimedMicrometerAspect(MeterRegistry registry, Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint) {
        this.registry = registry;
        this.tagsBasedOnJoinPoint = tagsBasedOnJoinPoint;
    }

    @Around("execution (@inc.evil.stock.config.metrics.TimeMetrics * *.*(..))")
    public Object timedMethod(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        TimeMetrics timed = method.getAnnotation(TimeMetrics.class);
        if (timed == null) {
            method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
            timed = method.getAnnotation(TimeMetrics.class);
        }

        final String metricName = timed.value().isEmpty() ? DEFAULT_METRIC_NAME : timed.value();
        final boolean stopWhenCompleted = CompletionStage.class.isAssignableFrom(method.getReturnType());

        if (!timed.longTask()) {
            return processWithTimer(pjp, timed, metricName, stopWhenCompleted);
        } else {
            return processWithLongTaskTimer(pjp, timed, metricName, stopWhenCompleted);
        }
    }

    private Object processWithTimer(ProceedingJoinPoint pjp, TimeMetrics timed, String metricName, boolean stopWhenCompleted) throws Throwable {
        Timer.Sample sample = Timer.start(registry);
        if (stopWhenCompleted) {
            try {
                return ((CompletionStage<?>) pjp.proceed()).whenComplete((result, throwable) ->
                        record(pjp, timed, metricName, sample, getExceptionTag(throwable)));
            } catch (Exception ex) {
                record(pjp, timed, metricName, sample, ex.getClass().getSimpleName());
                throw ex;
            }
        }

        String exceptionClass = DEFAULT_EXCEPTION_TAG_VALUE;
        try {
            return pjp.proceed();
        } catch (Exception ex) {
            exceptionClass = ex.getClass().getSimpleName();
            throw ex;
        } finally {
            record(pjp, timed, metricName, sample, exceptionClass);
        }
    }

    private void record(ProceedingJoinPoint pjp, TimeMetrics timed, String metricName, Timer.Sample sample, String exceptionClass) {
        try {
            sample.stop(Timer.builder(metricName)
                    .description(timed.description().isEmpty() ? null : timed.description())
                    .tags(parseExtraTags(timed, pjp))
                    .tags(EXCEPTION_TAG, exceptionClass)
                    .tags(tagsBasedOnJoinPoint.apply(pjp))
                    .publishPercentileHistogram(timed.histogram())
                    .publishPercentiles(timed.percentiles().length == 0 ? null : timed.percentiles())
                    .register(registry));
        } catch (Exception e) {
            // ignoring on purpose
        }
    }

    private String[] parseExtraTags(TimeMetrics timed, ProceedingJoinPoint pjp) {
        String[] tags = timed.extraTags();
        List<String> parsedTags = new ArrayList<>();
        Object[] args = pjp.getArgs();
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("args", args);
        ExpressionParser parser = new SpelExpressionParser();
        for (int i = 0; i < tags.length; i += 2) {
            String tagName = tags[i];
            String tagValue = tags[1];
            parsedTags.add(tagName);
            if (tagValue.contains("#")) {
                Object value = parser.parseExpression(tagValue).getValue(evaluationContext);
                parsedTags.add(value.toString());
            } else {
                parsedTags.add(tagValue);
            }
        }
        return parsedTags.toArray(new String[tags.length]);
    }

    private String getExceptionTag(Throwable throwable) {
        if (throwable == null) {
            return DEFAULT_EXCEPTION_TAG_VALUE;
        }
        if (throwable.getCause() == null) {
            return throwable.getClass().getSimpleName();
        }
        return throwable.getCause().getClass().getSimpleName();
    }

    private Object processWithLongTaskTimer(ProceedingJoinPoint pjp, TimeMetrics timed, String metricName, boolean stopWhenCompleted) throws Throwable {
        Optional<LongTaskTimer.Sample> sample = buildLongTaskTimer(pjp, timed, metricName).map(LongTaskTimer::start);
        if (stopWhenCompleted) {
            try {
                return ((CompletionStage<?>) pjp.proceed()).whenComplete((result, throwable) -> sample.ifPresent(this::stopTimer));
            } catch (Exception ex) {
                sample.ifPresent(this::stopTimer);
                throw ex;
            }
        }
        try {
            return pjp.proceed();
        } finally {
            sample.ifPresent(this::stopTimer);
        }
    }

    private void stopTimer(LongTaskTimer.Sample sample) {
        try {
            sample.stop();
        } catch (Exception e) {
            // ignoring on purpose
        }
    }

    private Optional<LongTaskTimer> buildLongTaskTimer(ProceedingJoinPoint pjp, TimeMetrics timed, String metricName) {
        try {
            return Optional.of(LongTaskTimer.builder(metricName)
                    .description(timed.description().isEmpty() ? null : timed.description())
                    .tags(parseExtraTags(timed, pjp))
                    .tags(tagsBasedOnJoinPoint.apply(pjp))
                    .register(registry));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
