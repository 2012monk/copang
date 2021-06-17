package com.alconn.copang.security;

import com.alconn.copang.common.WeUser;
import com.alconn.copang.exceptions.UnauthorizedException;
import com.alconn.copang.models.Coffee;
import com.alconn.copang.util.ListenerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Optional;

import static com.alconn.copang.util.ListenerUtils.getUserEmailFromContext;

@Slf4j
@Component
public class CoffeeListener {

    @PostLoad
    public void checkCoffee(Coffee coffee) throws UnauthorizedException {
        log.warn("====================post load workings============================");
        log.warn("coffee loaded {}", coffee.getUser().getUid());
        log.warn("user class {}",coffee.getUser().getClass().getSimpleName());
//        throw new UnauthorizedException();
    }


    @PreRemove
    public void authorization(Coffee coffee) throws UnauthorizedException {
        String inbound = coffee.getUser().getEmail();
        Optional<String> email = ListenerUtils.getUserEmailFromContext();
        if (!email.orElseThrow(UnauthorizedException::new).equals(inbound)){
            throw new  UnauthorizedException();
        }
//        WeUser user = (WeUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        if (!coffee.getUser().getEmail().equals(user.getEmail())){
//            throw new UnauthorizedException();
//        }
    }

    @PrePersist
    public void insertUser(Coffee coffee) {
        log.warn("====================working!! entity listener");
        if (coffee.getUser() == null) {
            WeUser user = (WeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.warn("pre persist =============== class :{}", user.getClass().getSimpleName());
            coffee.setUser(user);
            log.warn("user id {} coffee is {}", user, coffee);
        }
    }

    @PreUpdate
//    @PreRemove
    public void selfAuth(Coffee coffee) throws UnauthorizedException {
        String resourceEmail = coffee.getUser().getEmail();
        Optional<String> userEmail = getUserEmailFromContext();

        userEmail.orElseThrow(UnauthorizedException::new);
        if (userEmail.get().equals(resourceEmail)) {
            throw new UnauthorizedException("username un matched");
        }
    }


}
