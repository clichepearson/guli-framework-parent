package com.eduxueyuan.educenter.controller;


import com.edu.xueyuan.entity.R;
import com.edu.xueyuan.exception.EduException;
import com.eduxueyuan.educenter.config.ConstantPropertiesUtil;
import com.eduxueyuan.educenter.entity.UcenterMember;
import com.eduxueyuan.educenter.service.UcenterMemberService;
import com.eduxueyuan.educenter.utils.HttpClientUtils;
import com.eduxueyuan.educenter.utils.JwtUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    UcenterMemberService ucenterMemberService;

    @GetMapping("login")
    public String genQrConnect(){

        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;

        try {

             redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        String state = "guli";//这里写ngrok的前置域名

        String qrCodeUrl = String.format(baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state

        );


        return "redirect:" + qrCodeUrl;
    }

    @ApiOperation("回调函数")
    @GetMapping("callback")
    public String callback(String code,String state){

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;

        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new EduException(20001,"获取AccessToken失败");
        }

        Gson gson = new Gson();
        //将json字符串转换为json对象
        Map<String,Object> map = gson.fromJson(result, HashMap.class);

        String accessToken = (String)map.get("access_token");

        String openId = (String)map.get("openid");

        System.out.println(accessToken);

        System.out.println(openId);

        UcenterMember member = ucenterMemberService.getByOpenId(openId);

        System.out.println(member);

        if(member == null){

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);

            String userInfo = null;

            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new EduException(20001,"获取用户信息失败");
            }

            Map<String,Object> mapUserInfo = gson.fromJson(userInfo, HashMap.class);

            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openId);
            member.setAvatar(headimgurl);
            ucenterMemberService.save(member);
        }

        String token = JwtUtils.geneJsonWebToken(member);

        return "redirect:http://localhost:3000?token=" + token;
    }


}
