package com.github.anthonych.blog.resource;

import com.github.anthonych.blog.core.Article;
import com.github.anthonych.blog.dao.ArticleDAO;
import com.github.anthonych.blog.view.ArticleListView;
import com.github.anthonych.blog.view.ArticleView;
import com.github.anthonych.blog.view.Template;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by anthonychen on 9/14/14.
 */

@Path("/article")
public class ArticleResource {

    private ArticleDAO articleDAO;

    public ArticleResource(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/view/all")
    @Produces(MediaType.TEXT_HTML)
    public ArticleListView viewAllArticles() {
        return new ArticleListView(articleDAO.findAll());
    }


    @GET
    @UnitOfWork
    @Path("/view/{id}")
    @Produces(MediaType.TEXT_HTML)
    public ArticleView viewArticle(@PathParam("id") String id) {

        return new ArticleView(Template.VIEW_ARTICLE, articleDAO.findById(id));
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public ArticleView newArticle() {

        return new ArticleView(Template.NEW_ARTICLE, new Article());
    }

    @POST
    @UnitOfWork
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String saveArticle(@Valid Article article) {
        article.setPostDate(new Timestamp(new Date().getTime()));
        return articleDAO.saveArticle(article).getId();
    }


    @GET
    @Timed
    @UnitOfWork
    @Path("/api/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Article getArticle(@PathParam("id") String id) {
        return articleDAO.findById(id);
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/api/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getAllArticles() {
        return articleDAO.findAll();
    }

}
