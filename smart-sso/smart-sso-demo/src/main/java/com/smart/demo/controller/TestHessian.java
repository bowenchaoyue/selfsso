package com.smart.demo.controller;

import com.smart.sso.rpc.AuthenticationRpcService;
import com.smart.sso.rpc.RpcUser;
import com.smart.sso.server.model.Role;
import com.smart.sso.server.service.RoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestHessian {
    // test Hessian success
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        AuthenticationRpcService authenticationRpcService =(AuthenticationRpcService) applicationContext.getBean("authenticationRpcService");
        RpcUser sssssss = authenticationRpcService.findAuthInfo("sssssss");
        RoleService roleService = (RoleService)applicationContext.getBean("roleService");
        List<Role> byAppId = roleService.findByAppId(true, 81);
        System.out.println(sssssss);
    }
}
