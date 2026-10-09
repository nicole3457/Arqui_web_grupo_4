package upc.edu.pe.banca_upcina.DTO;

import java.util.Set;

public class UsersDTO {
    private int id;
    private String username;
    private String password;
    private Set<Integer> authorityIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(Set<Integer> authorityIds) {
        this.authorityIds = authorityIds;
    }
}
