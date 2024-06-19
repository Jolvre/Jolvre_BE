//package com.example.jolvre.external;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.multipart.MultipartFile;
//
//@Component
//public class ModelApi {
//
//    @Component
//    @FeignClient
//    public interface Model3D {
//        @GetMapping("/model/async")
//        String get3DModelUrl(@RequestBody MultipartFile image);
//    }
//}
//
