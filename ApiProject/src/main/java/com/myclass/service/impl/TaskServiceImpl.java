package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myclass.constants.StatusConstants;
import com.myclass.dto.TaskDto;
import com.myclass.dto.UserDetailDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Status;
import com.myclass.entity.Task;
import com.myclass.repository.StatusRepository;
import com.myclass.repository.TaskRepository;
import com.myclass.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	private TaskRepository taskRepository;
	private StatusRepository statusRepository;

	public TaskServiceImpl(TaskRepository taskRepository, StatusRepository statusRepository) {
		super();
		this.taskRepository = taskRepository;
		this.statusRepository = statusRepository;
	}

	//Lấy tất cả công việc cho admin
	@Override
	public List<TaskDto> getAll() {
		try {
			List<TaskDto> dtos = new ArrayList<TaskDto>();
			List<Task> tasks = taskRepository.findAll();
			if (!tasks.isEmpty()) {
				for (Task task : tasks) {
					TaskDto dto = new TaskDto(task.getId(), task.getName(), task.getStartDate(), task.getEndDate(),
							task.getStatusId(), task.getUserId(), task.getProjectId(), task.getManagerId(),
							task.getStatus().getName(), task.getUser().getFullName(), task.getProject().getName());
					dtos.add(dto);
				}
				return dtos;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int add(TaskDto dto) {
		try {
			Task task = new Task();
			Status status = statusRepository.findByName(StatusConstants.UNFULFILLED_STATUS);
			task.setName(dto.getName());
			task.setStartDate(dto.getStartDate());
			task.setEndDate(dto.getStartDate());
			task.setProjectId(dto.getProjectId());
			task.setStatusId(status.getId());
			task.setUserId(dto.getUserId());
			task.setManagerId(dto.getManagerId());
			taskRepository.save(task);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int deleteById(int id) {
		try {
			taskRepository.deleteById(id);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int edit(int id, TaskDto dto) {
		try {
			Task task = taskRepository.findById(id).get();
			task.setId(dto.getId());
			task.setName(dto.getName());
			task.setStartDate(dto.getStartDate());
			task.setEndDate(dto.getEndDate());
			task.setProjectId(dto.getProjectId());
			task.setUserId(dto.getUserId());
			task.setStatusId(dto.getStatusId());
			taskRepository.save(task);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public TaskDto findById(int id) {
		try {
			Task task = taskRepository.findById(id).get();
			if (task != null) {
				return new TaskDto(task.getId(), task.getName(), task.getStartDate(), task.getEndDate(),
						task.getStatusId(), task.getUserId(), task.getProjectId(), task.getManagerId(),
						task.getStatus().getName(), task.getUser().getFullName(), task.getProject().getName());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//Lấy tất cả công việc cho môt manager
	@Override
	public List<TaskDto> getAllOfManager(int id) {
		try {
			List<TaskDto> dtos = new ArrayList<TaskDto>();
			List<Task> tasks = taskRepository.findByManagerId(id);
			if (!tasks.isEmpty()) {
				for (Task task : tasks) {
					TaskDto dto = new TaskDto(task.getId(), task.getName(), task.getStartDate(), task.getEndDate(),
							task.getStatusId(), task.getUserId(), task.getProjectId(), task.getManagerId(),
							task.getStatus().getName(), task.getUser().getFullName(), task.getProject().getName());
					dtos.add(dto);
				}
				return dtos;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//Lấy tất cả công việc cho môt nhân viên
	@Override
	public List<TaskDto> getAllOfMember(int id) {
		try {
			List<TaskDto> dtos = new ArrayList<TaskDto>();
			List<Task> tasks = taskRepository.findByUserId(id);
			if (!tasks.isEmpty()) {
				for (Task task : tasks) {
					TaskDto dto = new TaskDto(task.getId(), task.getName(), task.getStartDate(), task.getEndDate(),
							task.getStatusId(), task.getUserId(), task.getProjectId(), task.getManagerId(),
							task.getStatus().getName(), task.getUser().getFullName(), task.getProject().getName());
					dtos.add(dto);
				}
				return dtos;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public int updateStatusTask(int statusId, int taskId) {
		try {			
			if(taskRepository.updateStatusTask(statusId, taskId) == 1) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	//Lấy công việc phân loại theo status của một project 
	@Override
	public HashMap<String, Integer> countStatusTaskInProject(int projectId) {
		try {
			int processingTask = taskRepository.countByProjectIdAndStatusName(projectId, StatusConstants.PROCESSING_STATUS);
			int completedTask = taskRepository.countByProjectIdAndStatusName(projectId, StatusConstants.COMPLETED_STATUS);
			int totalTaskInProject = taskRepository.countByProjectId(projectId);
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			result.put(StatusConstants.PROCESSING_STATUS, Math.round(processingTask*100/totalTaskInProject));
			result.put(StatusConstants.COMPLETED_STATUS, Math.round(completedTask*100/totalTaskInProject));
			result.put(StatusConstants.UNFULFILLED_STATUS,100 -  Math.round(processingTask*100/totalTaskInProject) - Math.round(completedTask*100/totalTaskInProject));
			return result;		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//Lấy công việc phân loại theo status và nhân viên của một project 
	@Override
	public List<UserDetailDto> getAllTaskInProject(int id) {
		try {
			List<UserDto> users = taskRepository.findUserInProject(id);
			List<UserDetailDto> userDetailDtos = new ArrayList<UserDetailDto>();
			//Lấy tất cả công việc của một project cho một nhân viên
			for (UserDto user : users) {
				List<TaskDto> processingTasksDtos = new ArrayList<TaskDto>();
				List<TaskDto> completedTasksDtos = new ArrayList<TaskDto>();
				List<TaskDto> unfulfilledTasksDtos = new ArrayList<TaskDto>();
				//Lấy số công việc đang xử lí
				List<Task> processingTasks = taskRepository.findTaskOfUserInProject(id, StatusConstants.PROCESSING_STATUS, user.getId());
				for (Task task : processingTasks) {
					TaskDto dto = new TaskDto();
					dto.setId(task.getId());
					dto.setName(task.getName());
					dto.setStartDate(task.getStartDate());
					dto.setEndDate(task.getEndDate());
					processingTasksDtos.add(dto);
				}
				//Lấy số công việc hoàn thành
				List<Task> completedTasks = taskRepository.findTaskOfUserInProject(id, StatusConstants.COMPLETED_STATUS, user.getId());
				for (Task task : completedTasks) {
					TaskDto dto = new TaskDto();
					dto.setId(task.getId());
					dto.setName(task.getName());
					dto.setStartDate(task.getStartDate());
					dto.setEndDate(task.getEndDate());
					completedTasksDtos.add(dto);
				}
				//Lấy số công việc chưa hoàn thành
				List<Task> unfulfilledTasks = taskRepository.findTaskOfUserInProject(id, StatusConstants.UNFULFILLED_STATUS, user.getId());
				for (Task task : unfulfilledTasks) {
					TaskDto dto = new TaskDto();
					dto.setId(task.getId());
					dto.setName(task.getName());
					dto.setStartDate(task.getStartDate());
					dto.setEndDate(task.getEndDate());
					unfulfilledTasksDtos.add(dto);
				}
				UserDetailDto dto = new UserDetailDto(user.getId(), user.getFullName(), user.getAvatar(), unfulfilledTasksDtos, completedTasksDtos, processingTasksDtos);
				userDetailDtos.add(dto);
			}
			return userDetailDtos;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Đếm tất cả số công việc phân loại theo status của User
	@Override
	public HashMap<String, Integer> countStatusTaskInUser(int userId) {
		try {
			int processingTask = taskRepository.countByUserIdAndStatusName(userId, StatusConstants.PROCESSING_STATUS);
			int completedTask = taskRepository.countByUserIdAndStatusName(userId, StatusConstants.COMPLETED_STATUS);
			int totalTaskInProject = taskRepository.countByUserId(userId);
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			result.put(StatusConstants.PROCESSING_STATUS, Math.round(processingTask*100/totalTaskInProject));
			result.put(StatusConstants.COMPLETED_STATUS, Math.round(completedTask*100/totalTaskInProject));
			result.put(StatusConstants.UNFULFILLED_STATUS, 100 - Math.round(processingTask*100/totalTaskInProject) - Math.round(completedTask*100/totalTaskInProject));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//Đếm tất cả số công việc phân loại theo status của Admin
	@Override
	public HashMap<String, Integer> countStatusTask() {
		try {
			int processingTask = taskRepository.countByStatusName(StatusConstants.PROCESSING_STATUS);
			int completedTask = taskRepository.countByStatusName(StatusConstants.COMPLETED_STATUS);
			int totalTaskInProject = taskRepository.findAll().size();
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			result.put(StatusConstants.PROCESSING_STATUS, Math.round(processingTask*100/totalTaskInProject));
			result.put(StatusConstants.COMPLETED_STATUS, Math.round(completedTask*100/totalTaskInProject));
			result.put(StatusConstants.UNFULFILLED_STATUS,100 - Math.round(processingTask*100/totalTaskInProject) - Math.round(completedTask*100/totalTaskInProject));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//Đếm tất cả số công việc phân loại theo status của Manager
	@Override
	public HashMap<String, Integer> countStatusTaskInManager(int managerId) {
		try {
			int processingTask = taskRepository.countByManagerIdAndStatusName(managerId,StatusConstants.PROCESSING_STATUS);
			int completedTask = taskRepository.countByManagerIdAndStatusName(managerId,StatusConstants.COMPLETED_STATUS);
			int totalTaskInProject = taskRepository.findByManagerId(managerId).size();
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			result.put(StatusConstants.PROCESSING_STATUS, Math.round(processingTask*100/totalTaskInProject));
			result.put(StatusConstants.COMPLETED_STATUS, Math.round(completedTask*100/totalTaskInProject));
			result.put(StatusConstants.UNFULFILLED_STATUS,100 - Math.round(processingTask*100/totalTaskInProject) - Math.round(completedTask*100/totalTaskInProject));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
