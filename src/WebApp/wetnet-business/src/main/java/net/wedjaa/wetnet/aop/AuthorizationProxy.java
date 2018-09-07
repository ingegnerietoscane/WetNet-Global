package net.wedjaa.wetnet.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
public class AuthorizationProxy {

    private Logger logger = LoggerFactory.getLogger(AuthorizationProxy.class);

  //  @Around("within(net.wedjaa.wetnet.business.dao.*)")
    //@Pointcut("execution(* net.wedjaa.wetnet.business.dao..*.*(..))")
    public Object dataAccessOperation(//ProceedingJoinPoint joinPoint
            ) throws Throwable {
        // Before method execution.
//        Object[] args = joinPoint.getArgs();
//        //Object target = joinPoint.getTarget();
//        //Object[] args = joinPoint.getArgs();
//        String methodName = joinPoint.getSignature().getName();
//
//        logger.info("______________________");
//        logger.info(methodName);
//        logger.info("______________________");
//        System.out.println("____________________________");
//
//        Object result = joinPoint.proceed();

        // After method execution.

       // return result;
        return null;
    }

}
