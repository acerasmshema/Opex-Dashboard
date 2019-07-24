/*******************************************************************************
 * Copyright (c) 2019 Ace Resource Advisory Services Sdn. Bhd., Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Ace Resource Advisory Services Sdn. Bhd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Ace Resource Advisory Services Sdn. Bhd.
 * 
 * Ace Resource Advisory Services Sdn. Bhd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Ace Resource Advisory Services Sdn. Bhd. SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 ******************************************************************************/
package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.KpiAnnotationEntity;
import com.rgei.kpi.dashboard.repository.KpiAnnotationEntityRepository;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDateSerachRes;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDeleteRequest;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationRequest;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationResponse;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationSearchRequest;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.KpiAnnotationUtil;
import com.rgei.kpi.dashboard.util.Utility;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDateRangeSerach;



@Service
public class KpiAnnotationServiceImpl implements KpiAnnotationService{

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiAnnotationServiceImpl.class);
	
	@Resource
	private KpiAnnotationEntityRepository kpiAnnotationEntityRepository;
	@Resource
	private RgeUserEntityRepository rgeUserEntityRepository;


	@Override
	public void saveKpiAnnotationRequest(KpiAnnotationRequest kpiAnnotationRequest) {
		logger.info("Saving kpi annotation", kpiAnnotationRequest);
		boolean status=Boolean.TRUE;
		try {
			kpiAnnotationEntityRepository.saveAll(KpiAnnotationUtil.convertToEntity(kpiAnnotationRequest,status));
		}catch(Exception e) {
			logger.error("Exception in saving the annotation.", e);
		}
	}  

	@Override
	public List<KpiAnnotationResponse> getAnnotationDetails(KpiAnnotationSearchRequest kpiAnnotationSearchRequest) {
		logger.info("Get annotation details for search request", kpiAnnotationSearchRequest);
		List<KpiAnnotationResponse> response = null;
		try {
			List<KpiAnnotationEntity>  kpiAnnotationEntities  = kpiAnnotationEntityRepository.getAnnotationDetails(CommonFunction.covertToInteger(kpiAnnotationSearchRequest.getKpiId()),
					CommonFunction.covertToInteger(kpiAnnotationSearchRequest.getMillId()), CommonFunction.covertToInteger(kpiAnnotationSearchRequest.getBuTypeId()),
					(Utility.stringToDateConvertor(kpiAnnotationSearchRequest.getAnnotationDate(), DashboardConstant.FORMAT)));
			logger.info("KpiAnnotion entity data", kpiAnnotationEntities);
			response = prePareSearchResponse(kpiAnnotationEntities);
		}catch(Exception e) {
			logger.error("Exception in retrieving annotations details.", e);
		}
		return response;
	}

	public  List<KpiAnnotationResponse> prePareSearchResponse(List<KpiAnnotationEntity> kpiAnnotationEntities) {
		List<KpiAnnotationResponse> responsList = new ArrayList<>();
		KpiAnnotationResponse  annotationResponse = null;
		if(kpiAnnotationEntities != null && !kpiAnnotationEntities.isEmpty()) {
			for(KpiAnnotationEntity entity:kpiAnnotationEntities) {
			annotationResponse = new KpiAnnotationResponse();
			annotationResponse.setAnnotationId(entity.getKpiAnnotationId());
			annotationResponse.setUserId(getUserDetails(entity.getCreatedBy().trim()));
			annotationResponse.setProcessLines(entity.getProcessLines());
			annotationResponse.setAnnotationDate(Utility.dateToStringConvertor(entity.getAnnotationDate(), DashboardConstant.FORMAT));
			annotationResponse.setDescription(entity.getDescription());
			responsList.add(annotationResponse);
			}
		}
		logger.info("Kpi annotation search response", responsList);
		return responsList;
	}
	private String getUserDetails(String  loginId) {
			return rgeUserEntityRepository.findByLoginId(loginId).getFirstName();
	}

	@Override
	public KpiAnnotationDateSerachRes kpiAnnotationDateRangeSerach(
			KpiAnnotationDateRangeSerach kpiAnnotationDateRangeSerach) {
		logger.info("Kpi annotation date range search", kpiAnnotationDateRangeSerach);
		List<KpiAnnotationEntity>  annotationEntity = kpiAnnotationEntityRepository.getAnnotationByDateRange(
				CommonFunction.covertToInteger(kpiAnnotationDateRangeSerach.getKpiId()),
				CommonFunction.covertToInteger(kpiAnnotationDateRangeSerach.getMillId()),
				CommonFunction.covertToInteger(kpiAnnotationDateRangeSerach.getBuTypeId()),
				Utility.stringToDateConvertor(kpiAnnotationDateRangeSerach.getStartDate(), DashboardConstant.FORMAT),
		        Utility.stringToDateConvertor(kpiAnnotationDateRangeSerach.getEndDate(), DashboardConstant.FORMAT));
		KpiAnnotationDateSerachRes response = new KpiAnnotationDateSerachRes();
		List<String> dates = new ArrayList<>();
		if(annotationEntity != null && !annotationEntity.isEmpty()) {
			for(KpiAnnotationEntity entity:annotationEntity) {
				dates.add(Utility.dateToStringConvertor(entity.getAnnotationDate(),DashboardConstant.FORMAT));
				response.setAnnotationDates(dates);
			}
		}
		return response;
	}

	@Override
	public void deleteAnnotation(List<KpiAnnotationDeleteRequest> kpiAnnotationDeleteRequest) {
		logger.info("deleting kpi annotation", kpiAnnotationDeleteRequest);
		KpiAnnotationEntity kpiAnnotationEntity = null;
		for (KpiAnnotationDeleteRequest annotationDeleteRequest : kpiAnnotationDeleteRequest) {
			try {
				kpiAnnotationEntity = kpiAnnotationEntityRepository
						.findById(Integer.parseInt(annotationDeleteRequest.getAnnotationId())).orElse(null);
				;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (kpiAnnotationEntity != null) {
				if (kpiAnnotationEntity.getCreatedBy().equals(annotationDeleteRequest.getUserId())) {
					kpiAnnotationEntity.setActive(Boolean.FALSE);
					kpiAnnotationEntity.setUpdatedDate(new Date());
					try {
						kpiAnnotationEntityRepository.save(kpiAnnotationEntity);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
