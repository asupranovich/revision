package com.asupranovich.revision.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Aspect
@Component
public class CollectRequestInfoAspect {

    @Around("@annotation(com.asupranovich.revision.aop.CollectRequestInfo) && args(request,..)")
    public Object collectMetrics(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        Object response = null;
        Exception exception = null;
        try {
            response = joinPoint.proceed();
            return response;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            printRequestInfo(joinPoint, request, response);
        }
    }

    private void printRequestInfo(ProceedingJoinPoint joinPoint, HttpServletRequest request, Object response) {
        PathAndMethod urlAndMethod = getUrlAndMethod(joinPoint);
        String url = urlAndMethod == null ? null : urlAndMethod.url();
        RequestMethod method = urlAndMethod == null ? null : urlAndMethod.method();
        String testHeader = request.getHeader("test");
        HttpStatusCode responseStatus = getResponseStatus(response);
        System.out.printf("[REQUEST] URL: %s, Method: %s, Test Header: %s, Response Status: %s%n", url, method, testHeader, responseStatus);
    }

    private HttpStatusCode getResponseStatus(Object response) {
        if (response == null) {
            return null;
        } else if (response instanceof ResponseEntity<?> responseEntity) {
            return responseEntity.getStatusCode();
        } else {
            return HttpStatusCode.valueOf(200);
        }
    }

    private PathAndMethod getUrlAndMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            String path = getFirst(annotation.value());
            RequestMethod requestMethod = getFirst(annotation.method());
            return new PathAndMethod(path, requestMethod);
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping annotation = method.getAnnotation(GetMapping.class);
            String path = getFirst(annotation.value());
            return new PathAndMethod(path, RequestMethod.GET);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping annotation = method.getAnnotation(PostMapping.class);
            String path = getFirst(annotation.value());
            return new PathAndMethod(path, RequestMethod.POST);
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping annotation = method.getAnnotation(PutMapping.class);
            String path = getFirst(annotation.value());
            return new PathAndMethod(path, RequestMethod.PUT);
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
            String path = getFirst(annotation.value());
            return new PathAndMethod(path, RequestMethod.DELETE);
        }

        return null;
    }

    private <T> T getFirst(T[] array) {
        if (array != null && array.length > 0) {
            return array[0];
        }
        return null;
    }

    record PathAndMethod(String url, RequestMethod method){}

}
