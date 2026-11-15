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

import app.components.MemberComponent;
import app.dto.DeleteDTO;
import app.dto.MemberDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/member")
public class MemberController {

	@Autowired
	private MemberComponent component;

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public MemberDTO add(@FormParam("name") String name, @FormParam("schoolId") String schoolId,
			@FormParam("cellphoneNumber") String cellphoneNumber, @FormParam("email") String email,
			@FormParam("affiliation") String affiliation) throws ServiceException {
		return component.addMember(Params.required("name", name), Params.required("schoolId", schoolId),
				Params.required("cellphoneNumber", cellphoneNumber), Params.optional(email),
				Params.required("affiliation", affiliation));
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public MemberDTO get(@QueryParam("pk") String pk) throws ServiceException {
		return component.getMember(Params.toLong("pk", pk));
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MemberDTO> all() {
		return component.getAllMembers();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MemberDTO> search(@QueryParam("name") String name) throws ServiceException {
		return component.searchMembers(Params.required("name", name));
	}

	@POST
	@Path("/updatephone")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public MemberDTO updatePhone(@FormParam("pk") String pk, @FormParam("cellphoneNumber") String cellphoneNumber)
			throws ServiceException {
		return component.updatePhone(Params.toLong("pk", pk), Params.required("cellphoneNumber", cellphoneNumber));
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteDTO delete(@FormParam("pk") String pk) throws ServiceException {
		return component.deleteMember(Params.toLong("pk", pk));
	}

}
