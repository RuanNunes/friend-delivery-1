package br.com.frienddelivery.api;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.frienddelivery.api.contract.FriendsApi;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import okhttp3.ResponseBody;

import java.io.ObjectInputFilter.FilterInfo;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;

/**
 * @author: Ruan Nunes
 * @version: 1.0 19/12/20
 */
public class FriendsResource implements FriendsApi {

    private final Set<Friend> friends = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FriendsResource() {
        friends.add(new Friend("Augusto Ribeiro", "augusto@email.com", "27098811725", "UE0J61AU8",
                "Rua Alcino Pereira Neto, 550. Ed.Trinidad Ap.306. Bairro Jardim Camburi. ES",
                List.of("pizza", "qualquer coisa com catupiri"), List.of("tomate", "cebola")));

    }

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
        friends.add(entity);
        return Response.ok(friends).build();    
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