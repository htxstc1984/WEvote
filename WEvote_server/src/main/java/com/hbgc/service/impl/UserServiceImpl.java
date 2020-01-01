package com.hbgc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hbgc.common.BaseServiceImpl;
import com.hbgc.domain.User;
import com.hbgc.mapper.UserMapper;
import com.hbgc.repository.UserRepository;
import com.hbgc.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Integer,UserRepository> implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    UserRepository userRepository;
    @Override
    public int regUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User userLogin(String userName, String passWord) {
        return userRepository.findByUserNameAndPassWord(userName,passWord);
    }

    @Override
    public List<User> queryUsersByPager(int type, int pageNum, int pageSize) {
        List<User> userList = null;
        switch(type){
            case 1:
                pageNum--;
                Pageable pager = PageRequest.of(pageNum,pageSize);
                Page page =  super.findAllByPager(pager);
                userList = page.getContent();
                break;
            case 0:
                PageHelper.startPage(pageNum, pageSize);
                List<User> list = userMapper.selectAll();
                PageInfo result = new PageInfo(list);
                userList = result.getList();
        }
        return userList;
    }
}
