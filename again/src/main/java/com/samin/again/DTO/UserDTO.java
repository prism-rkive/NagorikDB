package com.samin.again.DTO;

public class UserDTO {
    private Long nid;
    private String name;  // Include name

    // Constructor
    public UserDTO(Long nid, String name) {
        this.nid = nid;
        this.name = name;
    }

    // Getters and Setters
    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
