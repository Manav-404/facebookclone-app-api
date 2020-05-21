package com.facebookclone.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.facebookclone.model.FriendsProcess;

@Transactional
public interface FriendsProcessDao extends JpaRepository<FriendsProcess, Long>{
	
//	//@Query(value = "Insert into friends (user_one_id , user_two_id , user_action) values (?1 , ?2, ?3)" , nativeQuery = true)
//	public FriendsProcess sendRequest(long user_one_id , long user_two_id , long currentUserId );
//	
	@Query(value = "Select * from friends_process where user_one_id=?1 or user_two_id=?1 and status=1" , nativeQuery = true)
	public List<FriendsProcess> getFriends(long currentUserId);
	
	@Query(value = "Select * from friends_process where user_one_id=?1 or user_two_id=?1 and user_action=?1 and status=0" , nativeQuery = true)
	public List<FriendsProcess> getPendingRequest(long currentUserId);
	
	@Query(value = "Select * from friends_process where (user_one_id=?1 or user_two_id=?1) and status=0 and user_action != ?1" ,nativeQuery = true)
	public List<FriendsProcess> getToBeAcceptedList(long currentUserId);
	
	@Modifying
	@Query(value = "Update friends_process set status=1 where user_one_id=?2 or user_two_id=?2 and user_action=?1" ,nativeQuery = true)
	public int acceptFriend(long friendId , long currentUserId);
	
	@Modifying
	@Query(value = "Delete from friends_process where user_one_id=?1 or user_two_id=?1 and status =0 and user_action=?2" ,nativeQuery = true)
	public int deleteFriendRequest(long friendId , long currentUserId);

}
