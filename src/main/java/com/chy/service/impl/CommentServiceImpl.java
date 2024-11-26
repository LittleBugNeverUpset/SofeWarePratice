package com.chy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.pojo.Comment;
import com.chy.service.CommentService;
import com.chy.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author littlebug
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-11-26 10:37:17
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




