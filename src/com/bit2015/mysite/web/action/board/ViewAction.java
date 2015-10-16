package com.bit2015.mysite.web.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2015.mysite.dao.BoardDao;
import com.bit2015.mysite.vo.BoardVo;
import com.bit2015.web.WebUtil;
import com.bit2015.web.action.Action;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String no=request.getParameter("no");
		BoardDao dao = new BoardDao();
		BoardVo vo = dao.find(Long.parseLong(no));
		dao.count(vo);
		request.setAttribute("vo", vo);
		WebUtil.forwarding(request, response, "/views/board/view.jsp");
	}

}
