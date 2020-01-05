package com.gmoodle.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmoodle.models.entity.GroupMembers;

public interface IGroupMembersDao extends JpaRepository<GroupMembers, Long>{

}
