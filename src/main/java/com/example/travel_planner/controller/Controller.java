package com.example.travel_planner.controller;

import com.example.travel_planner.config.JwtTokenProvider;
import com.example.travel_planner.config.StatusCode;
import com.example.travel_planner.entity.Comments;
import com.example.travel_planner.entity.Plans;
import com.example.travel_planner.entity.Users;
import com.example.travel_planner.service.CommentService;
import com.example.travel_planner.service.LikeService;
import com.example.travel_planner.service.PlanService;
import com.example.travel_planner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private UserService userService;
    @Autowired
    private PlanService planService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/kakaoLogin")
    public ResponseEntity kakaoLogin(@RequestParam String token) {
        return userService.getUserInfoKakao(token);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> data) {
        return userService.login(data);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity checkEmail(@RequestBody Map<String, String> email) {
        return userService.checkEmail(email);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return userService.getUserInfo(token);
    }
    @PostMapping("/getUserUpdatePw")
    public ResponseEntity getUserUpdatePw(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, String> data){
        return userService.getUserUpdatePw(token, data);
    }

    @PostMapping("/getUserUpdate")
    public ResponseEntity getUserUpdate(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, String> data) {
       return userService.getUserUpdate(token, data);
    }
    @CrossOrigin
    @DeleteMapping("/userDelete")
    public ResponseEntity userDelete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return userService.userDelete(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Users user) {
        return userService.register(user);
    }

    @PostMapping("/tokenAuth") // 그저 테스트
    public ResponseEntity tokenAuth(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        if(jwtTokenProvider.validateAccessToken(token.split(" ")[1])){
            return new StatusCode(HttpStatus.OK, "인증 성공").sendResponse();
        }else{
            return new StatusCode(HttpStatus.UNAUTHORIZED, "만료된 토큰").sendResponse();
        }
    }

    @PostMapping("/getTokenUsedRefreshToken")
    public ResponseEntity getTokenUsedRefreshToken(@RequestBody Map<String, String> data){
        return userService.getTokenUsedRefreshToken(data);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(MultipartFile[] multipartFiles, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return userService.uploadFile(multipartFiles, token);
    }

    @GetMapping(value="/image/view", produces= MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("value") String value) throws IOException {
        return userService.getImage(value);
    }

    @PostMapping("/passwordChange")
    public ResponseEntity passwordChange(@RequestBody Map<String, String> email) {
        return userService.passwordChange(email);
    }

    @PostMapping("/getLikes")
    public ResponseEntity getLikes(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return likeService.getLikes(token);
    }

    @PostMapping("/getLikesPagination")
    public ResponseEntity getLikesPagination(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, Object> data){
        return likeService.getLikesPagination(token, data);
    }


    @PostMapping("/addLikes")
    public ResponseEntity addLikes(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, String> data){
        return likeService.addLikes(token, data);
    }

    @DeleteMapping("/removeLikes/{id}")
    public ResponseEntity removeLikes(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id){
        return likeService.removeLikes(token, id);
    }

    @PostMapping("/getLikesCount")
    public ResponseEntity getLikesCount(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return likeService.getLikesCount(token);
    }

    @PostMapping("/addComment")
    public ResponseEntity addComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, String> data){
        return commentService.addComment(token, data);
    }

    @GetMapping("/getComment")
    public ResponseEntity getComment(@RequestParam String id){
        return commentService.getComment(id);
    }
    @PostMapping("/getMyPage")
    public ResponseEntity getMyPage(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return commentService.getMyPage(token);
    }
    @PostMapping("/createPlan")
    public ResponseEntity createPlan(@RequestBody Map<String, String> plan) {
        return planService.createPlan(plan);
    }

    @GetMapping("/getUserPlan")
    public ResponseEntity getUserPlan(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return planService.getUserPlan(token);
    }
}
