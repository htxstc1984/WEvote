package com.hbgc.service;

import com.hbgc.common.BaseService;
import com.hbgc.domain.User;
import com.hbgc.repository.UserRepository;

import java.util.List;

public interface UserService extends BaseService<User,Integer,UserRepository> {
    int regUser(User user);
    User userLogin(String userName, String passWord);
    List<User> queryUsersByPager(int type, int pageNum, int pageSize);
}
