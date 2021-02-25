package com.ams.biblio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ams.biblio.entities.Book;
import com.ams.biblio.entities.Category;
import com.ams.biblio.entities.User;
import com.ams.biblio.repositories.BookRepository;
import com.ams.biblio.repositories.CategoryRepository;

@Controller
@RequestMapping("/book")
public class BookAdminController {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;

	public BookAdminController(BookRepository bookRepository, CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		this.bookRepository = bookRepository;
	}

	@GetMapping("list")
	public String listProviders(Model model) {

		List<Book> la = (List<Book>) bookRepository.findAll();
		if (la.size() == 0)
			la = null;
		model.addAttribute("books", la);
		return "back/book/listBooks";
	}

	@GetMapping("add")
	public String showAddArticleForm(Model model) {

		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("book", new Book());
		return "/back/book/addBook";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createNewUser(@Valid Book book, BindingResult result,
			@RequestParam(name = "categoryId", required = false) int id, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			return "back/book/addBook";
		} else {

			Category category = categoryRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
			book.setCategory(category);
			bookRepository.save(book);

		}
		return "redirect:list";
	}

	@GetMapping("delete/{id}")
	public String deleteProvider(@PathVariable("id") int id, Model model) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));

		bookRepository.delete(book);
		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showArticleFormToUpdate(@PathVariable("id") int id, Model model) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

		model.addAttribute("book", book);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("idCategory", book.getCategory().getId());

		return "/back/book/updateBook";
	}

	@PostMapping("edit")
	public String updateArticle(@Valid Book book, BindingResult result, Model model,
			@RequestParam(name = "categoryId", required = false) int id) {

		System.out.println(book.getId() + "  thsi is ddddddddddddddddd");
		if (result.hasErrors()) {

			return "/back/book/updateBook";
		}

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
		book.setCategory(category);

		bookRepository.save(book);
		return "redirect:list";
	}

}
