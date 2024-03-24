package kr.co.chunjae.user.service;

import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.dto.UserRequestDTO;
import kr.co.chunjae.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
        전화번호를 기준으로 사용자 정보 조회
     */
    public UserDTO getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    /**
        회원가입 처리
     */
    public void signUp(UserRequestDTO userRequestDTO) throws Exception { // 회원가입
        // 회원가입시 입력 항목 누락시 예외 발생
        if(userRequestDTO.getPhone().isEmpty() || userRequestDTO.getName().isEmpty() ||
                userRequestDTO.getPassword().isEmpty() || userRequestDTO.getGrade()== null){
            throw new IllegalArgumentException("핸드폰 번호, 이름, 비밀번호, 학년 필수 입력 항목입니다.");
        }
        // password 암호화 저장
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        try {
            userMapper.insertUser(userRequestDTO);
        } catch (DataIntegrityViolationException e) {
            // DB에 동일한 전화번호가 있으면 예외 발생
            throw new IllegalStateException("이미 등록된 핸드폰 번호입니다.");
        }
    }

    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }


    public boolean existsByPhone(String phone) {
        // 전화번호가 존재하면 true, 존재하지 않으면 false 반환
        return userMapper.findByPhone(phone) !=null;
    }

//    public void edit(UserDTO userDTO) { // 회원정보 수정
//        // password 암호화 저장
//        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        userMapper.updateUser(userDTO);
//    }
//
//    public void deleteUser(String phone) { // 회원 탈퇴
//        userMapper.deleteUser(phone);
//    }


    /*@Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid)%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }*/
}
