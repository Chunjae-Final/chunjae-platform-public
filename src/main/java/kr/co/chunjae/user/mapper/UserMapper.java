package kr.co.chunjae.user.mapper;

import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.dto.UserRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDTO getUserByPhone(String phone); // 회원정보 가져오기

    void insertUser(UserRequestDTO userRequestDTO); // 회원가입

    void updateUser(UserDTO userDTO); // 회원 정보 수정

    void deleteUser(String phone); // 회원 탈퇴

    boolean phoneCheck(String phone); // 중복체크

    UserDTO findByPhone(String phone);

    int selectByPhone(String phone);
}
