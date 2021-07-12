package com.example.octopus.service.impl;

import com.example.octopus.dao.project.SubProjectMapper;
import com.example.octopus.entity.VOs.SubProjectDetailVO;
import com.example.octopus.entity.project.SubProject;
import com.example.octopus.service.SubProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/4 2:18 下午
 */
@Service
public class SubProjectServiceImpl implements SubProjectService {

	@Autowired
	SubProjectMapper subProjectMapper;

	@Override
	public List<SubProjectDetailVO> listSubProjectDetail(){
	    return subProjectMapper.listAllSubProjectDetail();
    }

	@Override
	public List<SubProject> listSubProjectsByModuleId(long projectModuleId) {
		return subProjectMapper.listSubProjectsByModuleId(projectModuleId);
	}

	@Override
	public SubProject getById(long id) {
		return subProjectMapper.getById(id);
	}

	@Override
	public int getSubProjectNumsByProjectId(long projectId) {
		return subProjectMapper.getSubProjectNumsByProjectId(projectId);
	}

	@Override
	public boolean insert(SubProject subProject) {
		return subProjectMapper.insert(subProject);
	}

	@Override
	public boolean deleteById(long id) {
		return subProjectMapper.deleteById(id);
	}
}
