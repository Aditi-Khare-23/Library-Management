package com.library.member.Members.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.member.Members.model.Member;
import com.library.member.Members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private static List<Member> list=new ArrayList<>();

//    static RestTemplate restTemplate = new RestTemplate();
//    static String baseUrl = "http://localhost:8080/library-book-service";

//    public void updateCopiesAvailable(int bookId) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Step 1: Fetch the current value of copies_available
//        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                baseUrl + "/get/books/12340",
//                HttpMethod.GET,
//                requestEntity,
//                String.class
//        );
//
//        String bookJson = responseEntity.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            JsonNode rootNode = objectMapper.readTree(bookJson);
//            int copiesAvailable = rootNode.path("copies_available").asInt();
//            System.out.println("Current copies available - " + copiesAvailable);
//
//            // Step 2: Increment the value
//            copiesAvailable += 1;  // Increment by 1, or any other logic you need
//
//            // Step 3: Create the updated Book object
//            ((ObjectNode) rootNode).put("copies_available", copiesAvailable);
//            String updatedBookJson = objectMapper.writeValueAsString(rootNode);
//
//            // Send a PUT request to update the value in the Book service
//            HttpEntity<String> putRequestEntity = new HttpEntity<>(updatedBookJson, headers);
//            ResponseEntity<String> putResponseEntity = restTemplate.exchange(
//                    baseUrl + "/update/books/12340",
//                    HttpMethod.PUT,
//                    putRequestEntity,
//                    String.class
//            );
//
//            System.out.println("Update response - " + putResponseEntity.getBody());
//            //return putResponseEntity.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            //return null;
//        }
//    }

    @Autowired
    MemberRepository memberRepository;

    public Member addMember(Member m){
        return memberRepository.save(m);
//        return resultMember;
    }

    public List<Member> getAllMembers() {
        return list;
    }

    public static Member getMemberbyId(int member_id){
        Member member = null;
        try {
            member = list.stream().filter(e -> e.getId() == member_id).findFirst().get();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return member;
    }

    public ResponseEntity<?> deleteMember(int member_id){
        try {
            memberRepository.deleteById(member_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Member updateMember(Member member, int member_id){

//        Optional<Member> optionalMember = memberRepository.findById(id);
        Member Mem = memberRepository.findById(member_id).get();
        Mem.setName(member.getName());
        Mem.setBook_id(member.getBook_id());
        Mem.setEmail(member.getEmail());
        Mem.setAddress(member.getAddress());
        Mem.setPhone_no(member.getPhone_no());
//        Mem.setReturning(member.getReturning());
//        list = list.stream().map(m-> {
//            if (m.getId() == id){
//                m.setName(member.getName());
//                m.setEmail(member.getEmail());
//                m.setAddress(member.getAddress());
//                m.setPhone_no(member.getPhone_no());
//            }
//            memberRepository.save(member);
//            return m;
//        }).collect(Collectors.toList());
        int bookId = Mem.getBook_id();
        System.out.println(bookId);
//        String returnStatus = Mem.getReturning();

//        if("Yes".equals(returnStatus)){
//            restemplate.updateCopiesAvailable(1,bookId);
//            System.out.println("M");
//        }
//        else if ("No".equals(returnStatus)){
//            restemplate.updateCopiesAvailable(bookId);
//        }

        Member updatedMember = memberRepository.save(Mem);
        System.out.println(updatedMember.getName());
        return updatedMember;
    }
}
