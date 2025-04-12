package bloom_story.global.domain.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import bloom_story.global.domain.exception.custom.AuthorizationException;
import bloom_story.global.domain.exception.custom.DataDuplicationException;
import bloom_story.global.domain.exception.custom.DataNotFoundException;
import bloom_story.global.domain.exception.custom.WrongRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(DataDuplicationException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
        HttpServletRequest request,
        DataDuplicationException e
    ) {
        log.warn(e.getDetail());
        requestLogging(request);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getDetail());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
        HttpServletRequest request,
        DataNotFoundException e
    ) {
        log.warn(e.getDetail());
        requestLogging(request);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getDetail());
    }

    @ExceptionHandler(WrongRequestException.class)
    public ResponseEntity<Object> handleIllegalStateException(
        HttpServletRequest request,
        WrongRequestException e
    ) {
        log.warn(e.getDetail());
        requestLogging(request);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getDetail());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationException(
        HttpServletRequest request,
        AuthorizationException e
    ) {
        log.warn(e.getDetail());
        requestLogging(request);
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getDetail());
    }

    // 표준 예외 및 정의되어 있는 예외
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleRequestTooFastException(
        HttpServletRequest request,
        IllegalArgumentException e
    ) {
        log.warn(e.getMessage());
        requestLogging(request);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleRequestTooFastException(
        HttpServletRequest request,
        IllegalStateException e
    ) {
        log.warn(e.getMessage());
        requestLogging(request);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
        HttpServletRequest request,
        Exception e
    ) {
        String errorMessage = e.getMessage();
        String errorFile = e.getStackTrace()[0].getFileName();
        int errorLine = e.getStackTrace()[0].getLineNumber();
        String errorName = e.getClass().getSimpleName();
        String detail = String.format("""
                Exception: *%s*
                Location: *%s Line %d*
                
                ```%s```
                """,
            errorName, errorFile, errorLine, errorMessage);
        log.error("""
            서버에서 에러가 발생했습니다. uri: {} {}
            {}
            """, request.getMethod(), request.getRequestURI(), detail);
        requestLogging(request);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest webRequest
    ) {
        HttpServletRequest request = ((ServletWebRequest)webRequest).getRequest();
        log.warn("검증과정에서 문제가 발생했습니다. uri: {} {}, ", request.getMethod(), request.getRequestURI(), e);
        requestLogging(request);
        String errorMessages = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessages);
    }

    // 예외 메시지 구성 로직
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception e,
        @Nullable Object body,
        HttpHeaders headers,
        HttpStatusCode statusCode,
        WebRequest request
    ) {
        return buildErrorResponse(HttpStatus.valueOf(statusCode.value()), e.getMessage());
    }

    private ResponseEntity<Object> buildErrorResponse(
        HttpStatus httpStatus,
        String message
    ) {
        String errorTraceId = UUID.randomUUID().toString();
        log.warn("traceId: {}", errorTraceId);
        var response = new ErrorResponse(httpStatus.value(), message, errorTraceId);
        return ResponseEntity.status(httpStatus).body(response);
    }

    private void requestLogging(HttpServletRequest request) {
        log.info("request header: {}", getHeaders(request));
        log.info("request query string: {}", getQueryString(request));
        log.info("request body: {}", getRequestBody(request));
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getQueryString(HttpServletRequest httpRequest) {
        String queryString = httpRequest.getQueryString();
        if (queryString == null) {
            return " - ";
        }
        return queryString;
    }

    private String getRequestBody(HttpServletRequest request) {
        var wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper == null) {
            return " - ";
        }
        try {
            // body 가 읽히지 않고 예외처리 되는 경우에 캐시하기 위함
            wrapper.getInputStream().readAllBytes();
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length == 0) {
                return " - ";
            }
            return new String(buf, wrapper.getCharacterEncoding());
        } catch (Exception e
        ) {
            return " - ";
        }
    }
}
