package com.shop.service;

import com.shop.dto.MemberJoinDTO;

public interface MemberService{

    class MidExistException extends Exception{

    }

    void join(MemberJoinDTO memberJoinDTO)throws MidExistException;

}
