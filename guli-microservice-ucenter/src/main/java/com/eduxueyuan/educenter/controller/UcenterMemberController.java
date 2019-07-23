package com.eduxueyuan.educenter.controller;


import com.edu.xueyuan.entity.R;
import com.eduxueyuan.educenter.entity.UcenterMember;
import com.eduxueyuan.educenter.service.UcenterMemberService;
import com.eduxueyuan.educenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@Api(description = "用户模块")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    UcenterMemberService ucenterMemberService;

    @ApiOperation(value = "查询用户注册数")
    @GetMapping("getCountRegister/{day}")
    public R getCountRegister(@PathVariable String day){

        Integer count = ucenterMemberService.getCountRegister(day);

        return R.ok().data("countRegister",count);
    }

    @ApiOperation(value = "根据token获取用户信息")
    @PostMapping("getInfoByToken/{token}")
    public R getInfoByToken(@PathVariable("token") String token){


        Claims claims = JwtUtils.checkJWT(token);

        String nickname = (String)claims.get("nickname");

        String avatar = (String)claims.get("avatar");

        String id = (String)claims.get("id");

        UcenterMember member = new UcenterMember();

        member.setId(id);

        member.setAvatar(avatar);

        member.setNickname(nickname);

        return R.ok().data("loginInfo",member);
    }
}

