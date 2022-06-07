package dev.richard.soulnotes.dtos;

public class ResourceCreationResponse {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResourceCreationResponse(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "ResourceCreationResponse{" +
                "id=" + id +
                '}';
    }
}
