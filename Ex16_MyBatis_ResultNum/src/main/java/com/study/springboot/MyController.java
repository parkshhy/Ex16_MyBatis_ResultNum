package com.study.springboot;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.ISimpleBbsDao;

@Controller
public class MyController {
	
	//자동주입을 지정
	@Autowired
	//인터에시스 타입의 변수로 dao객체변수를 만듬
	ISimpleBbsDao dao;
	
	@RequestMapping("/")
	public String root() throws Exception{
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public String userlistPage(Model model) {
		//게시판의 리스트를 출력하기 위해 dao의 listDao() 메소드를 호출하여 리턴값을 model 변수에 담는다.
		model.addAttribute("list",dao.listDao());
		
		int nTotalCount = dao.articleCount();
		System.out.println("Count : " +nTotalCount);
		
		return "/list";
	}
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request,Model model) {
		String sId = request.getParameter("id");
		
		//Model을 매개변수로 전달받고 있다. 매개변수로 전달받은 model.addAttribute("key", "value"); 
		//메소드를 이용하여 view 전달할 데이터를 key, value쌍으로하여 전달할 수 있다.
		//개별 게시글을 보기 위해 dao의 viewDao메소드를 호출하여 리턴값을 model변수에 담는다.
		model.addAttribute("dto",dao.viewDao(sId));
		return "/view";
		
	}
	
	@RequestMapping("/writeForm")
	public String writeForm() {
		
		//입력 폼을 가진 JSP파일을 호출한다
		return "/writeForm";

	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request,Model model) {
		
		String sName = request.getParameter("writer");
		String sTitle = request.getParameter("title");
		String sContent = request.getParameter("content");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("item1",sName);
		map.put("item2",sTitle);
		map.put("item3",sContent);
		
		int nResult = dao.writeDao(map);
		System.out.println("Write : " + nResult);
		
		
		return "redirect:list";
	
	}
	
	@RequestMapping("/delete")	
	public String delete(HttpServletRequest request,Model model) {
	
		String sId = request.getParameter("id");
		int nResult = dao.deleteDao(sId);
		System.out.println("Delete : " + nResult);
		
		
		return "redirect:list";
		
	}
		
	}
	










