package come.dawnlight.chronicle_dawnlight.common.exception;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
@Data
public class BaseException extends Exception{
    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
