package com.bit2015.mysite.web.action.guestbook;



import com.bit2015.mysite.web.action.main.IndexAction;
import com.bit2015.mysite.web.action.member.JoinAction;
import com.bit2015.mysite.web.action.member.JoinFormAction;
import com.bit2015.mysite.web.action.member.JoinSuccessAction;
import com.bit2015.mysite.web.action.member.LoginAction;
import com.bit2015.mysite.web.action.member.LoginFormAction;
import com.bit2015.mysite.web.action.member.LogoutAction;
import com.bit2015.web.action.Action;
import com.bit2015.web.action.ActionFactory;

public class GuestBookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		// TODO Auto-generated method stub
		Action action = null;
		if ("insert".equals(actionName)) {
			action = new InsertAction();
		}else if ("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		}else{
			action = new ListAction();
		}
		return action;
	}

}
