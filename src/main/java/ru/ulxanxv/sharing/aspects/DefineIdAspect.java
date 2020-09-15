package ru.ulxanxv.sharing.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefineIdAspect {

    @Before(value = "@annotation(ru.ulxanxv.sharing.aspects.DefineId)")
    public void defineIdBefore(JoinPoint joinPoint) {
        ((IDefineId) joinPoint.getTarget()).defineAuthenticatedId();
    }

}
