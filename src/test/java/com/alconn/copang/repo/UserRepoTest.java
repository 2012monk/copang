package com.alconn.copang.repo;

import com.alconn.copang.common.WeUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepoTest {

    static WeUser user = WeUser.builder().email("test@test.com").password("pass").build();
    @Autowired
    UserRepo repo;

    @Transactional
    @Test
    void repoTest() {
        repo.save(user);
        log.info("user id: {}", user.getUid());
        assertEquals(repo.save(user).getEmail(), user.getEmail());
    }

    @Disabled
//    @BeforeEach
    @Test
    void setUp() {
        repo.save(user);
        assert user.getUid() != null;
    }

    @Test
    void name() {
        List<WeUser> list = repo.findAll();
        assert !list.isEmpty();
        assert repo.findByEmail(user.getEmail()).isPresent();
    }
}