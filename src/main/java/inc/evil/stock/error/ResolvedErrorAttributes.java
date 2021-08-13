package inc.evil.stock.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class ResolvedErrorAttributes extends DefaultErrorAttributes {
    private final MessageSource messageSource;

    public ResolvedErrorAttributes(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        resolveBindingErrors(errorAttributes);
        return errorAttributes;
    }

    @SuppressWarnings("unchecked")
    private void resolveBindingErrors(Map<String, Object> errorAttributes) {
        List<ObjectError> errors = (List<ObjectError>) errorAttributes.get("errors");
        if (errors == null)
            return;
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : errors) {
            String resolvedMessage = messageSource.getMessage(error, Locale.US);
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(String.format("Field '%s' %s but value was '%s'", fieldError.getField(), resolvedMessage, fieldError.getRejectedValue()));
            } else {
                errorMessages.add(resolvedMessage);
            }
        }
        errorAttributes.put("errors", errorMessages);
    }
}
