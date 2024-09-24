package com.wzf.controller;

import com.wzf.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的，从而防止文件覆盖
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //把文件内容存储到本地磁盘上
        file.transferTo(new File("C:\\Users\\DELL\\Desktop\\files\\" + fileName));
        return Result.success("url访问地址。。。");
    }
}
