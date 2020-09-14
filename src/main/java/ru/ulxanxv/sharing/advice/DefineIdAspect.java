package ru.ulxanxv.sharing.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.ulxanxv.sharing.controllers.MainController;

@Aspect
@Component
public class DefineIdAspect {

    @Before(value = "@annotation(ru.ulxanxv.sharing.advice.DefineId)")
    public void defineIdBefore(JoinPoint joinPoint) {
        ((MainController) joinPoint.getTarget()).defineAuthenticatedId();
    }

}
