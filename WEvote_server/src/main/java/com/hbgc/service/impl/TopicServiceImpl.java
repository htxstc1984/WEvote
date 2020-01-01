package com.hbgc.service.impl;

import com.hbgc.common.BaseServiceImpl;
import com.hbgc.domain.Topic;
import com.hbgc.repository.TopicRepository;
import com.hbgc.service.TopicService;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends BaseServiceImpl<Topic, Integer, TopicRepository> implements TopicService {
}
