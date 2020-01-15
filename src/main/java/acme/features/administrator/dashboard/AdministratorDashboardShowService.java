
package acme.features.administrator.dashboard;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	private AdministratorDashboardRepository repository;


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalAnnouncements", "totalCompanyRecords", "totalInvestorRecords", "minRewardRequest", "maxRewardRequest", "averageRewardRequest", "sdRewardRequest", "minRewardOffer", "maxRewardOffer", "averageRewardOffer",
			"sdMinRewardOffer", "sdMaxRewardOffer", "companiesPerSector", "companySectors", "investorsPerSector", "investorSectors", "avgJobsPerEmployer", "avgApplicationsPerEmployer", "avgApplicationsPerWorker", "ratioOfPendingApplications",
			"ratioOfAcceptedApplications", "ratioOfRejectedApplications", "ratioOfDraftJobs", "ratioOfPublishedJobs", "lastFourWeeksPerDay", "pendingApplicationsPerDay", "acceptedApplicationsPerDay", "rejectedApplicationsPerDay");

	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {

		assert request != null;

		Dashboard result = new Dashboard();

		result.setTotalAnnouncements(this.repository.getTotalAnnouncement());
		result.setTotalCompanyRecords(this.repository.getTotalCompanyRecord());
		result.setTotalInvestorRecords(this.repository.getTotalInvestorRecord());

		result.setMinRewardRequest(this.repository.getMinRewardRequest());
		result.setMaxRewardRequest(this.repository.getMaxRewardRequest());

		Money a = new Money();
		Double amountA = this.repository.getAverageRewardRequestAmount();
		String currencyA = this.repository.getAverageRewardRequestCurrency();
		if (amountA != null && currencyA != null) {
			a.setAmount(this.repository.getAverageRewardRequestAmount());
			a.setCurrency(this.repository.getAverageRewardRequestCurrency());
		} else {
			a.setAmount(0.);
			a.setCurrency("€");
		}
		result.setAverageRewardRequest(a);

		Money c = new Money();
		Double amountC = this.repository.getSdRewardRequestAmount();
		String currencyC = this.repository.getAverageRewardRequestCurrency();
		if (amountC != null && currencyC != null) {
			c.setAmount(this.repository.getSdRewardRequestAmount());
			c.setCurrency(this.repository.getAverageRewardRequestCurrency());
		} else {
			c.setAmount(0.);
			c.setCurrency("€");
		}
		result.setSdRewardRequest(c);

		result.setMinRewardOffer(this.repository.getMinRewardOffer());
		result.setMaxRewardOffer(this.repository.getMaxRewardOffer());

		Money b = new Money();
		Double amountB = this.repository.getAverageRewardOfferAmount();
		String currencyB = this.repository.getAverageRewardOfferCurrency();
		if (amountB != null && currencyB != null) {
			b.setAmount(this.repository.getAverageRewardOfferAmount());
			b.setCurrency(this.repository.getAverageRewardOfferCurrency());
		} else {
			b.setAmount(0.);
			b.setCurrency("€");
		}
		result.setAverageRewardOffer(b);

		Money d = new Money();
		Double amountD = this.repository.getMinSdRewardRequestAmount();
		String currencyD = this.repository.getAverageRewardOfferCurrency();
		if (amountD != null && currencyD != null) {
			d.setAmount(this.repository.getMinSdRewardRequestAmount());
			d.setCurrency(this.repository.getAverageRewardOfferCurrency());
		} else {
			d.setAmount(0.);
			d.setCurrency("€");
		}
		result.setSdMinRewardOffer(d);

		Money e = new Money();
		Double amountE = this.repository.getMaxSdRewardRequestAmount();
		String currencyE = this.repository.getAverageRewardOfferCurrency();
		if (amountE != null && currencyE != null) {
			e.setAmount(this.repository.getMaxSdRewardRequestAmount());
			e.setCurrency(this.repository.getAverageRewardOfferCurrency());
		} else {
			e.setAmount(0.);
			e.setCurrency("€");
		}
		result.setSdMaxRewardOffer(e);

		result.setCompaniesPerSector(this.repository.getCompaniesPerSector());

		result.setCompanySectors(this.repository.getCompanySectors());

		result.setInvestorsPerSector(this.repository.getInvestorPerSector());

		result.setInvestorSectors(this.repository.getInvestorSectors());

		result.setAvgJobsPerEmployer(this.repository.averageNumberOfJobsPerEmployer());

		result.setAvgApplicationsPerEmployer(this.repository.averageNumberOfApplicationsPerEmployer());

		result.setAvgApplicationsPerWorker(this.repository.averageNumberOfApplicationsPerWorker());

		result.setRatioOfAcceptedApplications(this.repository.ratioOfAcceptedApplications());

		result.setRatioOfPendingApplications(this.repository.ratioOfPendingApplications());

		result.setRatioOfRejectedApplications(this.repository.ratioOfRejectedApplications());

		result.setRatioOfDraftJobs(this.repository.ratioOfDraftJobs());

		result.setRatioOfPublishedJobs(this.repository.ratioOfPublishedJobs());

		Calendar haceCuatroSemanas = new GregorianCalendar();
		haceCuatroSemanas.add(Calendar.WEEK_OF_YEAR, -4);
		Date[] secuenciaFechas = new Date[28];

		Calendar aux = haceCuatroSemanas;
		for (int i = 0; i < 28; i++) {
			aux.add(Calendar.DAY_OF_YEAR, 1);
			secuenciaFechas[i] = aux.getTime();
		}

		result.setLastFourWeeksPerDay(secuenciaFechas);

		Integer[] pending = new Integer[28];

		for (int i = 0; i < 28; i++) {
			pending[i] = this.repository.pendingApplicationsPerDay(secuenciaFechas[i]);
		}

		result.setPendingApplicationsPerDay(pending);

		Integer[] accepted = new Integer[28];

		for (int i = 0; i < 28; i++) {
			accepted[i] = this.repository.acceptedApplicationsPerDay(secuenciaFechas[i]);
		}

		result.setAcceptedApplicationsPerDay(accepted);

		Integer[] rejected = new Integer[28];

		for (int i = 0; i < 28; i++) {
			rejected[i] = this.repository.rejectedApplicationsPerDay(secuenciaFechas[i]);
		}

		result.setRejectedApplicationsPerDay(rejected);

		return result;
	}

}
