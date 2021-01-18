package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.UserDto;
import com.myclass.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	public List<Task> findByUserId(int id);

	public List<Task> findByManagerId(int id);

	@Modifying(flushAutomatically = true)
	@Query("update Task t set t.statusId= :statusId WHERE t.id= :taskId")
	public int updateStatusTask(@Param("statusId") int statusId, @Param("taskId") int taskId);

	//Đếm tất cả công việc của một project
	public int countByProjectId(int projectId);

	//Tìm tất cả công việc của một user
	public int countByUserId(int userId);
	
	//Đếm công việc theo project id và status name
	@Query("SELECT count(t) FROM Task t JOIN t.status s WHERE t.projectId= :projectId AND s.name= :statusName")
	public int countByProjectIdAndStatusName(@Param("projectId") int projectId, @Param("statusName") String statusName);
	
	//Đếm công việc theo id của nhân viên và status name
	@Query("SELECT count(t) FROM Task t JOIN t.status s WHERE t.userId= :userId AND s.name= :statusName")
	public int countByUserIdAndStatusName(@Param("userId")int userId, @Param("statusName") String statusName);
	
	//Đếm công việc theo id của manager và status name
	@Query("SELECT count(t) FROM Task t JOIN t.status s WHERE t.managerId= :managerId AND s.name= :statusName")
	public int countByManagerIdAndStatusName(@Param("managerId") int managerId, @Param("statusName") String statusName);
	
	//Đếm công việc theo id của admin và status name
	@Query("SELECT count(t) FROM Task t JOIN t.status s WHERE s.name= :statusName")
	public int countByStatusName(@Param("statusName")String statusName);
	
	//Lấy danh sách công việc của 1 nhân viên theo status name thuộc 1 project
	@Query("FROM Task t JOIN t.status s WHERE t.projectId= :projectId AND s.name= :statusName AND t.userId= :userId")
	public List<Task> findTaskOfUserInProject(@Param("projectId") int projectId, @Param("statusName") String statusName, @Param("userId") int userId);
	
	//Lấy danh sách nhân viên thuộc 1 project
	@Query("SELECT new com.myclass.dto.UserDto(t.userId, u.fullName, u.avatar)FROM Task t JOIN t.user u WHERE t.projectId= :projectId GROUP BY t.user")
	public List<UserDto> findUserInProject(@Param("projectId") int projectId);
}
