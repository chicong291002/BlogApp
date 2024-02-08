package com.springboot.blog.springbootblogrestapi.services;

import com.springboot.blog.springbootblogrestapi.dto.LoginDto;
import com.springboot.blog.springbootblogrestapi.dto.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
