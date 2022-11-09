package com.vladveretilnyk.workshop.application;

import com.vladveretilnyk.workshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByUsersContains(User user);

}
