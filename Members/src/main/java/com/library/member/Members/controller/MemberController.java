package com.library.member.Members.controller;

import com.library.member.Members.model.Member;
import com.library.member.Members.repository.MemberRepository;
import com.library.member.Members.services.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library-member-service")
public class MemberController {

    @Autowired
    MemberRepository repo;

    @Autowired
    private MemberService memberService;

//    @Autowired
//    private restemplate restt;

    @GetMapping("/get/members")
    public ResponseEntity<List<Member>> getMembers(){
        List<Member> list = repo.findAll();
        if(list.size() < 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/get/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") int member_id){
        Member member = (Member) repo.findById(member_id).get();
        if (member == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(member));
    }

    @GetMapping("/exists/members/{id}")
    public ResponseEntity<Boolean> checkMemberExists(@PathVariable("id") int memberId){
        boolean exists = repo.existsById(memberId);
        return ResponseEntity.ok(exists);
    }
    @PostMapping("/save/members")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Member> addMember(@RequestBody Member member){
        Member m = null;
        try{
            m = this.memberService.addMember(member);
//            repo.save(m);
            return ResponseEntity.of(Optional.of(m));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/remove/members/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable("memberId") int member_id){
        try {
            return this.memberService.deleteMember(member_id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PutMapping("/update-copies/{bookId}")
//    public String updateBookCopies(@PathVariable String bookId, @RequestParam int incrementValue) {
//        String response = memberService.updateCopiesAvailable(bookId, incrementValue);
//
//        if (response != null) {
//            return "Successfully updated book with ID " + bookId + ". Response from Book Service: " + response;
//        } else {
//            return "Failed to update book with ID " + bookId;
//        }
//    }

    @PutMapping("/update/members/{member_id}")
    public ResponseEntity<Member> updateMember(@RequestBody Member member, @PathVariable("member_id") int member_id){
//        member = repo.findById(member_id).get();
        Member existingEntity = repo.findById(member.getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        if (existingEntity.getId() != (member.getId())) {
            throw new IllegalArgumentException("Primary key cannot be updated.");
        }
        try {
            this.memberService.updateMember(member, member_id);
//            if (member.getReturning() == "Yes") {
//                System.out.println("Hello");
//                this.restt.useExchangeMethodsOfRestTemplate(1, member.getBook_id());
//            }
//            else if (member.getReturning() == "No") {
//                System.out.println("Helloo");
//                this.restt.useExchangeMethodsOfRestTemplate(member.getBook_id());
//            }
            return ResponseEntity.ok().body(member);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

//        return mem;
    }
//    @PutMapping("/update/members/{member_id}/{copies_available}")
//    public ResponseEntity<Member>updatedCopies(@PathVariable int member_id, @PathVariable int copies_available){
//        member
//    }

}
