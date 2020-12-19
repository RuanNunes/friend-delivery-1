package br.com.frienddelivery.api.contract;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import br.com.frienddelivery.api.FriendsResource.Friend;

/**
 * @author: Ruan Nunes
 * @version: 1.0 19/12/20
 */
@Path("/api/friends/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Friends Api", description = "Operations on resources Friends.")
public interface FriendsApi {
  
    @GET
    @Operation(summary = "Get all Friends")
    public Set<Friend> get();

    @GET
    @Path("{name}")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "friend not found")
    @Operation(summary = "Find friend by name")
    public Friend findByName(@PathParam("name") String name);

    @POST
    @Transactional
    @APIResponse(responseCode = "201",
            description = "friend created",
            content = @Content(schema = @Schema(implementation = Friend.class)))
    @APIResponse(responseCode = "406", description = "Invalid data")
    @APIResponse(responseCode = "409", description = "friend already exists")
    @Operation(summary = "Create new friend")
    public Response create(@Valid Friend entity) ;

    @PUT
    @Path("{name}")
    @Transactional
    @APIResponse(responseCode = "200",
            description = "friend updated",
            content = @Content(schema = @Schema(implementation = Friend.class)))
    @APIResponse(responseCode = "404", description = "friend not found")
    @APIResponse(responseCode = "409", description = "friend already exists")
    @Operation(summary = "Edit friend by name")
    public Response update(@PathParam("name") String name, @Valid Friend newEntity) ;

    @DELETE
    @Transactional
    @Path("{name}")
    @APIResponse(responseCode = "204", description = "friend deleted")
    @APIResponse(responseCode = "404", description = "friend not found")
    @Operation(summary = "Delete friend by name")
    public Response delete(@PathParam("name") String name);

}
