package inc.evil.stock.e2e;

import java.util.Objects;
import java.util.Set;

public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private Set<String> errors;
    private String path;

    public ErrorResponse() {
    }

    private ErrorResponse(ErrorResponseBuilder builder) {
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.errors = builder.errors;
        this.path = builder.path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return status == that.status && Objects.equals(error, that.error) && Objects.equals(message, that.message) &&
                Objects.equals(errors, that.errors) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, message, errors, path);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp='" + timestamp + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                ", path='" + path + '\'' +
                '}';
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    public static class ErrorResponseBuilder {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private Set<String> errors;
        private String path;

        public ErrorResponseBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorResponseBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder errors(Set<String> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
