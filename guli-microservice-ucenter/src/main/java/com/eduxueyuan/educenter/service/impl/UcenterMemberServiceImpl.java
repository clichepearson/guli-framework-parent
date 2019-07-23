package com.eduxueyuan.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eduxueyuan.educenter.entity.UcenterMember;
import com.eduxueyuan.educenter.mapper.UcenterMemberMapper;
import com.eduxueyuan.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public Integer getCountRegister(String day) {

        return baseMapper.countRegister(day);
    }


    //根据openid获取用户信息
    @Override
    public UcenterMember getByOpenId(String openId) {

        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("openid",openId);

        UcenterMember member = baseMapper.selectOne(queryWrapper);

        return member;
    }
}
