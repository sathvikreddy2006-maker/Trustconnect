
package com.trustconnect.controller;

import com.trustconnect.entity.TrustMember;
import com.trustconnect.service.TrustMemberService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class TrustMemberController {

    private final TrustMemberService memberService;

    public TrustMemberController(TrustMemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/trust/{trustId}")
    public TrustMember registerMember(
            @PathVariable Long trustId,
            @RequestBody TrustMember member) {
        return memberService.registerMember(trustId, member);
    }

    @GetMapping("/trust/{trustId}")
    public List<TrustMember> getMembers(
            @PathVariable Long trustId) {
        return memberService.getMembers(trustId);
    }

    @GetMapping("/{id}")
    public TrustMember getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }
}
