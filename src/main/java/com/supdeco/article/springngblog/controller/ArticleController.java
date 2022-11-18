package com.supdeco.article.springngblog.controller;

import com.supdeco.article.springngblog.register.ArticleRegister;
import com.supdeco.article.springngblog.security.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article/")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody ArticleRegister articleRegister) {
        articleService.createPost(articleRegister);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleRegister>> showAllPosts() {
        return new ResponseEntity<>(articleService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ArticleRegister> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(articleService.readSinglePost(id), HttpStatus.OK);
    }
}
