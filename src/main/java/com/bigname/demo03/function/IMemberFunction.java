package com.bigname.demo03.function;

import org.springframework.stereotype.Service;

import com.bigname.demo03.core.Member;

@Service
public interface IMemberFunction {
	Member login(String name, String passsword) throws Exception;
}
