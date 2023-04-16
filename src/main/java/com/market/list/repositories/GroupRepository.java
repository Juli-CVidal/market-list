package com.market.list.repositories;

import com.market.list.enums.GroupType;
import com.market.list.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT g FROM Group g WHERE g.id = :id")
    Optional<Group> findById(@Param("id") Integer id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT g FROM Group g JOIN g.accounts a WHERE a.id = :accountId")
    List<Group> findGroupsByAccount(@Param("accountId") Integer id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT g FROM Group g WHERE  g.groupType = :type")
    List<Group> findGroupByType(@Param("type") GroupType type);
}
