package app.rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.ReportComponent;
import app.dto.PeakHoursDTO;
import app.dto.RevenueDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/report")
public class ReportController {

	@Autowired
	private ReportComponent component;

	@GET
	@Path("/revenue")
	@Produces(MediaType.APPLICATION_JSON)
	public RevenueDTO revenue(@QueryParam("from") String from, @QueryParam("to") String to) throws ServiceException {
		return component.getRevenue(Params.toDate("from", from), Params.toDate("to", to));
	}

	@GET
	@Path("/peakhours")
	@Produces(MediaType.APPLICATION_JSON)
	public PeakHoursDTO peakHours(@QueryParam("from") String from, @QueryParam("to") String to)
			throws ServiceException {
		return component.getPeakHours(Params.toDate("from", from), Params.toDate("to", to));
	}

}
