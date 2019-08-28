import { environment } from "../environments/environment";

export class AppConstants {

    static BASE_API_URL_v1 = environment.API_URL + '/restCall/v1';

    static BASE_API_URL_v2 = environment.API_URL + '/restCall/v2';

    static apiURLs = {
        ALL_MILLS_URL: AppConstants.BASE_API_URL_v1 + '/location/all_mills',
        ALL_BU_TYPE_URL: AppConstants.BASE_API_URL_v1 + '/location/get_all_bu_type',
        PROCESS_LINES_URL: AppConstants.BASE_API_URL_v1 + '/yesterday/total_process_line',
        ANNUAL_TARGET_URL: AppConstants.BASE_API_URL_v1 + '/yesterday/ytd_process_line',
        PROJECTED_DATA_URL: AppConstants.BASE_API_URL_v1 + '/process_line/projected_target_details',
        YTD_ALL_PROCESS_LINES_URL: AppConstants.BASE_API_URL_v1 + '/yesterday/all_process_lines',
        KPI_PULP_AREA_URL: AppConstants.BASE_API_URL_v1 + '/date_range/daily_kpi_pulp_area',
        KPI_PULP_URL: AppConstants.BASE_API_URL_v1 + '/date_range/daily_kpi_pulp',
        YTD_PROCESS_LINE_URL: AppConstants.BASE_API_URL_v2 + '/yesterday/ytd_process_line',
        YTD_PROCESS_LINE_TARGET_URL: AppConstants.BASE_API_URL_v1 + '/yesterday/ytd_target_process_line',
        DR_ALL_PROCESS_LINES_URL: AppConstants.BASE_API_URL_v1 + '/date_range/all_process_lines',
        ALL_PROCESS_LINES_TARGET_URL: AppConstants.BASE_API_URL_v1 + '/date_range/all_process_lines_target',
        DR_SELECTED_PROCESS_LINES_URL: AppConstants.BASE_API_URL_v1 + '/date_range/selected_process_lines',
        DOWNLOAD_DATA_GRID_URL: AppConstants.BASE_API_URL_v1 + '/date_range/selected_process_lines_grid_data',
        LOGIN_URL: AppConstants.BASE_API_URL_v1 + '/user_info/login',
        PROCESS_LINES_BY_KPI_ID_URL: AppConstants.BASE_API_URL_v1 + '/kpi_category/get_process_lines',
        LOGOUT_URL: AppConstants.BASE_API_URL_v1 + '/user_info/logout',
        KPI_TYPE_URL: AppConstants.BASE_API_URL_v1 + '/kpi_category/get_kpi_type',
        CONSUMPTION_API_URL: AppConstants.BASE_API_URL_v1 + '/kpi_category/selected_kpi_process_lines',
        CONSUMPTION_GRID_API_URL: AppConstants.BASE_API_URL_v1 + '/kpi_category/yesterday_all_process_lines_data',

        CONSUMPTION_API_GRID_URL: AppConstants.BASE_API_URL_v1 + '/kpi_category/selected_kpi_grid_data',
        SAVE_ANNOTATION_URL: AppConstants.BASE_API_URL_v1 + '/kpi_annotation/save_annotation',
        FIND_ANNOTATION_URL: AppConstants.BASE_API_URL_v1 + '/kpi_annotation/get_annotation',

        GET_MAINTENANCE_DAYS_URL: AppConstants.BASE_API_URL_v1 + '/maintenance_days/get_maintenance_days',
        SAVE_MAINTENANCE_DAYS_URL: AppConstants.BASE_API_URL_v1 + '/maintenance_days/save_maintenance_days',
        DELETE_MAINTENANCE_DAYS_URL: AppConstants.BASE_API_URL_v1 + '/maintenance_days/delete_maintenance_days',
        SAVE_TARGET_DAYS_URL: AppConstants.BASE_API_URL_v1 + '/kpi_category/save_target_days',
        ANNOTATION_DATES_URL: AppConstants.BASE_API_URL_v1 + '/kpi_annotation/get_annotation_date',
        ALL_PROCESS_LINES_URL: AppConstants.BASE_API_URL_v1 + '/process_line/get_all_process_lines',
        BENCHMARK_FILTER_URL: AppConstants.BASE_API_URL_v1 + '/benchmarking/get_selected_data',
    };
}