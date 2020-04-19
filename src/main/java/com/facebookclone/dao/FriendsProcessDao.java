package com.facebookclone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.facebookclone.model.FriendsProcess;


public interface FriendsProcessDao {
	
	@Query(value = "Insert into friends (user_one_id , user_two_id , user_action) values (?1 , ?2, ?3)" , nativeQuery = true)
	public FriendsProcess sendRequest(long user_one_id , long user_two_id , long currentUserId );
	
	@Query(value = "Select * from friends where user_one_id=?1 or user_two_id=?2 and status=1" , nativeQuery = true)
	public List<FriendsProcess> getFriends(long currentUserId);
	
	@Query(value = "Select * from friends where user_one_id=?1 or user_two_id=?1 and status=0 and user_action !=?1 and status=0" ,nativeQuery = true)
	public List<FriendsProcess> getToBeAcceptedList(long currentUserId);
	
	@Query(value = "Update friends set status=1 where user_one_id=?1 or user_two_id=?1 and user_action=?2" ,nativeQuery = true)
	public FriendsProcess acceptFriend(long friendId , long currentUserId);
	
	@Query(value = "Delete from friends where user_one_id=?1 or user_two_id=?1 and status =0 and user_action=?2" ,nativeQuery = true)
	public void deleteFriendRequest(long friendId , long currentUserId);

}
