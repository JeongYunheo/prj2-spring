package com.prj2spring.service.member;

import com.prj2spring.domain.member.Member;
import com.prj2spring.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MemberService {

    final MemberMapper mapper;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public void add(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setEmail(member.getEmail().trim());
        member.setNickName(member.getNickName().trim());

        mapper.insert(member);
    }

    public Member getByEmail(String email) {
        return mapper.selectByEmail(email);
    }

    public Member getByNickName(String nickName) {
        return mapper.selectByNickName(nickName);
    }

    public boolean validate(Member member) {
        if (member.getEmail() == null || member.getEmail().isBlank()) {
            return false;
        }
        if (member.getPassword() == null || member.getPassword().isBlank()) {
            return false;
        }
        String emailPattern = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*";

        if (!member.getEmail().trim().matches(emailPattern)) {
            return false;
        }

        return true;
    }

    public List<Member> selectAll() {
        return mapper.selectAll();
    }

    public Member getById(Integer id) {
        return mapper.selectById(id);
    }

    public void remove(Integer id) {
        mapper.deleteById(id);
    }

    public boolean hasAccess(Member member) {
        Member dbMember = mapper.selectById(member.getId());
        System.out.println("dbMember = " + dbMember);
        if (dbMember == null) {
            return false;
        }

        return passwordEncoder.matches(member.getPassword(), dbMember.getPassword());
    }

    public void modify(Member member) {
        if (member.getPassword() != null && member.getPassword().length() > 0) {
            // 패스워드가 입력되면 바꾸기
            member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        } else {
            // 입력이 안됐으니 기존 값으로 유지
            Member dbMember = mapper.selectById(member.getId());
            member.setPassword(dbMember.getPassword());
        }
        mapper.update(member);
    }

    public boolean hasAccessModify(Member member) {
        Member dbMember = mapper.selectById(member.getId());
        if (dbMember == null) {
            return false;
        }
        if (!passwordEncoder.matches(member.getOldPassword(), dbMember.getPassword())) {
            return false;
        }

        return true;
    }
}
