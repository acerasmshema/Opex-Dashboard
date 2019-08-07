package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.repository.ProcessLineRepository;
import com.rgei.kpi.dashboard.response.model.ProcessLineDetailsResponse;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.ProcessLineUtility;

@Service
public class ProcessLineServiceImpl implements ProcessLineService{
	
	@Resource
	ProcessLineRepository processLineRepository;

	@Override
	public List<ProcessLineDetailsResponse> getProcessLines(String millId) {
		if(Objects.nonNull(millId)) {
			List<ProcessLineEntity> processLine = processLineRepository.getPrcessLineByLocation(CommonFunction.covertToInteger(millId), Boolean.TRUE);
			return ProcessLineUtility.generateResponse(processLine);
		}
		return null;
	}

}
