package br.com.frienddelivery.resteasyjackson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Path("/friends/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FriendsResource {

    private final Set<Friend> friends = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FriendsResource() {
        friends.add(new Friend("Augusto Ribeiro",
                                "augusto@email.com", 
                                "27098811725",
                                "UE0J61AU8",
                                "Rua Alcino Pereira Neto, 550. Ed.Trinidad Ap.306. Bairro Jardim Camburi. ES",
                                List.of("pizza","qualquer coisa com catupiri"),
                                List.of("tomate","cebola")));
        
    }

    @GET
    public Set<Friend> list() {
        return friends;
    }

    @POST
    public Set<Friend> add(Friend friend) {
        friends.add(friend);
        return friends;
    }

    @DELETE
    public Set<Friend> delete(Friend friend) {
        friends.removeIf(existingQuark -> existingQuark.name.contentEquals(friend.name));
        return friends;
    }

    public static class Friend {
        public String name;
        public String email;
        public String phone;
        public String slack_id;
        public String address;
        public List<String> preferences;
        public List<String> cant_be;
        public Friend() {
        }

        public Friend(String name, String email, String phone, String slack_id, String address,
                    List<String> preferences, List<String> cant_be) 
        {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.slack_id = slack_id;
            this.address = address;
            this.preferences = preferences;
            this.cant_be = cant_be;
        }
    }
}
