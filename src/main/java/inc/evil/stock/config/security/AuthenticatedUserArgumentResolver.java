package inc.evil.stock.config.security;

import inc.evil.stock.user.UserDto;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final SecurityUtil securityUtil;

    public AuthenticatedUserArgumentResolver(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter methodParameter) {
        return methodParameter.getParameterType() == UserDto.class && isParameterAnnotatedWith(AuthenticatedUser.class, methodParameter);
    }

    private boolean isParameterAnnotatedWith(Class<? extends Annotation> annotation, MethodParameter methodParameter) {
        return Arrays.stream(methodParameter.getParameterAnnotations())
                .map(Annotation::annotationType)
                .anyMatch(annotationType -> annotationType == annotation);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  @NonNull NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        return securityUtil.getAuthenticatedUser();
    }
}
