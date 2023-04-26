package com.market.list.services;

import com.market.list.entities.Account;
import com.market.list.entities.Group;
import com.market.list.entities.Listing;
import com.market.list.exceptions.MarketException;
import com.market.list.repositories.GroupRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    private final AccountService accountService;
    private final ListingService listingService;

    @Autowired
    public GroupService(GroupRepository groupRepository, AccountService accountService, ListingService listingService) {
        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.listingService = listingService;
    }


    // ========= CREATE ========

    @Transactional
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    // ======== READ ========

    @Transactional(readOnly = true)
    public Group findById(String id) throws MarketException {
        return groupRepository.findById(id).orElseThrow(() -> new MarketException(Constants.NOT_FOUND));
    }


    // ======== UPDATE ========

    @Transactional
    public Group update(Group group) {
        return groupRepository.save(group);
    }

    // ======== DELETE ========

    @Transactional
    public void delete(String id) throws MarketException {
        Group group = this.findById(id);
        if (null != group && group.getAccounts().isEmpty()) {
            groupRepository.delete(group);
        }
    }

    // ======== RELATED TO LISTING AND ACCOUNT MANAGEMENT ========

    @Transactional(readOnly = true)
    public Listing findListingInGroup(String groupId, String listingId) throws MarketException {
        Group group = this.findById(groupId);
        Listing listing = listingService.findById(listingId);
        if (!listing.getGroup().equals(group)) {
            throw new MarketException(Constants.NOT_FOUND);
        }
        return listing;
    }

    @Transactional
    public Group addListingToGroup(String groupdId, String listingId) throws MarketException {
        Group group = this.findById(groupdId);
        Listing listing = listingService.findById(listingId);
        group.getListings().add(listing);
        listingService.setGroupToListing(listing, group);

        return this.update(group);
    }

    @Transactional
    public void removeListingFromGroup(String groupId, String listId) throws MarketException {
        Group group = this.findById(groupId);
        Listing listing = listingService.findById(listId);

        if (group.getListings().remove(listing)) {
            listingService.delete(listing.getId());
            update(group);
        }
    }

    @Transactional(readOnly = true)
    public Account findAccountInGroup(String groupId, String accountId) throws MarketException {
        Group group = this.findById(groupId);
        Account account = accountService.findAccount(accountId, null);
        if (!account.getGroups().contains(group)) {
            throw new MarketException(Constants.NOT_FOUND);
        }
        return account;
    }

    @Transactional
    public void transferOwnership(Group group, String newOwnerId) throws MarketException {
        Account account = accountService.findAccount(newOwnerId, null);
        group.setOwner(account);
        group.getAccounts().remove(account);
        update(group);
    }


    @Transactional
    public void addAccountToGroup(String groupId, String accountId) throws MarketException {
        Group group = this.findById(groupId);
        Account account = accountService.findAccount(accountId, null);
        accountService.addGroupToAccount(account, group);

        group.getAccounts().add(account);
        update(group);
    }

    @Transactional
    public void removeAccountFromGroup(String groupId, String accountId) throws MarketException {
        Group group = this.findById(groupId);
        Account account = accountService.findAccount(accountId, null);

        if (group.getAccounts().remove(account)) {
            accountService.removeGroupFromAccount(account, group);
        }
        update(group);
    }


}
