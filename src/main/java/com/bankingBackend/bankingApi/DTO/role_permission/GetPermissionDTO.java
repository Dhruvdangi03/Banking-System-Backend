package com.bankingBackend.bankingApi.DTO.role_permission;

public class GetPermissionDTO {
    private String api;

    public GetPermissionDTO() {
    }

    public GetPermissionDTO(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
