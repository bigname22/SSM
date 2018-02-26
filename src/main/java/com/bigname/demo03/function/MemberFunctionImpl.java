package com.bigname.demo03.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigname.common.StringUtil;
import com.bigname.demo03.core.Member;
import com.bigname.demo03.dao.MemberDao;

@Service
public class MemberFunctionImpl implements IMemberFunction{
	@Autowired
	MemberDao mDao;
	
	public Member login(String name, String passsword) throws Exception {
		System.out.println(name + passsword);
		if(StringUtil.isNullOrZero(name)){
			System.out.println("用戶名不能為空");
			return null;
		}
		if(StringUtil.isNullOrZero(passsword)){
			System.out.println("密碼不能為空");
			return null;
		}
		Member member = mDao.selectMemberByName(name);
		return member;
	}

}
