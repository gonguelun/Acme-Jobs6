
package acme.features.employer.application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.jobs.Application;
import acme.entities.roles.Employer;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/employer/application/")
public class EmployerApplicationController extends AbstractController<Employer, Application> {

	@Autowired
	private EmployerApplicationListMineService	listMineService;

	@Autowired
	private EmployerApplicationShowService		showService;

	@Autowired
	private EmployerApplicationRejectService	rejectService;

	@Autowired
	private EmployerApplicationAcceptService	acceptService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addCustomCommand(CustomCommand.UPDATE_REJECT, BasicCommand.UPDATE, this.rejectService);
		super.addCustomCommand(CustomCommand.UPDATE_ACCEPT, BasicCommand.UPDATE, this.acceptService);
	}

}
