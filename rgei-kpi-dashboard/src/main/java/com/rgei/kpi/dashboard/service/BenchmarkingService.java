package com.rgei.kpi.dashboard.service;

import com.rgei.kpi.dashboard.response.model.BenchmarkingReponse;
import com.rgei.kpi.dashboard.response.model.BenchmarkingRequest;

public interface BenchmarkingService {

	BenchmarkingReponse getBenchmarkingData(BenchmarkingRequest benchmarkingRequest);

}
