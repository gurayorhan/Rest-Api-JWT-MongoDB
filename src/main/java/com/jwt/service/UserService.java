package com.jwt.service;

import com.jwt.dto.UserDto;
import com.jwt.repository.UserRepository;
import com.jwt.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto add(UserDto userDto) throws Exception {
        try{
            userDto.setPassword(DigestUtils.sha256Hex(userDto.getPassword()));
            return modelMapper.map(userRepository.save(modelMapper.map(userDto,User.class)),UserDto.class);
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

}
