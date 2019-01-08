package com.service.impl;

import com.dao.mapper.RoleMapper;
import com.dao.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.IRoleService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertRole(Role role) {
        roleMapper.insertRole(role);
    }

    @Override
    public Role getRole(Long roleId) {
        log.info("日志测试哦，{}", roleId);
        return roleMapper.getRole(roleId);
    }
}
