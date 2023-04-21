package com.market.list.controllers;

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
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody Group group, @RequestParam("ownerId") Integer ownerId) {
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
    public ResponseEntity<ApiResponse<Group>> getGroupById(@RequestParam(value = "id", required = false) Integer id) {
        try {
            if (null == id) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

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
    public ResponseEntity<ApiResponse<Group>> removeGroup(@PathVariable("groupId") Integer groupId,
                                                          @RequestBody Integer ownerId) {
        try {
            Group group = groupService.findById(groupId);
            if (isNotOwner(group, ownerId)) {
                return apiHandler.handleForbidden();
            }
            groupService.delete(groupId);
            return apiHandler.handleSuccessDeletion(Constants.GROUP_DELETED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.NOT_FOUND);
        }

    }

    // ======== RELATED TO ACCOUNT MANAGEMENT ========

    @PutMapping("/transfer/{groupId}")
    public ResponseEntity<ApiResponse<Group>> transferOwnership(@PathVariable("groupId") Integer groupId,
                                                                @RequestParam("newOwnerId") Integer newOwnerId,
                                                                @RequestBody Integer ownerId) {
        try {
            Group group = groupService.findById(groupId);
            if (isNotOwner(group, ownerId)) {
                return apiHandler.handleForbidden();
            }

            groupService.transferOwnership(group, newOwnerId);
            return apiHandler.handleSuccessModification(group, Constants.NEW_OWNER);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(me.getMessage());
        }
    }

    @PutMapping("/addAccount/{groupId}")
    public ResponseEntity<ApiResponse<Group>> addAccountToGroup(@PathVariable("groupId") Integer groupId,
                                                                @RequestParam(value = "accountId", required = false) Integer accountId,
                                                                @RequestBody Integer ownerId) {
        try {
            Group group = groupService.findById(groupId);
            if (isNotOwner(group, ownerId)) {
                return apiHandler.handleForbidden();
            }

            if (null == accountId) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            groupService.addAccountToGroup(groupId, accountId);
            return apiHandler.handleSuccessAddition(Constants.ACCOUNT_ADDED_TO_GROUP);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.GROUP_HAS_ERRORS(me.getMessage()));
        }
    }

    @PutMapping("/removeAccount/{groupId}")
    public ResponseEntity<ApiResponse<Group>> removeAccountFromGroup(@PathVariable("groupId") Integer groupId,
                                                                     @RequestParam(value = "accountId", required = false) Integer accountId,
                                                                     @RequestBody Integer ownerId) {
        try {
            Group group = groupService.findById(groupId);
            if (isNotOwner(group, ownerId)) {
                return apiHandler.handleForbidden();
            }

            if (null == accountId) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            groupService.removeAccountFromGroup(groupId, accountId);
            return apiHandler.handleSuccessRemoving(Constants.ACCOUNT_REMOVED_FROM_GROUP);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.GROUP_HAS_ERRORS(me.getMessage()));
        }
    }

    // ======== RELATED TO LISTING MANAGEMENT ========

    @PutMapping("/addListing/{groupId}")
    public ResponseEntity<ApiResponse<Group>> addListingToGroup(@PathVariable("groupId") Integer groupId,
                                                                @RequestParam(value = "listingId", required = false) Integer listingId,
                                                                @RequestBody Integer ownerId) {
        try {
            Group group = groupService.findById(groupId);
            if (isNotOwner(group, ownerId)) {
                return apiHandler.handleForbidden();
            }

            if (null == listingId) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            groupService.addListingToGroup(groupId, listingId);
            return apiHandler.handleSuccessAddition(Constants.LISTING_ADDED_TO_GROUP);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.GROUP_HAS_ERRORS(me.getMessage()));
        }
    }

    @PutMapping("/removeListing/{groupId}")
    public ResponseEntity<ApiResponse<Group>> removeListingFromGroup(@PathVariable("groupId") Integer groupId,
                                                                     @RequestParam(value = "listingId", required = false) Integer listingId) {
        try {

            if (null == listingId) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            groupService.removeListingFromGroup(groupId, listingId);
            return apiHandler.handleSuccessRemoving(Constants.LISTING_REMOVED_FROM_GROUP);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, me.getMessage());
        }
    }


    private boolean isNotOwner(Group group, Integer accountId) {
        return !Objects.equals(group.getOwner().getId(), accountId);
    }
}
