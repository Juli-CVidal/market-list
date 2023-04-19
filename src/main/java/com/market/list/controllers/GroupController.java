package com.market.list.controllers;

import com.market.list.entities.Account;
import com.market.list.entities.ApiResponse;
import com.market.list.entities.Group;
import com.market.list.exception.MarketException;
import com.market.list.handler.ApiHandler;
import com.market.list.services.AccountService;
import com.market.list.services.GroupService;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    private final AccountService accountService;

    private final ApiHandler<Group> apiHandler;

    @Autowired
    public GroupController(GroupService groupService, AccountService accountService, ApiHandler<Group> apiHandler) {
        this.groupService = groupService;
        this.accountService = accountService;
        this.apiHandler = apiHandler;
    }


    // ======== CREATE ========

    @PostMapping
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody Group group, @RequestParam(value = "ownerId") Integer ownerId) {
        try {
            group.setOwner(accountService.findAccount(ownerId, null));
            groupService.create(group);
            return apiHandler.handleSuccessCreation(group, Constants.GROUP_CREATED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(group, Constants.GROUP_HAS_ERRORS(me.getMessage()));
        }
    }

    // ======== READ ========

    @GetMapping
    public ResponseEntity<ApiResponse<Group>> getGroupById(@RequestParam(value = "id") Integer id) {
        try {
            return apiHandler.handleSuccessGet(groupService.findById(id), Constants.GROUP_FOUND);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(Constants.NOT_FOUND);
        }
    }

    // ======== UPDATE ========

    @PutMapping
    public ResponseEntity<ApiResponse<Group>> updateGroup(@RequestBody Group group) {
        groupService.update(group);
        return apiHandler.handleSuccessModification(group, Constants.GROUP_MODIFIED);
    }

    // ======== DELETE ========

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Group>> removeGroup(@PathVariable("groupId") Integer groupId, @RequestParam(value = "ownerId") Integer ownerId){
        try{
            Group group = groupService.findById(groupId);
            if (!Objects.equals(group.getOwner().getId(), ownerId)){
                return apiHandler.handleForbiddenMessage(Constants.FORBIDDEN);
            }
            groupService.delete(groupId);
            return apiHandler.handleSuccessDeletion(Constants.GROUP_DELETED);
        } catch (MarketException me){
            return apiHandler.handleExceptionMessage(null,Constants.NOT_FOUND);
        }

    }

    // ======== RELATED TO ACCOUNT MANAGEMENT ========

    @PutMapping("/transfer/{groupId}")
    public ResponseEntity<ApiResponse<Group>> transferOwnership(@PathVariable("groupId") Integer groupId, @RequestParam(value = "newOwnerId") Integer newOwnerId) {
        try {
            Group group = groupService.findById(groupId);
            Account newOwner = accountService.findAccount(newOwnerId, null);
            group.setOwner(newOwner);
            group.getAccounts().remove(newOwner);
            return apiHandler.handleSuccessModification(group, Constants.NEW_OWNER);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(me.getMessage());
        }
    }
}
