package com.nerdysoft.taskmanager.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.nerdysoft.taskmanager.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);
        Map<String, String> userFields = getUserFields(jsonNode.fields());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authenticationRoles = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        User user = new User();
        user.setId(Integer.valueOf(userFields.get("id")));
        user.setUserName(userFields.get("userName"));
        user.setUserLastName(userFields.get("userLastName"));
        user.setPassword(userFields.get("password"));
        user.setEmail(userFields.get("email"));
        if (authenticationRoles.contains("ROLE_ADMIN")) {
            user.setEnabled(Boolean.valueOf(userFields.get("enabled")));
            user.setAdmin(Boolean.valueOf(userFields.get("admin")));
        } else {
            user.setEnabled(null);
            user.setAdmin(null);
        }
        return user;
    }

    private Map<String, String> getUserFields(Iterator<Map.Entry<String, JsonNode>> jsonNodes) {
        Map<String, String> userFields = new HashMap<>();
        while (jsonNodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = jsonNodes.next();
            System.out.println("key --> " + entry.getKey() + " value-->" + entry.getValue());
            switch (entry.getKey()) {
                case "id": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
                case "userName": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
                case "userLastName": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
                case "password": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
                case "email": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
                case "enabled": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
                case "admin": {
                    userFields.put(entry.getKey(), entry.getValue().asText());
                }
            }
        }
        return userFields;
    }
}