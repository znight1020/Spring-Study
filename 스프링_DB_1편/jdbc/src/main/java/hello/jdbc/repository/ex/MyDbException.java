package hello.jdbc.repository.ex;

// RuntimeException 을 상속받았기 때문에 언체크예외
public class MyDbException extends RuntimeException{
    public MyDbException() {
        super();
    }

    public MyDbException(String message) {
        super(message);
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }
}
