package kurs.rest.messaging.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kurs.rest.messaging.beans.Comments;
import kurs.rest.messaging.beans.Likes;
import kurs.rest.messaging.beans.Post;
import kurs.rest.messaging.beans.PostsSent;
import kurs.rest.messaging.beans.User;
import kurs.rest.messaging.service.CommentsService;
import kurs.rest.messaging.service.LikesService;
import kurs.rest.messaging.service.PostService;
import kurs.rest.messaging.service.Service;
import kurs.rest.messaging.service.UserService;

@RestController
@RequestMapping("/messagingSystem")
public class MessagingController {
	
	// attributes - creating instances with @Autowired, @Resource or @Inject 
	// and @Qualifier from Spring
	@Autowired
	private PostService service;
	
	@Autowired
	@Qualifier(value = "userService")
	private UserService userService;
	
	@Inject
	@Qualifier(value = "service")
	private Service ser;
	
	@Autowired
	@Qualifier(value="likesService")
	private LikesService likesService;
	
	@Resource
	@Qualifier(value="commentsService")
	private CommentsService commentsService;
	
	// POST SERVICE METHODS
	@GetMapping("/trendingMessages")
	public List<PostsSent> getMostTrendingMessages() throws SQLException { 
		return service.getMostTrendingMessages();
	}
	
	@GetMapping("/posts")
	public List<PostsSent> getPosts() throws SQLException {
		return service.getPosts();
	}
	
	@GetMapping("/posts/{id}")
	public List<PostsSent> getPostsById(@PathVariable Integer id) throws SQLException {
		return service.getPosts(id);
	}
	
	@PostMapping("/posts")
	public Post insertPost(@RequestBody Post post) throws SQLException {
		return service.insertPost(post);
	}
	
	// USER SERVICE METHODS
	@PostMapping("/users")
	public User insertUser(@RequestBody User user) throws SQLException {
		return userService.insertUser(user);
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User user) throws SQLException {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Integer id) throws SQLException {
		userService.deleteUser(id);
	}
	
	@GetMapping("/users")
	public User loginUser(@RequestParam(name = "username", required = false) String username) throws SQLException {
		return userService.loginUser(username);
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable Integer id) throws SQLException {
		return ser.getUser(id);
	}
	
	// LIKES SERVICE METHODS
	@PostMapping("/likes")
	public Likes insertLike(@RequestParam(name="user_ID") Integer user_ID, 
			@RequestParam(name="post_ID")Integer post_ID) throws SQLException {
		return likesService.insertLike(user_ID, post_ID);
	}
	
	@DeleteMapping("/likes")
	public void deleteLike(@RequestParam(name="user_ID")Integer user_ID, 
			@RequestParam(name="post_ID")Integer post_ID) throws SQLException {
		likesService.deleteLike(user_ID, post_ID);
	}
	
	@GetMapping("/likes")
	public List<PostsSent> getLikesPerPerson(@RequestParam(name="post_ID")Integer post_ID) throws SQLException {
		return likesService.getLikesPerPerson(post_ID);
	}
	
	//COMMENTS SERVICE METHODS
	@PostMapping("/comments")
	public Comments insertComment(@RequestBody Comments comment) throws SQLException {
		return commentsService.insertComment(comment);
	}
	
	@DeleteMapping("/comments/{comment_ID}")
	public void deleteComment(@PathVariable Integer comment_ID) throws SQLException {
		commentsService.deleteComment(comment_ID);
	}
	
	@GetMapping("/comments")
	public List<Comments> getCommentsPerPost(@RequestParam(name="post_ID") Integer post_ID) throws SQLException {
		return commentsService.getCommentsPerPost(post_ID);
	}
	
	@GetMapping("/comments/{post_ID}")
	public List<Comments> countComments(@PathVariable Integer post_ID) throws SQLException {
		return commentsService.countComments(post_ID);
	}
	
	// every service method works, except update user!!
}
