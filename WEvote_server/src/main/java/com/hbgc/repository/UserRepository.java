package com.hbgc.repository;

import com.hbgc.common.BaseRepository;
import com.hbgc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


public interface UserRepository extends BaseRepository<User,Integer> {
    User findByUserNameAndPassWord(String userName, String passWord);
    Page<User> findAll(Pageable pageable);
}
