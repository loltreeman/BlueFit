package app.rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.PlanComponent;
import app.dto.DeleteDTO;
import app.dto.PlanDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/plan")
public class MembershipPlanController {

	@Autowired
	private PlanComponent component;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public PlanDTO add(@FormParam("name") String name, @FormParam("price") String price,
			@FormParam("durationDays") String durationDays,
			@FormParam("eligibleAffiliation") String eligibleAffiliation) throws ServiceException {
		return component.addPlan(Params.required("name", name), Params.toMoney("price", price),
				Params.toInt("durationDays", durationDays), Params.required("eligibleAffiliation", eligibleAffiliation));
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public PlanDTO get(@QueryParam("pk") String pk) throws ServiceException {
		return component.getPlan(Params.toLong("pk", pk));
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PlanDTO> all() {
		return component.getAllPlans();
	}

	@GET
	@Path("/available")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PlanDTO> available(@QueryParam("memberPk") String memberPk) throws ServiceException {
		return component.getAvailablePlans(Params.toLong("memberPk", memberPk));
	}

	@POST
	@Path("/updateprice")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public PlanDTO updatePrice(@FormParam("pk") String pk, @FormParam("price") String price) throws ServiceException {
		return component.updatePrice(Params.toLong("pk", pk), Params.toMoney("price", price));
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteDTO delete(@FormParam("pk") String pk) throws ServiceException {
		return component.deletePlan(Params.toLong("pk", pk));
	}

}
