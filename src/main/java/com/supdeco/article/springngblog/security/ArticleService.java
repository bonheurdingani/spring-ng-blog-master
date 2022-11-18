package com.supdeco.article.springngblog.security;

import com.supdeco.article.springngblog.model.Article;
import com.supdeco.article.springngblog.register.ArticleRegister;
import com.supdeco.article.springngblog.exception.PostNotFoundException;
import com.supdeco.article.springngblog.repository.ArticleRepository;
import com.supdeco.article.springngblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ArticleService {

    @Autowired
    private AuthService authService;
    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public List<ArticleRegister> showAllPosts() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(ArticleRegister articleRegister) {
        Article article = mapFromDtoToPost(articleRegister);
        articleRepository.save(article);
    }

    @Transactional
    public ArticleRegister readSinglePost(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(article);
    }

    private ArticleRegister mapFromPostToDto(Article article) {
        ArticleRegister articleRegister = new ArticleRegister();
        articleRegister.setId(article.getId());
        articleRegister.setTitle(article.getTitle());
        articleRegister.setContent(article.getContent());
        articleRegister.setUsername(article.getUsername());
        return articleRegister;
    }

    private Article mapFromDtoToPost(ArticleRegister articleRegister) {
        Article article = new Article();
        article.setTitle(articleRegister.getTitle());
        article.setContent(articleRegister.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("Utilisateur Not Found"));
        article.setCreatedOn(Instant.now());
        article.setUsername(loggedInUser.getUsername());
        article.setUpdatedOn(Instant.now());
        return article;
    }
}
