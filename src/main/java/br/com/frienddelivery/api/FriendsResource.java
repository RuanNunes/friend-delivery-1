package br.com.frienddelivery.api;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import br.com.frienddelivery.api.contract.FriendsApi;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author: Ruan Nunes
 * @version: 1.0 19/12/20
 */
public class FriendsResource implements FriendsApi {

    private final Set<Friend> friends = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @Override
    public Set<Friend> get() {
        return friends;
    }

    @Override
    public Friend findByName(String name) {
        return friends.stream().filter(friend -> friend.name.equals(name)).findFirst().orElse(null);
    }

    @Override
    public Response create(@Valid Friend entity) {
        if(Objects.nonNull(findByName(entity.name))){
            return Response.status(Status.CONFLICT).build();
        }
        entity.address = "teste";
        friends.add(entity);
        return Response.ok(entity).build();    
    }

    @Override
    public Response update(String name, @Valid Friend newEntity) {
        if(Objects.isNull(findByName(name))){
            throw new WebApplicationException("friend not found", Status.NOT_FOUND);
        }
        delete(name);
        create(newEntity);
        return Response.ok(newEntity).build();
    }

    @Override
    public Response delete(String name) {
        friends.removeIf(existingFriend ->
        existingFriend.name.contentEquals(name));
        return Response.ok(friends).build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Friend {
        public String name;
        public String email;
        public String phone;
        public String slack_id;
        public String address;
        public List<String> preferences;
        public List<String> cant_be;
    }
}