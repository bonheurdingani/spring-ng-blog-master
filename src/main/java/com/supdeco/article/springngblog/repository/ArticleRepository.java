package com.supdeco.article.springngblog.repository;

import com.supdeco.article.springngblog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
